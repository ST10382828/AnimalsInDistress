package student.projects.animalsindistress.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import student.projects.animalsindistress.R
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth

class MoreFragment : Fragment(R.layout.fragment_more) {
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Support Us section (6 items)
        view.findViewById<View>(R.id.menu_item_volunteer).setOnClickListener {
            findNavController().navigate(R.id.volunteerFragment)
        }
        view.findViewById<View>(R.id.menu_item_shop).setOnClickListener {
            findNavController().navigate(R.id.shopFragment)
        }
        view.findViewById<View>(R.id.menu_item_dancing_donkey).setOnClickListener {
            findNavController().navigate(R.id.dancingDonkeyFragment)
        }
        view.findViewById<View>(R.id.menu_item_heart_sole_store).setOnClickListener {
            findNavController().navigate(R.id.heartSoleStoreFragment)
        }
        view.findViewById<View>(R.id.menu_item_golf_day).setOnClickListener {
            findNavController().navigate(R.id.golfDay2025Fragment)
        }
        view.findViewById<View>(R.id.menu_item_thousand_heroes).setOnClickListener {
            findNavController().navigate(R.id.thousandHeroesFragment)
        }
        
        // Ways to Give section (6 items)
        view.findViewById<View>(R.id.menu_item_myschool).setOnClickListener {
            findNavController().navigate(R.id.mySchoolFragment)
        }
        view.findViewById<View>(R.id.menu_item_monthly_debit).setOnClickListener {
            findNavController().navigate(R.id.monthlyDebitFragment)
        }
        view.findViewById<View>(R.id.menu_item_payroll_giving).setOnClickListener {
            findNavController().navigate(R.id.payrollGivingFragment)
        }
        view.findViewById<View>(R.id.menu_item_legacies).setOnClickListener {
            findNavController().navigate(R.id.legaciesFragment)
        }
        view.findViewById<View>(R.id.menu_item_pre_loved).setOnClickListener {
            findNavController().navigate(R.id.preLovedFragment)
        }
        view.findViewById<View>(R.id.menu_item_tax_certificate).setOnClickListener {
            findNavController().navigate(R.id.taxCertificateFragment)
        }
        
        // About Us section (5 items)
        view.findViewById<View>(R.id.menu_item_news).setOnClickListener {
            findNavController().navigate(R.id.newsListFragment)
        }
        view.findViewById<View>(R.id.menu_item_case_study).setOnClickListener {
            findNavController().navigate(R.id.caseStudyFragment)
        }
        view.findViewById<View>(R.id.menu_item_team).setOnClickListener {
            findNavController().navigate(R.id.teamFragment)
        }
        view.findViewById<View>(R.id.menu_item_partners).setOnClickListener {
            findNavController().navigate(R.id.partnersFragment)
        }
        view.findViewById<View>(R.id.menu_item_annual_report).setOnClickListener {
            findNavController().navigate(R.id.annualReportFragment)
        }
        
        // Programs section (3 items)
        view.findViewById<View>(R.id.menu_item_services).setOnClickListener {
            findNavController().navigate(R.id.servicesFragment)
        }
        view.findViewById<View>(R.id.menu_item_programs).setOnClickListener {
            findNavController().navigate(R.id.programsFragment)
        }
        view.findViewById<View>(R.id.menu_item_equine_outreach).setOnClickListener {
            findNavController().navigate(R.id.equineOutreachFragment)
        }
        
        // Get In Touch section (3 items)
        view.findViewById<View>(R.id.menu_item_contact).setOnClickListener {
            findNavController().navigate(R.id.contactFragment)
        }
        view.findViewById<View>(R.id.menu_item_call).setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:0114660261"))
            startActivity(intent)
        }
        view.findViewById<View>(R.id.menu_item_email).setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:animals@animalsindistress.org.za"))
            startActivity(intent)
        }
        
        // Follow Us section (2 items)
        view.findViewById<View>(R.id.menu_item_facebook).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/tsfaid"))
            startActivity(intent)
        }
        view.findViewById<View>(R.id.menu_item_instagram).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/thesocietyforanimalsindistress/"))
            startActivity(intent)
        }
        
        // FAQ (1 item)
        view.findViewById<View>(R.id.menu_item_faq).setOnClickListener {
            findNavController().navigate(R.id.faqFragment)
        }

        // Login/Logout button at bottom
        val authButton = view.findViewById<MaterialButton>(R.id.menu_item_login)
        fun refreshAuthButton() {
            val isLoggedIn = FirebaseAuth.getInstance().currentUser != null
            if (isLoggedIn) {
                authButton.text = "Logout"
                authButton.setOnClickListener {
                    FirebaseAuth.getInstance().signOut()
                    refreshAuthButton()
                }
            } else {
                authButton.text = "Login"
                authButton.setOnClickListener {
                    findNavController().navigate(R.id.loginFragment)
                }
            }
        }
        refreshAuthButton()
    }
}



