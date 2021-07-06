package com.team5.quizzle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation

class question : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_question, container, false)

        // study button
        view.findViewById<Button>(R.id.study_button).setOnClickListener{
            bundle.putString("Mode", "Study")
            Navigation.findNavController(view).navigate(R.id.action_question_to_flashCard)
        }

        // test button
        view.findViewById<Button>(R.id.test_button).setOnClickListener{
            bundle.putString("Mode", "Test")
            Navigation.findNavController(view).navigate(R.id.action_question_to_testMode)
        }

        return view
    }
}