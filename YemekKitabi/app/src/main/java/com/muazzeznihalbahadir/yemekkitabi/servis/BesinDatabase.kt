package com.muazzeznihalbahadir.yemekkitabi.servis

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.muazzeznihalbahadir.yemekkitabi.model.Besin

@Database(entities = arrayOf(Besin::class),version = 1)
    abstract class BesinDatabase : RoomDatabase(){
    abstract fun besinDao() : BesinDAO

    //Singleton
    companion object{
        @Volatile private var instance:BesinDatabase? = null
        //tüm thread lerden ulaşılabimesi için volatile yazıldı

        private var lock=Any()
        operator fun invoke(context: Context)= instance ?: synchronized(lock){
            instance?: databaseOlustur(context).also {
                instance = it
            }
        }

        private fun databaseOlustur(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            BesinDatabase::class.java,
            "besinDatabase"
        ).build()
    }


}