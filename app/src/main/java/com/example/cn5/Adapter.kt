package com.example.cn5

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.FirebaseDatabase

class Adapter(val mCtx: Context, val layoutResId: Int, val list: List<News> )
    : ArrayAdapter<News>(mCtx,layoutResId,list){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId,null)

        val textUpdate = view.findViewById<TextView>(R.id.TextUpdate)
        val textDelete = view.findViewById<TextView>(R.id.TextDelete)

        val textJudul = view.findViewById<TextView>(R.id.textJudul)
        val textIsi = view.findViewById<TextView>(R.id. textIsi)

        val news = list[position]

        textJudul.text = news.judul
        textIsi.text = news.isi


        textUpdate.setOnClickListener {
            showUpdateDialog(news)
        }
        textDelete.setOnClickListener {
            Deleteinfo(news)
        }

        return view

    }

    private fun Deleteinfo(news: News) {
        val progressDialog = ProgressDialog(context,
            R.style.AppTheme)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Deleting...")
        progressDialog.show()
        val mydatabase = FirebaseDatabase.getInstance().getReference("News")
        mydatabase.child(news.id).removeValue()
        Toast.makeText(mCtx,"Deleted!!",Toast.LENGTH_SHORT).show()
        val intent = Intent(context, MainNews::class.java)
        context.startActivity(intent)
    }

    private fun showUpdateDialog(news: News) {
        val builder = AlertDialog.Builder(mCtx)
        builder.setTitle("Update")
        val inflater = LayoutInflater.from(mCtx)





        val view = inflater.inflate(R.layout.update, null)
        val textJudul = view.findViewById<EditText>(R.id.inputJudul)
        val textIsi = view.findViewById<EditText>(R.id.inputIsi)
        val textLink = view.findViewById<EditText>(R.id.inputLink)
        val textKategori = view.findViewById<EditText>(R.id.inputKategori)

        textJudul.setText(news.judul)
        textIsi.setText(news.isi)
        textLink.setText(news.link)
        textKategori.setText(news.kategori)

        builder.setView(view)
        builder.setPositiveButton("Update") { dialog, which ->
            val dbNews = FirebaseDatabase.getInstance().getReference("News")
            val judul = textJudul.text.toString().trim()
            val isi = textIsi.text.toString().trim()
            val link = textLink.text.toString().trim()
            val kategori = textKategori.text.toString().trim()
            if (judul.isEmpty()){
                textJudul.error = "Masukan Judul"
                textJudul.requestFocus()
                return@setPositiveButton
            }
            if (isi.isEmpty()){
                textIsi.error = "Masukan Isi"
                textIsi.requestFocus()
                return@setPositiveButton
            }
            if (link.isEmpty()){
                textLink.error = "Masukan Isi"
                textLink.requestFocus()
                return@setPositiveButton
            }
            if (kategori.isEmpty()){
                textKategori.error = "Masukan Isi"
                textKategori.requestFocus()
                return@setPositiveButton
            }
            val news = News(news.id,judul,isi, link, kategori)

            dbNews.child(news.id).setValue(news).addOnCompleteListener {
                Toast.makeText(mCtx,"Updated",Toast.LENGTH_SHORT).show()
            }

        }
        builder.setNegativeButton("No") { dialog, which ->
        }
        val alert = builder.create()
        alert.show()

    }
}
