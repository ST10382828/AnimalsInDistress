package student.projects.animalsindistress.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import student.projects.animalsindistress.ui.theme.*

// Annual Report Screen
@Composable
fun AnnualReportScreen() {
    SimpleInfoScreen(
        title = "Annual Report",
        description = "View our latest annual reports to learn about our impact and financial transparency.",
        sections = listOf(
            InfoSection("2024 Annual Report", "Our comprehensive annual report detailing all activities, achievements, and financial information for the year 2024."),
            InfoSection("Key Achievements", "• Treated over 5,000 animals\n• Conducted 200+ mobile clinic visits\n• Educated 10,000+ community members"),
            InfoSection("Financial Transparency", "We are committed to transparency. All donations are used directly for animal care and community education.")
        )
    )
}

// 1000 Heroes Screen
@Composable
fun ThousandHeroesScreen() {
    SimpleInfoScreen(
        title = "1000 Heroes",
        description = "Join our 1000 Heroes initiative and become a monthly supporter",
        sections = listOf(
            InfoSection("What is 1000 Heroes?", "1000 Heroes is our monthly giving program where supporters commit to a regular monthly donation. These consistent contributions help us plan better and provide ongoing care for animals in need."),
            InfoSection("Your Impact", "As a 1000 Heroes member, your monthly donation directly supports:\n• Regular veterinary care\n• Mobile clinic operations\n• Emergency treatments\n• Community education programs"),
            InfoSection("Join Today", "Sign up for a monthly donation of any amount and become one of our 1000 Heroes. Every contribution makes a difference.")
        )
    )
}

// MySchool Screen
@Composable
fun MySchoolScreen() {
    SimpleInfoScreen(
        title = "MySchool MyVillage MyPlanet",
        description = "Support SAID every time you shop",
        sections = listOf(
            InfoSection("How It Works", "MySchool MyVillage MyPlanet is a fundraising programme that allows you to donate to SAID simply by using your MySchool card when shopping at partner retailers."),
            InfoSection("Getting Started", "1. Register for a MySchool card\n2. Choose SAID as your beneficiary\n3. Use your card when shopping\n4. A percentage of your purchase goes to SAID"),
            InfoSection("Partner Retailers", "Partner retailers include Woolworths, Engen, Waltons, and many more. Every swipe helps us help animals.")
        )
    )
}

// Monthly Debit Screen
@Composable
fun MonthlyDebitScreen() {
    SimpleInfoScreen(
        title = "Monthly Debit Order",
        description = "Set up a monthly debit order for ongoing support",
        sections = listOf(
            InfoSection("Why Monthly Giving?", "Monthly debit orders provide us with predictable, consistent income that helps us plan better care for animals and ensures we can maintain our services year-round."),
            InfoSection("Benefits", "• Tax-deductible (Section 18A certificate provided)\n• Helps us plan ahead\n• Makes a sustained impact\n• Can be adjusted or cancelled anytime"),
            InfoSection("Set Up Your Debit Order", "Contact us to set up a monthly debit order. Choose any amount that works for your budget - every contribution helps.")
        )
    )
}

// Payroll Giving Screen
@Composable
fun PayrollGivingScreen() {
    SimpleInfoScreen(
        title = "Payroll Giving",
        description = "Donate directly from your salary",
        sections = listOf(
            InfoSection("What is Payroll Giving?", "Payroll giving allows you to donate to SAID directly from your salary before tax. This means you get tax relief immediately and can support animals in need effortlessly."),
            InfoSection("Advantages", "• Automatic monthly donations\n• Immediate tax relief\n• No admin hassle\n• Easy to set up and cancel"),
            InfoSection("How to Start", "Speak to your company's HR or payroll department about setting up a payroll giving scheme for SAID. We can provide all necessary documentation.")
        )
    )
}

// Legacies Screen
@Composable
fun LegaciesScreen() {
    SimpleInfoScreen(
        title = "Legacies & Bequests",
        description = "Leave a lasting legacy for animals in need",
        sections = listOf(
            InfoSection("Leave a Legacy", "By including SAID in your will, you can ensure that your love for animals continues to make a difference long into the future. Your bequest will help us continue providing vital veterinary care and education."),
            InfoSection("Types of Bequests", "• Specific sum of money\n• Percentage of your estate\n• Specific property or assets\n• Residual estate after other bequests"),
            InfoSection("More Information", "For confidential information about leaving a legacy to SAID, please contact us. We can provide sample wording for your will and answer any questions.")
        )
    )
}

// Pre-Loved Screen
@Composable
fun PreLovedScreen() {
    SimpleInfoScreen(
        title = "Pre-Loved Donations",
        description = "Donate pre-loved items to support our work",
        sections = listOf(
            InfoSection("What We Accept", "We gladly accept donations of:\n• Clothing and accessories\n• Books and media\n• Household items\n• Toys and games\n• Furniture (depending on condition)"),
            InfoSection("Drop-Off Location", "Bring your pre-loved items to our facility in Midrand during operating hours. Please ensure items are clean and in good condition."),
            InfoSection("Why Donate?", "Your pre-loved items are sold in our shop, with all proceeds going directly to animal care. You declutter, we fundraise, animals benefit!")
        )
    )
}

// Tax Certificate Screen
@Composable
fun TaxCertificateScreen() {
    SimpleInfoScreen(
        title = "18A Tax Certificate",
        description = "Request your tax certificate for donations",
        sections = listOf(
            InfoSection("Tax-Deductible Donations", "SAID is a registered Public Benefit Organisation (PBO) and Non-Profit Organization (NPO). All monetary donations qualify for Section 18A tax certificates, which can be used to reduce your taxable income."),
            InfoSection("How to Request", "To request your 18A tax certificate:\n1. Email animals@animalsindistress.org.za\n2. Provide your full name and donation details\n3. Include your proof of payment\n4. We'll send your certificate within 5 business days"),
            InfoSection("Donor Information Required", "Please provide:\n• Full name\n• ID number or company registration\n• Contact details\n• Donation amount and date\n• Proof of payment")
        )
    )
}

// Not Found Screen - MOVED TO NotFoundScreen.kt
// @Composable
// fun NotFoundScreen() { ... }

// Helper Components
@Composable
private fun SimpleInfoScreen(
    title: String,
    description: String,
    sections: List<InfoSection>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    title,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp
                    ),
                    color = Foreground,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    description,
                    style = MaterialTheme.typography.titleMedium,
                    color = MutedForeground,
                    textAlign = TextAlign.Center
                )
            }
        }
        
        items(sections.size) { index ->
            val section = sections[index]
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        section.title,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        fontSize = 20.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        section.content,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MutedForeground
                    )
                }
            }
        }
    }
}

private data class InfoSection(val title: String, val content: String)
