package org.sid.tmdbapp.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import org.sid.tmdbapp.data.model.ErrorResponse
import org.sid.tmdbapp.data.model.Results
import org.sid.tmdbapp.data.model.TrendingMovieResponse
import org.sid.tmdbapp.data.remote.api.Resource
import org.sid.tmdbapp.ui.viewModel.MoviesViewModel


@Composable
fun MoviesScreen(navController: NavController,viewModel: MoviesViewModel) {
    val state by viewModel.getTrendingMovie.observeAsState(initial = Resource.Loading)
    when (state) {
        is Resource.Loading -> LoadingScreen()
        is Resource.Success -> MovieGridScreen(navController,viewModel,movies = (state as Resource.Success).trendingMovieResponse.results, onMovieClick = {})
        is Resource.Error -> ErrorScreen(errorResponse = (state as Resource.Error).errorResponse)
    }
}
//  MovieGridScreen(movies = movies, onMovieClick = {})
@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(errorResponse: ErrorResponse) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = errorResponse.statusMessage ?: "An error occurred",
            color = Color.Red,
            textAlign = TextAlign.Center
        )
    }
}



@Composable
fun MovieGridScreen(navController: NavController,viewModel: MoviesViewModel,movies: List<Results>, onMovieClick: (Results) -> Unit) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    val filteredMovies = movies.filter {
        it.title?.contains(searchQuery.text, ignoreCase = true) ?: false
    }

    Column {
            SearchBar(searchQuery) { query ->
                searchQuery = query
            }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredMovies) { movie ->
                MovieItem(movie,viewModel, onClick = { onMovieClick(movie) } ,navController)
            }
        }
    }
}

@Composable
fun SearchBar(searchQuery: TextFieldValue, onSearchQueryChange: (TextFieldValue) -> Unit) {
    BasicTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .border(1.dp, Color.LightGray, shape = RoundedCornerShape(8.dp))
                    .padding(top = 24.dp,start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Search, // Search icon from Material Icons
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f) // Optional: adjust the color and opacity
                )
                Box(modifier = Modifier.padding(start = 8.dp)) {
                    if (searchQuery.text.isEmpty()) {
                        Text(
                            "Search movies...",
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                        )
                    }
                    innerTextField() // This displays the text field content
                }
            }
        }
    )
}

@Composable
fun MovieItem(movie: Results,viewModel: MoviesViewModel, onClick: (Results) -> Unit, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                viewModel.selectMovie(movie)
                navController.navigate("movie_detail")
            }
    ) {
        Image(
            painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500${movie.posterPath}"),
            contentDescription = movie.title,
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(15.dp)),
            contentScale = ContentScale.Crop
        )
        movie.title?.let {
            Text(
                text = it,
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}