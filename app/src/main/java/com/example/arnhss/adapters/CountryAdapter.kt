package com.example.arnhss.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.arnhss.R
import com.example.arnhss.types.Country


class CountryAdapter(private var ctx:Context, private var countryList:List<Country>):RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var img:ImageView = itemView.findViewById<ImageView>(R.id.row_flag)
        var countryName:TextView = itemView.findViewById<TextView>(R.id.country_name)
        var countryCode:TextView = itemView.findViewById<TextView>(R.id.row_country_code)

        var cardView:CardView = itemView.findViewById<CardView>(R.id.country_card)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(ctx).inflate(R.layout.country_select_row, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country:Country
        holder.countryName.text = countryList[position].name
        holder.countryCode.text = countryList[position].dial_code

        val imageUrl = "https://flagcdn.com/48x36/${countryList[position].code.lowercase()}.png"

        print(imageUrl)

        Glide.with(holder.img.context)
            .load(imageUrl)
            .placeholder(R.drawable.india_flag)  // Placeholder image
            .into(holder.img)



    }
}