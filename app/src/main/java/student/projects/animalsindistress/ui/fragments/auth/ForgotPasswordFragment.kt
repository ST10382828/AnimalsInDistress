package student.projects.animalsindistress.ui.fragments.auth

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import student.projects.animalsindistress.R

class ForgotPasswordFragment : Fragment(R.layout.fragment_forgot_password) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.btnSendReset).setOnClickListener {
            val email = view.findViewById<EditText>(R.id.etEmail).text?.toString()?.trim().orEmpty()
            if (email.isEmpty()) {
                Toast.makeText(requireContext(), "Enter your email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Reset link sent", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }
                .addOnFailureListener { err ->
                    Toast.makeText(requireContext(), err.localizedMessage ?: "Failed to send reset email", Toast.LENGTH_LONG).show()
                }
        }
    }
}


