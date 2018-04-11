package com.home.template.utils

import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertThat

class Test {

    private val subject: TransformUtil? = null

    @org.junit.Test
    fun testSubj() {
        assertThat(String(), instanceOf(String::class.java))
    }
}
