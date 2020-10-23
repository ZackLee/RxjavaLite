package me.lino.rxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LinoObservable.create(object : LinoOnSubscriber<Int> {
            override fun setObserver(observer: LinoObserver<Int>) {
                observer.onNext(6)
                observer.onNext(66)
                observer.onNext(666)
                observer.onComplete()
            }
        }).map { item -> "map 操作符转换后的数据${item}" }

            .setObserver(object : LinoObserver<String> {
                override fun onSubscribe() {
                    Log.d(TAG, "onSubscribe")
                }

                override fun onNext(t: String) {
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