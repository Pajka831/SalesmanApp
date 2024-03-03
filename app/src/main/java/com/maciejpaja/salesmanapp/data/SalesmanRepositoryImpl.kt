package com.maciejpaja.salesmanapp.data

import com.google.gson.Gson
import com.maciejpaja.salesmanapp.models.Salesman
import com.maciejpaja.salesmanapp.models.SalesmanList
import io.reactivex.rxjava3.core.Single


class SalesmanRepositoryImpl : SalesmanRepository {

    private val lastSalesmenData: List<Salesman>? = null

    override fun downloadSalesmenData(): Single<List<Salesman>> {
        return if (lastSalesmenData == null) {
            Single.fromCallable {
                val gson = Gson()
                gson.fromJson(
                    "[\n" +
                            "{\"name\":\"Artem Titarenko\", \"areas\": [\"76133\"]},\n" +
                            "{\"name\":\"Bernd Schmitt\", \"areas\": [\"7619*\"]},\n" +
                            "{\"name\":\"Chris Krapp\", \"areas\": [\"762*\"]},\n" +
                            "{\"name\":\"Alex Uber\", \"areas\": [\"86*\"]}\n" +
                            "]", SalesmanList::class.java
                )
            }
        } else Single.just(lastSalesmenData)
    }
}