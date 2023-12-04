package com.example.android1_easyeatsfinal

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import com.google.gson.Gson
import kotlin.math.roundToInt

class MacroDashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_macro_dashboard)

        val backToCurate = findViewById<Button>(R.id.backToCurate)




        //TODO curate the latest foods added and use it as a meal plan
        val currentMacros = mealPlanMacros()
        //TODO calculate macros remaining and apply them to the percentage bars
        val userData = UserSample()
        setAndDisplayMacroBars(currentMacros, userData)

        backToCurate.setOnClickListener {
            val back2CurateIntent = Intent(this@MacroDashboard, CurateFoodView::class.java)
            startActivity(back2CurateIntent)
        }



    }

    fun setAndDisplayMacroBars(currentMacros:Array<Double>, userValues:UserSample){
        //Connect to Macro Bars
        val calorieBar = findViewById<ProgressBar>(R.id.barCalorie)
        val proteinBar = findViewById<ProgressBar>(R.id.barProtein)
        val carbBar = findViewById<ProgressBar>(R.id.barCarbs)
        val fatBar = findViewById<ProgressBar>(R.id.barFat)


        //TODO calculate macro estimates and set them to values
        val calsRemaining:Double = currentMacros[0] / userValues.maxCalories * 100
        val proteinRemaining:Double = currentMacros[1] / userValues.maxProtein * 100
        val carbsRemaining:Double = currentMacros[2] / userValues.maxCarbs * 100
        val fatRemaining:Double = currentMacros[3] / userValues.maxFat * 100

        calorieBar.progress = calsRemaining.roundToInt()
        proteinBar.progress = proteinRemaining.roundToInt()
        carbBar.progress = carbsRemaining.roundToInt()
        fatBar.progress = fatRemaining.roundToInt()



    }

    fun mealPlanMacros():Array<Double>{

        //get the current array of foods
        val currentFoods = getCurrentFoods()

        //macros
        var currentCalories:Double = 0.0
        var currentProtein:Double = 0.0
        var currentCarbs:Double = 0.0
        var currentFat:Double = 0.0

        //TODO loop through every food and set them to a value
        for (food in currentFoods){
            currentCalories += food.calories
            currentProtein += food.protein
            currentCarbs += food.carbs
            currentFat += food.fat

        }
        //Array to store current values and to return it later
        val tmpCurrentMacros:Array<Double> = arrayOf<Double>(currentCalories, currentProtein, currentCarbs, currentFat)

        return tmpCurrentMacros
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
}