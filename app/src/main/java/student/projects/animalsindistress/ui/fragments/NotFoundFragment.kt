package student.projects.animalsindistress.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import student.projects.animalsindistress.R

class NotFoundFragment : Fragment(R.layout.fragment_not_found) {
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        view.findViewById<View>(R.id.btn_go_home).setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
        
        view.findViewById<View>(R.id.btn_go_back).setOnClickListener {
            findNavController().popBackStack()
        }
    }
}


