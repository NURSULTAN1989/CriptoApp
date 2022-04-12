package com.example.criptoapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.criptoapp.databinding.DetailActivityBinding
import com.squareup.picasso.Picasso

class CoinDetailActivity:AppCompatActivity() {
    lateinit var binding:DetailActivityBinding
    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)){
            finish()
            return
        }
        val fromSymbol=intent.getStringExtra(EXTRA_FROM_SYMBOL)
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        if (fromSymbol != null) {
            viewModel.getDetailInfo(fromSymbol).observe(this, Observer {
                with(binding){
                    tvDPrice.text=it.price.toString()
                    tvDMinPrice.text=it.lowday.toString()
                    tvDMaxPrice.text=it.highday.toString()
                    tvDLastMarket.text=it.lastmarket.toString()
                    tvDUpdate.text=it.getFormattedTime()
                    tvFromSymbol.text=it.fromsymbol
                    tvToSymbol.text=it.tosymbol
                    Picasso.get().load(it.getFullImageUrl()).into(imgView)
                }
                Log.d("Detail_Info", it.toString())
            })
        }

    }
    companion object{
       private const val EXTRA_FROM_SYMBOL="fSym"
        fun newIntent(context: Context,fromSymbol:String):Intent{
            val intent=Intent(context,CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL,fromSymbol)
            return intent
        }
    }

}