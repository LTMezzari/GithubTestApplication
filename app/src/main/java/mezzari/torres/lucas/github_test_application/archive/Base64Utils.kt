package mezzari.torres.lucas.github_test_application.archive

import android.util.Base64

/**
 * @author Lucas T. Mezzari
 * @since 13/08/2020
 */
fun String.encodeBase64(flag: Int = Base64.DEFAULT): String {
    return Base64.encodeToString(this.toByteArray(), flag)
}

fun String.decodeBase64(flag: Int = Base64.DEFAULT): String {
    return String(Base64.decode(this, flag))
}