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
        
        // Setup click listeners for all menu items
        view.findViewById<View>(R.id.menu_item_volunteer)?.setOnClickListener {
            findNavController().navigate(R.id.volunteerFragment)
        }
        
        view.findViewById<View>(R.id.menu_item_shop)?.setOnClickListener {
            findNavController().navigate(R.id.shopFragment)
        }
        
        view.findViewById<View>(R.id.menu_item_dancing_donkey)?.setOnClickListener {
            findNavController().navigate(R.id.dancingDonkeyFragment)
        }
        
        view.findViewById<View>(R.id.menu_item_thousand_heroes)?.setOnClickListener {
            findNavController().navigate(R.id.thousandHeroesFragment)
        }
        
        view.findViewById<View>(R.id.menu_item_myschool)?.setOnClickListener {
            findNavController().navigate(R.id.mySchoolFragment)
        }
        
        view.findViewById<View>(R.id.menu_item_monthly_debit)?.setOnClickListener {
            findNavController().navigate(R.id.monthlyDebitFragment)
        }
        
        view.findViewById<View>(R.id.menu_item_payroll_giving)?.setOnClickListener {
            findNavController().navigate(R.id.payrollGivingFragment)
        }
        
        view.findViewById<View>(R.id.menu_item_legacies)?.setOnClickListener {
            findNavController().navigate(R.id.legaciesFragment)
        }
        
        view.findViewById<View>(R.id.menu_item_pre_loved)?.setOnClickListener {
            findNavController().navigate(R.id.preLovedFragment)
        }
        
        view.findViewById<View>(R.id.menu_item_tax_certificate)?.setOnClickListener {
            findNavController().navigate(R.id.taxCertificateFragment)
        }
        
        view.findViewById<View>(R.id.menu_item_news)?.setOnClickListener {
            findNavController().navigate(R.id.newsListFragment)
        }
        
        view.findViewById<View>(R.id.menu_item_success_stories)?.setOnClickListener {
            findNavController().navigate(R.id.storiesFeedFragment)
        }
        
        view.findViewById<View>(R.id.menu_item_donation_impact)?.setOnClickListener {
            findNavController().navigate(R.id.donationImpactFragment)
        }
        
        view.findViewById<View>(R.id.menu_item_case_study)?.setOnClickListener {
            findNavController().navigate(R.id.caseStudyFragment)
        }
        
        view.findViewById<View>(R.id.menu_item_team)?.setOnClickListener {
            findNavController().navigate(R.id.teamFragment)
        }
        
        view.findViewById<View>(R.id.menu_item_services)?.setOnClickListener {
            findNavController().navigate(R.id.servicesFragment)
        }
        
        view.findViewById<View>(R.id.menu_item_equine_outreach)?.setOnClickListener {
            findNavController().navigate(R.id.equineOutreachFragment)
        }
        
        view.findViewById<View>(R.id.menu_item_contact)?.setOnClickListener {
            findNavController().navigate(R.id.contactFragment)
        }
        
        view.findViewById<View>(R.id.menu_item_call)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:0114660261"))
            startActivity(intent)
        }
        
        view.findViewById<View>(R.id.menu_item_email)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:animals@animalsindistress.org.za")
            }
            startActivity(intent)
        }
        
        view.findViewById<View>(R.id.menu_item_facebook)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/tsfaid"))
            startActivity(intent)
        }
        
        view.findViewById<View>(R.id.menu_item_instagram)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/thesocietyforanimalsindistress"))
            startActivity(intent)
        }
        
        view.findViewById<View>(R.id.menu_item_faq)?.setOnClickListener {
            findNavController().navigate(R.id.faqFragment)
        }

        // Admin menu items (only visible for admin users)
        val adminSectionHeader = view.findViewById<View>(R.id.admin_section_header)
        val adminContactSubmissionsLayout = view.findViewById<View>(R.id.menu_item_admin_contact_submissions)
        val adminStoriesEditorLayout = view.findViewById<View>(R.id.menu_item_admin_stories_editor)
        val adminGalleryEditorLayout = view.findViewById<View>(R.id.menu_item_admin_gallery_editor)
        val adminMedicalAidRequestsCard = view.findViewById<com.google.android.material.card.MaterialCardView>(R.id.menu_item_admin_medical_aid_requests)
        
        // Get parent MaterialCardViews for the first three
        val adminContactSubmissionsCard = adminContactSubmissionsLayout?.parent as? com.google.android.material.card.MaterialCardView
        val adminStoriesEditorCard = adminStoriesEditorLayout?.parent as? com.google.android.material.card.MaterialCardView
        val adminGalleryEditorCard = adminGalleryEditorLayout?.parent as? com.google.android.material.card.MaterialCardView
        
        adminContactSubmissionsCard?.setOnClickListener {
            try {
                findNavController().navigate(R.id.adminContactSubmissionsFragment)
            } catch (e: Exception) {
                android.util.Log.e("MoreFragment", "Navigation error", e)
            }
        }
        
        adminStoriesEditorCard?.setOnClickListener {
            try {
                findNavController().navigate(R.id.adminStoriesEditorFragment)
            } catch (e: Exception) {
                android.util.Log.e("MoreFragment", "Navigation error", e)
            }
        }
        
        adminGalleryEditorCard?.setOnClickListener {
            try {
                findNavController().navigate(R.id.adminGalleryEditorFragment)
            } catch (e: Exception) {
                android.util.Log.e("MoreFragment", "Navigation error", e)
            }
        }
        
        adminMedicalAidRequestsCard?.setOnClickListener {
            try {
                findNavController().navigate(R.id.adminMedicalAidRequestsFragment)
            } catch (e: Exception) {
                android.util.Log.e("MoreFragment", "Navigation error", e)
            }
        }
        
        // Also make the inner LinearLayouts clickable
        adminContactSubmissionsCard?.let { card ->
            val innerLayout = card.getChildAt(0) as? android.view.ViewGroup
            innerLayout?.setOnClickListener {
                try {
                    findNavController().navigate(R.id.adminContactSubmissionsFragment)
                } catch (e: Exception) {
                    android.util.Log.e("MoreFragment", "Navigation error", e)
                }
            }
        }
        
        adminStoriesEditorCard?.let { card ->
            val innerLayout = card.getChildAt(0) as? android.view.ViewGroup
            innerLayout?.setOnClickListener {
                try {
                    findNavController().navigate(R.id.adminStoriesEditorFragment)
                } catch (e: Exception) {
                    android.util.Log.e("MoreFragment", "Navigation error", e)
                }
            }
        }
        
        adminGalleryEditorCard?.let { card ->
            val innerLayout = card.getChildAt(0) as? android.view.ViewGroup
            innerLayout?.setOnClickListener {
                try {
                    findNavController().navigate(R.id.adminGalleryEditorFragment)
                } catch (e: Exception) {
                    android.util.Log.e("MoreFragment", "Navigation error", e)
                }
            }
        }
        
        adminMedicalAidRequestsCard?.let { card ->
            val innerLayout = card.getChildAt(0) as? android.view.ViewGroup
            innerLayout?.setOnClickListener {
                try {
                    findNavController().navigate(R.id.adminMedicalAidRequestsFragment)
                } catch (e: Exception) {
                    android.util.Log.e("MoreFragment", "Navigation error", e)
                }
            }
        }

        // Login/Logout button at bottom
        val authButton = view.findViewById<MaterialButton>(R.id.menu_item_login)
        fun refreshAuthButton() {
            val currentUser = FirebaseAuth.getInstance().currentUser
            val isLoggedIn = currentUser != null
            
            if (isLoggedIn) {
                authButton.text = "Logout"
                authButton.setOnClickListener {
                    FirebaseAuth.getInstance().signOut()
                    refreshAuthButton()
                    // Hide admin options on logout
                    adminSectionHeader?.visibility = View.GONE
                    adminContactSubmissionsCard?.visibility = View.GONE
                    adminStoriesEditorCard?.visibility = View.GONE
                    adminGalleryEditorCard?.visibility = View.GONE
                    adminMedicalAidRequestsCard?.visibility = View.GONE
                }
                
                // Check if user is admin and show admin options
                currentUser?.let { user ->
                    com.google.firebase.firestore.FirebaseFirestore.getInstance()
                        .collection("users")
                        .document(user.uid)
                        .get()
                        .addOnSuccessListener { doc ->
                            val role = doc.getString("role") ?: "user"
                            if (role == "admin") {
                                adminSectionHeader?.visibility = View.VISIBLE
                                adminContactSubmissionsCard?.visibility = View.VISIBLE
                                adminStoriesEditorCard?.visibility = View.VISIBLE
                                adminGalleryEditorCard?.visibility = View.VISIBLE
                                adminMedicalAidRequestsCard?.visibility = View.VISIBLE
                            } else {
                                adminSectionHeader?.visibility = View.GONE
                                adminContactSubmissionsCard?.visibility = View.GONE
                                adminStoriesEditorCard?.visibility = View.GONE
                                adminGalleryEditorCard?.visibility = View.GONE
                                adminMedicalAidRequestsCard?.visibility = View.GONE
                            }
                        }
                }
            } else {
                authButton.text = "Login"
                authButton.setOnClickListener {
                    findNavController().navigate(R.id.loginFragment)
                }
                adminSectionHeader?.visibility = View.GONE
                adminContactSubmissionsCard?.visibility = View.GONE
                adminStoriesEditorCard?.visibility = View.GONE
                adminGalleryEditorCard?.visibility = View.GONE
                adminMedicalAidRequestsCard?.visibility = View.GONE
            }
        }
        refreshAuthButton()
    }
}

