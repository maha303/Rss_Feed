package com.example.rss_feed

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var rvMain:RecyclerView
    var recentQuestions = mutableListOf<Questions>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvMain=findViewById(R.id.rvMain)

        FetchTopQuestions().execute()

    }
    private inner class FetchTopQuestions : AsyncTask<Void,Void,MutableList<Questions>>(){
        val parser = XMLParser()
        override fun doInBackground(vararg params: Void?): MutableList<Questions> {
            val url= URL("https://stackoverflow.com/feeds")
            val urlConnection=url.openConnection()as HttpURLConnection
            recentQuestions=urlConnection.getInputStream()?.let {
                parser.parse(it)

            }
            as MutableList<Questions>
            return recentQuestions



        }

        override fun onPostExecute(result: MutableList<Questions>?) {
            super.onPostExecute(result)
         //   val adapter = ArrayAdapter(this@MainActivity,android.R.layout.simple_list_item_1,recentQuestions)
          // rvMain.adapter=adapter
            rvMain.adapter=RVAdapter(recentQuestions)
            rvMain.layoutManager= LinearLayoutManager(this@MainActivity)

// }

 }

}}