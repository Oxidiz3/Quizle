package com.team5.quizzle

import android.content.ContentValues.TAG
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Document

class CardCreation : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_card_creation, container, false)
        view.findViewById<Button>(R.id.cardCreationSubmit).setOnClickListener{

            // Gets answer or definition from the back side field
            var answer = view.findViewById<TextInputEditText>(R.id.backSideInput).text

            // Gets question or prompt or vocab word or prompt from the front side
            var question = view.findViewById<TextInputEditText>(R.id.frontSideInput).text

            // Runs the database function to load the card into the database
            dataBase(question = question.toString(), answer = answer.toString())

            // Informs the user that the upload was successful using a snackbar
            Snackbar.make(view, "Uploaded successfully", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

            // Returns to the main screen
            Navigation.findNavController(view).navigate(R.id.action_cardCreation_to_mainContent)
        }
        return view
    }
    private fun dataBase(question: String, answer: String) {

        // Used to flag if the flash card exists
        var wordExists = false

        //Flash card from user inputed fiels
        val firstCard = hashMapOf(
            "Word" to question,
            "Definition" to answer
        )

        //DEBUG

        Log.d(TAG, "Document firstCard ${firstCard}")

        //Check for flash card in database
        val db = Firebase.firestore
        val cardRef = db.collection("Flashcard")

        // Look at all the cards with the same vocab word or question
        val cardQuery = cardRef.whereEqualTo("Word", question)
            .get()
            //Set an on success listner to run when those cards are found
            .addOnSuccessListener { documents ->

                //Look at each of those flash cards
                for (document in documents) {

                    //DEBUG
                    Log.d(TAG, "Document data defintion: ${document.data["Definition"]}")
                    Log.d(TAG, "firstCard def: ${firstCard["Definition"]}")

                    // Match the definitions / answers against each other
                    if (document.data["Definition"] == firstCard["Definition"]) {
                        //Flag true if it exists
                        wordExists = true
                    }
                }

                if (wordExists){
                    Log.d(TAG, "Word exists")
                }
                else{
                    //If it doesn't exist then add to database
                    db.collection("Flashcard")
                        .add(firstCard)
                }
            }
        }
}