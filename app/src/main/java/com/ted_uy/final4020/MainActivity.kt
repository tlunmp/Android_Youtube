package com.ted_uy.final4020

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.ted_uy.final4020.Adaptors.MainAdapter
import com.ted_uy.final4020.DBHelpers.DBHelper
import com.ted_uy.final4020.Models.DataModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //declaring the dbhandler
    companion object {
        lateinit var  dbHelper : DBHelper
    }


    //creating option
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.main_menu,menu)
        return true

    }


    //puting ption on toolbar
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val res_id = item!!.itemId
        //fav icon wehn click
        if(res_id ==R.id.action_favorites){
           val ifFavEmpty =  dbHelper.checkIfDBFavEmpty()

            if(ifFavEmpty == true){
                Toast.makeText(this,"No Favorites Added", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, FavActivity::class.java)
                startActivity(intent)
            }
        }

        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar as Toolbar?)

        //declaring dbhelper
        dbHelper = DBHelper(this, null,1)
        //var envVar: String = System.getenv("SHINCHI42APIKEY") ?: "default_value"
       // Log.e("MSG", "${envVar}")

        val dataModel = DataModel()
        val itemList = dataModel.fetchJson()

        val newStoreData = dataModel.storeDataModelFromItemList(itemList)



        //check if the db is empty
        val ifDBEmpty = dbHelper.checkIfDBEmpty()

        //if db is empty then store data if not do not
        if(ifDBEmpty == true){
            dbHelper.addList(newStoreData)
            Log.e("MSG", "is empty")
        }

        val dataList = dbHelper.getData()


        recyclerViewMain.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        recyclerViewMain.adapter = MainAdapter(dataList)
       // Log.e("TAG", "ON CREATE IS DONE!")



    }





}
