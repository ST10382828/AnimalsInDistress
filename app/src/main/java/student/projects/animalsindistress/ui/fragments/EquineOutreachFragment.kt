package student.projects.animalsindistress.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import student.projects.animalsindistress.R

class EquineOutreachFragment : Fragment(R.layout.fragment_equine_outreach) {
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        view.findViewById<View>(R.id.btn_donate_equine).setOnClickListener {
            findNavController().navigate(R.id.donateFragment)
        }
        
        view.findViewById<View>(R.id.btn_volunteer_equine).setOnClickListener {
            findNavController().navigate(R.id.volunteerFragment)
        }
    }
}


