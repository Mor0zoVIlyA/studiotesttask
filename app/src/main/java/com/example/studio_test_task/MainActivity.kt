package com.example.studio_test_task

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.card.presentation.CardViewModel
import com.card.presentation.ProductScreen
import com.example.studio_test_task.ui.theme.StudiotesttaskTheme
import com.main_screen.presentation.MainScreen
import com.main_screen.presentation.MainViewModel
import com.second_layer_catalog.presentation.SecondScreen
import com.second_layer_catalog.presentation.SecondScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudiotesttaskTheme {
                // A surface container using the 'background' color from the theme
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main_screen") {
        composable("main_screen") {
            val viewModel = hiltViewModel<MainViewModel>()
            val list by viewModel.dataList.collectAsState()
            MainScreen(list,
                navController,
                viewModel::toggleExpanded,
                viewModel::isExpanded
            )
        }
        composable("second_layer/{slug}/{label}") { backStackEntry ->
            val slug = backStackEntry.arguments?.getString("slug") ?: ""
            val label = backStackEntry.arguments?.getString("label") ?: ""
            val secondViewModel =
                hiltViewModel<SecondScreenViewModel, SecondScreenViewModel.ViewModelFactory> { viewModelFactory ->
                    viewModelFactory.create(slug)
                }
            val state by secondViewModel.listState.collectAsState()
            SecondScreen(label, state, navController)
        }
        composable("description/{slug}") { backStackEntry ->
            val slug = backStackEntry.arguments?.getString("slug") ?: ""
            val cardViewModel =
                hiltViewModel<CardViewModel, CardViewModel.ViewModelFactory> { viewModelFactory ->
                    viewModelFactory.create(slug)
                }
            val state by cardViewModel.infoState.collectAsState()
            ProductScreen(state, cardViewModel::shareInfo, navController)
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StudiotesttaskTheme {
        Greeting("Android")
    }
}