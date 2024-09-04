package org.sid.tmdbapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import org.sid.tmdbapp.ui.viewModel.MoviesViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(movieViewModel: MoviesViewModel, navController: NavController) {
    val movie by movieViewModel.selectedMovie.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(10.dp)
        ) {
            item {
                movie?.let {
                    Spacer(modifier = Modifier.height(8.dp))

                    // Movie poster image
                    Image(
                        painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500${it.posterPath}"),
                        contentDescription = it.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp)
                            .clip(RoundedCornerShape(15.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Movie title
                    it.title?.let { title ->
                        Text(
                            text = title,
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    // Movie overview
                    it.overview?.let { o ->
                        Text(
                            text = o,
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 18.sp
                            ),
                        )
                    }
                }
            }
        }


    }

}