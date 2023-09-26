package com.example.volleydemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    val objectURL = "https://raw.githubusercontent.com/dcdhameliya/Volley-Demo/main/object.json"

    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        simpleStringRequest()

    }

    private fun simpleStringRequest() {
        val queue = Volley.newRequestQueue(this)

        val stringRequest = StringRequest(
            Request.Method.GET, objectURL,
            { response ->
                Log.e("CUSTOM---->", response.toString())


//                need to uncomment the function depending on the method you want to use

//                Method 1
//                setDataToStringList(response)

//                Method 2
//                setDataToCustomModelList(response)

//                Method 3
//                setDataToGsonModelList(response)

            },
            { error ->
                Log.e("CUSTOM---->", error.message.toString())
            })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    private fun setDataToStringList(response: String) {

        val obj = JSONObject(response)
        val dataArray = obj.getJSONArray("sellers")
        val list = ArrayList<String>()

        for (i in 0 until dataArray.length()) {
            val dataObject = dataArray.getJSONObject(i)
            val name = dataObject.getString("name")
            list.add(name)
        }

        val adapter = StringAdapter(this, list, object : StringAdapter.OnItemClickListener {
            override fun onItemClick(name: String) {
                Toast.makeText(this@MainActivity, name, Toast.LENGTH_SHORT).show()
            }
        })

        recyclerView.adapter = adapter


    }

    private fun setDataToCustomModelList(response: String) {

        val obj = JSONObject(response)
        val dataArray = obj.getJSONArray("sellers")
        val modelList = ArrayList<Model>()

        for (i in 0 until dataArray.length()) {
            val dataObject = dataArray.getJSONObject(i)
            val model = Model(
                dataObject.getString("name"),
                dataObject.getString("seller_id"),
                dataObject.getString("seller_type"),
                dataObject.getInt("is_confidential")
            )
            modelList.add(model)
        }


        val adapter = ObjAdapter(this, modelList, object : ObjAdapter.OnItemClickListener {
            override fun onItemClick(model: Model) {
                Toast.makeText(
                    this@MainActivity,
                    model.name + " CUSTOM",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        recyclerView.adapter = adapter


    }


    private fun setDataToGsonModelList(response: String) {

        val obj = JSONObject(response)
        val dataArray = obj.getJSONArray("sellers")

        val gson = GsonBuilder().create()
        val modelList =
            ArrayList<Model>(gson.fromJson(dataArray.toString(), Array<Model>::class.java).toList())

        val adapter = ObjAdapter(this, modelList, object : ObjAdapter.OnItemClickListener {
            override fun onItemClick(model: Model) {
                Toast.makeText(
                    this@MainActivity,
                    model.name + " GSON",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        recyclerView.adapter = adapter

    }

}