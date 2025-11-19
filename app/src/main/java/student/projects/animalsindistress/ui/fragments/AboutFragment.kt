package student.projects.animalsindistress.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import student.projects.animalsindistress.R

class AboutFragment : Fragment(R.layout.fragment_about) {
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Make Equine Support Unit card clickable to navigate to Equine Outreach
        view.findViewById<View>(R.id.card_equine_support)?.setOnClickListener {
            findNavController().navigate(R.id.equineOutreachFragment)
        }
    }
}


