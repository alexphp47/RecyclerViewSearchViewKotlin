package com.example.recyclerviewsearchviewkotlin

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import java.util.ArrayList
// to add to github
class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private var recyclerView: RecyclerView? = null
    private var adapter: SearchAdapter? = null
    private var editsearch: SearchView? = null

    private val myImageNameList = arrayOf("Benz", "Bike", "Car", "Carrera", "Ferrari", "Harly", "Lamborghini", "Silver")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler) as RecyclerView

        imageModelArrayList = populateList()
        Log.d("hjhjh", imageModelArrayList.size.toString() + "")
        adapter = SearchAdapter(this, imageModelArrayList)
        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

        recyclerView!!.addOnItemTouchListener(
            RecyclerTouchListener(
                applicationContext,
                recyclerView!!,
                object : ClickListener {

                    override fun onClick(view: View, position: Int) {
                        Toast.makeText(this@MainActivity, imageModelArrayList[position].getNames(), Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onLongClick(view: View?, position: Int) {

                    }
                })
        )


        editsearch = findViewById(R.id.search) as SearchView
        editsearch!!.setOnQueryTextListener(this)

    }

    override fun onQueryTextSubmit(query: String): Boolean {

        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        adapter!!.filter(newText)
        return false
    }

    private fun populateList(): ArrayList<SearchModel> {

        val list = ArrayList<SearchModel>()

        for (i in 0..7) {
            val imageModel = SearchModel()
            imageModel.setNames(myImageNameList[i])
            list.add(imageModel)
        }

        return list
    }

    interface ClickListener {
        fun onClick(view: View, position: Int)

        fun onLongClick(view: View?, position: Int)
    }

    internal class RecyclerTouchListener(
        context: Context,
        recyclerView: RecyclerView,
        private val clickListener: ClickListener?
    ) : RecyclerView.OnItemTouchListener {

        private val gestureDetector: GestureDetector

        init {
            gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapUp(e: MotionEvent): Boolean {
                    return true
                }

                override fun onLongPress(e: MotionEvent) {
                    val child = recyclerView.findChildViewUnder(e.x, e.y)
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child))
                    }
                }
            })
        }

        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {

            val child = rv.findChildViewUnder(e.x, e.y)
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child))
            }
            return false
        }

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

        }


    }

    companion object {
        lateinit var imageModelArrayList: ArrayList<SearchModel>
    }

}
