package com.ted_uy.final4020.Models

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import java.util.concurrent.CountDownLatch


class Data(var items: List<Items>)
class Items(var snippet: Snippet)
class Snippet(var description: String, var publishedAt: String, var title: String, var channelId: String, var thumbnails: Thumbnails, var resourceId: ResourceId)
class Thumbnails(var maxres: MaxRes, var medium: Medium, var high: High)
class ResourceId(var videoId: String)
class MaxRes(var url : String, var width: Int, var height: Int)
class Medium(var url: String, var width: Int, var height: Int)
class High(var url: String, var width: Int, var height: Int)



class DataModel {


    private val API_KEY = "APIKEY"
    private val url = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=25&playlistId=UUM2N9uCL0nvKNuJT7u4t2Lw&key=${API_KEY}"
    private var videoId : String = ""
    private var videoDescription : String = ""
    private var videoTitle : String = ""
    private var urlImage : String = ""


    fun setURLImage(urlImage : String){
        this.urlImage = urlImage
    }

    fun setVideoId(videoId: String){
        this.videoId = videoId
    }

    fun setVideoTitle(videoTitle: String){
        this.videoTitle = videoTitle
    }

    fun setVideoDescription(videoDescription: String){
        this.videoDescription = videoDescription
    }

    fun getURL(): String {
        return this.url
    }

    fun getURLIMage() : String {
        return this.urlImage
    }

    fun getVideoDescription(): String{
        return this.videoDescription
    }

    fun getVideoTitle(): String{
        return this.videoTitle
    }

    fun getVideoId(): String {
        return this.videoId
    }

    fun getAPI(): String {
        return this.API_KEY
    }

    fun getAsStoredData():StoreNewData {

        val storeNewData = StoreNewData(videoTitle,videoId, urlImage,videoDescription)
        return storeNewData
    }

    fun storeDataModelFromItemList(itemList: List<Items>): List<StoreNewData>{

        var storeDataList = mutableListOf<StoreNewData>()

        Log.e("MSG", "${itemList.size}")

        for (index in 0..itemList.size-1){

            storeDataList.add(StoreNewData(
                itemList.get(index).snippet.title,
                itemList.get(index).snippet.resourceId.videoId,
                itemList.get(index).snippet.thumbnails.high.url,
                itemList.get(index).snippet.description
            ))
        }

        return storeDataList
    }

    fun fetchJson(): MutableList<Items> {


        var dataList = mutableListOf<Items>()

        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        val countDownLatch = CountDownLatch(1)

        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response?.body()?.string()
               // Log.e("MSG","${body}")
                val gson = GsonBuilder().create()
                val feed = gson.fromJson(body, Data::class.java)

                for (index in 0..feed.items.size -1) {
                    dataList.add(index, feed.items[index])
                }

                Log.e("msg","process 3")

                countDownLatch.countDown()

            }
            override fun onFailure(call: Call, e: IOException) {
                Log.e("MSG", "Failed to execute")
            }

        })

        println("fetching json")

        countDownLatch.await()
        return dataList
    }

}
