package student.projects.animalsindistress.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import student.projects.animalsindistress.R

class MoreFragment : Fragment(R.layout.fragment_more) {
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Setup click listeners for all menu items
        view.findViewById<View>(R.id.menu_item_volunteer)?.setOnClickListener {
            findNavController().navigate(R.id.volunteerFragment)
        }
        
        view.findViewById<View>(R.id.menu_item_shop)?.setOnClickListener {
            findNavController().navigate(R.id.shopFragment)
        }
        
        view.findViewById<View>(R.id.menu_item_news)?.setOnClickListener {
            findNavController().navigate(R.id.newsListFragment)
        }
        
        view.findViewById<View>(R.id.menu_item_success_stories)?.setOnClickListener {
            findNavController().navigate(R.id.storiesFeedFragment)
        }
        
        view.findViewById<View>(R.id.menu_item_case_study)?.setOnClickListener {
            findNavController().navigate(R.id.caseStudyFragment)
        }
        
        view.findViewById<View>(R.id.menu_item_team)?.setOnClickListener {
            findNavController().navigate(R.id.teamFragment)
        }
        
        view.findViewById<View>(R.id.menu_item_partners)?.setOnClickListener {
            findNavController().navigate(R.id.partnersFragment)
        }
        
        view.findViewById<View>(R.id.menu_item_services)?.setOnClickListener {
            findNavController().navigate(R.id.servicesFragment)
        }
        
        view.findViewById<View>(R.id.menu_item_programs)?.setOnClickListener {
            findNavController().navigate(R.id.programsFragment)
        }
        
        view.findViewById<View>(R.id.menu_item_equine_outreach)?.setOnClickListener {
            findNavController().navigate(R.id.equineOutreachFragment)
        }
        
        view.findViewById<View>(R.id.menu_item_contact)?.setOnClickListener {
            findNavController().navigate(R.id.contactFragment)
        }
        
        view.findViewById<View>(R.id.menu_item_call)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:0114660261"))
            startActivity(intent)
        }
        
        view.findViewById<View>(R.id.menu_item_email)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:animals@animalsindistress.org.za")
            }
            startActivity(intent)
        }
        
        view.findViewById<View>(R.id.menu_item_facebook)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/tsfaid"))
            startActivity(intent)
        }
        
        view.findViewById<View>(R.id.menu_item_instagram)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/thesocietyforanimalsindistress"))
            startActivity(intent)
        }
    }
}

