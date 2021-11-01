package com.muazzeznihalbahadir.yemekkitabi.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.muazzeznihalbahadir.yemekkitabi.model.Besin
import com.muazzeznihalbahadir.yemekkitabi.servis.BesinDatabase
import kotlinx.coroutines.launch

class BesinDetayViewModel(application: Application) : BaseViewModel(application) {
    val besinLiveData = MutableLiveData<Besin>()

    fun roomVerisiniAl (uuid:Int){
       launch {
           val dao = BesinDatabase(getApplication()).besinDao()
           val besin = dao.getBesin(uuid)
           besinLiveData.value=besin
       }
    }
}