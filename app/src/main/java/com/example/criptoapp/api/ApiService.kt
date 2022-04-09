package com.example.criptoapp.api

import com.example.criptoapp.pojo.CoinInfoListofData
import com.example.criptoapp.pojo.CoinPriceInfoRawData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("top/totalvolfull")
    fun getTopCoinsInfo(
        @Query(QUERY_PARAM_API_KEY) apiKey:String="6010b4f3d48b530493c3eccb1457055dda4724cbd0fed478fbab1fbfdd10a37c",
        @Query(QUERY_PARAM_LIMIT) limit:Int=10,
        @Query(QUERY_PARAM_TO_SYMBOL) tSym:String=CURRENCY
    ): Single<CoinInfoListofData>
    @GET("pricemultifull")
    fun getFullPriceList(
        @Query(QUERY_PARAM_API_KEY) apiKey:String="6010b4f3d48b530493c3eccb1457055dda4724cbd0fed478fbab1fbfdd10a37c",
        @Query(QUERY_PARAM_FROM_SYMBOLS) fsyms:String,
        @Query(QUERY_PARAM_TO_SYMBOLS) tsyms:String= CURRENCY
    ):Single<CoinPriceInfoRawData>
    companion object{
        private const val QUERY_PARAM_API_KEY="api_key"
        private const val QUERY_PARAM_LIMIT="limit"
        private const val QUERY_PARAM_TO_SYMBOL="tsym"
        private const val QUERY_PARAM_TO_SYMBOLS="tsyms"
        private const val QUERY_PARAM_FROM_SYMBOLS="fsyms"
        private const val CURRENCY="USD"
    }
}