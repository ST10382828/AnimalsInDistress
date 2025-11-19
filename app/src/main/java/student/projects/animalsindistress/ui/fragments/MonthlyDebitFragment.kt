package student.projects.animalsindistress.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import student.projects.animalsindistress.R
import student.projects.animalsindistress.data.ContactSubmission

class MonthlyDebitFragment : Fragment(R.layout.fragment_monthly_debit) {
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val etInitials = view.findViewById<EditText>(R.id.et_initials)
        val etTitle = view.findViewById<EditText>(R.id.et_title)
        val etFullName = view.findViewById<EditText>(R.id.et_full_name)
        val etEmail = view.findViewById<EditText>(R.id.et_email)
        val etContactNumber = view.findViewById<EditText>(R.id.et_contact_number)
        val etTaxAddress = view.findViewById<EditText>(R.id.et_tax_address)
        val etPaymentMethod = view.findViewById<EditText>(R.id.et_payment_method)
        val etBank = view.findViewById<EditText>(R.id.et_bank)
        val etBranch = view.findViewById<EditText>(R.id.et_branch)
        val etBranchCode = view.findViewById<EditText>(R.id.et_branch_code)
        val etAccountNumber = view.findViewById<EditText>(R.id.et_account_number)
        val etAccountType = view.findViewById<EditText>(R.id.et_account_type)
        val etCommencementDate = view.findViewById<EditText>(R.id.et_commencement_date)
        val etDonationAmount = view.findViewById<EditText>(R.id.et_donation_amount)
        val etTaxCertificate = view.findViewById<EditText>(R.id.et_tax_certificate)
        val etTaxFullName = view.findViewById<EditText>(R.id.et_tax_full_name)
        val etTaxPhone = view.findViewById<EditText>(R.id.et_tax_phone)
        val etIdNumber = view.findViewById<EditText>(R.id.et_id_number)
        val etTaxNumber = view.findViewById<EditText>(R.id.et_tax_number)
        val checkboxAgree = view.findViewById<CheckBox>(R.id.checkbox_agree)
        val btnSubmit = view.findViewById<Button>(R.id.btn_submit_debit)
        
        // Set default values
        etTitle?.setText("Mr")
        etPaymentMethod?.setText("Debit Order")
        etAccountType?.setText("Savings")
        etCommencementDate?.setText("01 Jan")
        etTaxCertificate?.setText("Yes")
        
        btnSubmit?.setOnClickListener {
            // Validate required fields
            val initials = etInitials?.text?.toString()?.trim() ?: ""
            val title = etTitle?.text?.toString()?.trim() ?: ""
            val fullName = etFullName?.text?.toString()?.trim() ?: ""
            val email = etEmail?.text?.toString()?.trim() ?: ""
            val contactNumber = etContactNumber?.text?.toString()?.trim() ?: ""
            val taxAddress = etTaxAddress?.text?.toString()?.trim() ?: ""
            val paymentMethod = etPaymentMethod?.text?.toString()?.trim() ?: ""
            val bank = etBank?.text?.toString()?.trim() ?: ""
            val branch = etBranch?.text?.toString()?.trim() ?: ""
            val branchCode = etBranchCode?.text?.toString()?.trim() ?: ""
            val accountNumber = etAccountNumber?.text?.toString()?.trim() ?: ""
            val accountType = etAccountType?.text?.toString()?.trim() ?: ""
            val commencementDate = etCommencementDate?.text?.toString()?.trim() ?: ""
            val donationAmount = etDonationAmount?.text?.toString()?.trim() ?: ""
            val taxCertificate = etTaxCertificate?.text?.toString()?.trim() ?: ""
            val taxFullName = etTaxFullName?.text?.toString()?.trim() ?: ""
            val taxPhone = etTaxPhone?.text?.toString()?.trim() ?: ""
            val idNumber = etIdNumber?.text?.toString()?.trim() ?: ""
            val taxNumber = etTaxNumber?.text?.toString()?.trim() ?: ""
            val agreed = checkboxAgree?.isChecked ?: false
            
            if (initials.isEmpty() || title.isEmpty() || fullName.isEmpty() || email.isEmpty() || 
                contactNumber.isEmpty() || taxAddress.isEmpty() || paymentMethod.isEmpty() ||
                bank.isEmpty() || branch.isEmpty() || branchCode.isEmpty() || accountNumber.isEmpty() ||
                accountType.isEmpty() || commencementDate.isEmpty() || donationAmount.isEmpty() ||
                taxCertificate.isEmpty() || !agreed) {
                Toast.makeText(requireContext(), "Please fill in all required fields and agree to the terms", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            // Build message with all form data
            val message = buildString {
                appendLine("MONTHLY DEBIT ORDER REQUEST")
                appendLine("==========================")
                appendLine()
                appendLine("Personal Information:")
                appendLine("Initials: $initials")
                appendLine("Title: $title")
                appendLine("Full Name: $fullName")
                appendLine("Email: $email")
                appendLine("Contact Number: $contactNumber")
                appendLine("Address for Tax Certificate: $taxAddress")
                appendLine()
                appendLine("Payment Method: $paymentMethod")
                appendLine()
                appendLine("Bank Details:")
                appendLine("Bank: $bank")
                appendLine("Branch: $branch")
                appendLine("Branch Code: $branchCode")
                appendLine("Account Number: $accountNumber")
                appendLine("Account Type: $accountType")
                appendLine()
                appendLine("Debit Order Details:")
                appendLine("Commencement Date: $commencementDate")
                appendLine("Monthly Donation Amount: R $donationAmount")
                appendLine()
                appendLine("18A Tax Certificate Required: $taxCertificate")
                if (taxCertificate.equals("Yes", ignoreCase = true)) {
                    appendLine()
                    appendLine("Tax Certificate Information:")
                    appendLine("Full Name: $taxFullName")
                    appendLine("Contact Phone: $taxPhone")
                    appendLine("ID Number: $idNumber")
                    appendLine("Tax Number: $taxNumber")
                }
            }
            
            // Save to Firestore
            val db = Firebase.firestore
            val submission = ContactSubmission(
                name = fullName,
                email = email,
                phone = contactNumber,
                message = message,
                timestamp = Timestamp.now(),
                read = false
            )
            
            db.collection("contact_submissions")
                .add(submission)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Debit order request submitted successfully! Thank you.", Toast.LENGTH_LONG).show()
                    // Clear form
                    etInitials?.setText("")
                    etTitle?.setText("Mr")
                    etFullName?.setText("")
                    etEmail?.setText("")
                    etContactNumber?.setText("")
                    etTaxAddress?.setText("")
                    etPaymentMethod?.setText("Debit Order")
                    etBank?.setText("")
                    etBranch?.setText("")
                    etBranchCode?.setText("")
                    etAccountNumber?.setText("")
                    etAccountType?.setText("Savings")
                    etCommencementDate?.setText("01 Jan")
                    etDonationAmount?.setText("")
                    etTaxCertificate?.setText("Yes")
                    etTaxFullName?.setText("")
                    etTaxPhone?.setText("")
                    etIdNumber?.setText("")
                    etTaxNumber?.setText("")
                    checkboxAgree?.isChecked = false
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Failed to submit request. Please try again.", Toast.LENGTH_SHORT).show()
                }
        }
    }
}


