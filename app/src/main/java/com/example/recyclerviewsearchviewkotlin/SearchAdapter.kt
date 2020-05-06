package com.example.recyclerviewsearchviewkotlin

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import java.util.ArrayList
import java.util.Locale

/**
 * Created by Parsania Hardik on 26-Jun-17.
 */
class SearchAdapter(ctx: Context, private val imageModelArrayList: ArrayList<SearchModel>) :
    RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {

    private val inflater: LayoutInflater
    private val arraylist: ArrayList<SearchModel>

    init {

        inflater = LayoutInflater.from(ctx)
        this.arraylist = ArrayList<SearchModel>()
        this.arraylist.addAll(MainActivity.imageModelArrayList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.MyViewHolder {

        val view = inflater.inflate(R.layout.rv_item, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchAdapter.MyViewHolder, position: Int) {

        holder.time.setText(imageModelArrayList[position].getNames())
    }

    override fun getItemCount(): Int {
        return imageModelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var time: TextView

        init {

            time = itemView.findViewById(R.id.tv) as TextView
        }

    }

    // Filter Class
    fun filter(charText: String) {
        var charText = charText
        charText = charText.toLowerCase(Locale.getDefault())
        MainActivity.imageModelArrayList.clear()
        if (charText.length == 0) {
            MainActivity.imageModelArrayList.addAll(arraylist)
        } else {
            for (wp in arraylist) {
                if (wp.getNames().toLowerCase(Locale.getDefault()).contains(charText)) {
                    MainActivity.imageModelArrayList.add(wp)
                }
            }
        }
        notifyDataSetChanged()
    }

}