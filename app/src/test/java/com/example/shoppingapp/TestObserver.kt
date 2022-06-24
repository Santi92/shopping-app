package com.example.shoppingapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.*


class TestObserver<T> : Observer<T> {
    private val valueHistory: MutableList<T> = ArrayList()

    override fun onChanged(value: T) {
        valueHistory.add(value);
    }

    fun valueHistory(): List<T>? {
        return Collections.unmodifiableList(valueHistory)
    }

    companion object {
        fun <T> test(liveData: LiveData<T>): TestObserver<T> {
            val observer: TestObserver<T> = TestObserver()
            liveData.observeForever(observer)
            return observer
        }
    }

}

