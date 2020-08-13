package mezzari.torres.lucas.github_test_application.archive

import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Lucas T. Mezzari
 * @since 13/08/2020
 */
fun Date.format(format: String = "yyyy-MM-dd", locale: Locale = Locale.getDefault()): String {
    return SimpleDateFormat(format, locale).format(this) ?: ""
}

fun String.toDate(format: String = "yyyy-MM-dd", locale: Locale = Locale.getDefault()): Date? {
    return SimpleDateFormat(format, locale).parse(this)
}