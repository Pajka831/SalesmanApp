package com.maciejpaja.salesmanapp.utils

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers


class SchedulersProvider {
    val mainScheduler: Scheduler = AndroidSchedulers.mainThread()
    val ioScheduler: Scheduler = Schedulers.io()
}