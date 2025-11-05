package student.projects.animalsindistress.ui.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import student.projects.animalsindistress.R
import student.projects.animalsindistress.data.Donation
import student.projects.animalsindistress.data.DonationImpactRepository
import student.projects.animalsindistress.data.DonationStats
import student.projects.animalsindistress.data.DonorLevel
import student.projects.animalsindistress.data.CampaignGoal
import java.io.File
import java.io.FileOutputStream
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Fragment displaying the user's donation impact dashboard with gamification features
 */
class DonationImpactFragment : Fragment(R.layout.fragment_donation_impact) {
    
    private val repository = DonationImpactRepository()
    private val auth = FirebaseAuth.getInstance()
    private val currencyFormat = NumberFormat.getCurrencyInstance(Locale("en", "ZA"))
    private val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    
    private var currentStats: DonationStats? = null
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val user = auth.currentUser
        if (user == null) {
            showLoginPrompt(view)
            return
        }
        
        setupUI(view)
        loadDashboardData(view)
    }
    
    private fun showLoginPrompt(view: View) {
        view.findViewById<LinearLayout>(R.id.layout_logged_in)?.visibility = View.GONE
        view.findViewById<LinearLayout>(R.id.layout_login_prompt)?.visibility = View.VISIBLE
        
        view.findViewById<View>(R.id.btn_go_to_login)?.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
    }
    
    private fun setupUI(view: View) {
        view.findViewById<LinearLayout>(R.id.layout_logged_in)?.visibility = View.VISIBLE
        view.findViewById<LinearLayout>(R.id.layout_login_prompt)?.visibility = View.GONE
        
        // Setup buttons
        view.findViewById<View>(R.id.btn_share_impact)?.setOnClickListener {
            shareImpactCard(view)
        }
        
        view.findViewById<View>(R.id.btn_download_certificate)?.setOnClickListener {
            download18ACertificate()
        }
        
        view.findViewById<View>(R.id.btn_make_donation)?.setOnClickListener {
            findNavController().navigate(R.id.donateFragment)
        }
        
        view.findViewById<View>(R.id.btn_refresh)?.setOnClickListener {
            loadDashboardData(view)
        }
    }
    
    private fun loadDashboardData(view: View) {
        val progressBar = view.findViewById<ProgressBar>(R.id.progress_loading)
        val contentLayout = view.findViewById<ScrollView>(R.id.scroll_content)
        
        progressBar?.visibility = View.VISIBLE
        contentLayout?.visibility = View.GONE
        
        lifecycleScope.launch {
            // Load user stats
            val statsResult = repository.getUserDonationStats()
            statsResult.onSuccess { stats ->
                currentStats = stats
                displayStats(view, stats)
            }.onFailure { error ->
                Toast.makeText(requireContext(), "Error loading stats: ${error.message}", Toast.LENGTH_SHORT).show()
            }
            
            // Load donation history
            val donationsResult = repository.getUserDonations(limit = 10)
            donationsResult.onSuccess { donations ->
                displayDonationHistory(view, donations)
            }.onFailure { error ->
                Toast.makeText(requireContext(), "Error loading history: ${error.message}", Toast.LENGTH_SHORT).show()
            }
            
            // Load campaign goals
            val goalsResult = repository.getActiveCampaignGoals()
            goalsResult.onSuccess { goals ->
                displayCampaignGoals(view, goals)
            }.onFailure { error ->
                // Campaign goals are optional, just log
                println("No active campaign goals: ${error.message}")
            }
            
            progressBar?.visibility = View.GONE
            contentLayout?.visibility = View.VISIBLE
        }
    }
    
    private fun displayStats(view: View, stats: DonationStats) {
        // Total donation amount
        view.findViewById<TextView>(R.id.text_total_amount)?.text = currencyFormat.format(stats.totalDonations)
        view.findViewById<TextView>(R.id.text_donation_count)?.text = "${stats.donationCount} donations"
        
        // Donor level badge
        view.findViewById<TextView>(R.id.text_donor_level)?.apply {
            text = "${stats.donorLevel.emoji} ${stats.donorLevel.displayName}"
        }
        
        // Progress to next level
        view.findViewById<LinearProgressIndicator>(R.id.progress_next_level)?.apply {
            progress = (stats.nextLevelProgress * 100).toInt()
        }
        
        val remainingToNextLevel = stats.nextLevelThreshold - stats.totalDonations
        view.findViewById<TextView>(R.id.text_next_level_info)?.text = 
            if (stats.donorLevel == DonorLevel.DIAMOND) {
                "You've reached the highest level! 🎉"
            } else {
                "${currencyFormat.format(remainingToNextLevel)} more to reach ${getNextLevel(stats.donorLevel).displayName}"
            }
        
        // Impact metrics
        view.findViewById<TextView>(R.id.text_dogs_fed)?.text = stats.totalDogsFeeding.toString()
        view.findViewById<TextView>(R.id.text_surgeries)?.text = stats.totalSurgeriesEnabled.toString()
        view.findViewById<TextView>(R.id.text_clinics)?.text = stats.totalClinicsSupported.toString()
        view.findViewById<TextView>(R.id.text_animals_helped)?.text = stats.totalAnimalsHelped.toString()
        
        // Impact message
        val impactMessage = generateImpactMessage(stats)
        view.findViewById<TextView>(R.id.text_impact_message)?.text = impactMessage
        
        // Member since
        stats.firstDonationDate?.let { timestamp ->
            view.findViewById<TextView>(R.id.text_member_since)?.text = 
                "Member since ${dateFormat.format(timestamp.toDate())}"
        }
    }
    
    private fun displayDonationHistory(view: View, donations: List<Donation>) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_donation_history)
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        recyclerView?.adapter = DonationHistoryAdapter(donations)
        
        if (donations.isEmpty()) {
            view.findViewById<TextView>(R.id.text_no_donations)?.visibility = View.VISIBLE
            recyclerView?.visibility = View.GONE
        } else {
            view.findViewById<TextView>(R.id.text_no_donations)?.visibility = View.GONE
            recyclerView?.visibility = View.VISIBLE
        }
    }
    
    private fun displayCampaignGoals(view: View, goals: List<CampaignGoal>) {
        val container = view.findViewById<LinearLayout>(R.id.container_campaign_goals)
        container?.removeAllViews()
        
        if (goals.isEmpty()) {
            view.findViewById<MaterialCardView>(R.id.card_campaign_goals)?.visibility = View.GONE
            return
        }
        
        view.findViewById<MaterialCardView>(R.id.card_campaign_goals)?.visibility = View.VISIBLE
        
        goals.forEach { goal ->
            val goalView = layoutInflater.inflate(R.layout.item_campaign_goal, container, false)
            
            goalView.findViewById<TextView>(R.id.text_goal_title)?.text = goal.title
            goalView.findViewById<TextView>(R.id.text_goal_description)?.text = goal.description
            
            val progressBar = goalView.findViewById<LinearProgressIndicator>(R.id.progress_goal)
            progressBar?.progress = (goal.progress * 100).toInt()
            
            goalView.findViewById<TextView>(R.id.text_goal_progress)?.text = 
                "${currencyFormat.format(goal.currentAmount)} of ${currencyFormat.format(goal.targetAmount)}"
            
            goalView.findViewById<TextView>(R.id.text_goal_remaining)?.text = 
                "${currencyFormat.format(goal.remainingAmount)} remaining"
            
            container?.addView(goalView)
        }
    }
    
    private fun generateImpactMessage(stats: DonationStats): String {
        return when {
            stats.totalDogsFeeding > 50 -> "Your ${currencyFormat.format(stats.totalDonations)} has fed ${stats.totalDogsFeeding} dogs! 🐕"
            stats.totalSurgeriesEnabled > 0 -> "Amazing! You've enabled ${stats.totalSurgeriesEnabled} life-saving surgeries! 🏥"
            stats.totalClinicsSupported > 5 -> "You've supported ${stats.totalClinicsSupported} mobile clinic outreach programs! 🚑"
            stats.totalAnimalsHelped > 10 -> "Your generosity has helped ${stats.totalAnimalsHelped} animals in need! ❤️"
            stats.totalDonations >= 500 -> "Your R${stats.totalDonations.toInt()} donation is making a real difference! 🌟"
            else -> "Every donation counts! Thank you for your support! 💚"
        }
    }
    
    private fun getNextLevel(currentLevel: DonorLevel): DonorLevel {
        return when (currentLevel) {
            DonorLevel.SUPPORTER -> DonorLevel.BRONZE
            DonorLevel.BRONZE -> DonorLevel.SILVER
            DonorLevel.SILVER -> DonorLevel.GOLD
            DonorLevel.GOLD -> DonorLevel.PLATINUM
            DonorLevel.PLATINUM -> DonorLevel.DIAMOND
            DonorLevel.DIAMOND -> DonorLevel.DIAMOND
        }
    }
    
    private fun shareImpactCard(view: View) {
        val stats = currentStats ?: return
        
        // Create shareable card view
        val cardView = view.findViewById<MaterialCardView>(R.id.card_shareable_impact) ?: return
        
        // Generate bitmap from card
        val bitmap = Bitmap.createBitmap(cardView.width, cardView.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        cardView.draw(canvas)
        
        // Save to cache
        try {
            val cachePath = File(requireContext().cacheDir, "images")
            cachePath.mkdirs()
            val file = File(cachePath, "impact_card_${System.currentTimeMillis()}.png")
            val stream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.close()
            
            // Share
            val contentUri = FileProvider.getUriForFile(
                requireContext(),
                "${requireContext().packageName}.fileprovider",
                file
            )
            
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "image/png"
                putExtra(Intent.EXTRA_STREAM, contentUri)
                putExtra(Intent.EXTRA_TEXT, 
                    "I've donated ${currencyFormat.format(stats.totalDonations)} to Animals in Distress and helped ${stats.totalAnimalsHelped} animals! Join me in making a difference! 🐾 #AnimalsInDistress")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            
            startActivity(Intent.createChooser(shareIntent, "Share Your Impact"))
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error sharing: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun download18ACertificate() {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        
        lifecycleScope.launch {
            val result = repository.getDonationsForYear(currentYear)
            result.onSuccess { donations ->
                if (donations.isEmpty()) {
                    Toast.makeText(requireContext(), 
                        "No donations found for $currentYear", 
                        Toast.LENGTH_SHORT).show()
                    return@onSuccess
                }
                
                val totalForYear = donations.sumOf { it.amount }
                
                // Show dialog with annual summary
                val message = """
                    Annual Donation Summary for $currentYear
                    
                    Total Donations: ${currencyFormat.format(totalForYear)}
                    Number of Donations: ${donations.size}
                    
                    An 18A tax certificate will be sent to your email within 2 business days.
                    
                    Thank you for your generosity!
                """.trimIndent()
                
                androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setTitle("18A Tax Certificate")
                    .setMessage(message)
                    .setPositiveButton("Request Certificate") { _, _ ->
                        requestCertificate(donations.first().id)
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }.onFailure { error ->
                Toast.makeText(requireContext(), 
                    "Error: ${error.message}", 
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun requestCertificate(donationId: String) {
        lifecycleScope.launch {
            val result = repository.request18ACertificate(donationId)
            result.onSuccess { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            }.onFailure { error ->
                Toast.makeText(requireContext(), 
                    "Error: ${error.message}", 
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    /**
     * RecyclerView adapter for donation history
     */
    private inner class DonationHistoryAdapter(
        private val donations: List<Donation>
    ) : RecyclerView.Adapter<DonationHistoryAdapter.ViewHolder>() {
        
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val textAmount: TextView = view.findViewById(R.id.text_donation_amount)
            val textDate: TextView = view.findViewById(R.id.text_donation_date)
            val textImpact: TextView = view.findViewById(R.id.text_donation_impact)
            val textReceipt: TextView = view.findViewById(R.id.text_receipt_number)
        }
        
        override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ViewHolder {
            val view = layoutInflater.inflate(R.layout.item_donation_history, parent, false)
            return ViewHolder(view)
        }
        
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val donation = donations[position]
            
            holder.textAmount.text = currencyFormat.format(donation.amount)
            holder.textDate.text = dateFormat.format(donation.timestamp.toDate())
            holder.textReceipt.text = "Receipt: ${donation.receiptNumber}"
            
            val impactText = buildString {
                if (donation.dogsFeeding > 0) append("🐕 ${donation.dogsFeeding} dogs fed  ")
                if (donation.surgeriesEnabled > 0) append("🏥 ${donation.surgeriesEnabled} surgeries  ")
                if (donation.clinicsSupported > 0) append("🚑 ${donation.clinicsSupported} clinics  ")
            }.ifEmpty { "Thank you for your support!" }
            
            holder.textImpact.text = impactText
        }
        
        override fun getItemCount() = donations.size
    }
}
