package me.lino.rxjava

import me.lino.rxjava.thread.Schedulers


class SubscribeObservable<T>(
    private val source: OnSubscriber<T>,
    private val thread: Int
) : OnSubscriber<T> {

    override fun setObserver(downStream: Observer<T>) {
        val observable = LinoSubscribeObserver(downStream)
        //提交任务给指定线程
        Schedulers.INSTANCE.submitSubscribeWork(source, observable, thread)
    }

    class LinoSubscribeObserver<T>(private val downStream: Observer<T>) : Observer<T> {
        override fun onSubscribe() {
            downStream.onSubscribe()
        }

        override fun onNext(t: T) {
            downStream.onNext(t)
        }

        override fun onComplete() {
            downStream.onComplete()
        }

    }

}