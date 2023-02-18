package com.boyasec.test.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test

internal class PatternUtilsTest {

    @Test
    fun isEmail() {
        assertThat(PatternUtils.isEmail("12356")).isFalse()
        assertThat(PatternUtils.isEmail("12356@qq")).isFalse()
        assertThat(PatternUtils.isEmail("12356@qq.com")).isTrue()
        assertThat(PatternUtils.isEmail("abc@qq.com")).isTrue()
        assertThat(PatternUtils.isEmail("12356@google.com")).isTrue()
        assertThat(PatternUtils.isEmail("12356.com@google.com")).isTrue()
        assertThat(PatternUtils.isEmail(".12com@google.com")).isFalse()
        assertThat(PatternUtils.isEmail("12356@google.com ")).isFalse()
        assertThat(PatternUtils.isEmail("12356@@google.com ")).isFalse()
    }


}