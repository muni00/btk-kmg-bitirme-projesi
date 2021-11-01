package com.muazzeznihalbahadir.yemekkitabi.servis

import com.muazzeznihalbahadir.yemekkitabi.model.Besin
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class BesinAPIServis {
    //BASE_URL -> https://raw.githubusercontent.com/
    // atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json
    private val BASE_URL="https://raw.githubusercontent.com/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//rx java kullanacaksan ekle aksi halde yazma
        .build()
        .create(BesinAPI::class.java)


    fun getData():Single<List<Besin>>{
        return api.getBesin()
    }

}