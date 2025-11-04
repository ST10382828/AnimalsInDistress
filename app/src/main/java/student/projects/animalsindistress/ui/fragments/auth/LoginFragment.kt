package student.projects.animalsindistress.ui.fragments.auth

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import student.projects.animalsindistress.R
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.tvGoToRegister)?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        view.findViewById<View>(R.id.tvForgotPassword)?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        val prefs = requireContext().getSharedPreferences("auth_prefs", android.content.Context.MODE_PRIVATE)
        val savedEmail = prefs.getString("remember_email", null)
        val savedRemember = prefs.getBoolean("remember_me", false)
        if (!savedEmail.isNullOrBlank()) {
            view.findViewById<EditText>(R.id.etEmail).setText(savedEmail)
        }
        view.findViewById<CheckBox>(R.id.cbRememberMe).isChecked = savedRemember

        view.findViewById<View>(R.id.btnLogin)?.setOnClickListener {
            val email = view.findViewById<EditText>(R.id.etEmail).text?.toString()?.trim().orEmpty()
            val password = view.findViewById<EditText>(R.id.etPassword).text?.toString()?.trim().orEmpty()
            val remember = view.findViewById<CheckBox>(R.id.cbRememberMe).isChecked
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    prefs.edit().apply {
                        putBoolean("remember_me", remember)
                        if (remember) putString("remember_email", email) else remove("remember_email")
                    }.apply()
                    Toast.makeText(requireContext(), "Signed in", Toast.LENGTH_SHORT).show()
                    // Navigate to home after successful login
                    findNavController().navigate(R.id.homeFragment)
                }
                .addOnFailureListener { err ->
                    Toast.makeText(requireContext(), err.localizedMessage ?: "Login failed", Toast.LENGTH_LONG).show()
                }
        }
    }
}


