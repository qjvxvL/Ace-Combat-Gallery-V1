package dev.dwikiy.ace_combat_gallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.dwikiy.ace_combat_gallery.ui.theme.AceCombatGalleryTheme
import dev.dwikiy.ace_combat_gallery.Routes.MainRoute.Login.toLogin
import dev.dwikiy.ace_combat_gallery.Routes.MainRoute.SignUp.toSignUp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AceCombatGalleryTheme {
            // Scaffold provides a basic layout structure
            Scaffold { padding ->

                MainNavigation()
            }
        }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


sealed class Routes(val route: String) {

    data object MainRoute : Routes("mainRoutes") {

        data object Login : Routes("${MainRoute.route}/login") {
            fun NavController.toLogin() = navigate("${MainRoute.route}/login")
        }

        data object ForgotPassword : Routes("${MainRoute.route}/forgotPassword") {
            fun NavController.toForgotPassword() = navigate("${MainRoute.route}/forgotPassword")

        }

        data object SignUp : Routes("${MainRoute.route}/signUp") {
            fun NavController.toSignUp() = navigate("${MainRoute.route}/signUp")

        }

        data object Home : Routes("${MainRoute.route}/home") {
            fun NavController.toHome() = navigate("${MainRoute.route}/home")

        }
    }

}

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Routes.MainRoute.Login.route) {
        composable ("audio_player_screen") {
            Box(
                modifier = Modifier.fillMaxSize()
                    .background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                SleekAudioPlayer()
            }
        }

        composable(route = Routes.MainRoute.Login.route){
            LoginScreen(navController)
        }

        composable(route = Routes.MainRoute.SignUp.route) {
            SignUpScreen(navController)
        }

        composable(route = Routes.MainRoute.Home.route) {
            HomeScreen()
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AceCombatGalleryTheme {
        Greeting("Android")
    }
}