package com.ted_uy.final4020.Models


class StoreDataModel {
    var listID : Int = 0
    var videoTitles = ""
    var videoid = ""
    var urlImage = ""
    var description = ""
}

class StoreNewData(title: String, videoid: String, urlImage: String, description: String){
    var videoTitles = title
    var videoid = videoid
    var urlImage = urlImage
    var description = description
}