package student.projects.animalsindistress.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import student.projects.animalsindistress.R

class HomeFragment : Fragment(R.layout.fragment_home) {
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Hero donate button
        view.findViewById<View>(R.id.home_donate_btn)?.setOnClickListener {
            findNavController().navigate(R.id.donateFragment)
        }
        
        // Action cards
        view.findViewById<View>(R.id.action_donate)?.setOnClickListener {
            findNavController().navigate(R.id.donateFragment)
        }
        
        view.findViewById<View>(R.id.action_volunteer)?.setOnClickListener {
            findNavController().navigate(R.id.volunteerFragment)
        }
        
        view.findViewById<View>(R.id.action_partners)?.setOnClickListener {
            findNavController().navigate(R.id.partnersFragment)
        }
        
        view.findViewById<View>(R.id.action_shop)?.setOnClickListener {
            findNavController().navigate(R.id.shopFragment)
        }
        
        view.findViewById<View>(R.id.action_share)?.setOnClickListener {
            findNavController().navigate(R.id.contactFragment)
        }
        
        view.findViewById<View>(R.id.action_events)?.setOnClickListener {
            findNavController().navigate(R.id.eventCalendarFragment)
        }
        
        // Latest News contact button
        view.findViewById<View>(R.id.home_contact_btn)?.setOnClickListener {
            findNavController().navigate(R.id.contactFragment)
        }
        
        // Bottom CTA buttons
        view.findViewById<View>(R.id.home_donate_outline_btn)?.setOnClickListener {
            findNavController().navigate(R.id.donateFragment)
        }
        
        view.findViewById<View>(R.id.home_contact_outline_btn)?.setOnClickListener {
            findNavController().navigate(R.id.contactFragment)
        }
    }
}


