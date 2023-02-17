package com.boyasec.test.utils

import java.util.regex.Pattern

object PatternUtils {
    fun isEmail(email: String): Boolean {
        return try {
            val check = "([a-z0-9A-Z]+[-|.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}"
            val regex = Pattern.compile(check)
            regex.matcher(email).matches()
        } catch (e: Throwable) {
            false
        }
    }
}