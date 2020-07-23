package com.angus.menu.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExpenseDAO {
    @Insert
    fun add(expense: Expense)

    @Query( "select * from Expense")
    fun getAll() : List<Expense>
}