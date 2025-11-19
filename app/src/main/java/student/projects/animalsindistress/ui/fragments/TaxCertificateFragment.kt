package student.projects.animalsindistress.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import student.projects.animalsindistress.R
import student.projects.animalsindistress.data.ContactSubmission

class TaxCertificateFragment : Fragment(R.layout.fragment_tax_certificate) {
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val etFirstName = view.findViewById<EditText>(R.id.et_first_name)
        val etLastName = view.findViewById<EditText>(R.id.et_last_name)
        val etPhone = view.findViewById<EditText>(R.id.et_phone)
        val etResidentialAddress = view.findViewById<EditText>(R.id.et_residential_address)
        val etPostalCode = view.findViewById<EditText>(R.id.et_postal_code)
        val etIdNumber = view.findViewById<EditText>(R.id.et_id_number)
        val etTaxNumber = view.findViewById<EditText>(R.id.et_tax_number)
        val etReference = view.findViewById<EditText>(R.id.et_reference)
        val btnEmailSally = view.findViewById<Button>(R.id.btn_email_sally)
        
        btnEmailSally?.setOnClickListener {
            val firstName = etFirstName?.text?.toString()?.trim() ?: ""
            val lastName = etLastName?.text?.toString()?.trim() ?: ""
            val phone = etPhone?.text?.toString()?.trim() ?: ""
            val residentialAddress = etResidentialAddress?.text?.toString()?.trim() ?: ""
            val postalCode = etPostalCode?.text?.toString()?.trim() ?: ""
            val idNumber = etIdNumber?.text?.toString()?.trim() ?: ""
            val taxNumber = etTaxNumber?.text?.toString()?.trim() ?: ""
            val reference = etReference?.text?.toString()?.trim() ?: ""
            
            // Validate required fields
            if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty() || 
                residentialAddress.isEmpty() || postalCode.isEmpty() || 
                idNumber.isEmpty() || taxNumber.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            val fullName = "$firstName $lastName".trim()
            
            // Build email message
            val emailSubject = "18A Tax Certificate Request - $fullName"
            val emailBody = buildString {
                appendLine("18A Tax Certificate Request")
                appendLine("==========================")
                appendLine()
                appendLine("Full Name: $fullName")
                appendLine("Phone Number: $phone")
                appendLine("Residential Address: $residentialAddress")
                appendLine("Postal Code: $postalCode")
                appendLine("ID Number: $idNumber")
                appendLine("Registered Tax Number: $taxNumber")
                if (reference.isNotEmpty()) {
                    appendLine("Reference Used: $reference")
                }
            }
            
            // Create email intent
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:animals@animalsindistress.org.za")
                putExtra(Intent.EXTRA_SUBJECT, emailSubject)
                putExtra(Intent.EXTRA_TEXT, emailBody)
            }
            
            try {
                startActivity(intent)
                
                // Also save to Firestore for record keeping
                val db = Firebase.firestore
                val submission = ContactSubmission(
                    name = fullName,
                    email = "", // No email field in form
                    phone = phone,
                    message = "18A Tax Certificate Request\n\n$emailBody",
                    timestamp = Timestamp.now(),
                    read = false
                )
                
                db.collection("contact_submissions")
                    .add(submission)
                    .addOnSuccessListener {
                        // Clear form after successful submission
                        etFirstName?.setText("")
                        etLastName?.setText("")
                        etPhone?.setText("")
                        etResidentialAddress?.setText("")
                        etPostalCode?.setText("")
                        etIdNumber?.setText("")
                        etTaxNumber?.setText("")
                        etReference?.setText("")
                    }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "No email app found. Please email animals@animalsindistress.org.za directly.", Toast.LENGTH_LONG).show()
            }
        }
    }
}


