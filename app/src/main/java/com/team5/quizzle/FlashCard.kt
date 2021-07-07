package com.team5.quizzle

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FlashCard.newInstance] factory method to
 * create an instance of this fragment.
 */
class FlashCard : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var question: String? = null
    private var answer: String? = null

    lateinit var questionTextView : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        getVocab()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_flash_card, container, false)

        // Set the title text to the correct button
        view.findViewById<TextView>(R.id.titleText).text = bundle.getString(titleKey)

        questionTextView = view.findViewById<TextView>(R.id.textView3)


        view.findViewById<TextView>(R.id.textView3).text = answer
        // Answer button
        view.findViewById<Button>(R.id.btn_answer).setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_flashCard_to_mainContent)
        }

        // Back button
        view.findViewById<Button>(R.id.btn_back).setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_flashCard_to_mainContent)
        }

        return view
    }


    private fun getVocab() {
        val db = Firebase.firestore
        val subject = bundle.getString(titleKey)
        Log.d(TAG, "$subject")
        val docRef = db.collection("Flashcard").document("$subject-1")
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    question = document.data?.get("Word")?.toString()
                    questionTextView.text = question
                    answer = document.data?.get("Definition")?.toString()
                    Log.d(TAG, "Word: $question Definition: $answer")
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FlashCard.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FlashCard().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}