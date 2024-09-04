package org.sid.tmdbapp.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import org.sid.tmdbapp.ui.screens.MovieDetailScreen
import org.sid.tmdbapp.ui.screens.MoviesScreen
import org.sid.tmdbapp.ui.viewModel.MoviesViewModel

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            AppNavHost(navController)
        }
    }

    @Composable
    fun AppNavHost(navController: NavHostController) {

        val movieViewModel: MoviesViewModel = viewModel()
        NavHost(navController, startDestination = "movies") {
            composable("movies") { MoviesScreen(navController,movieViewModel) }
            composable("movie_detail") {
                MovieDetailScreen(movieViewModel, navController)
            }
        }
    }

}
