package me.lino.rxjava

import me.lino.rxjava.thread.Schedulers

class LinoObserverObservable<T>(
    private val source: LinoOnSubscriber<T>,
    private val thread: Int
) : LinoOnSubscriber<T> {

    override fun setObserver(downStream: LinoObserver<T>) {
        val observer = LinoObserverObserver(downStream, thread)
        source.setObserver(observer)
    }

    class LinoObserverObserver<T>(
        val downStream: LinoObserver<T>,
        val thread: Int
    ) : LinoObserver<T> {

        override fun onSubscribe() {
            Schedulers.INSTANCE.submitObserverWork({
                downStream.onSubscribe()
            }, thread)
        }

        override fun onNext(item: T) {
            Schedulers.INSTANCE.submitObserverWork({
                downStream.onNext(item)
            }, thread)
        }

        override fun onComplete() {
            Schedulers.INSTANCE.submitObserverWork({
                downStream.onComplete()
            }, thread)
        }
    }
}