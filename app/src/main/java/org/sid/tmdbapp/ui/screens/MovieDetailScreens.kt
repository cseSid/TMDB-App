package org.sid.tmdbapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.sid.tmdbapp.ui.viewModel.MoviesViewModel


@Composable
fun MovieDetailScreen(movieViewModel: MoviesViewModel) {
    val movie by movieViewModel.selectedMovie.observeAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        movie?.let {
            Text(
                text = it.title ?: "No Title",
                style = MaterialTheme.typography.headlineMedium
            )
            // Display more movie details here
        } ?: run {
            Text(
                text = "No Movie Data",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}