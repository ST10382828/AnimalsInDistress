package student.projects.animalsindistress.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import student.projects.animalsindistress.R

class DonateFragment : Fragment(R.layout.fragment_donate) {
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Credit card donation buttons
        view.findViewById<View>(R.id.btn_donate_100)?.setOnClickListener {
            Toast.makeText(requireContext(), "Processing R100 donation...", Toast.LENGTH_SHORT).show()
            // In a real app, would open payment gateway
        }
        
        view.findViewById<View>(R.id.btn_donate_500)?.setOnClickListener {
            Toast.makeText(requireContext(), "Processing R500 donation...", Toast.LENGTH_SHORT).show()
        }
        
        view.findViewById<View>(R.id.btn_donate_1000)?.setOnClickListener {
            Toast.makeText(requireContext(), "Processing R1000 donation...", Toast.LENGTH_SHORT).show()
        }
        
        view.findViewById<View>(R.id.btn_donate_custom)?.setOnClickListener {
            Toast.makeText(requireContext(), "Opening custom amount form...", Toast.LENGTH_SHORT).show()
        }
        
        // Monthly debit order button
        view.findViewById<View>(R.id.btn_monthly_debit)?.setOnClickListener {
            findNavController().navigate(R.id.monthlyDebitFragment)
        }
        
        // Contact information - phone numbers
        view.findViewById<View>(R.id.contact_phone)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:0114660261"))
            startActivity(intent)
        }
        
        view.findViewById<View>(R.id.contact_mobile)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:0836408825"))
            startActivity(intent)
        }
        
        // Contact information - email
        view.findViewById<View>(R.id.contact_email)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:animals@animalsindistress.org.za"))
            startActivity(intent)
        }
        
        // Tax certificate button
        view.findViewById<View>(R.id.btn_tax_certificate)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:animals@animalsindistress.org.za?subject=Tax Certificate Request"))
            startActivity(intent)
        }
        
        // Help links at the bottom
        view.findViewById<View>(R.id.help_volunteer)?.setOnClickListener {
            findNavController().navigate(R.id.volunteerFragment)
        }
        
        view.findViewById<View>(R.id.help_shop)?.setOnClickListener {
            findNavController().navigate(R.id.shopFragment)
        }
        
        view.findViewById<View>(R.id.help_preloved)?.setOnClickListener {
            findNavController().navigate(R.id.preLovedFragment)
        }
        
        view.findViewById<View>(R.id.help_myschool)?.setOnClickListener {
            findNavController().navigate(R.id.mySchoolFragment)
        }
    }
}


