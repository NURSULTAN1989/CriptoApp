package com.example.criptoapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.criptoapp.databinding.ItemCoinInfoBinding
import com.example.criptoapp.pojo.CoinPriceInfo
import com.squareup.picasso.Picasso

class CoinInfoAdapter(private val context:Context):RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {
    var coinPriceInfoList:List<CoinPriceInfo> = listOf()
    set(value) {
        field=value
        notifyDataSetChanged()
    }
    var onCoinClickListener:OnCoinClickListener?=null
    inner class CoinInfoViewHolder(var binding: ItemCoinInfoBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        return CoinInfoViewHolder(ItemCoinInfoBinding.inflate(inflater,parent,false))
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin=coinPriceInfoList[position]
        val symbolsTemplate=context.resources.getString(R.string.symbols_template)
        val lastUpdateTemplate=context.resources.getString(R.string.last_update_teplate)
        with(holder.binding){
            tvSymbols.text= String.format(symbolsTemplate,coin.fromsymbol,coin.tosymbol)
            tvPrice.text=coin.price.toString()
            tvTime.text= String.format(lastUpdateTemplate,coin.getFormattedTime())
            Picasso.get().load(coin.getFullImageUrl()).into(ivLogoCoin)
        }
        holder.itemView.setOnClickListener {
            onCoinClickListener?.onCoinClick(coin)
        }

    }

    override fun getItemCount(): Int {
        return coinPriceInfoList.size
    }
    interface OnCoinClickListener{
        fun onCoinClick(coinPriceInfo: CoinPriceInfo)
    }
}