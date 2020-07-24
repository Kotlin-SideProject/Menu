package com.angus.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angus.menu.room.Expense
import kotlinx.android.synthetic.main.expense_row.view.*

class ExpenseAdapter() : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> (){

    private var expenses = emptyList<Expense>()
    inner class ExpenseViewHolder(view : View) : RecyclerView.ViewHolder(view){
        var date = view.expense_date
        var info = view.expense_info
        var amounts = view.expense_amounts
    }

     fun setExpenses(expenses : List<Expense>) {
        this.expenses = expenses
        notifyDataSetChanged() // 一定要加嗎??????
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.expense_row, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun getItemCount(): Int {
        return expenses.size
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        var expense = expenses.get(position)
        expense?.run {
            holder.date.text = expense.date
            holder.info.text = expense.info
            holder.amounts.text = expense.amount.toString()
        }
    }
}