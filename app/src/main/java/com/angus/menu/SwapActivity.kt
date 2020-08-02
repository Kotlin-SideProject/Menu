package com.angus.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_swap.*

class SwapActivity : AppCompatActivity() {
    private lateinit var detail: DetailFragment
    private lateinit var contact: ContactFragment
    private  lateinit var currencyFragment : Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swap)
        contact = ContactFragment.instance
        detail = DetailFragment.instance
        supportFragmentManager.beginTransaction().run{
            add(R.id.container, contact)
            commit()
            currencyFragment = contact
//            Log.d("SwapActivity.kt", "onCreate: ${currencyFragment.toString()}" )
        }

        fab.setOnClickListener {
            swap()
        }
    }

    private fun swap() {
        currencyFragment = if(currencyFragment == contact) detail else  contact
        supportFragmentManager.beginTransaction().run {
            replace(R.id.container, currencyFragment)
            addToBackStack(null)
            commit()
        }
    }
}