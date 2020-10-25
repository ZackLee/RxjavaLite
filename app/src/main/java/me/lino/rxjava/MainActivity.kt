package me.lino.rxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import me.lino.rxjava.thread.Schedulers

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Observable.create(object : OnSubscriber<Int> {
            override fun setObserver(observer: Observer<Int>) {
                Log.d(TAG, "上游线程：${Thread.currentThread().name}")
                observer.onNext(6)
                observer.onNext(66)
                observer.onNext(666)
                observer.onComplete()
            }
        }).subscribeOn(Schedulers.IO)
            .map { item -> "map 操作符转换后的数据${item}" }
            .observerOn(Schedulers.MAIN)
            .setObserver(object : Observer<String> {
                override fun onSubscribe() {
                    Log.d(TAG, "onSubscribe")
                }

                override fun onNext(t: String) {
                    Log.d(TAG, "下游线程：${Thread.currentThread().name}")
                    Log.d(TAG, "onNext:${t}")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete()")
                }
            })
    }

    companion object {
        const val TAG = "RxjavaLite"
    }
}