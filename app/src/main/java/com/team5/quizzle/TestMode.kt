package com.team5.quizzle

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TestMode : Fragment() {
    private var word: String? = "loading..."
    private var defintion: String? = null
    private var userAnswer: String? = null
    private var index: Int = 1


    lateinit var wordTextView : TextView
    lateinit var textInput : TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getVocab()

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view = inflater.inflate(R.layout.fragment_test_mode, container, false)
        textInput = view.findViewById(R.id.answerField)
        textInput.setImeActionLabel("Answer", KeyEvent.KEYCODE_ENTER);

        // Inflate the layout for this fragment
        wordTextView = view.findViewById(R.id.textView3)
        wordTextView.text = word
        view.findViewById<Button>(R.id.btn_answer).setOnClickListener{
            userAnswer = textInput.text.toString()
            Log.d(TAG, "Answer: $userAnswer")

            if (checkAnswer(userAnswer!!)){
                Snackbar.make(view, "Correct Answer", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
                textInput.setText("")
                getVocab()


            }
            else{
                // Show the answer was wrong
                Snackbar.make(view, "Incorrect Answer", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
                Log.d(TAG, "User Incorrect")

            }
        }

        return view
    }

    private fun getVocab() {
        val db = Firebase.firestore
        val subject = bundle.getString(titleKey)
        Log.d(TAG, "$subject")
        val docRef = db.collection("Flashcard").document("$subject-$index")
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    word = document.data?.get("Word")?.toString()
                    wordTextView.text = word
                    defintion = document.data?.get("Definition")?.toString()
                    Log.d(TAG, "Word: $word Definition: $defintion")
                    index += 1
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
                index = 1
            }

    }
    private fun checkAnswer(userAnswer: String): Boolean {
        return userAnswer == defintion
    }

}