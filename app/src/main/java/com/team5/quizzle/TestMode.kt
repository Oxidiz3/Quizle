package com.team5.quizzle

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TestMode : Fragment() {
    private var word: String? = "loading..."
    private var defintion: String? = null


    lateinit var wordTextView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getVocab()

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        var view = inflater.inflate(R.layout.fragment_test_mode, container, false)
        // Inflate the layout for this fragment
        wordTextView = view.findViewById<TextView>(R.id.textView3)
        wordTextView.text = word

        return view
    }

    private fun getVocab() {
        val db = Firebase.firestore
        val subject = bundle.getString(titleKey)
        Log.d(ContentValues.TAG, "$subject")
        val docRef = db.collection("Flashcard").document("$subject-1")
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(ContentValues.TAG, "DocumentSnapshot data: ${document.data}")
                    word = document.data?.get("Word")?.toString()
                    wordTextView.text = word
                    defintion = document.data?.get("Definition")?.toString()
                    Log.d(ContentValues.TAG, "Word: $word Definition: $defintion")
                } else {
                    Log.d(ContentValues.TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }

    }
}