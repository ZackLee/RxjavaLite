package me.lino.rxjava

import me.lino.rxjava.thread.Schedulers

class ObserverObservable<T>(
    private val source: OnSubscriber<T>,
    private val thread: Int
) : OnSubscriber<T> {

    override fun setObserver(downStream: Observer<T>) {
        val observer = LinoObserverObserver(downStream, thread)
        source.setObserver(observer)
    }

    class LinoObserverObserver<T>(
        val downStream: Observer<T>,
        val thread: Int
    ) : Observer<T> {

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