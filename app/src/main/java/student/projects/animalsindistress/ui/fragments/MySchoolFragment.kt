package student.projects.animalsindistress.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import student.projects.animalsindistress.R

class MySchoolFragment : Fragment(R.layout.fragment_my_school) {
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Complete online application form button
        view.findViewById<View>(R.id.btn_complete_online)?.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.myschool.co.za/portal/register"))
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Unable to open link", Toast.LENGTH_SHORT).show()
            }
        }
        
        // Download application form button
        view.findViewById<View>(R.id.btn_download_form)?.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://animalsindistress.org.za/wp-content/uploads/2023/06/brandedapplicationform-5550-2022-02-07T08-10-500200.pdf"))
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Unable to open download link", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


