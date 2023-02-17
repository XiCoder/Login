package com.boyasec.test.utils


sealed class PasswordLevel {
    object Easy : PasswordLevel()
    object Middle : PasswordLevel()
    object Strong : PasswordLevel()
}

data class CheckResult(
    val value: String,
    var length: Int = 0,
    var letterSize: Int = 0,
    var numSize: Int = 0,
    var lowerSize: Int = 0,
    var upperSize: Int = 0,
    var otherSize: Int = 0,
    var isAllEq: Boolean = true,
    // 连续数字|连续字母|连续键盘
    var isContinue: Boolean = false

) {

    fun isAllLetter(): Boolean {
        return length == letterSize
    }

    fun isAllDigit(): Boolean {
        return length == numSize
    }


}

object PasswordUtils {

    private const val MIN_LENGTH = 6

    private const val MIDDLE_LENGTH = 12

    private const val MAX_CONTINUE_NUM = 3

    private val continueList = arrayOf(
        "qwertyuiopasdfghjklzxcvbnm",
        "QWERYUIOPASDFGHJKLZXCVBNM",
        "1234567890",
        "abcdefghijklmnopqrstuvwxyz"
    )

    /**
     * 首先密码长度要大于6位
     */
    fun checkPasswordLevel(password: String): PasswordLevel {
        val result = CheckResult(password)
        var level = 0
        checkContainLetter(result)
        if (result.isAllEq || result.length < MIN_LENGTH) {
            return PasswordLevel.Easy
        }
        if (result.length > MIN_LENGTH) {
            level++
        }
        if (result.length > MIDDLE_LENGTH) {
            level++
        }
        // 含有数字
        if (result.numSize > 0) {
            level++
        }
        // 含有字母
        if (result.letterSize > 0) {
            level++
        }
        //含有特殊字符
        if (result.otherSize > 0) {
            level++
        }
        // 数字字母混合+1
        if (!result.isAllDigit() && !result.isAllLetter()) {
            level++
        }
        // 大小写混合+1
        if (result.lowerSize > 0 && result.upperSize > 0) {
            level++
        }
        //存在连续字符-1
        if (result.isContinue) {
            level--
        }
        println(result.toString() + "----level =${level}")
        return when (level) {
            in 0..2 -> PasswordLevel.Easy
            in 3..4 -> PasswordLevel.Middle
            in 5..6 -> PasswordLevel.Strong
            else -> {
                PasswordLevel.Easy
            }
        }
    }


    /**
     * 检查密码中包含的字母个数
     */
    private fun checkContainLetter(config: CheckResult): CheckResult {
        var continueLetterFlag = false
        var continueNumFlag = false
        var lastChar: Char? = null
        var allEqFlag = true
        var eqCount = 1
        config.value.toCharArray().forEach {
            if (it.isLetter()) {
                config.letterSize++
                if (it.isLowerCase()) {
                    config.lowerSize++
                } else {
                    config.upperSize++
                }
            } else if (it.isDigit()) {
                config.numSize++
            } else {
                config.otherSize++
            }
            // 检查是否存在连续相同数字
            lastChar?.let { last ->
                if (last != it) {
                    eqCount = 1
                    allEqFlag = false
                } else {
                    eqCount++
                    if (eqCount >= MAX_CONTINUE_NUM) {
                        config.isContinue = true
                    }
                }
            }
            lastChar = it
        }
        config.isAllEq = allEqFlag
        config.length = config.value.length
        return config
    }

    /**
     * 检查密码是否包含小写字母
     */
    private fun checkContainLower(password: String): Int {
        return password.toCharArray().count { it.isLetter().and(it.isLowerCase()) }
    }

    private fun checkContinue(result: CheckResult): CheckResult {
        if (result.length < MIN_LENGTH) {
            return result
        }
        var temp: String
        var offset: Int
        continueList.forEach { continueText ->
            offset = 0
            for (i in MAX_CONTINUE_NUM..result.length) {
                temp = result.value.substring(offset, i)
                if (continueText.contains(temp)) {
                    result.isContinue = true
                    return result
                }
            }
        }
        return result
    }
}