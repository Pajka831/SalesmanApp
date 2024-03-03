package com.maciejpaja.salesmanapp.ui


import androidx.lifecycle.ViewModel
import com.maciejpaja.salesmanapp.data.SalesmanRepository
import com.maciejpaja.salesmanapp.models.Salesman
import com.maciejpaja.salesmanapp.utils.SchedulersProvider
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.TimeUnit

class MainViewModel(
    private val salesmanRepository: SalesmanRepository,
    private val schedulersProvider: SchedulersProvider
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val inputSubject: PublishSubject<String> = PublishSubject.create()

    private val _salesmenState = MutableStateFlow<List<Salesman>>(emptyList())
    val salesmenState = _salesmenState.asStateFlow()

    private var salesmenList: List<Salesman> = listOf()

    private val _expandedCardIdsList = MutableStateFlow<List<String>>(emptyList())
    val expandedCardIdsList: StateFlow<List<String>> get() = _expandedCardIdsList

    init {
        downloadSalesmenData()
        observeTextChange()
    }

    private fun downloadSalesmenData() {
        compositeDisposable.add(salesmanRepository.downloadSalesmenData()
            .subscribeOn(schedulersProvider.ioScheduler)
            .observeOn(schedulersProvider.mainScheduler)
            .subscribe(
                {
                    salesmenList = it
                    _salesmenState.value = salesmenList
                }, {}
            ))
    }

    fun onSearchInputChange(input: String) {
        inputSubject.onNext(input)
    }

    fun onExpandItemClick(salesmanName: String) {
        val expandedList = _expandedCardIdsList.value.toMutableList()
        if (expandedList.contains(salesmanName)) {
            expandedList.remove(salesmanName)
        } else {
            expandedList.add(salesmanName)
        }
        _expandedCardIdsList.value = expandedList
    }

    private fun observeTextChange() {
        compositeDisposable.add(inputSubject
            .debounce(1, TimeUnit.SECONDS)
            .flatMapSingle {
                Single.fromCallable { filterSalesmenList(it) }
            }
            .subscribeOn(schedulersProvider.ioScheduler)
            .observeOn(schedulersProvider.mainScheduler)
            .subscribe {
                _salesmenState.value = it
            })
    }

    private fun filterSalesmenList(input: String): List<Salesman> {
        return salesmenList.filter { salesman ->
            salesman.areas.any {
                it.contains(input)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}