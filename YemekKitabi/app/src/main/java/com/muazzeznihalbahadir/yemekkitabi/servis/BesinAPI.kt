package com.muazzeznihalbahadir.yemekkitabi.servis

import com.muazzeznihalbahadir.yemekkitabi.model.Besin
import io.reactivex.Single
import retrofit2.http.GET

interface BesinAPI {
    //get almak
    //post yollamak
    //BASE_URL -> https://raw.githubusercontent.com/
    // atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json
    @GET("atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")
    fun getBesin() : Single<List<Besin>>
}