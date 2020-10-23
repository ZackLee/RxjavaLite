package me.lino.rxjava

//继承 被观察者，成为被观察者
// map 转换 在原来直通的上下游中间

//1. 创建自己的观察者对象，然后将自己的观察者对象给上游
//2. 上游 传消息给下游其实是传给了map,
//3. map 在自己的观察者中在对数据进行进一步的操作之后，将操作之后的数据传递给真正的下游

class LinoMapObservable<T, R>(
    private val source: LinoOnSubscriber<T>,
    private val func: (T) -> R
) : LinoOnSubscriber<R> {


    override fun setObserver(downObserver: LinoObserver<R>) {
        //这时downObserver 才是下游
        val map = LinoMapObserver(downObserver, func) //创建自己的观察者对象
        source.setObserver(map)
    }

    // 在map自己定义一个观察者，用于接收上游传下来的数据。
    class LinoMapObserver<T, R>(
        private val downStream: LinoObserver<R>,
        private val func: ((T) -> R)
    ) : LinoObserver<T> {
        override fun onSubscribe() {
            downStream.onSubscribe()
        }

        override fun onNext(t: T) {
            val result = func.invoke(t)
            downStream.onNext(result)
        }

        override fun onComplete() {
            downStream.onComplete()
        }

    }

}