package com.example.criptoapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.criptoapp.api.ApiFactory
import com.example.criptoapp.database.AppDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CoinViewModel(application: Application):AndroidViewModel(application) {

    private val db=AppDatabase.getInstance(application)
    private val compositeDisposable=CompositeDisposable()

    val priceList=db.coinPriceInfoDao().getPriceList()

    fun loadData(){
        val disposable=ApiFactory.apiService.getTopCoinsInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("TEST_OF_LOADING_DATA", it.toString())
            },{
                it.message?.let { it1 -> Log.d("TEST_OF_LOADING_DATA", it1) }
            })

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}