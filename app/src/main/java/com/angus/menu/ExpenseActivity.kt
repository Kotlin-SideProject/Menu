package com.angus.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import androidx.room.RoomDatabase
import com.angus.menu.room.Expense
import com.angus.menu.room.ExpenseDatabase
import kotlinx.android.synthetic.main.activity_expense.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

class ExpenseActivity : AppCompatActivity() {
    private lateinit var expenses: List<Expense>
    private lateinit var adapter: ExpenseAdapter
    private lateinit var database: ExpenseDatabase
    companion object{
        private var TAG = "ExpenseActivity.kt"
    }

    //initial data
    var expenseData = arrayListOf<Expense>(
        Expense("2020/06/01", "lunch",120 ),
        Expense("2020/06/03", "parking", 60),
        Expense("2020/06/03", "日用品", 215),
        Expense("2020/06/06", "停車費", 150)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense)
        database = Room.databaseBuilder(this@ExpenseActivity,
            ExpenseDatabase::class.java,
            "expense.db")
            .build()
        CoroutineScope(Dispatchers.IO).launch {
             expenses = database.expenseDao().getAll()
            Log.d(TAG, "expenses: ${expenses.size}")

            runOnUiThread {
                recycler.setHasFixedSize(true)
                recycler.layoutManager = LinearLayoutManager(this@ExpenseActivity)
                adapter = ExpenseAdapter()
                adapter.setExpenses(expenses)
                recycler.adapter = adapter
            }

        }

        fab.setOnClickListener {
            Executors.newSingleThreadExecutor().execute {
                for(expense in expenseData){
                    database.expenseDao().add(expense)
                }
                expenses = database.expenseDao().getAll()

                runOnUiThread {
                    recycler.setHasFixedSize(true)
                    recycler.layoutManager = LinearLayoutManager(this@ExpenseActivity)
                    adapter = ExpenseAdapter()
                    adapter.setExpenses(expenses)
                    recycler.adapter = adapter
                        }
            }
        }
    }
}