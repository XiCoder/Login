package com.boyasec.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.boyasec.test.data.RouteConfig
import com.boyasec.test.ui.page.LoginPage
import com.boyasec.test.ui.page.MainPage
import com.boyasec.test.ui.theme.LoginTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginTestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController, startDestination = RouteConfig.ROUTE_LOGIN
                    ) {
                        composable(RouteConfig.ROUTE_LOGIN) { LoginPage(navController = navController) }
                        composable(
                            "${RouteConfig.ROUTE_MAIN}/{account}/{password}", arguments = listOf(
                                navArgument("account") { type = NavType.StringType },
                                navArgument("password") { type = NavType.StringType })
                        ) {
                            val account = it.arguments?.getString("account")
                            val password = it.arguments?.getString("password")
                            MainPage(
                                name = account, password = password, navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val navController = rememberNavController()
    LoginTestTheme {
        LoginPage(navController)
    }
}


