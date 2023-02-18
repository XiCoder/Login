package com.boyasec.test.utils

import com.boyasec.test.utils.PasswordUtils.scanCheck
import com.google.common.truth.Truth.assertThat
import org.junit.Test

internal class PasswordUtilsTest {

    @Test
    fun checkPasswordLevel() {
        assertThat(PasswordUtils.checkPasswordLevel("aaaaAA")).isInstanceOf(PasswordLevel.Easy::class.java)
        assertThat(PasswordUtils.checkPasswordLevel("aaaaaa")).isInstanceOf(PasswordLevel.Easy::class.java)
        assertThat(PasswordUtils.checkPasswordLevel("123aaa")).isInstanceOf(PasswordLevel.Easy::class.java)
        assertThat(PasswordUtils.checkPasswordLevel("123Aaaa")).isInstanceOf(PasswordLevel.Middle::class.java)
        assertThat(PasswordUtils.checkPasswordLevel("132Aaaa")).isInstanceOf(PasswordLevel.Middle::class.java)
        assertThat(PasswordUtils.checkPasswordLevel("123Aaaa123")).isInstanceOf(PasswordLevel.Middle::class.java)
        assertThat(PasswordUtils.checkPasswordLevel("123AaAa123")).isInstanceOf(PasswordLevel.Middle::class.java)
        assertThat(PasswordUtils.checkPasswordLevel("123AaAa123@")).isInstanceOf(PasswordLevel.Strong::class.java)
        assertThat(PasswordUtils.checkPasswordLevel("151Aa15681@")).isInstanceOf(PasswordLevel.Strong::class.java)
        assertThat(PasswordUtils.checkPasswordLevel("AAAAAAAA")).isInstanceOf(PasswordLevel.Easy::class.java)
        assertThat(PasswordUtils.checkPasswordLevel("aaaaAAAA")).isInstanceOf(PasswordLevel.Easy::class.java)
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