package me.lino.rxjava.thread

import android.os.Handler
import android.os.Looper
import android.os.Message
import me.lino.rxjava.Observer
import me.lino.rxjava.OnSubscriber
import java.util.concurrent.Executors

public class Schedulers() {
    private var IOThreadPool = Executors.newCachedThreadPool()

    private var handler = Handler(Looper.getMainLooper()) { message ->
        message.callback.run()

        return@Handler true
    }

    companion object {
        //定义一个线程安全的单例模式
        val INSTANCE: Schedulers by
        lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            Schedulers()
        }
        val IO = 0 //定义IO线程策略
        val MAIN = 1 //定义main线程策略
    }


    fun <T> submitSubscribeWork(
        source: OnSubscriber<T>, //上游
        downStream: Observer<T>,//下游
        thread: Int//指定的线程
    ) {
        when (thread) {
            IO -> {
                IOThreadPool.submit {
                    //从线程池抽取一个线程执行上游和下游的连接操作
                    source.setObserver(downStream)
                }
            }
            MAIN -> {
                val message = Message.obtain(handler) {
                    //上下游的连接
                    source.setObserver(downStream)
                }
                handler.sendMessage(message)

            }
        }
    }

    fun submitObserverWork(function: () -> Unit, thread: Int) {
        when (thread) {
            IO -> {
                IOThreadPool?.submit {
                    function.invoke() //调用高阶函数
                }
            }
            MAIN -> {
                handler?.let {
                    val m = Message.obtain(it) {
                        function.invoke()//调用高阶函数
                    }
                    it.sendMessage(m)
                }
            }
        }

    }
}
