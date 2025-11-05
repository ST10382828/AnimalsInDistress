package student.projects.animalsindistress.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

/**
 * Data model representing a donation with impact metrics
 */
data class Donation(
    @DocumentId
    val id: String = "",
    val userId: String = "",
    val userEmail: String = "",
    val userName: String = "",
    val amount: Double = 0.0,
    val timestamp: Timestamp = Timestamp.now(),
    val paymentMethod: String = "card", // card, bank_transfer, debit_order
    val isRecurring: Boolean = false,
    val category: String = "general", // general, medical, education, feeding
    
    // Impact tracking
    val dogsFeeding: Int = 0,
    val surgeriesEnabled: Int = 0,
    val clinicsSupported: Int = 0,
    val animalsHelped: Int = 0,
    
    // Receipt/Certificate info
    val receiptNumber: String = "",
    val issuedCertificate: Boolean = false,
    val certificateUrl: String = ""
) {
    companion object {
        /**
         * Calculate impact metrics based on donation amount
         */
        fun calculateImpact(amount: Double): ImpactMetrics {
            // Based on real-world estimates from the donate page
            val dogsFeeding = (amount / 20.0).toInt() // R100 feeds 5 dogs for a week = R20 per dog per week
            val surgeriesEnabled = (amount / 5000.0).toInt() // R5000 enables emergency surgery
            val clinicsSupported = (amount / 1000.0).toInt() // R1000 supports mobile clinic outreach
            val animalsHelped = (amount / 100.0).toInt() // General estimate
            
            return ImpactMetrics(
                dogsFeeding = dogsFeeding,
                surgeriesEnabled = surgeriesEnabled,
                clinicsSupported = clinicsSupported,
                animalsHelped = animalsHelped
            )
        }
    }
}

/**
 * Impact metrics calculated from donation amount
 */
data class ImpactMetrics(
    val dogsFeeding: Int = 0,
    val surgeriesEnabled: Int = 0,
    val clinicsSupported: Int = 0,
    val animalsHelped: Int = 0
)

/**
 * User's cumulative donation statistics
 */
data class DonationStats(
    val totalDonations: Double = 0.0,
    val donationCount: Int = 0,
    val totalDogsFeeding: Int = 0,
    val totalSurgeriesEnabled: Int = 0,
    val totalClinicsSupported: Int = 0,
    val totalAnimalsHelped: Int = 0,
    val firstDonationDate: Timestamp? = null,
    val lastDonationDate: Timestamp? = null,
    val donorLevel: DonorLevel = DonorLevel.SUPPORTER,
    val nextLevelThreshold: Double = 0.0,
    val nextLevelProgress: Float = 0f
) {
    companion object {
        /**
         * Calculate donor level based on total donations
         */
        fun calculateDonorLevel(totalAmount: Double): DonorLevel {
            return when {
                totalAmount >= 50000 -> DonorLevel.DIAMOND
                totalAmount >= 20000 -> DonorLevel.PLATINUM
                totalAmount >= 10000 -> DonorLevel.GOLD
                totalAmount >= 5000 -> DonorLevel.SILVER
                totalAmount >= 1000 -> DonorLevel.BRONZE
                else -> DonorLevel.SUPPORTER
            }
        }
        
        /**
         * Get next level threshold
         */
        fun getNextLevelThreshold(currentLevel: DonorLevel): Double {
            return when (currentLevel) {
                DonorLevel.SUPPORTER -> 1000.0
                DonorLevel.BRONZE -> 5000.0
                DonorLevel.SILVER -> 10000.0
                DonorLevel.GOLD -> 20000.0
                DonorLevel.PLATINUM -> 50000.0
                DonorLevel.DIAMOND -> 100000.0 // Just for display
            }
        }
    }
}

/**
 * Donor level based on cumulative donations
 */
enum class DonorLevel(val displayName: String, val colorHex: String, val emoji: String) {
    SUPPORTER("Supporter", "#6B7280", "❤️"),
    BRONZE("Bronze Hero", "#CD7F32", "🥉"),
    SILVER("Silver Guardian", "#C0C0C0", "🥈"),
    GOLD("Gold Champion", "#FFD700", "🥇"),
    PLATINUM("Platinum Protector", "#E5E4E2", "💎"),
    DIAMOND("Diamond Savior", "#B9F2FF", "💠")
}

/**
 * Quarterly or yearly goal for fundraising campaigns
 */
data class CampaignGoal(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val targetAmount: Double = 0.0,
    val currentAmount: Double = 0.0,
    val startDate: Timestamp = Timestamp.now(),
    val endDate: Timestamp = Timestamp.now(),
    val category: String = "general", // general, surgeries, feeding, education
    val isActive: Boolean = true
) {
    val progress: Float
        get() = if (targetAmount > 0) (currentAmount / targetAmount).toFloat().coerceIn(0f, 1f) else 0f
    
    val remainingAmount: Double
        get() = (targetAmount - currentAmount).coerceAtLeast(0.0)
}
