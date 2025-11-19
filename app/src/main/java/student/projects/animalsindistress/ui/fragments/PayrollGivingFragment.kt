package student.projects.animalsindistress.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import student.projects.animalsindistress.R

class PayrollGivingFragment : Fragment(R.layout.fragment_payroll_giving) {
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Contact fundraising button
        view.findViewById<View>(R.id.btn_contact_fundraising)?.setOnClickListener {
            // Navigate to contact page or open email
            try {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:fundraiser@animalsindistress.org.za")
                    putExtra(Intent.EXTRA_SUBJECT, "Payroll Giving Inquiry")
                }
                startActivity(intent)
            } catch (e: Exception) {
                // Fallback to contact page if no email app
                findNavController().navigate(R.id.contactFragment)
            }
        }
    }
}


