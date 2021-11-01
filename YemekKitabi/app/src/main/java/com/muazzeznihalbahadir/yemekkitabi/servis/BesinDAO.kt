package com.muazzeznihalbahadir.yemekkitabi.servis

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.muazzeznihalbahadir.yemekkitabi.model.Besin

@Dao
interface BesinDAO {
    //Data Access Object
    @Insert
    suspend fun insertAll (vararg besin:Besin) : List<Long>
    //long tipinde verilerin uuid sini döndürür
    //suspend->coroutine scope

    @Query("SELECT * FROM besin")
    suspend fun getAllBesin():List<Besin>

    @Query("SELECT * FROM besin WHERE uuid =:besinId")
    suspend fun getBesin(besinId :Int):Besin

    @Query("DELETE FROM besin")
    suspend fun deleteAllBesin()
}