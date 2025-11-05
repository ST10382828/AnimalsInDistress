package student.projects.animalsindistress.data

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.Calendar

/**
 * Repository for managing donation tracking and impact calculations
 */
class DonationImpactRepository {
    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()
    
    companion object {
        private const val TAG = "DonationImpactRepo"
        private const val COLLECTION_DONATIONS = "donations"
        private const val COLLECTION_CAMPAIGN_GOALS = "campaign_goals"
    }
    
    /**
     * Record a new donation (typically called after successful payment)
     * In production, this would be triggered by a payment webhook
     */
    suspend fun recordDonation(
        amount: Double,
        paymentMethod: String,
        isRecurring: Boolean = false,
        category: String = "general"
    ): kotlin.Result<String> {
        return try {
            val user = auth.currentUser
            if (user == null) {
                // Allow anonymous donations but with limited tracking
                val donation = createAnonymousDonation(amount, paymentMethod, isRecurring, category)
                val docRef = db.collection(COLLECTION_DONATIONS).add(donation).await()
                Log.d(TAG, "Anonymous donation recorded: ${docRef.id}")
                kotlin.Result.success(docRef.id)
            } else {
                val impact = Donation.calculateImpact(amount)
                val receiptNumber = generateReceiptNumber()
                
                val donation = Donation(
                    userId = user.uid,
                    userEmail = user.email ?: "",
                    userName = user.displayName ?: "Anonymous",
                    amount = amount,
                    timestamp = Timestamp.now(),
                    paymentMethod = paymentMethod,
                    isRecurring = isRecurring,
                    category = category,
                    dogsFeeding = impact.dogsFeeding,
                    surgeriesEnabled = impact.surgeriesEnabled,
                    clinicsSupported = impact.clinicsSupported,
                    animalsHelped = impact.animalsHelped,
                    receiptNumber = receiptNumber,
                    issuedCertificate = false
                )
                
                val docRef = db.collection(COLLECTION_DONATIONS)
                    .add(donation)
                    .await()
                
                Log.d(TAG, "Donation recorded for user ${user.uid}: ${docRef.id}")
                kotlin.Result.success(docRef.id)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error recording donation", e)
            kotlin.Result.failure(e)
        }
    }
    
    private fun createAnonymousDonation(
        amount: Double,
        paymentMethod: String,
        isRecurring: Boolean,
        category: String
    ): Map<String, Any> {
        val impact = Donation.calculateImpact(amount)
        return mapOf(
            "userId" to "",
            "userEmail" to "",
            "userName" to "Anonymous",
            "amount" to amount,
            "timestamp" to Timestamp.now(),
            "paymentMethod" to paymentMethod,
            "isRecurring" to isRecurring,
            "category" to category,
            "dogsFeeding" to impact.dogsFeeding,
            "surgeriesEnabled" to impact.surgeriesEnabled,
            "clinicsSupported" to impact.clinicsSupported,
            "animalsHelped" to impact.animalsHelped,
            "receiptNumber" to generateReceiptNumber(),
            "issuedCertificate" to false
        )
    }
    
    /**
     * Get donation statistics for the current user
     */
    suspend fun getUserDonationStats(): kotlin.Result<DonationStats> {
        return try {
            val user = auth.currentUser ?: return kotlin.Result.failure(Exception("User not logged in"))
            
            val donations = db.collection(COLLECTION_DONATIONS)
                .whereEqualTo("userId", user.uid)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .await()
                .toObjects(Donation::class.java)
            
            if (donations.isEmpty()) {
                return kotlin.Result.success(DonationStats())
            }
            
            val totalAmount = donations.sumOf { it.amount }
            val totalDogsFeeding = donations.sumOf { it.dogsFeeding }
            val totalSurgeries = donations.sumOf { it.surgeriesEnabled }
            val totalClinics = donations.sumOf { it.clinicsSupported }
            val totalAnimals = donations.sumOf { it.animalsHelped }
            
            val donorLevel = DonationStats.calculateDonorLevel(totalAmount)
            val nextThreshold = DonationStats.getNextLevelThreshold(donorLevel)
            val progress = if (nextThreshold > 0) (totalAmount / nextThreshold).toFloat().coerceIn(0f, 1f) else 1f
            
            val stats = DonationStats(
                totalDonations = totalAmount,
                donationCount = donations.size,
                totalDogsFeeding = totalDogsFeeding,
                totalSurgeriesEnabled = totalSurgeries,
                totalClinicsSupported = totalClinics,
                totalAnimalsHelped = totalAnimals,
                firstDonationDate = donations.lastOrNull()?.timestamp,
                lastDonationDate = donations.firstOrNull()?.timestamp,
                donorLevel = donorLevel,
                nextLevelThreshold = nextThreshold,
                nextLevelProgress = progress
            )
            
            Log.d(TAG, "Retrieved stats for user ${user.uid}: $stats")
            kotlin.Result.success(stats)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting user stats", e)
            kotlin.Result.failure(e)
        }
    }
    
    /**
     * Get user's donation history
     */
    suspend fun getUserDonations(limit: Int = 50): kotlin.Result<List<Donation>> {
        return try {
            val user = auth.currentUser ?: return kotlin.Result.failure(Exception("User not logged in"))
            
            val donations = db.collection(COLLECTION_DONATIONS)
                .whereEqualTo("userId", user.uid)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(limit.toLong())
                .get()
                .await()
                .toObjects(Donation::class.java)
            
            Log.d(TAG, "Retrieved ${donations.size} donations for user ${user.uid}")
            kotlin.Result.success(donations)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting user donations", e)
            kotlin.Result.failure(e)
        }
    }
    
    /**
     * Get active campaign goals
     */
    suspend fun getActiveCampaignGoals(): kotlin.Result<List<CampaignGoal>> {
        return try {
            val goals = db.collection(COLLECTION_CAMPAIGN_GOALS)
                .whereEqualTo("isActive", true)
                .orderBy("endDate", Query.Direction.ASCENDING)
                .get()
                .await()
                .toObjects(CampaignGoal::class.java)
            
            Log.d(TAG, "Retrieved ${goals.size} active campaign goals")
            kotlin.Result.success(goals)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting campaign goals", e)
            kotlin.Result.failure(e)
        }
    }
    
    /**
     * Get donations for a specific year (for annual summary)
     */
    suspend fun getDonationsForYear(year: Int): kotlin.Result<List<Donation>> {
        return try {
            val user = auth.currentUser ?: return kotlin.Result.failure(Exception("User not logged in"))
            
            val startOfYear = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, Calendar.JANUARY)
                set(Calendar.DAY_OF_MONTH, 1)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.time
            
            val endOfYear = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, Calendar.DECEMBER)
                set(Calendar.DAY_OF_MONTH, 31)
                set(Calendar.HOUR_OF_DAY, 23)
                set(Calendar.MINUTE, 59)
                set(Calendar.SECOND, 59)
                set(Calendar.MILLISECOND, 999)
            }.time
            
