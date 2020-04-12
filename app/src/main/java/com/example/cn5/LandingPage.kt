package com.example.cn5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_landing_page.*

class LandingPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page)
//        account = findViewById(android.R.id.account)
        account.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))}
    }

}
