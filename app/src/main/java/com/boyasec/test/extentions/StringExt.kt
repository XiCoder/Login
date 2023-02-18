package com.boyasec.test.extentions

import com.boyasec.test.utils.PatternUtils

/**
 * 根据正则判断是否是邮箱
 */
fun String?.isEmail(): Boolean {
    return this?.let {
        return PatternUtils.isEmail(it)
    } ?: false
}