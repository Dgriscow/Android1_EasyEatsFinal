package com.example.android1_easyeatsfinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //go to food entry button
        val curateViewButton = findViewById<Button>(R.id.toCurate)




        curateViewButton.setOnClickListener {
            val toCurate = Intent(this@MainActivity, CurateFoodView::class.java)
            startActivity(toCurate)
        }




    }
}