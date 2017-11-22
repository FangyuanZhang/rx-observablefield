package com.github.chuross.rxobservablefield

import android.databinding.ObservableField
import com.github.chuross.rxobservablefield.internal.ObservableUtils
import io.reactivex.Observable

class RxObservableField<T> : ObservableField<T> {

    @Transient
    private var observable: Observable<T>? = null
    val rx: Observable<T> get() = observable ?: newObservable().also { observable = it }

    constructor(): super()

    constructor(default: T): super(default)

    override fun get(): T? = super.get()

    private fun newObservable(): Observable<T> = ObservableUtils.toObservable(this).share()
}