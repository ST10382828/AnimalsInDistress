package student.projects.animalsindistress.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.content.Intent
import android.net.Uri
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import student.projects.animalsindistress.R
import student.projects.animalsindistress.data.ContactSubmission

class ContactFragment : Fragment(R.layout.fragment_contact) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Phone and email click handlers
        view.findViewById<View>(R.id.phone_section)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:0114660261"))
            startActivity(intent)
        }

        view.findViewById<View>(R.id.email_section)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:animals@animalsindistress.org.za")
            }
            startActivity(intent)
        }

        // Social media buttons
        view.findViewById<View>(R.id.btn_facebook)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/tsfaid"))
            startActivity(intent)
        }

        view.findViewById<View>(R.id.btn_twitter)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://x.com/SaidFundraiser"))
            startActivity(intent)
        }

        view.findViewById<View>(R.id.btn_instagram)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/thesocietyforanimalsindistress/"))
            startActivity(intent)
        }

        view.findViewById<View>(R.id.btn_linkedin)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/company/the-said/"))
            startActivity(intent)
        }

        view.findViewById<View>(R.id.btn_whatsapp)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.link/quddop"))
            startActivity(intent)
        }

        val etName = view.findViewById<EditText>(R.id.etName)
        val etEmail = view.findViewById<EditText>(R.id.etEmail)
        val etMessage = view.findViewById<EditText>(R.id.etMessage)
        val btnSubmit = view.findViewById<Button>(R.id.btn_submit_contact)

        btnSubmit?.setOnClickListener {
            val name = etName?.text?.toString()?.trim() ?: ""
            val email = etEmail?.text?.toString()?.trim() ?: ""
            val message = etMessage?.text?.toString()?.trim() ?: ""

            if (name.isEmpty() || email.isEmpty() || message.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Save to Firestore
            val db = Firebase.firestore
            val submission = ContactSubmission(
                name = name,
                email = email,
                phone = "", // No phone field in simplified form
                message = message,
                timestamp = Timestamp.now(),
                read = false
            )

            db.collection("contact_submissions")
                .add(submission)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Message sent successfully!", Toast.LENGTH_SHORT).show()
                    // Clear form
                    etName?.setText("")
                    etEmail?.setText("")
                    etMessage?.setText("")
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Failed to send message. Please try again.", Toast.LENGTH_SHORT).show()
                }
        }
    }
}


