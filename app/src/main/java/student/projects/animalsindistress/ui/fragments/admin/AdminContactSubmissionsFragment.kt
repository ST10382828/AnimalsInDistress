package student.projects.animalsindistress.ui.fragments.admin

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import student.projects.animalsindistress.R
import student.projects.animalsindistress.data.ContactSubmission
import java.text.SimpleDateFormat
import java.util.*

class AdminContactSubmissionsFragment : Fragment(R.layout.fragment_admin_contact_submissions) {

    private val db = Firebase.firestore
    private val submissions = mutableListOf<ContactSubmission>()
    private lateinit var adapter: SubmissionsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Check if user is admin
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            Toast.makeText(requireContext(), "Please log in first", Toast.LENGTH_SHORT).show()
            return
        }

        db.collection("users").document(currentUser.uid).get()
            .addOnSuccessListener { doc ->
                val role = doc.getString("role") ?: "user"
                if (role != "admin") {
                    Toast.makeText(requireContext(), "Admin access required", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }
                loadSubmissions()
            }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewSubmissions)
        adapter = SubmissionsAdapter(submissions) { submission, action ->
            when (action) {
                "markRead" -> markAsRead(submission)
                "delete" -> deleteSubmission(submission)
            }
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun loadSubmissions() {
        db.collection("contact_submissions")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                submissions.clear()
                for (document in result) {
                    val submission = document.toObject(ContactSubmission::class.java).copy(id = document.id)
                    submissions.add(submission)
                }
                adapter.notifyDataSetChanged()
                updateEmptyState()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to load submissions", Toast.LENGTH_SHORT).show()
            }
    }

    private fun markAsRead(submission: ContactSubmission) {
        db.collection("contact_submissions").document(submission.id)
            .update("read", true)
            .addOnSuccessListener {
                val index = submissions.indexOfFirst { it.id == submission.id }
                if (index != -1) {
                    submissions[index] = submission.copy(read = true)
                    adapter.notifyItemChanged(index)
                }
                Toast.makeText(requireContext(), "Marked as read", Toast.LENGTH_SHORT).show()
            }
    }

    private fun deleteSubmission(submission: ContactSubmission) {
        db.collection("contact_submissions").document(submission.id)
            .delete()
            .addOnSuccessListener {
                val index = submissions.indexOfFirst { it.id == submission.id }
                if (index != -1) {
                    submissions.removeAt(index)
                    adapter.notifyItemRemoved(index)
                }
                updateEmptyState()
                Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateEmptyState() {
        view?.findViewById<TextView>(R.id.tvEmptyState)?.visibility =
            if (submissions.isEmpty()) View.VISIBLE else View.GONE
        view?.findViewById<RecyclerView>(R.id.recyclerViewSubmissions)?.visibility =
            if (submissions.isEmpty()) View.GONE else View.VISIBLE
    }
}

class SubmissionsAdapter(
    private val submissions: List<ContactSubmission>,
    private val onAction: (ContactSubmission, String) -> Unit
) : RecyclerView.Adapter<SubmissionsAdapter.ViewHolder>() {

    private val dateFormat = SimpleDateFormat("MMM d, yyyy 'at' h:mm a", Locale.getDefault())

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ViewHolder {
        val view = android.view.LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact_submission, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(submissions[position])
    }

    override fun getItemCount() = submissions.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvName: TextView = view.findViewById(R.id.tvSubmissionName)
        private val tvEmail: TextView = view.findViewById(R.id.tvSubmissionEmail)
        private val tvPhone: TextView = view.findViewById(R.id.tvSubmissionPhone)
        private val tvMessage: TextView = view.findViewById(R.id.tvSubmissionMessage)
        private val tvDate: TextView = view.findViewById(R.id.tvSubmissionDate)
        private val unreadIndicator: View = view.findViewById(R.id.unreadIndicator)
        private val btnMarkRead: Button = view.findViewById(R.id.btnMarkRead)
        private val btnDelete: Button = view.findViewById(R.id.btnDelete)

        fun bind(submission: ContactSubmission) {
            tvName.text = submission.name
            tvEmail.text = submission.email
            tvPhone.text = submission.phone
            tvMessage.text = submission.message
            tvDate.text = dateFormat.format(submission.timestamp.toDate())
            
            unreadIndicator.visibility = if (submission.read) View.GONE else View.VISIBLE
            btnMarkRead.visibility = if (submission.read) View.GONE else View.VISIBLE

            btnMarkRead.setOnClickListener { onAction(submission, "markRead") }
            btnDelete.setOnClickListener { onAction(submission, "delete") }
        }
    }
}
