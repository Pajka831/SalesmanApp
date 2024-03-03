package com.maciejpaja.salesmanapp.data

import com.maciejpaja.salesmanapp.models.Salesman
import io.reactivex.rxjava3.core.Single

interface SalesmanRepository {
    fun downloadSalesmenData(): Single<List<Salesman>>
}