package me.lino.rxjava


//监听者
interface Observer<T> {

    fun onSubscribe()
    fun onNext(t: T)
    fun onComplete()

}