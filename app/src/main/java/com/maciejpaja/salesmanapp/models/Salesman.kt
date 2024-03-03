package com.maciejpaja.salesmanapp.models


class SalesmanList : ArrayList<Salesman>()

data class Salesman(
    val name: String,
    val areas: List<String>
)