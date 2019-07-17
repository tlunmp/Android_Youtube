package com.ted_uy.final4020

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ted_uy.final4020.Adaptors.MainAdapter
import kotlinx.android.synthetic.main.activity_fav.*

class FavActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fav)


        val favList = MainActivity.dbHelper.getFavList()

        //input the recylerview
        favRecyclerView.layoutManager = LinearLayoutManager(this)
        favRecyclerView.adapter = MainAdapter(favList)
    }
}
