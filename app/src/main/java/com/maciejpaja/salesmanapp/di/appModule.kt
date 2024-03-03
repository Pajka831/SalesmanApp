package com.maciejpaja.salesmanapp.di

import com.maciejpaja.salesmanapp.data.SalesmanRepository
import com.maciejpaja.salesmanapp.data.SalesmanRepositoryImpl
import com.maciejpaja.salesmanapp.ui.MainViewModel
import com.maciejpaja.salesmanapp.utils.SchedulersProvider
import org.koin.dsl.module

val appModule = module {
    single<SalesmanRepository> { SalesmanRepositoryImpl()}
    single<SchedulersProvider> { SchedulersProvider() }
    factory { MainViewModel(get(), get()) }
}