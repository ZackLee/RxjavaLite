package me.lino.rxjava

class Observable<T>(onSubscriber: OnSubscriber<T>) {
    var source: OnSubscriber<T>? = onSubscriber

    companion object {
        fun <T> create(onSubscriber: OnSubscriber<T>): Observable<T> {
            return Observable(onSubscriber)
        }
    }

    fun setObserver(observer: Observer<T>) {
        observer.onSubscribe()
        source?.setObserver(observer)
    }

    //转换 T -> R
    fun <R> map(func: (T) -> R): Observable<R> {
        val map = MapObservable(this.source!!, func)
        return Observable(map)
    }

    fun subscribeOn(thread: Int): Observable<T> {
        val subscriber = SubscribeObservable(this.source!!, thread)
        return Observable(subscriber)
    }

    fun observerOn(thread: Int): Observable<T> {
        val subscriber = ObserverObservable(this.source!!, thread)
        return Observable(subscriber)
    }

}