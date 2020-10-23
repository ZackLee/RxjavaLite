package me.lino.rxjava

import me.lino.rxjava.thread.Schedulers


class LinoSubscribeObservable<T>(
    val source: LinoOnSubscriber<T>,
    private val thread: Int
) : LinoOnSubscriber<T> {

    override fun setObserver(downStream: LinoObserver<T>) {
        val observable = LinoSubscribeObserver(downStream)
        //提交任务给指定线程
        Schedulers.INSTANCE.submitSubscribeWork(source, observable, thread)
    }

    class LinoSubscribeObserver<T>(private val downStream: LinoObserver<T>) : LinoObserver<T> {
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