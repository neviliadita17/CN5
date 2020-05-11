package com.example.cn5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main_news.*

class NewsList : AppCompatActivity() {

    lateinit var ref: DatabaseReference
    lateinit var list: MutableList<News>
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)

        ref = FirebaseDatabase.getInstance().getReference("News")
        list = mutableListOf()
        listView = findViewById(R.id.listViewNews)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()) {

                    list.clear()
                    for (h in p0.children) {
                        val newss = h.getValue(News::class.java)
                        list.add(newss!!)
                    }
                    val adapter = AdapterNewsUsers(this@NewsList, R.layout.item_news_list, list)
                    listView.adapter = adapter
                }
            }
        })

    }
}
