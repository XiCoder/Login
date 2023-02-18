package com.boyasec.test.utils

sealed class PasswordLevel {
    object Easy : PasswordLevel()
    object Middle : PasswordLevel()
    object Strong : PasswordLevel()
    object VeryStrong : PasswordLevel()
}

data class CheckResult(
    val value: String,
    var length: Int = 0,
    var letterSize: Int = 0,
    var digitSize: Int = 0,
    var lowerSize: Int = 0,
    var upperSize: Int = 0,
    var otherSize: Int = 0,
    // 字符全部相同
    var isAllEq: Boolean = true,
    // 连续数字|连续字母|连续键盘
    var isContinue: Boolean = false
) {

    fun isAllLetter(): Boolean {
        return length == letterSize
    }

    fun isAllDigit(): Boolean {
        return length == digitSize
    }

    fun containLetterAndDigit(): Boolean {
        return letterSize > 0 && digitSize > 0
    }

    fun containUpperAndLower(): Boolean {
        return upperSize > 0 && lowerSize > 0
    }

    fun containDigit(): Boolean {
        return digitSize > 0
    }

    fun containLetter(): Boolean {
        return letterSize > 0
    }

    fun containOther(): Boolean {
        return otherSize > 0
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
     * 根据检查结果计算密码强度
     * 密码强度规则：密码长度 +1
     *             包含数字 +1
     *             含字母 +1
     *             包含特殊字符 +1
     *             数字字母混合+1
     *             大小写混合+1
     *             存在连续字符-1
     *
     * @param password 密码
     */
    fun checkPasswordLevel(password: String): PasswordLevel {
        val result = CheckResult(password).scanCheck().checkContinue()
        var level = 0
        // 长度小于Min或者字符全部相同直接返回Easy
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
        if (result.containDigit()) {
            level++
        }
        // 含有字母
        if (result.containLetter()) {
            level++
        }
        //含有特殊字符
        if (result.containOther()) {
            level++
        }
        // 数字字母混合+1
        if (result.containLetterAndDigit()) {
            level++
        }
        // 大小写混合+1
        if (result.containUpperAndLower()) {
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
                PasswordLevel.VeryStrong
            }
        }
    }


    /**
     * 统计各种类型的个数
     */
    fun CheckResult.scanCheck(): CheckResult {
        var lastChar: Char? = null
        var allEqFlag = true
        var eqCount = 1
        this.value.toCharArray().forEach {
            if (it.isLetter()) {
                this.letterSize++
                if (it.isLowerCase()) {
                    this.lowerSize++
                } else {
                    this.upperSize++
                }
            } else if (it.isDigit()) {
                this.digitSize++
            } else {
                this.otherSize++
            }
            // 检查是否存在连续相同数字
            lastChar?.let { last ->
                if (last != it) {
                    eqCount = 1
                    allEqFlag = false
                } else {
                    eqCount++
                    if (eqCount >= MAX_CONTINUE_NUM) {
                        this.isContinue = true
                    }
                }
            }
            lastChar = it
        }
        this.isAllEq = allEqFlag
        this.length = this.value.length
        return this
    }


    /**
     * 检查是否包含顺序字母、顺序数字、键盘顺序
     */
    fun CheckResult.checkContinue(): CheckResult {
        if (this.length < MIN_LENGTH) {
            return this
        }
        var temp: String
        var offset: Int
        continueList.forEach { continueText ->
            offset = 0
            for (i in MAX_CONTINUE_NUM..this.length) {
                temp = this.value.substring(offset, i)
                if (continueText.contains(temp)) {
                    this.isContinue = true
                    return this
                }
            }
        }
        return this
    }
}