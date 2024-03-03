package com.maciejpaja.salesmanapp.ui

import com.maciejpaja.salesmanapp.data.SalesmanRepository
import com.maciejpaja.salesmanapp.models.Salesman
import com.maciejpaja.salesmanapp.utils.SchedulersProvider
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


class MainViewModelTest {


    private lateinit var salesmanRepository: SalesmanRepository

    private lateinit var schedulersProvider: SchedulersProvider
    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        schedulersProvider = mock {
            on { mainScheduler } doReturn Schedulers.trampoline()
            on { ioScheduler } doReturn Schedulers.trampoline()
        }
        salesmanRepository = mock<SalesmanRepository>()

        viewModel = MainViewModel(salesmanRepository, schedulersProvider)

    }

    @Test
    fun initializedTest() {
        val expectedState: List<Salesman> = listOf(Salesman("Maciej", areas = listOf("7634")))
        whenever(salesmanRepository.downloadSalesmenData()).thenReturn(Single.just(expectedState))

        assertEquals(expectedState, viewModel.salesmenState)
    }
}