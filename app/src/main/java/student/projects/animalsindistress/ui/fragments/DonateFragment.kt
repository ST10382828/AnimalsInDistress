package student.projects.animalsindistress.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import student.projects.animalsindistress.R
import student.projects.animalsindistress.data.DonationImpactRepository

class DonateFragment : Fragment(R.layout.fragment_donate) {
    
    private val repository = DonationImpactRepository()
    private val auth = FirebaseAuth.getInstance()
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Link to Impact Dashboard
        view.findViewById<View>(R.id.btn_view_impact)?.setOnClickListener {
            findNavController().navigate(R.id.donationImpactFragment)
        }
        
        // Donation amount buttons - navigate to in-app donation form
        view.findViewById<View>(R.id.btn_donate_100)?.setOnClickListener {
            findNavController().navigate(R.id.makeDonationFragment)
        }
        
        view.findViewById<View>(R.id.btn_donate_500)?.setOnClickListener {
            findNavController().navigate(R.id.makeDonationFragment)
        }
        
        view.findViewById<View>(R.id.btn_donate_1000)?.setOnClickListener {
            findNavController().navigate(R.id.makeDonationFragment)
        }
        
        view.findViewById<View>(R.id.btn_donate_custom)?.setOnClickListener {
            findNavController().navigate(R.id.makeDonationFragment)
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
        // Show impact preview before redirecting to payment
        val amountValue = when(amount) {
            "100" -> 100.0
            "500" -> 500.0
            "1000" -> 1000.0
            else -> 0.0
        }
        
        if (amountValue > 0) {
            val impact = student.projects.animalsindistress.data.Donation.calculateImpact(amountValue)
            
            val message = buildString {
                append("Your R${amountValue.toInt()} donation will:\n\n")
                if (impact.dogsFeeding > 0) append("🐕 Feed ${impact.dogsFeeding} dogs\n")
                if (impact.surgeriesEnabled > 0) append("🏥 Enable ${impact.surgeriesEnabled} surgery(ies)\n")
                if (impact.clinicsSupported > 0) append("🚑 Support ${impact.clinicsSupported} mobile clinic(s)\n")
                append("\nProceed to secure payment?")
            }
            
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Your Impact")
                .setMessage(message)
                .setPositiveButton("Continue to Payment") { _, _ ->
                    proceedToPayment(amount, amountValue)
                }
                .setNegativeButton("Cancel", null)
                .show()
        } else {
            proceedToPayment(amount, 0.0)
        }
    }
    
    private fun proceedToPayment(amount: String, amountValue: Double) {
        // Open PayFast donation page
        val url = when(amount) {
            "100" -> "https://www.payfast.co.za/eng/process?cmd=_donations&receiver=11654229&item_name=General+Donation&amount=100"
            "500" -> "https://www.payfast.co.za/eng/process?cmd=_donations&receiver=11654229&item_name=General+Donation&amount=500"
            "1000" -> "https://www.payfast.co.za/eng/process?cmd=_donations&receiver=11654229&item_name=General+Donation&amount=1000"
            else -> "https://www.payfast.co.za/eng/process?cmd=_donations&receiver=11654229&item_name=General+Donation"
        }
        openUrl(url)
        
        // Track donation attempt (in production, actual recording would happen via webhook after successful payment)
        if (amountValue > 0) {
            trackDonationAttempt(amountValue)
        }
    }
    
    private fun trackDonationAttempt(amount: Double) {
        // This is a simulation. In production, you'd only record after successful payment
        // via a PayFast webhook or return URL confirmation
        val user = auth.currentUser
        if (user != null) {
            Toast.makeText(requireContext(), 
                "Donation tracking enabled. Visit Impact Dashboard after payment to see your impact!", 
                Toast.LENGTH_LONG).show()
        }
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


