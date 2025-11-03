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
        
        // Donation amount buttons
        view.findViewById<View>(R.id.btn_donate_100)?.setOnClickListener {
            openDonationLink("100")
        }
        
        view.findViewById<View>(R.id.btn_donate_500)?.setOnClickListener {
            openDonationLink("500")
        }
        
        view.findViewById<View>(R.id.btn_donate_1000)?.setOnClickListener {
            openDonationLink("1000")
        }
        
        view.findViewById<View>(R.id.btn_donate_custom)?.setOnClickListener {
            openDonationLink("custom")
        }
        
        // Monthly debit button
        view.findViewById<View>(R.id.btn_monthly_debit)?.setOnClickListener {
            findNavController().navigate(R.id.monthlyDebitFragment)
        }
        
        // Contact buttons
        view.findViewById<View>(R.id.contact_phone)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:0113231920"))
            startActivity(intent)
        }
        
        view.findViewById<View>(R.id.contact_mobile)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:0823707992"))
            startActivity(intent)
        }
        
        view.findViewById<View>(R.id.contact_email)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:admin@animalsindistress.org.za")
            }
            startActivity(intent)
        }
        
        // Tax certificate button
        view.findViewById<View>(R.id.btn_tax_certificate)?.setOnClickListener {
            openUrl("https://animalsindistress.org.za/downloads/18A-Certificate.pdf")
        }
        
        // Other ways to help links
        view.findViewById<View>(R.id.help_volunteer)?.setOnClickListener {
            findNavController().navigate(R.id.volunteerFragment)
        }
        
        view.findViewById<View>(R.id.help_shop)?.setOnClickListener {
            findNavController().navigate(R.id.shopFragment)
        }
        
        view.findViewById<View>(R.id.help_preloved)?.setOnClickListener {
            findNavController().navigate(R.id.contactFragment)
        }
        
        view.findViewById<View>(R.id.help_myschool)?.setOnClickListener {
            openUrl("https://www.myschool.co.za/")
        }
    }
    
    private fun openDonationLink(amount: String) {
        // Open PayFast donation page (matching Compose version)
        val url = when(amount) {
            "100" -> "https://www.payfast.co.za/eng/process?cmd=_donations&receiver=11654229&item_name=General+Donation&amount=100"
            "500" -> "https://www.payfast.co.za/eng/process?cmd=_donations&receiver=11654229&item_name=General+Donation&amount=500"
            "1000" -> "https://www.payfast.co.za/eng/process?cmd=_donations&receiver=11654229&item_name=General+Donation&amount=1000"
            else -> "https://www.payfast.co.za/eng/process?cmd=_donations&receiver=11654229&item_name=General+Donation"
        }
        openUrl(url)
    }
    
    private fun openUrl(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Unable to open link", Toast.LENGTH_SHORT).show()
        }
    }
}


