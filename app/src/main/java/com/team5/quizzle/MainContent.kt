package com.team5.quizzle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

val bundle = Bundle()
const val titleKey = "TitleKey"

class MainContent : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main_content, container, false)

        // Plus
        view.findViewById<FloatingActionButton>(R.id.addFlashCard).setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_mainContent_to_cardCreation)
        }

        // Math button
        view.findViewById<Button>(R.id.math_button).setOnClickListener{
            bundle.putString(titleKey, "Math")
            Navigation.findNavController(view).navigate(R.id.action_mainContent_to_flashCard)
        }

        // Writing button
        view.findViewById<Button>(R.id.writing_button).setOnClickListener{
            bundle.putString(titleKey, "Writing")
            Navigation.findNavController(view).navigate(R.id.action_mainContent_to_flashCard)
        }

        // Science button
        view.findViewById<Button>(R.id.science_button).setOnClickListener{
            bundle.putString(titleKey, "Science")
            Navigation.findNavController(view).navigate(R.id.action_mainContent_to_flashCard)
        }

        // Reading button
        view.findViewById<Button>(R.id.reading_button).setOnClickListener{
            bundle.putString(titleKey, "Reading")
            Navigation.findNavController(view).navigate(R.id.action_mainContent_to_flashCard)
        }

        return view
    }


}