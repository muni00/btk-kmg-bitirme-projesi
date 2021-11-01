package com.muazzeznihalbahadir.yemekkitabi.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class OzelSharedPraferences {
    companion object{
        private val ZAMAN ="zaman"
        private var sharedPraferences:SharedPreferences?=null

        @Volatile private var instance : OzelSharedPraferences?=null

        private var lock=Any()

        operator fun invoke(context: Context)= OzelSharedPraferences.instance ?: synchronized(lock){
            instance?: ozelSaredPreferencesYap(context).also {
                instance= it
            }

        }
        private fun ozelSaredPreferencesYap(context: Context):OzelSharedPraferences{
            sharedPraferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return  OzelSharedPraferences()
        }

    }

    fun zamaniKaydet(zaman:Long){
        sharedPraferences?.edit(commit = true){
            putLong(ZAMAN,zaman)
        }
    }
    fun zamaniAl()= sharedPraferences?.getLong(ZAMAN,0)
}
