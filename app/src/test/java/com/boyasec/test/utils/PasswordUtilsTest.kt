package com.boyasec.test.utils

import com.boyasec.test.utils.PasswordUtils.scanCheck
import com.google.common.truth.Truth.assertThat
import org.junit.Test

internal class PasswordUtilsTest {

    @Test
    fun checkPasswordLevel() {
        // 密码长度小于6或者字符全部相等
        assertThat(PasswordUtils.checkPasswordLevel("aaaaaa")).isInstanceOf(PasswordLevel.Easy::class.java)
        assertThat(PasswordUtils.checkPasswordLevel("111111111")).isInstanceOf(PasswordLevel.Easy::class.java)
        assertThat(PasswordUtils.checkPasswordLevel("aaaaaaaaa")).isInstanceOf(PasswordLevel.Easy::class.java)
        //纯字母
        assertThat(PasswordUtils.checkPasswordLevel("afaasgg")).isInstanceOf(PasswordLevel.Easy::class.java)
        //纯数字
        assertThat(PasswordUtils.checkPasswordLevel("1275857")).isInstanceOf(PasswordLevel.Easy::class.java)
        // 数字与字母组合连续
        assertThat(PasswordUtils.checkPasswordLevel("123aa21")).isInstanceOf(PasswordLevel.Middle::class.java)
        // 数字与字母组合包含大小写连续
        assertThat(PasswordUtils.checkPasswordLevel("123Aaba")).isInstanceOf(PasswordLevel.Middle::class.java)
        // 数字与字母组合包含大小写不连续,长度大于6
        assertThat(PasswordUtils.checkPasswordLevel("132Aaba")).isInstanceOf(PasswordLevel.Strong::class.java)
        // 数字与字母组合包含大小写连续，长度大于6
        assertThat(PasswordUtils.checkPasswordLevel("123Aaaa123")).isInstanceOf(PasswordLevel.Middle::class.java)
        // 数字与字母组合包含大小写不连续，长度大于6
        assertThat(PasswordUtils.checkPasswordLevel("143Aaba113")).isInstanceOf(PasswordLevel.Strong::class.java)
        //数字与字母组合包含大小写不连续，长度大于12
        assertThat(PasswordUtils.checkPasswordLevel("143AaAa143144")).isInstanceOf(PasswordLevel.Strong::class.java)
        //数字与字母组合包含大小写不连续，长度大于6,特殊字符
        assertThat(PasswordUtils.checkPasswordLevel("124Aa153@")).isInstanceOf(PasswordLevel.Strong::class.java)
        //数字与字母组合包含大小写连续，长度大于6,特殊字符
        assertThat(PasswordUtils.checkPasswordLevel("123Aa153@")).isInstanceOf(PasswordLevel.Strong::class.java)
        //数字与字母组合包含大小写不连续，长度大于12,包含特殊字符
        assertThat(PasswordUtils.checkPasswordLevel("151Aa15681@42")).isInstanceOf(PasswordLevel.VeryStrong::class.java)
        //数字与字母组合包含大小写连续，长度大于12,包含特殊字符
        assertThat(PasswordUtils.checkPasswordLevel("123Aa15681@42")).isInstanceOf(PasswordLevel.VeryStrong::class.java)

    }

    @Test
    fun checkAllNum(){
        assertThat(CheckResult("11").scanCheck().isAllDigit()).isTrue()
        assertThat(CheckResult("1111111").scanCheck().isAllDigit()).isTrue()
        assertThat(CheckResult("131231441").scanCheck().isAllDigit()).isTrue()
        assertThat(CheckResult("13asd14f41").scanCheck().isAllDigit()).isFalse()
        assertThat(CheckResult("aaAAgag").scanCheck().isAllDigit()).isFalse()
    }

    @Test
    fun checkAllLetter() {
        assertThat(CheckResult("aa").scanCheck().isAllLetter()).isTrue()
        assertThat(CheckResult("111").scanCheck().isAllLetter()).isFalse()
        assertThat(CheckResult("asdga11").scanCheck().isAllLetter()).isFalse()
        assertThat(CheckResult("sadgasdgsadgsa").scanCheck().isAllLetter()).isTrue()
        assertThat(CheckResult("13asd14f41").scanCheck().isAllLetter()).isFalse()
        assertThat(CheckResult("aaAAgag").scanCheck().isAllLetter()).isTrue()
    }


    @Test
    fun checkShortPassword() {
        assertThat(PasswordUtils.checkPasswordLevel("123")).isInstanceOf(PasswordLevel.Easy::class.java)
        assertThat(PasswordUtils.checkPasswordLevel("1234")).isInstanceOf(PasswordLevel.Easy::class.java)
        assertThat(PasswordUtils.checkPasswordLevel("12345")).isInstanceOf(PasswordLevel.Easy::class.java)
        assertThat(PasswordUtils.checkPasswordLevel("abc")).isInstanceOf(PasswordLevel.Easy::class.java)
        assertThat(PasswordUtils.checkPasswordLevel("abc12")).isInstanceOf(PasswordLevel.Easy::class.java)
        assertThat(PasswordUtils.checkPasswordLevel("123abc")).isInstanceOf(PasswordLevel.Easy::class.java)
    }

    /**
     * 数字字母混合
     */
    @Test
    fun checkLetterAndDigit() {
        assertThat(CheckResult("aa").scanCheck().containLetterAndDigit()).isFalse()
        assertThat(CheckResult("111").scanCheck().containLetterAndDigit()).isFalse()
        assertThat(CheckResult("asdga11").scanCheck().containLetterAndDigit()).isTrue()
        assertThat(CheckResult("sadgasdgsadgsa").scanCheck().containLetterAndDigit()).isFalse()
        assertThat(CheckResult("13asd14f41").scanCheck().containLetterAndDigit()).isTrue()
        assertThat(CheckResult("aaAAgag").scanCheck().containLetterAndDigit()).isFalse()
    }

    /**
     * 数字字母混合
     */
    @Test
    fun checkOther() {
        assertThat(CheckResult("aa").scanCheck().containOther()).isFalse()
        assertThat(CheckResult("111").scanCheck().containOther()).isFalse()
        assertThat(CheckResult("asdga11").scanCheck().containOther()).isFalse()
        assertThat(CheckResult("sad@#dgsadgsa").scanCheck().containOther()).isTrue()
        assertThat(CheckResult("@@!!").scanCheck().containOther()).isTrue()
        assertThat(CheckResult("123@!").scanCheck().containOther()).isTrue()
    }

    /**
     * 相同字符
     */
    @Test
    fun checkEq() {
        assertThat(CheckResult("aa").scanCheck().isAllEq).isTrue()
        assertThat(CheckResult("111").scanCheck().isAllEq).isTrue()
        assertThat(CheckResult("aa111").scanCheck().isAllEq).isFalse()
        assertThat(CheckResult("1111111").scanCheck().isAllEq).isTrue()
        assertThat(CheckResult("AAAAAAAA").scanCheck().isAllEq).isTrue()
        assertThat(CheckResult("AAAagag1AAA11").scanCheck().isAllEq).isFalse()
        assertThat(CheckResult("AAAaaaAA").scanCheck().isAllEq).isFalse()
        assertThat(CheckResult("@@@@@@@@").scanCheck().isAllEq).isTrue()
        assertThat(CheckResult("@@@@####").scanCheck().isAllEq).isFalse()
    }

}