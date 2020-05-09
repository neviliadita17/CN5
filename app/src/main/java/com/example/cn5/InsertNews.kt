package com.example.cn5

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_insert_news.*

class InsertNews : AppCompatActivity(){
    lateinit var ref : DatabaseReference
    private var imgPath: Uri? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_news)
        val languages = resources.getStringArray(R.array.Kategori)
        val spinner = findViewById<Spinner>(R.id.SPKategori)
        if (spinner != null) {
            val adapter = ArrayAdapter(                this,
                android.R.layout.simple_spinner_item, languages
            )
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    Toast.makeText(
                        this@InsertNews,
                        getString(R.string.selected_item) + " " +
                                "" + languages[position], Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
        ref = FirebaseDatabase.getInstance().getReference("News")
        BInsert.setOnClickListener {
            savedata()
            val intent = Intent (this, MainNews::class.java)
            startActivity(intent)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            imgPath = data?.data
        }
    }

    private fun savedata() {
        val judul = ETJudul.text.toString()
        val isi = ETIsi.text.toString()
        val link = ETLink.text.toString()
        val linkimage = ETImageLink.text.toString()
        val kategori = SPKategori.getSelectedItem().toString()

        val newsId = ref.push().key.toString()
        val news = News(newsId,judul,isi,link, linkimage,kategori)

        ref.child(newsId).setValue(news).addOnCompleteListener {
            Toast.makeText(this, "Successs",Toast.LENGTH_SHORT).show()
            ETJudul.setText("")
            ETIsi.setText("")
            ETLink.setText("")

        }
    }


}