package com.mrg.mrgmoney.DataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Coin(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "date") val date: String?,
    @ColumnInfo(name = "type") val type: String?,
    @ColumnInfo(name = "amount") val amount: Int?,
    @ColumnInfo(name = "total") val total: Int?,
)
