package com.angus.menu

import android.content.Intent
import android.content.pm.PackageManager
import android.Manifest.permission.*
import android.content.ContentResolver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.contact_row.view.*

class MainActivity : AppCompatActivity() {
    companion object{
        var REQUSET_CONTACTS = 100
    }

    private val REQUEST_EXPENSE: Int = 200
    private val REQUEST_CAMERA: Int = 100
    val contacts = mutableListOf<Contact>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        var contacts = listOf<Contact>(
//        Contact("Angus", "123456789"),
//        Contact("Ruby", "9873543213"),
//        Contact("Tom", "65732687564"),
//        Contact("Hank", "6546456354321"))
        val permission =  ActivityCompat.checkSelfPermission(MainActivity@this, READ_CONTACTS)
        if (permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity@this, arrayOf(READ_CONTACTS, WRITE_CONTACTS), REQUSET_CONTACTS)
        }else{
            readContacts()
        }
    }

    private fun readContacts() {
            val cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                null
            )
        cursor?.run {
            while (this.moveToNext()){
                val id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name = cursor.getString(getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                contacts.add(Contact(name, ""))
            }
            setupRecyclerView()


        }
    }

    private fun setupRecyclerView() {
        recycle.setHasFixedSize(true)
        recycle.layoutManager = LinearLayoutManager(MainActivity@ this)
        recycle.adapter = object : RecyclerView.Adapter<ContactViewHolder>() {
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode){
            REQUSET_CONTACTS -> {
                if (grantResults.size > 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    readContacts()
                }else{
                    AlertDialog.Builder(MainActivity@this)
                        .setMessage("必須允許聯絡人權限才可顯示資料")
                        .setPositiveButton("OK", null)
                        .show()
                }
            }
        }
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

            R.id.action_camera -> {
                val camera = Intent(MainActivity@this, CameraActivity::class.java)
                startActivityForResult(camera, REQUEST_CAMERA)
            }

            R.id.action_expense -> {
                startActivityForResult(Intent(MainActivity@this, ExpenseActivity::class.java), REQUEST_EXPENSE)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}