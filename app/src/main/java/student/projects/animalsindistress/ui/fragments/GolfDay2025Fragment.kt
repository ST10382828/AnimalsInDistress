package student.projects.animalsindistress.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import student.projects.animalsindistress.R

class GolfDay2025Fragment : Fragment(R.layout.fragment_golf_day_2025) {
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        view.findViewById<View>(R.id.btn_register_interest).setOnClickListener {
            findNavController().navigate(R.id.contactFragment)
        }
    }
}


