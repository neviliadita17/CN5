package com.example.cn5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main_news.*

class MainNews : AppCompatActivity() {

    lateinit var ref : DatabaseReference
    lateinit var list : MutableList<News>
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_news)

        ref = FirebaseDatabase.getInstance().getReference("News")
        list = mutableListOf()
        listView = findViewById(R.id.listView)




        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()){

                    list.clear()
                    for (h in p0.children){
                        val newss = h.getValue(News::class.java)
                        list.add(newss!!)
                    }
                    val adapter = Adapter(this@MainNews,R.layout.news,list)
                    listView.adapter = adapter
                }
            }
        })
        BTambah.setOnClickListener {
            val intent = Intent (this, InsertNews::class.java)
            startActivity(intent)
        }
    }

}
