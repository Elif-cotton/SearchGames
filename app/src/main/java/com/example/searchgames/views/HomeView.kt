package com.example.searchgames.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.searchgames.components.CardGame
import com.example.searchgames.components.MainTopBar
import com.example.searchgames.util.Constants.Companion.CUSTOM_BLACK
import com.example.searchgames.viewmodel.GamesViewModel
import androidx.compose.runtime.setValue

import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.searchgames.components.Loader

@Composable
fun HomeView(viewModel: GamesViewModel, navController: NavController){
    Scaffold(
        topBar = {
            MainTopBar(title = "API GAMES", onClickBackButton = {}) {
                navController.navigate("SearchGameView")
            }
        }
    ) {
        ContentHomeView(viewModel, it, navController)
    }

}

@Composable
fun ContentHomeView(viewModel: GamesViewModel, pad: PaddingValues, navController: NavController) {

    val gamesPage = viewModel.gamesPage.collectAsLazyPagingItems()
    var search by remember { mutableStateOf("") }


    Column(
        modifier = Modifier.padding(pad),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextField(
            value = search,
            onValueChange = { search = it },
            label = { Text(text = "Search") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    val zero = 0
                    navController.navigate("DetailView/${zero}/?${search}")
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 18.dp, end = 10.dp)
        )

        LazyColumn(
            modifier = Modifier
                .background(Color(CUSTOM_BLACK))
        ) {
            items(gamesPage.itemCount) { index ->
                val item = gamesPage[index]
                if (item != null){
                    CardGame(item) {
                        navController.navigate("DetailView/${item.id}/?${search}")
                    }
                    Text(
                        text = item.name,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
            }
            when(gamesPage.loadState.append){
                is LoadState.NotLoading -> Unit
                LoadState.Loading -> {
                    item {
                        Column(
                            modifier = Modifier
                                .fillParentMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Loader()
                        }
                    }
                }
                is LoadState.Error -> {
                    item {
                        Text(text = "Error al cargar")
                    }
                }
            }
        }
    }

}

