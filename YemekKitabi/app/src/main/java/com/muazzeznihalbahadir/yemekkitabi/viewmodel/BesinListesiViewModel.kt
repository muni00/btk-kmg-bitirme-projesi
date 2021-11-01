package com.muazzeznihalbahadir.yemekkitabi.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Database
import com.muazzeznihalbahadir.yemekkitabi.model.Besin
import com.muazzeznihalbahadir.yemekkitabi.servis.BesinAPIServis
import com.muazzeznihalbahadir.yemekkitabi.servis.BesinDatabase
import com.muazzeznihalbahadir.yemekkitabi.util.OzelSharedPraferences
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class BesinListesiViewModel(application: Application): BaseViewModel(application){

    val besinler = MutableLiveData<List<Besin>>()
    val besinHataMesaji = MutableLiveData<Boolean>()
    val besinYukleniyor = MutableLiveData<Boolean>()
    private var guncellemeZamani=10*60*1000*1000*1000L //10 dakikayı nano saniyeye çevirme işlemleri

    private val besinApiServis=BesinAPIServis()
    private val disposable = CompositeDisposable() //rx java ile geliyor despose işlemleri için
    private val ozelSharedPraferences = OzelSharedPraferences(getApplication())

    fun refreshData(){

        val kaydedilmeZamani=ozelSharedPraferences.zamaniAl()
        if (kaydedilmeZamani!=null && kaydedilmeZamani!=0L && (System.nanoTime()-kaydedilmeZamani)<guncellemeZamani){
            //sqlite tan çek
            verileriSqlitetanAl()
        }else{
            //vakti geldiği için internetten çek
            verileriIntenettenAl()
        }
    }
    private fun verileriSqlitetanAl(){
        launch {
            //verileri sqlite tan çekme işlemi de suspend funlar içinde olduğundan launch içinde yapılmalıdır
           val besinListesi = BesinDatabase(getApplication()).besinDao().getAllBesin()
            besinleriGoster(besinListesi)
            Toast.makeText(getApplication(),"Verileri Room'dan Aldık",Toast.LENGTH_LONG).show()
        }
    }
    private  fun verileriIntenettenAl(){
        besinYukleniyor.value = true
        disposable.add(
            besinApiServis.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Besin>>(){
                    override fun onSuccess(t: List<Besin>) {
                        //başarılı olursa
                        sqliteSakla(t)
                        Toast.makeText(getApplication(),"Verileri Internet'ten Aldık",Toast.LENGTH_LONG).show()
                    }

                    override fun onError(e: Throwable) {
                        //başarısız olursa
                        besinYukleniyor.value=false
                        besinHataMesaji.value=true
                        e.printStackTrace()
                    }


                })
        )
    }
    private fun besinleriGoster(besinListesi:List<Besin>){
        besinler.value = besinListesi
        besinYukleniyor.value=false
        besinHataMesaji.value=false
    }
    private fun sqliteSakla(besinListesi:List<Besin>){
        launch {
            val dao=BesinDatabase(getApplication()).besinDao()
            dao.deleteAllBesin()
            val uuidList = dao.insertAll(*besinListesi.toTypedArray())

            var i=0
            while (i<besinListesi.size){
                besinListesi[i].uuid = uuidList[i].toInt()
                i=i+1
            }
            besinleriGoster(besinListesi)
        }
        ozelSharedPraferences.zamaniKaydet(System.nanoTime())
    }
}