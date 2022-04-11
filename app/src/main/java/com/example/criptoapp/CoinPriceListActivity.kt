package com.example.criptoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.criptoapp.databinding.ActivityCoinPriceListBinding
import com.example.criptoapp.pojo.CoinPriceInfo


class CoinPriceListActivity : AppCompatActivity() {
  private lateinit var viewModel: CoinViewModel
  lateinit var binding: ActivityCoinPriceListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCoinPriceListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter=CoinInfoAdapter(this)
        adapter.onCoinClickListener=object :CoinInfoAdapter.OnCoinClickListener{
            override fun onCoinClick(coinPriceInfo: CoinPriceInfo) {
               val intent=CoinDetailActivity.newIntent(this@CoinPriceListActivity,coinPriceInfo.fromsymbol)
                startActivity(intent)
            }
        }
        binding.rvCoinPriceList.adapter=adapter
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.priceList.observe(this, Observer {
            adapter.coinPriceInfoList=it
        })
        viewModel.getDetailInfo("BTC").observe(this, Observer {

        })
    }
}