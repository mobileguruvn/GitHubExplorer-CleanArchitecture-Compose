package com.brian.githubbrowser.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.brian.githubbrowser.navigation.Routes.UserDetail
import com.brian.users.presentation.userdetail.UserDetailScreen
import com.brian.users.presentation.users.UserListScreen
import com.brian.users.utils.NAV_ARG_LOGIN_NAME

@Composable
fun GithubNavGraph(
    navController: NavHostController,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    NavHost(
        navController = navController,
        startDestination = Routes.UserList.route
    ) {
        composable(route = Routes.UserList.route) {
            UserListScreen(onUserClick = { loginName ->
                navController.navigate(UserDetail.createRoute(loginName))
            }, contentPadding = contentPadding)
        }

        composable(
            route = UserDetail.route,
            arguments = listOf(
                navArgument(NAV_ARG_LOGIN_NAME) { type = NavType.StringType }
            )) { backStackEntry ->
            UserDetailScreen(contentPadding = contentPadding)
        }
    }

}