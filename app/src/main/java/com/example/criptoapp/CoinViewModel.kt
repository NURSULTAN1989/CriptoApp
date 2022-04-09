package com.example.criptoapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.criptoapp.api.ApiFactory
import com.example.criptoapp.database.AppDatabase
import com.example.criptoapp.pojo.CoinPriceInfo
import com.example.criptoapp.pojo.CoinPriceInfoRawData
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()

    val priceList = db.coinPriceInfoDao().getPriceList()

    fun loadData() {
        val disposable = ApiFactory.apiService.getTopCoinsInfo(limit = 50)
            .map { it.data?.map { it.coinInfo?.name }?.joinToString(",") }
            .flatMap { ApiFactory.apiService.getFullPriceList(fsyms = it) }
            .map { getPriceListFromRawData(it) }
            .subscribeOn(Schedulers.io())
            .subscribe({
                if (it != null) {
                    db.coinPriceInfoDao().insertPriceList(it)
                }
            }, {
                it.message?.let { it1 -> Log.d("TEST_OF_LOADING_DATA", it1) }
            })

    }

    fun getPriceListFromRawData(
        coinPriceInfoRawData: CoinPriceInfoRawData,
    ): List<CoinPriceInfo> {
        val result=ArrayList<CoinPriceInfo>()
        val jsonObject = coinPriceInfoRawData.coinPriceInfoJsonObject
        if (jsonObject == null) return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(currencyJson.getAsJsonObject(currencyKey),
                    CoinPriceInfo::class.java)
                result.add(priceInfo)
            }

        }
        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}