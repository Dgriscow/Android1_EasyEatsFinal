package com.example.android1_easyeatsfinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class CurateFoodView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_curate_food_view)


        val back_to_input = findViewById<Button>(R.id.back2Input)





        back_to_input.setOnClickListener {
            val back2InputIntent = Intent(this@CurateFoodView, MainActivity::class.java)
            startActivity(back2InputIntent)
        }





    }
}