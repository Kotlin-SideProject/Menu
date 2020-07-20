package com.angus.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.contact_row.view.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var contacts = listOf<Contact>(
        Contact("Angus", "123456789"),
        Contact("Ruby", "9873543213"),
        Contact("Tom", "65732687564"),
        Contact("Hank", "6546456354321"))
        recycle.setHasFixedSize(true)
        recycle.layoutManager = LinearLayoutManager(MainActivity@this)
        recycle.adapter = object : RecyclerView.Adapter<ContactViewHolder>(){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
                var view = layoutInflater.inflate(R.layout.contact_row, parent, false)
                return ContactViewHolder(view)
            }

            override fun getItemCount(): Int {
                return contacts.size
            }

            override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
                holder.name.setText(contacts.get(position).name)
                holder.phone.setText(contacts.get(position).phone)
            }

        }
    }
    class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var name = view.contact_name
        var phone = view.contact_phone

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_contacts -> {
                startActivity(Intent(this, MaterialActivity::class.java))
            }

            R.id.action_help -> {
                // late innite
            }
        }
        return super.onOptionsItemSelected(item)
    }
}