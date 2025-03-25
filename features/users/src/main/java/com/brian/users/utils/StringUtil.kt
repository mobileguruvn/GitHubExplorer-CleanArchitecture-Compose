package com.brian.users.utils


fun String?.safe(): String {
    return this ?: ""
}

fun formatNumberByStep(step: Int = 10, number: Int): String {
    return if (number >= step) "${number / step}0+" else number.toString()
}