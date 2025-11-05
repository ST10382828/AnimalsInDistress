package student.projects.animalsindistress.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import student.projects.animalsindistress.R
import student.projects.animalsindistress.data.Donation
import student.projects.animalsindistress.data.DonationImpactRepository
import java.text.NumberFormat
import java.util.*

/**
 * In-app donation form with simulated payment processing
 */
class MakeDonationFragment : Fragment(R.layout.fragment_make_donation) {
    
    private val repository = DonationImpactRepository()
    private val auth = FirebaseAuth.getInstance()
    private val currencyFormat = NumberFormat.getCurrencyInstance(Locale("en", "ZA"))
    
    private var selectedAmount: Double = 0.0
    private var selectedPaymentMethod: String = "card"
    private var selectedCategory: String = "general"
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Check if user is logged in
        val user = auth.currentUser
        if (user == null) {
            showLoginPrompt(view)
            return
        }
        
        setupAmountButtons(view)
        setupPaymentMethods(view)
        setupCategories(view)
        setupDonateButton(view)
    }
    
    private fun showLoginPrompt(view: View) {
        view.findViewById<LinearLayout>(R.id.layout_donation_form)?.visibility = View.GONE
        view.findViewById<LinearLayout>(R.id.layout_login_prompt)?.visibility = View.VISIBLE
        
        view.findViewById<View>(R.id.btn_go_to_login)?.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
    }
    
    private fun setupAmountButtons(view: View) {
        val amounts = listOf(
            R.id.btn_amount_100 to 100.0,
            R.id.btn_amount_250 to 250.0,
            R.id.btn_amount_500 to 500.0,
            R.id.btn_amount_1000 to 1000.0,
            R.id.btn_amount_2500 to 2500.0,
            R.id.btn_amount_5000 to 5000.0
        )
        
        val customAmountInput = view.findViewById<TextInputEditText>(R.id.input_custom_amount)
        
        amounts.forEach { (buttonId, amount) ->
            view.findViewById<MaterialButton>(buttonId)?.setOnClickListener { button ->
                selectedAmount = amount
                highlightSelectedAmount(view, button as MaterialButton)
                customAmountInput?.setText("")
                updateImpactPreview(view)
            }
        }
        
        // Custom amount
        customAmountInput?.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                clearAmountSelection(view)
            }
        }
        
        view.findViewById<MaterialButton>(R.id.btn_use_custom_amount)?.setOnClickListener {
            val customAmount = customAmountInput?.text.toString().toDoubleOrNull()
            if (customAmount != null && customAmount >= 10.0) {
                selectedAmount = customAmount
                clearAmountSelection(view)
                updateImpactPreview(view)
            } else {
                Toast.makeText(requireContext(), "Please enter a valid amount (minimum R10)", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun setupPaymentMethods(view: View) {
        val radioGroup = view.findViewById<RadioGroup>(R.id.radio_payment_method)
        radioGroup?.setOnCheckedChangeListener { _, checkedId ->
            selectedPaymentMethod = when (checkedId) {
                R.id.radio_card -> "card"
                R.id.radio_eft -> "eft"
                R.id.radio_debit_order -> "debit_order"
                else -> "card"
            }
        }
    }
    
    private fun setupCategories(view: View) {
        val spinner = view.findViewById<Spinner>(R.id.spinner_category)
        val categories = arrayOf("General Donation", "Medical Care", "Feeding Program", "Education & Outreach")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.adapter = adapter
        
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedCategory = when (position) {
                    0 -> "general"
                    1 -> "medical"
                    2 -> "feeding"
                    3 -> "education"
                    else -> "general"
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
    
    private fun updateImpactPreview(view: View) {
        if (selectedAmount <= 0) {
            view.findViewById<MaterialCardView>(R.id.card_impact_preview)?.visibility = View.GONE
            return
        }
        
        view.findViewById<MaterialCardView>(R.id.card_impact_preview)?.visibility = View.VISIBLE
        
        val impact = Donation.calculateImpact(selectedAmount)
        
        view.findViewById<TextView>(R.id.text_preview_amount)?.text = currencyFormat.format(selectedAmount)
        view.findViewById<TextView>(R.id.text_preview_dogs)?.text = "${impact.dogsFeeding} dogs fed"
        view.findViewById<TextView>(R.id.text_preview_surgeries)?.text = "${impact.surgeriesEnabled} surgeries"
        view.findViewById<TextView>(R.id.text_preview_clinics)?.text = "${impact.clinicsSupported} clinics"
        view.findViewById<TextView>(R.id.text_preview_animals)?.text = "${impact.animalsHelped} animals"
    }
    
    private fun setupDonateButton(view: View) {
        view.findViewById<MaterialButton>(R.id.btn_process_donation)?.setOnClickListener {
            if (selectedAmount < 10.0) {
                Toast.makeText(requireContext(), "Please select a donation amount (minimum R10)", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            processDonation(view)
        }
    }
    
    private fun processDonation(view: View) {
        val progressCard = view.findViewById<MaterialCardView>(R.id.card_processing)
        val formLayout = view.findViewById<ScrollView>(R.id.scroll_form)
        val progressText = view.findViewById<TextView>(R.id.text_processing_status)
        
        // Show processing UI
        formLayout?.visibility = View.GONE
        progressCard?.visibility = View.VISIBLE
        
        lifecycleScope.launch {
            try {
                // Simulate payment processing steps
                progressText?.text = "Connecting to payment gateway..."
                delay(1000)
                
                progressText?.text = "Verifying payment details..."
                delay(1500)
                
                progressText?.text = "Processing donation..."
                delay(1500)
                
                // Record donation to Firebase
                val result = repository.recordDonation(
                    amount = selectedAmount,
                    paymentMethod = selectedPaymentMethod,
                    isRecurring = false,
                    category = selectedCategory
                )
                
                result.onSuccess { donationId ->
                    progressText?.text = "Success! Recording your contribution..."
                    delay(500)
                    
                    // Show success animation
                    showSuccessDialog(donationId)
                }.onFailure { error ->
                    progressCard?.visibility = View.GONE
                    formLayout?.visibility = View.VISIBLE
                    Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_LONG).show()
                }
                
            } catch (e: Exception) {
                progressCard?.visibility = View.GONE
                formLayout?.visibility = View.VISIBLE
                Toast.makeText(requireContext(), "Error processing donation: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
    
    private fun showSuccessDialog(donationId: String) {
        val impact = Donation.calculateImpact(selectedAmount)
        
        val message = buildString {
            append("🎉 Thank you for your generous donation!\n\n")
            append("Amount: ${currencyFormat.format(selectedAmount)}\n")
            append("Receipt: ${donationId.take(12)}...\n\n")
            append("Your Impact:\n")
            if (impact.dogsFeeding > 0) append("🐕 ${impact.dogsFeeding} dogs will be fed\n")
            if (impact.surgeriesEnabled > 0) append("🏥 ${impact.surgeriesEnabled} surgery(ies) enabled\n")
            if (impact.clinicsSupported > 0) append("🚑 ${impact.clinicsSupported} clinic(s) supported\n")
            append("❤️ ${impact.animalsHelped} animals helped\n\n")
            append("View your updated Impact Dashboard now!")
        }
        
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Donation Successful! 🎉")
            .setMessage(message)
            .setPositiveButton("View Impact Dashboard") { _, _ ->
                findNavController().navigate(R.id.donationImpactFragment)
            }
            .setNegativeButton("Close") { _, _ ->
                findNavController().navigateUp()
            }
            .setCancelable(false)
            .show()
    }
    
    private fun highlightSelectedAmount(view: View, selectedButton: MaterialButton) {
        // Reset all buttons
        listOf(
            R.id.btn_amount_100,
            R.id.btn_amount_250,
            R.id.btn_amount_500,
            R.id.btn_amount_1000,
            R.id.btn_amount_2500,
            R.id.btn_amount_5000
        ).forEach { buttonId ->
            view.findViewById<MaterialButton>(buttonId)?.apply {
                strokeWidth = 0
            }
        }
        
        // Highlight selected
        selectedButton.strokeWidth = 4
    }
    
    private fun clearAmountSelection(view: View) {
        listOf(
            R.id.btn_amount_100,
            R.id.btn_amount_250,
            R.id.btn_amount_500,
            R.id.btn_amount_1000,
            R.id.btn_amount_2500,
            R.id.btn_amount_5000
        ).forEach { buttonId ->
            view.findViewById<MaterialButton>(buttonId)?.strokeWidth = 0
        }
    }
}
