package com.ted_uy.final4020.DBHelpers


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.ted_uy.final4020.Models.StoreDataModel
import com.ted_uy.final4020.Models.StoreNewData


class  DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?, version: Int?) : SQLiteOpenHelper(context, DB_NAME, factory, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        //creating the main page table
        val createDataList = ("CREATE TABLE $DATA_TABLE_NAME  (" +
                "$COL_LISTID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COL_TITLE TEXT," +
                "$COL_VIDEOID TEXT," +
                "$COL_URLIMAGE TEXT," +
                "$COL_DESCRIPTION TEXT)")

        val creatFav = ("CREATE TABLE $FAV_TABLE_NAME  (" +
                "$COL_LISTID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COL_TITLE TEXT," +
                "$COL_VIDEOID TEXT," +
                "$COL_URLIMAGE TEXT," +
                "$COL_DESCRIPTION TEXT)")


        //executing the creation of two database
        db?.execSQL(createDataList)
        db?.execSQL(creatFav)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    companion object {
        private val DB_NAME = "Datas.db"
        private val DB_VERSION = 1

        private val DATA_TABLE_NAME = "Data"
        private val COL_LISTID = "dataID"
        private val COL_TITLE = "title"
        private val COL_VIDEOID = "videoID"
        private val COL_URLIMAGE = "urlImage"
        private val COL_DESCRIPTION = "description"

        private val FAV_TABLE_NAME = "Favorites"

    }

    //get the playlist
    fun getData(): ArrayList<StoreDataModel> {
        val query = "Select * From $DATA_TABLE_NAME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        val dataList = ArrayList<StoreDataModel>()

        //cursor run along the database and check the details and return it
        if (cursor.count == 0) {
            Log.e("MSG", "No Data Found")
        } else {
            while (cursor.moveToNext()) {
                val listData = StoreDataModel()
                listData.listID = cursor.getInt(cursor.getColumnIndex(COL_LISTID))
                listData.videoTitles = cursor.getString(cursor.getColumnIndex(COL_TITLE))
                listData.description = cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION))
                listData.videoid = cursor.getString(cursor.getColumnIndex(COL_VIDEOID))
                listData.urlImage = cursor.getString(cursor.getColumnIndex(COL_URLIMAGE))
                dataList.add(listData)
            }
            Log.e("MSG", "${cursor.count.toString()} Data Found")
        }

        cursor.close()
        db.close()
        return dataList
    }


    //get the favlist
    fun getFavList(): ArrayList<StoreDataModel> {
        val query = "Select * From $FAV_TABLE_NAME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        val dataList = ArrayList<StoreDataModel>()

        //cursor run along the database and check the details and return it
        if (cursor.count == 0) {
            Log.e("MSG", "No Data Found")
        } else {
            while (cursor.moveToNext()) {
                val listData = StoreDataModel()
                listData.listID = cursor.getInt(cursor.getColumnIndex(COL_LISTID))
                listData.videoTitles = cursor.getString(cursor.getColumnIndex(COL_TITLE))
                listData.description = cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION))
                listData.videoid = cursor.getString(cursor.getColumnIndex(COL_VIDEOID))
                listData.urlImage = cursor.getString(cursor.getColumnIndex(COL_URLIMAGE))
                dataList.add(listData)
            }
            Log.e("MSG", "${cursor.count.toString()} Data Found")
        }

        cursor.close()
        db.close()
        return dataList
    }

    //check if there is duplicate
    fun checkIfFavorited(videoID : String): Boolean {

        var isFavorite = true

        val query = "Select * From $FAV_TABLE_NAME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)

        if (cursor.count == 0) {
            isFavorite = false
        } else {
            while (cursor.moveToNext()) {

                if (cursor.getString(cursor.getColumnIndex(COL_VIDEOID)) == videoID) {
                    isFavorite = true
                } else {
                    isFavorite = false
                }
            }
        }

        cursor.close()

        return isFavorite
    }

    //chek if fav is empty
    fun checkIfDBFavEmpty(): Boolean {

        var empty: Boolean = true

        val query = "Select * From $FAV_TABLE_NAME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        if (cursor.count == 0) {
            empty = true
        } else {
            empty = false
        }

        cursor.close()
        return empty
    }

    //check if db is empty
    fun checkIfDBEmpty(): Boolean {

        var empty : Boolean

        val query = "Select * From $DATA_TABLE_NAME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        if (cursor.count == 0) {
            empty = true
        } else {
            empty = false
        }

        cursor.close()

        return empty
    }
    //add list to main page
    fun addList(dataList: List<StoreNewData>) {

        val values = ContentValues()
        val db = this.writableDatabase

        for (index in 0..dataList.size -1) {

            values.put(COL_DESCRIPTION, dataList.get(index).description)
            values.put(COL_TITLE, dataList.get(index).videoTitles)
            values.put(COL_URLIMAGE, dataList.get(index).urlImage)
            values.put(COL_VIDEOID, dataList.get(index).videoid)

            val result = db.insert(DATA_TABLE_NAME, null, values)
            if(result == -1.toLong()) {
                Log.e("MSG", "Failed")
            } else {
                Log.e("MSG", "Success")
            }
        }
        db.close()

    }

    //add list to main page
    fun addFavList(dataFavList: StoreNewData) {
        val values = ContentValues()

        values.put(COL_DESCRIPTION, dataFavList.description)
        values.put(COL_TITLE, dataFavList.videoTitles)
        values.put(COL_URLIMAGE, dataFavList.urlImage)
        values.put(COL_VIDEOID, dataFavList.videoid)

        val db = this.writableDatabase

        val result = db.insert(FAV_TABLE_NAME, null, values)
        if(result == -1.toLong()) {
            Log.e("MSG", "Failed")
        } else {

            Log.e("MSG", "Success")
        }

        db.close()

    }
}