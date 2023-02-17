package com.boyasec.test.extentions

import com.boyasec.test.utils.PatternUtils

fun String?.isEmail(): Boolean {
    return this?.let {
        return PatternUtils.isEmail(it)
    } ?: false
}