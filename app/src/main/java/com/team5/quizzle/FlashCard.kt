package com.team5.quizzle

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.core.view.MotionEventCompat
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
    private var flipCounter: Int = 2
    private var index: Int = 1

    //Variable is set later so we use lateinit
    lateinit var questionTextView : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        //Run the get vocab to get the first flash card
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

        //initialize the question text view to the middle card text
        questionTextView = view.findViewById(R.id.textView3)


        // Set the text to the answer
        questionTextView.text = answer


        // Flip button
        view.findViewById<Button>(R.id.btn_answer).setOnClickListener{
            //Switch the flip counter between 2 and 3 to decide which side of the card to look at
            if (flipCounter % 2 == 0){
                //Answer side
                questionTextView.text = answer
                flipCounter += 1
            }
            else{
                //question side
                questionTextView.text = question
                flipCounter -= 1
            }

        }

        //set on click listener to the text view to switch cards when they are pressed
        questionTextView.setOnClickListener {
            index += 1
            getVocab()
            //Check to see if the card is null
            if (question == null){
                index = 1
                getVocab()
            }
        }
        // Back button
        view.findViewById<Button>(R.id.btn_back).setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_flashCard_to_mainContent)
        }

        return view
    }


    private fun getVocab() {

        val db = Firebase.firestore
        //get the subject string from the title
        val subject = bundle.getString(titleKey)
        Log.d(TAG, "$subject")

        //Call the card based on the subject and index from the database
        val docRef = db.collection("Flashcard").document("$subject-$index")
        docRef.get()
                //add on success listener to run code once the database document is obtained
            .addOnSuccessListener { document ->

                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    //set the question variable
                    question = document.data?.get("Word")?.toString()
                    //change the text view to show the question/word
                    questionTextView.text = question
                    //set the answer variable
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