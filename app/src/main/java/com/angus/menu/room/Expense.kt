package com.angus.menu.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class Expense (
    @NonNull
    @ColumnInfo(name = "dataAt")
    var date : String,
    var info : String,
    var amount : Int){
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0
}