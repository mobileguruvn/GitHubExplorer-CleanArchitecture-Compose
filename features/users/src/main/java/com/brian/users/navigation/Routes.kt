package com.brian.users.navigation


const val NAV_ARG_LOGIN_NAME = "login_name"

sealed class Routes(val route: String) {

    object UserList : Routes("user_list")
    object UserDetail : Routes("user_detail/{$NAV_ARG_LOGIN_NAME}") {
        fun createRoute(loginName: String) = "user_detail/$loginName"
    }

}