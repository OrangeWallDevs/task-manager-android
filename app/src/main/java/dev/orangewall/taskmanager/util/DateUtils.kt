package dev.orangewall.taskmanager.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDateToISO(date: Date): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return dateFormat.format(date)
}