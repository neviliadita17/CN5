package com.example.cn5

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.news.*

class Adapter(val mCtx: Context, val layoutResId: Int, val list: List<News> )
    : ArrayAdapter<News>(mCtx,layoutResId,list){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId,null)

        val textUpdate = view.findViewById<Button>(R.id.BtnUpdate)
        val textDelete = view.findViewById<Button>(R.id.BtnDelete)

        val textJudul = view.findViewById<TextView>(R.id.textJudul)
        val textLink = view.findViewById<TextView>(R.id.textLink)
        val image = view.findViewById<ImageView>(R.id.img_item_photo)
        val news = list[position]

        textJudul.text = news.judul
        textLink.text = news.link
        var url = news.linkimage

        //dependency glide dipanggil dan diterapkan ke image1
            Glide.with(context)
            .load(url)
            .placeholder(R.color.colorPrimary)
            .error(R.color.colorPrimary)
                . into(image)



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
        val textLinkImage = view.findViewById<EditText>(R.id.inputLinkImage)
        val textKategori = view.findViewById<EditText>(R.id.inputKategori)

        textJudul.setText(news.judul)
        textIsi.setText(news.isi)
        textLink.setText(news.link)
        textLinkImage.setText(news.linkimage)
        textKategori.setText(news.kategori)

        builder.setView(view)
        builder.setPositiveButton("Update") { dialog, which ->
            val dbNews = FirebaseDatabase.getInstance().getReference("News")
            val judul = textJudul.text.toString().trim()
            val isi = textIsi.text.toString().trim()
            val link = textLink.text.toString().trim()
            val linkimage = textLinkImage.text.toString().trim()
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
                textLink.error = "Masukan Link"
                textLink.requestFocus()
                return@setPositiveButton
            }
            if (link.isEmpty()){
                textLinkImage.error = "Masukan Link Image"
                textLinkImage.requestFocus()
                return@setPositiveButton
            }
            if (kategori.isEmpty()){
                textKategori.error = "Masukan Kategori"
                textKategori.requestFocus()
                return@setPositiveButton
            }
            val news = News(news.id,judul,isi, link, linkimage, kategori)

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
