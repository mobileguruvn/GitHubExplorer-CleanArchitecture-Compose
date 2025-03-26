package com.brian.githubbrowser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.githubbrowser.designsystem.theme.GitHubBrowserTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.brian.githubbrowser.navigation.GithubNavGraph
import com.brian.githubbrowser.navigation.Routes
import com.githubbrowser.designsystem.CenterTitleTopAppBar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GitHubBrowserTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GitHubBrowser()
                }
            }
        }
    }
}

@Composable
fun GitHubBrowser() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val canNavigateBack = navController.previousBackStackEntry != null
    val currentScreen = currentBackStackEntry?.destination?.route
    val screenTitle = when (currentScreen) {
        Routes.UserList.route -> R.string.user_list_title
        Routes.UserDetail.route -> R.string.user_detail_title
        else -> R.string.app_name

    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            CenterTitleTopAppBar(
                screenTitle = screenTitle,
                canNavigateBack = canNavigateBack,
                navigateUp = { navController.navigateUp() }
            )
        }) { innerPadding ->
        GithubNavGraph(
            navController,
            innerPadding
        )
    }
}