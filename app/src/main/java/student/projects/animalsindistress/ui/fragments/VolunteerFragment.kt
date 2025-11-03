package student.projects.animalsindistress.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import student.projects.animalsindistress.R

class VolunteerFragment : Fragment(R.layout.fragment_volunteer) {
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Contact Us button
        view.findViewById<View>(R.id.btn_contact_volunteer)?.setOnClickListener {
            findNavController().navigate(R.id.contactFragment)
        }
    }
}


