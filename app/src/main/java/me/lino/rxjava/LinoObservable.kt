package me.lino.rxjava

class LinoObservable<T>(onSubscriber: LinoOnSubscriber<T>) {
    var source: LinoOnSubscriber<T>? = onSubscriber

    companion object {
        fun <T> create(onSubscriber: LinoOnSubscriber<T>): LinoObservable<T> {
            return LinoObservable(onSubscriber)
        }
    }

    fun setObserver(observer: LinoObserver<T>) {
        observer.onSubscribe()
        source?.setObserver(observer)
    }

}