package com.mrg.mrgmoney.DataBase

import android.content.ContentValues
import android.os.Build
import android.util.Log
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface CoinDao {
    @Query("SELECT * FROM coin ORDER BY uid DESC")
    fun getAll():LiveData<List<Coin>>

    @Query("SELECT * FROM coin WHERE uid IN (:coinIds)")
    fun loadAllByIds(coinIds: IntArray): List<Coin>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg coin: Coin)

    @Delete
    fun delete(coin: Coin)

    @Query("DELETE FROM coin")
    suspend fun deleteAll()

}