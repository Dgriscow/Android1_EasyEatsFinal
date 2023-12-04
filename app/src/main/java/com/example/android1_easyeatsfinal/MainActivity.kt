package com.example.android1_easyeatsfinal

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //go to food entry button
        val curateViewButton = findViewById<Button>(R.id.toCurate)

        val lvIngredientInputs = findViewById<ListView>(R.id.lvNewIngs)

        val addIngToArray = findViewById<FloatingActionButton>(R.id.fbAddIng)


        //New Food Inputs
        val nameEntry = findViewById<EditText>(R.id.entryName)

        val calEntry = findViewById<EditText>(R.id.entryCalorie)
        val proteinEntry = findViewById<EditText>(R.id.entryProtein)
        val fatEntry = findViewById<EditText>(R.id.entryFat)
        val carbsEntry = findViewById<EditText>(R.id.entryCarbs)

        val imageURlEntry = findViewById<EditText>(R.id.entryImage)




        val ingredientEntry = findViewById<EditText>(R.id.entryIngredient)



        //Final Add Food Button

        val addNewFoodButton = findViewById<Button>(R.id.btnAddNewFood)

        //Temporary Array to Store Inputs of Ingredients before making a food object and saving it
        var newIngredients = arrayListOf<String>()


        //adapter for the food list view
        val ing_array_adapter: ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            newIngredients)

        lvIngredientInputs.adapter = ing_array_adapter


        //Clears Inputs From Screen
        fun clearInputs(){

            nameEntry.text.clear()

            calEntry.text.clear()

            proteinEntry.text.clear()

            fatEntry.text.clear()

            carbsEntry.text.clear()

            imageURlEntry.text.clear()

            //Clear Data Array
            newIngredients.clear()
            ing_array_adapter.notifyDataSetChanged()

        }



        //TODO Make it so the floating button takes whatever text is in the ing entry and when pressed adds it to the newIngredients Array
        addIngToArray.setOnClickListener{

            for (item in newIngredients){
                Log.e("log", "This World  $item.")
            }




            val anotherIngredient = ingredientEntry.text.toString()


            ingredientEntry.setText("")


            newIngredients.add(anotherIngredient)



            ing_array_adapter.notifyDataSetChanged()

        }


        //Todo Add the add Food Functionality
        /*
        When the add food button is pressed it needs to do the following:

        Ensure that the user has filled out ALL of the entry bars locations,
        Take all the information and convert it to a Food Data Object

        Add this NEW object to a shared preferences that can be access on any view of the program

         */
        addNewFoodButton.setOnClickListener {
            //Should Use various functions to preform this "heavy" button call

            val checkResult:Boolean = checkInputs()

            /*
            TODO preform check inputs and if it returns true, clear all values on screen,
            and create a new food object and add it to the users foods
             */

            if(checkResult){
                //check is successful

                //create a new food Item
                val newFood = Food(foodName = nameEntry.text.toString(), calories = calEntry.text.toString().toDouble(), protein = proteinEntry.text.toString().toDouble(), fat = fatEntry.text.toString().toDouble(), carbs = carbsEntry.text.toString().toDouble(), imgLink = imageURlEntry.text.toString(), ingredients = newIngredients)

                //Todo present a toast message saying food created when created
                Toast.makeText(applicationContext, "Successfully Created A New Food Item!", Toast.LENGTH_LONG).show()

                clearInputs()

                //Add the new foods to the preferences
                addNewFood(newFood)



            }



        }




        curateViewButton.setOnClickListener {
            val toCurate = Intent(this@MainActivity, CurateFoodView::class.java)
            startActivity(toCurate)
        }






    }




    private fun checkInputs():Boolean{

        //Entry Values repeated
        val nameEntry = findViewById<EditText>(R.id.entryName)

        val calEntry = findViewById<EditText>(R.id.entryCalorie)
        val proteinEntry = findViewById<EditText>(R.id.entryProtein)
        val fatEntry = findViewById<EditText>(R.id.entryFat)
        val carbsEntry = findViewById<EditText>(R.id.entryCarbs)

        val imageURlEntry = findViewById<EditText>(R.id.entryImage)

        val lvIngredientInputs = findViewById<ListView>(R.id.lvNewIngs)

        val ingredientEntry = findViewById<EditText>(R.id.entryIngredient)


        //check each section and return FALSE if anything didint

        //name Empty check
        if (nameEntry.text.toString().isEmpty()){
            //Return Error When empty
            nameEntry.error= "Enter Food Name"
            nameEntry.requestFocus()
            return false
        }

        //Calorie Empty check
        if (calEntry.text.toString().isEmpty()){
            //Return Error When empty
            calEntry.error= "Enter Calorie Entry Here"
            calEntry.requestFocus()
            return false
        }

        //Protein Empty check
        if (proteinEntry.text.toString().isEmpty()){
            //Return Error When empty
            proteinEntry.error= "Enter Calorie Entry Here"
            proteinEntry.requestFocus()
            return false
        }

        //Fat Empty check
        if (fatEntry.text.toString().isEmpty()){
            //Return Error When empty
            fatEntry.error= "Enter Fat Entry Here"
            fatEntry.requestFocus()
            return false
        }

        //Carbs Empty check
        if (carbsEntry.text.toString().isEmpty()){
            //Return Error When empty
            carbsEntry.error= "Enter Calorie Entry Here"
            carbsEntry.requestFocus()
            return false
        }

        //Image Empty check
        if (imageURlEntry.text.toString().isEmpty()){
            //Return Error When empty
            imageURlEntry.error= "Enter Calorie Entry Here"
            imageURlEntry.requestFocus()
            return false
        }

        //Todo: ListView Empty Check

        if (lvIngredientInputs.adapter.isEmpty){
            Log.e("log", "The cheese Burger LIVES")
            ingredientEntry.error = "Error\nEnter Some Ingredients To be a valid Food"
            return false

        }


        return true

    }

    //TODO update Food Preferences
    /*
    Get the all the latest foods
     */

    fun addNewFood(newFood: Food) {

        val FOODKEY = getString(R.string.FoodsKey)
        //shared Preference editor of the Foods preference
        val sharedPrefs: SharedPreferences = this.getSharedPreferences("User", Context.MODE_PRIVATE)

        //void function that JUST adds a new food to the food set

        //first grab all the old preferences and make a variable for it


        //Procedure:
        /*
        1: retrieve latest Array of Food objects
        2: add the new food item to the array
        3: convert the array into a string set of the Json format
        4: put the new set back into the preferences

         */

        //TODO get all foods that were entered into the preference
        var currentFoods = getCurrentFoods()
        //TODO with the arrray loaded, add the new food item, then convert the entire array back to json
        //Convert the array to a mutable array
        //add the New food to the custimizableFoods


        //Preform a null check, null is returned when the array is empty,
        val modifiableList = currentFoods.toMutableList()
        modifiableList.add(newFood)
        //TODO convert the array to a json string
        val newFoodsJson = arrayToJsonString(modifiableList.toTypedArray())

        //TODO set the Foods Preferences to the new updated current foods
        with(sharedPrefs.edit()) {
            putString(FOODKEY, newFoodsJson)
            apply()
        }



    }



    //Takes a object and converts it to Json
    fun foodToJson(newFood: Food): String {
        val gson = Gson()
        return gson.toJson(newFood)
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