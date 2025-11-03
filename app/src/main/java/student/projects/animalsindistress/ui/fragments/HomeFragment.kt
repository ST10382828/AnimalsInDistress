package student.projects.animalsindistress.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import student.projects.animalsindistress.R

class HomeFragment : Fragment(R.layout.fragment_home) {
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Main donate button in hero section
        view.findViewById<MaterialButton>(R.id.home_donate_btn)?.setOnClickListener {
            findNavController().navigate(R.id.donateFragment)
        }
        
        // Action card - Donate Today
        view.findViewById<View>(R.id.action_donate)?.setOnClickListener {
            findNavController().navigate(R.id.donateFragment)
        }
        
        // Action card - Volunteer (Hop on board)
        view.findViewById<View>(R.id.action_volunteer)?.setOnClickListener {
            findNavController().navigate(R.id.volunteerFragment)
        }
        
        // Action card - Partners (Pawtner with us)
        view.findViewById<View>(R.id.action_partners)?.setOnClickListener {
            findNavController().navigate(R.id.contactFragment)
        }
        
        // Action card - Shop (Shop with a purrpose)
        view.findViewById<View>(R.id.action_shop)?.setOnClickListener {
            findNavController().navigate(R.id.shopFragment)
        }
        
        // Action card - Share (Roar the word)
        view.findViewById<View>(R.id.action_share)?.setOnClickListener {
            findNavController().navigate(R.id.contactFragment)
        }
        
        // Action card - Events (Paws for a cause)
        view.findViewById<View>(R.id.action_events)?.setOnClickListener {
            findNavController().navigate(R.id.newsListFragment)
        }
        
        // Latest Mews - Contact Us button
        view.findViewById<MaterialButton>(R.id.home_contact_btn)?.setOnClickListener {
            findNavController().navigate(R.id.contactFragment)
        }
        
        // Final CTA - Donate button
        view.findViewById<MaterialButton>(R.id.home_donate_outline_btn)?.setOnClickListener {
            findNavController().navigate(R.id.donateFragment)
        }
        
        // Final CTA - Contact Us button
        view.findViewById<MaterialButton>(R.id.home_contact_outline_btn)?.setOnClickListener {
            findNavController().navigate(R.id.contactFragment)
        }
    }
}


