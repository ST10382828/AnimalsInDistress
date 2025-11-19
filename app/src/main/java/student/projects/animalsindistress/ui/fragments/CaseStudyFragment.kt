package student.projects.animalsindistress.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import student.projects.animalsindistress.R

class CaseStudyFragment : Fragment(R.layout.fragment_case_study) {
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Download case study button
        view.findViewById<View>(R.id.btn_download_case_study)?.setOnClickListener {
            // Open the case study PDF download link
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Case-Study-August-2020.pdf"))
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Unable to open download link", Toast.LENGTH_SHORT).show()
            }
        }
    }
}




