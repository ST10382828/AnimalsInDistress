package student.projects.animalsindistress.ui.fragments

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import student.projects.animalsindistress.R
import android.os.Bundle
import android.view.View
import android.widget.Toast

class ContactFragment : Fragment(R.layout.fragment_contact) {
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Phone section - dial when clicked
        view.findViewById<View>(R.id.phone_section)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:0114660261"))
            startActivity(intent)
        }
        
        // Email section - send email when clicked
        view.findViewById<View>(R.id.email_section)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:animals@animalsindistress.org.za"))
            startActivity(intent)
        }
        
        // Social media buttons
        view.findViewById<View>(R.id.btn_facebook)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/tsfaid"))
            startActivity(intent)
        }
        
        view.findViewById<View>(R.id.btn_twitter)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/SaidFundraiser"))
            startActivity(intent)
        }
        
        view.findViewById<View>(R.id.btn_instagram)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/thesocietyforanimalsindistress/"))
            startActivity(intent)
        }
        
        // Submit contact form
        view.findViewById<View>(R.id.btn_submit_contact)?.setOnClickListener {
            Toast.makeText(requireContext(), "Form submitted!", Toast.LENGTH_SHORT).show()
            // In a real app, would collect form data and send to server/email
        }
    }
}


