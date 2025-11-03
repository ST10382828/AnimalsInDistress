package student.projects.animalsindistress.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import student.projects.animalsindistress.R

class DancingDonkeyFragment : Fragment(R.layout.fragment_dancing_donkey) {
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        view.findViewById<View>(R.id.btn_book_party).setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:animals@animalsindistress.org.za"))
            startActivity(intent)
        }
    }
}


