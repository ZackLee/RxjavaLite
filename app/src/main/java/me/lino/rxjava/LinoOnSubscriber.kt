package me.lino.rxjava

//被监听者
// 因为rxjava 不知道开发者要发送哪些数据啊，那就设计成接口，自己实现吧
// 更不知道开发者具体想发什么类型数据，那就用泛型占个位置
interface LinoOnSubscriber<T> {

    fun setObserver(observer: LinoObserver<T>)
}