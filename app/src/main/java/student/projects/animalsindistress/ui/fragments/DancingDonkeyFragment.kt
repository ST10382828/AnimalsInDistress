package student.projects.animalsindistress.ui.fragments

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

class DancingDonkeyFragment : Fragment(R.layout.fragment_dancing_donkey) {
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val etName = view.findViewById<EditText>(R.id.et_name)
        val etEmail = view.findViewById<EditText>(R.id.et_email)
        val etPhone = view.findViewById<EditText>(R.id.et_phone)
        val etDate = view.findViewById<EditText>(R.id.et_date)
        val etEventType = view.findViewById<EditText>(R.id.et_event_type)
        val etMessage = view.findViewById<EditText>(R.id.et_message)
        val btnSubmit = view.findViewById<Button>(R.id.btn_submit_party)
        
        // Set default event type
        etEventType?.setText("Kids Party")
        
        btnSubmit?.setOnClickListener {
            val name = etName?.text?.toString()?.trim() ?: ""
            val email = etEmail?.text?.toString()?.trim() ?: ""
            val phone = etPhone?.text?.toString()?.trim() ?: ""
            val date = etDate?.text?.toString()?.trim() ?: ""
            val eventType = etEventType?.text?.toString()?.trim() ?: ""
            val message = etMessage?.text?.toString()?.trim() ?: ""
            
            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || date.isEmpty() || message.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            // Create message with party booking details
            val fullMessage = "Party Booking Request\n\n" +
                    "Name: $name\n" +
                    "Email: $email\n" +
                    "Phone: $phone\n" +
                    "Date of Event: $date\n" +
                    "Type of Event: $eventType\n\n" +
                    "Message:\n$message"
            
            // Save to Firestore
            val db = Firebase.firestore
            val submission = ContactSubmission(
                name = name,
                email = email,
                phone = phone,
                message = fullMessage,
                timestamp = Timestamp.now(),
                read = false
            )
            
            db.collection("contact_submissions")
                .add(submission)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Party booking request sent successfully!", Toast.LENGTH_SHORT).show()
                    // Clear form
                    etName?.setText("")
                    etEmail?.setText("")
                    etPhone?.setText("")
                    etDate?.setText("")
                    etEventType?.setText("Kids Party")
                    etMessage?.setText("")
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Failed to send request. Please try again.", Toast.LENGTH_SHORT).show()
                }
        }
    }
}


