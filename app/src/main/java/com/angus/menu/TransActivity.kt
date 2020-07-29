package com.angus.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Request
import okhttp3.Request.Builder
import java.io.BufferedReader
import java.net.URL
import org.json.JSONArray
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.collections.ArrayList


class TransActivity : AppCompatActivity() {
    companion object{
        var TAG = "TransActivity.kt"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trans)
        //提供 HTTP 網路連線的能力
        val client = okhttp3.OkHttpClient.Builder()
            .build()
        //設定一個連線必要的資訊 址則使用 url() 方法定義，此時還未連線至主機
        val request = Builder()
            .url("https://atm201605.appspot.com/h")
            .build()
        //接著與網頁建立連線
        CoroutineScope(Dispatchers.IO).launch {
            var response = client.newCall(request).execute()
            response.body?.run {
//                Log.d(TAG, string())
            val json = string()
//                parseJSON(json)
//                parseGSON(json)
                parseJackson(json)
            }
        }
        /*CoroutineScope(Dispatchers.IO).launch {
            val reader =  URL("https://atm201605.appspot.com/h")
                .openConnection()
                .getInputStream()
                .bufferedReader()
            val json = reader.use(BufferedReader::readText)
            Log.d(TAG, json)
        }*/
    }

    private fun parseJackson(json: String) {
        val mapper = ObjectMapper().registerModule(KotlinModule())
        val trans : List<Transaction> =  mapper.readValue(json)
        trans.forEach { t ->
            Log.d(TAG, t.toString());
        }
    }

    private fun parseGSON(json: String) {
        val gson = Gson()
        //第二個參數則是提供給 Gson 類別我們想要轉出的資料格式
        val trans = gson.fromJson<ArrayList<Transaction>>(json,
                        object : TypeToken<ArrayList<Transaction>>(){}.type)
        trans.forEach { t ->
            Log.d(TAG, "GSON ${t.toString()}")
        }
    }

    private fun parseJSON(json: String) {
        val trans = mutableListOf<Transaction>()
        val array = JSONArray(json)
        for (i in 0 until array.length()){
            val obj = array.getJSONObject(i)
            val account = obj.getString("account")
            val date = obj.getString("date")
            val amount = obj.getInt("amount")
            val type = obj.getInt("type")
            val t = Transaction(account, date, amount, type)
            Log.d(TAG, t.toString())
            trans.add(t)
        }
    }
}