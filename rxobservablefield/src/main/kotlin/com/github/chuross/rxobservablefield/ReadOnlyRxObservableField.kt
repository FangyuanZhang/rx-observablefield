package com.github.chuross.rxobservablefield

import android.databinding.ObservableField
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

class ReadOnlyRxObservableField<T>(private val source: Observable<T>, disposables: CompositeDisposable, default: T? = null) : ObservableField<T>() {

    val rx: Observable<T> get() = source

    init {
        disposables.add(
                when {
                    default != null -> Observable.concat(Observable.just(default), this.source.doOnNext { super.set(it) }).subscribe()
                    else -> this.source.doOnNext { super.set(it) }.subscribe()
                }
        )
    }

    override fun get(): T? = super.get()

    fun or(default: T): T = get() ?: default

    @Deprecated("This class is ReadOnly!", ReplaceWith("not call"))
    override fun set(value: T) {
        throw UnsupportedOperationException()
    }
}