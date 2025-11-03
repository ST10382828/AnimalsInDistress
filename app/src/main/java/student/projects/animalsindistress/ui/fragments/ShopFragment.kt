package student.projects.animalsindistress.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import student.projects.animalsindistress.R

class ShopFragment : Fragment(R.layout.fragment_shop) {
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        view.findViewById<View>(R.id.btn_visit_online_shop).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://animalsindistress.org.za/shop/"))
            startActivity(intent)
        }
    }
}


