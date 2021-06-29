package com.team5.quizzle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CardCreation.newInstance] factory method to
 * create an instance of this fragment.
 */
class CardCreation : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_card_creation, container, false)
        view.findViewById<Button>(R.id.cardCreationSubmit).setOnClickListener{

            //Make the call to the database here vvvvvvv
            var question = view.findViewById<TextInputEditText>(R.id.backSideInput).text
            var answer = view.findViewById<TextInputEditText>(R.id.frontSideInput).text
            dataBase(question = question.toString(), answer = answer.toString())
            Navigation.findNavController(view).navigate(R.id.action_cardCreation_to_mainContent)


        }
        return view
    }
    private fun dataBase(question: String, answer: String): Boolean {

        val firstCard = hashMapOf(
            "Word" to question,
            "Definition" to answer
        )
        val db = Firebase.firestore
        val cardRef = db.collection("Flashcard")
        val cardQuery = cardRef.whereEqualTo("Word", question)
            .get()

        if (cardQuery.equals(firstCard)){
            return false
        }


            db.collection("Flashcard")
                .add(firstCard)
            return true
        }


    private fun check() {

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CardCreation.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CardCreation().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}