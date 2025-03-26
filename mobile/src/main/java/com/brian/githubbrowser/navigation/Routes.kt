package com.brian.githubbrowser.navigation

import com.brian.users.utils.NAV_ARG_LOGIN_NAME


sealed class Routes(val route: String) {

    object UserList : Routes("user_list")
    object UserDetail : Routes("user_detail/{$NAV_ARG_LOGIN_NAME}") {
        fun createRoute(loginName: String) = "user_detail/$loginName"
    }

}