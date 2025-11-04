package student.projects.animalsindistress.ui.fragments.auth

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import student.projects.animalsindistress.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.Timestamp

class RegisterFragment : Fragment(R.layout.fragment_register) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.tvGoToLogin)?.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        view.findViewById<View>(R.id.btnRegister)?.setOnClickListener {
            val name = view.findViewById<EditText>(R.id.etName).text?.toString()?.trim().orEmpty()
            val email = view.findViewById<EditText>(R.id.etEmail).text?.toString()?.trim().orEmpty()
            val password = view.findViewById<EditText>(R.id.etPassword).text?.toString()?.trim().orEmpty()
            val confirm = view.findViewById<EditText>(R.id.etConfirmPassword).text?.toString()?.trim().orEmpty()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password != confirm) {
                Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { result ->
                    val user = result.user
                    if (user == null) {
                        Toast.makeText(requireContext(), "Registration succeeded", Toast.LENGTH_SHORT).show()
                        findNavController().navigateUp()
                        return@addOnSuccessListener
                    }

                    // Update display name
                    user.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())
                        .addOnCompleteListener { }

                    // Create Firestore user doc with role "user"
                    val uid = user.uid
                    val db = Firebase.firestore
                    val data = hashMapOf(
                        "name" to name,
                        "email" to email,
                        "role" to "user",
                        "createdAt" to Timestamp.now()
                    )
                    db.collection("users").document(uid).set(data, SetOptions.merge())
                        .addOnCompleteListener {
                            Toast.makeText(requireContext(), "Account created", Toast.LENGTH_SHORT).show()
                            findNavController().navigateUp()
                        }
                }
                .addOnFailureListener { err ->
                    Toast.makeText(requireContext(), err.localizedMessage ?: "Registration failed", Toast.LENGTH_LONG).show()
                }
        }
    }
}


