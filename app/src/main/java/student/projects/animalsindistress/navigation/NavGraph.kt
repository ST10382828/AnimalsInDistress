package student.projects.animalsindistress.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import student.projects.animalsindistress.ui.screens.DonateScreen
import student.projects.animalsindistress.ui.screens.ContactScreen
import student.projects.animalsindistress.ui.screens.ServicesScreen
import student.projects.animalsindistress.ui.screens.ProgramsScreen
import student.projects.animalsindistress.ui.screens.NewsListScreen
import student.projects.animalsindistress.ui.screens.NewsDetailScreen
import student.projects.animalsindistress.ui.screens.GalleryScreen
import student.projects.animalsindistress.ui.screens.TeamScreen
import student.projects.animalsindistress.ui.screens.PartnersScreen
import student.projects.animalsindistress.ui.screens.EquineOutreachScreen
import student.projects.animalsindistress.ui.screens.CaseStudyScreen
import student.projects.animalsindistress.ui.screens.DancingDonkeyScreen
import student.projects.animalsindistress.ui.screens.AboutScreen
import student.projects.animalsindistress.ui.screens.ShopScreen
import student.projects.animalsindistress.ui.screens.FAQScreen
import student.projects.animalsindistress.ui.screens.VolunteerScreen
import student.projects.animalsindistress.ui.screens.AnnualReportScreen
import student.projects.animalsindistress.ui.screens.ThousandHeroesScreen
import student.projects.animalsindistress.ui.screens.MySchoolScreen
import student.projects.animalsindistress.ui.screens.MonthlyDebitScreen
import student.projects.animalsindistress.ui.screens.PayrollGivingScreen
import student.projects.animalsindistress.ui.screens.LegaciesScreen
import student.projects.animalsindistress.ui.screens.PreLovedScreen
import student.projects.animalsindistress.ui.screens.TaxCertificateScreen
import student.projects.animalsindistress.ui.screens.NotFoundScreen
import student.projects.animalsindistress.ui.screens.HomeScreen
import student.projects.animalsindistress.ui.screens.HeartSoleStoreScreen
import student.projects.animalsindistress.ui.screens.GolfDay2025Screen
import student.projects.animalsindistress.ui.screens.EducationScreen
import student.projects.animalsindistress.ui.screens.MoreScreen

sealed class Route(val path: String) {
    data object Home : Route("home")
    data object Donate : Route("donate")
    data object Contact : Route("contact")
    data object Services : Route("services")
    data object Programs : Route("programs")
    data object News : Route("news")
    data object NewsDetail : Route("news/{slug}")
    data object Gallery : Route("gallery")
    data object Team : Route("team")
    data object Partners : Route("partners")
    data object EquineOutreach : Route("equine-outreach")
    data object CaseStudy : Route("case-study")
    data object DancingDonkey : Route("dancing-donkey")
    data object About : Route("about")
    data object Shop : Route("shop")
    data object FAQ : Route("faq")
    data object Volunteer : Route("volunteer")
    data object AnnualReport : Route("annual-report")
    data object ThousandHeroes : Route("thousand-heroes")
    data object MySchool : Route("myschool")
    data object MonthlyDebit : Route("monthly-debit")
    data object PayrollGiving : Route("payroll-giving")
    data object Legacies : Route("legacies")
    data object PreLoved : Route("pre-loved")
    data object TaxCertificate : Route("tax-certificate")
    data object HeartSoleStore : Route("heart-sole-store")
    data object GolfDay2025 : Route("golf-day-2025")
    data object Education : Route("education")
    data object More : Route("more")
    data object NotFound : Route("not-found")
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Route.Home.path) {
        composable(Route.Home.path) { 
            HomeScreen(
                onDonateClick = { navController.navigate(Route.Donate.path) },
                onNavigate = { route -> navController.navigate(route) }
            )
        }
        composable(Route.Donate.path) { DonateScreen() }
        composable(Route.Contact.path) { ContactScreen() }
        composable(Route.Services.path) { ServicesScreen() }
        composable(Route.Programs.path) { ProgramsScreen() }
        composable(Route.News.path) { NewsListScreen(onOpen = { slug -> navController.navigate("news/$slug") }) }
        composable(Route.NewsDetail.path) { backStackEntry ->
            val slug = backStackEntry.arguments?.getString("slug") ?: ""
            NewsDetailScreen(slug = slug)
        }
        composable(Route.Gallery.path) { GalleryScreen() }
        composable(Route.Team.path) { TeamScreen() }
        composable(Route.Partners.path) { PartnersScreen() }
        composable(Route.EquineOutreach.path) { EquineOutreachScreen() }
        composable(Route.CaseStudy.path) { CaseStudyScreen() }
        composable(Route.DancingDonkey.path) { DancingDonkeyScreen() }
        composable(Route.About.path) { AboutScreen() }
        composable(Route.Shop.path) { ShopScreen() }
        composable(Route.FAQ.path) { FAQScreen() }
        composable(Route.Volunteer.path) { VolunteerScreen() }
        composable(Route.AnnualReport.path) { AnnualReportScreen() }
        composable(Route.ThousandHeroes.path) { ThousandHeroesScreen() }
        composable(Route.MySchool.path) { MySchoolScreen() }
        composable(Route.MonthlyDebit.path) { MonthlyDebitScreen() }
        composable(Route.PayrollGiving.path) { PayrollGivingScreen() }
        composable(Route.Legacies.path) { LegaciesScreen() }
        composable(Route.PreLoved.path) { PreLovedScreen() }
        composable(Route.TaxCertificate.path) { TaxCertificateScreen() }
        composable(Route.HeartSoleStore.path) { HeartSoleStoreScreen() }
        composable(Route.GolfDay2025.path) { GolfDay2025Screen() }
        composable(Route.Education.path) { EducationScreen() }
        composable(Route.More.path) { MoreScreen(onNavigate = { route -> navController.navigate(route) }) }
        composable(Route.NotFound.path) { NotFoundScreen() }
    }
}


