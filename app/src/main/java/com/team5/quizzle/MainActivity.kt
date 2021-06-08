package com.team5.quizzle

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.navigation.findNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))


        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
//            testDb()
        }
        val mathButton : Button = findViewById(R.id.math_button)
        mathButton.setOnClickListener {flashCards("math")}
        }
//        findViewById<FloatingActionButton>(R.id.math_button).setOnClickListener { view ->
//            Snackbar.make(view, "Hello World!", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
    private fun flashCards(subject: String){
        val newIntent = Intent(this, FlashCards::class.java)
        var databaseGet = ""
        when (subject) {
            "math" -> databaseGet = "math"
            "science " -> databaseGet = "science"
            "reading" -> databaseGet = "reading"
            "writing" -> databaseGet = "writing"
        }
        newIntent.putExtra("subject", databaseGet)
        startActivity(newIntent)
    }
/*        private fun testDb(){

            val firstCard = hashMapOf(
                "Word" to "Socks",
                "Definition" to "Feet gloves"
            )
            val db = Firebase.firestore
            db.collection("Flashcard")
                .add(firstCard)
        }*/
//
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}