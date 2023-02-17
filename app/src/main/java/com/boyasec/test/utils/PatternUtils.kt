package com.boyasec.test.utils

import java.util.regex.Pattern

object PatternUtils {
    fun isEmail(email: String): Boolean {
        return try {
            val check =
                "^\\s*\\w+(?:\\.?[\\w-]+)*@[a-zA-Z\\d]+(?:[-.][a-zA-Z\\d]+)*\\.[a-zA-Z]+\\s*$"
            val regex = Pattern.compile(check)
            regex.matcher(email).matches()
        } catch (e: Throwable) {
            false
        }
    }
}