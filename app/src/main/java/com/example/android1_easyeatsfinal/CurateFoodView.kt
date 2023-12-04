package com.example.android1_easyeatsfinal

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.random.Random

class CurateFoodView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_curate_food_view)

        //TODO: create a function to populate the screen with a randomly selected item from the Foods Array


        val back_to_input = findViewById<Button>(R.id.back2Input)
        val to_Dashboard = findViewById<Button>(R.id.toDashboard)

        val curateButton =  findViewById<Button>(R.id.btnCurate)

        val foodImage = findViewById<ImageView>(R.id.foodImage)

        val foodNameTitle = findViewById<TextView>(R.id.txtFoodTitle)
        val calorieStats = findViewById<TextView>(R.id.amtCals)
        val carbsStats = findViewById<TextView>(R.id.amtCarbs)
        val fatStats = findViewById<TextView>(R.id.amtFat)
        val proteinStats = findViewById<TextView>(R.id.amtProtein)



        //Ass soon as the screen appears, Curate a random Food
        curateRandomFood()




        //Curate Button
        curateButton.setOnClickListener {
            curateRandomFood()
        }




        back_to_input.setOnClickListener {
            val back2InputIntent = Intent(this@CurateFoodView, MainActivity::class.java)
            startActivity(back2InputIntent)
        }

        to_Dashboard.setOnClickListener {
            val toDashboardIntent = Intent(this@CurateFoodView, MacroDashboard::class.java)
            startActivity(toDashboardIntent)
        }




    }
    fun getCurrentFoods(): Array<Food> {
        val gson = Gson()

        val FOODKEY = getString(R.string.FoodsKey)
        //shared Preference editor of the Foods preference
        val sharedPrefs: SharedPreferences = this.getSharedPreferences("User", Context.MODE_PRIVATE)

        // Default value (an empty set in this case), in case anything goes wrong or the preference is empty
        val defaultValue: String = ""

        // Retrieve the set from SharedPreferences
        //CF = Current Foods
        val retrivedCFString: String? = sharedPrefs.getString(FOODKEY, defaultValue)

        //Convert the String Set Into a Array of Foods
        //Loop through each item in the set and convert it to a Array

        //make sure that the string set isint Null, then preform loop addition

        //Finally once the Loop is complete, return the full array of Food Items
        var arrayedJson = jsonStringToArray(retrivedCFString)

        return arrayedJson


    }

    // Function to convert array to JSON string
    fun arrayToJsonString(array: Array<Food>): String {
        val gson = Gson()
        return gson.toJson(array)
    }

    // Function to convert JSON string to array
    fun jsonStringToArray(jsonString: String?): Array<Food> {
        val gson = Gson()
        // The second parameter of fromJson specifies the type of the object to be converted to
        var foodArray = gson.fromJson(jsonString, Array<Food>::class.java)
        //Null check, if the returned Value is null use a empty array
        if (foodArray == null){
            val tempFood = Food()
            var temp:Array<Food> = arrayOf<Food>(tempFood)
            foodArray = temp

        }

        return foodArray
    }

    fun curateRandomFood(){


        //Macro Values
        val foodNameTitle = findViewById<TextView>(R.id.txtFoodTitle)

        val foodImage = findViewById<AppCompatImageView>(R.id.foodImage)


        val calorieStats = findViewById<TextView>(R.id.amtCals)
        val carbsStats = findViewById<TextView>(R.id.amtCarbs)
        val fatStats = findViewById<TextView>(R.id.amtFat)
        val proteinStats = findViewById<TextView>(R.id.amtProtein)


        //curate the latest foods before doing anything
        val currentFoods = getCurrentFoods()

        //size of the Array
        val arraySize:Int = currentFoods.size

        //generate a random integer from 0 to the arrays size
        val randomIndex:Int = Random.nextInt(0, arraySize)

        //generate a random Food
        val randomFood:Food = currentFoods[randomIndex]


        //TODO Set the screen values to the respective Values
        foodNameTitle.text = randomFood.foodName
        calorieStats.text = randomFood.calories.toInt().toString()
        carbsStats.text = randomFood.carbs.toInt().toString()
        fatStats.text = randomFood.fat.toInt().toString()
        proteinStats.text = randomFood.protein.toInt().toString()

        //TODO set the on screen image
        val imageURL = randomFood.imgLink

        //load image from url string using picasso
        Log.e("lego", "CUSTOM IMAGE LOADER $imageURL")
        Picasso.with(this)
            .load(imageURL)
            .resize(263, 300)
            .centerCrop()
            .into(foodImage)





        Log.e("lego", "CIL done")





    }


}