            val donations = db.collection(COLLECTION_DONATIONS)
                .whereEqualTo("userId", user.uid)
                .whereGreaterThanOrEqualTo("timestamp", Timestamp(startOfYear))
                .whereLessThanOrEqualTo("timestamp", Timestamp(endOfYear))
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .get()
                .await()
                .toObjects(Donation::class.java)
            
            Log.d(TAG, "Retrieved ${donations.size} donations for year $year")
            kotlin.Result.success(donations)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting donations for year $year", e)
            kotlin.Result.failure(e)
        }
    }
    
    /**
     * Request 18A tax certificate
     */
    suspend fun request18ACertificate(donationId: String): kotlin.Result<String> {
        return try {
            val user = auth.currentUser ?: return kotlin.Result.failure(Exception("User not logged in"))
            
            // In production, this would trigger a Cloud Function to generate the PDF
            db.collection(COLLECTION_DONATIONS)
                .document(donationId)
                .update(mapOf(
                    "issuedCertificate" to true,
                    "certificateRequestedAt" to Timestamp.now()
                ))
                .await()
            
            Log.d(TAG, "18A certificate requested for donation $donationId")
            kotlin.Result.success("Certificate request submitted. You will receive it via email within 2 business days.")
        } catch (e: Exception) {
            Log.e(TAG, "Error requesting certificate", e)
            kotlin.Result.failure(e)
        }
    }
    
    /**
     * Generate a unique receipt number
     */
    private fun generateReceiptNumber(): String {
        val timestamp = System.currentTimeMillis()
        val random = (1000..9999).random()
        return "AID-${timestamp}-${random}"
    }
}
