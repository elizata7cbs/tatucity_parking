package com.example.shadowparklogin

sealed class Screens (val screen: String) {
    data object Home: Screens("dashboard")
    data object Search: Screens("search")
    data object Notification: Screens("notification")
    data object Profile: Screens("profile")
}