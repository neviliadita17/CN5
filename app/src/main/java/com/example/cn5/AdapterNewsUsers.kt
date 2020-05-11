package com.example.cn5

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class AdapterNewsUsers(val mCtx: Context, val layoutResId: Int, val list: List<News> )
    : ArrayAdapter<News>(mCtx,layoutResId,list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val textJudul = view.findViewById<TextView>(R.id.textJudul)
        val image = view.findViewById<ImageView>(R.id.img_item_photo)
        val news = list[position]

        textJudul.text = news.judul
        var url = news.linkimage

        //dependency glide dipanggil dan diterapkan ke image1
        Glide.with(context)
            .load(url)
            .placeholder(R.color.colorPrimary)
            .error(R.color.colorPrimary)
            .into(image)


        return view
    }
}