package com.example.minimoneybox

import com.example.minimoneybox.util.LoginValidationUtil
import com.example.minimoneybox.util.LoginValidationUtil.LoginFieldsInput
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class LoginValidationUtilAllFieldsValidTest(
    private val allFieldsInput: LoginFieldsInput,
    private val expectedResult: Boolean
) {

    @Test
    fun allFieldsValid() {
        assertThat(LoginValidationUtil().allFieldsValid(allFieldsInput), equalTo(expectedResult))
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun allFieldsInput() = listOf(
            arrayOf(LoginFieldsInput("foo@bar.com", "Test123456789", "FooBarS"), true),
            arrayOf(LoginFieldsInput("foo@bar.com", "Test123456789", ""), true),
            arrayOf(LoginFieldsInput("foo@bar.com", "Test", ""), false),
            arrayOf(LoginFieldsInput("", "Test123456789", ""), false),
            arrayOf(LoginFieldsInput("", "", ""), false)
        )
    }
}

@RunWith(Parameterized::class)
class LoginValidationUtilIsValidEmailTest(
    private val input: String,
    private val expectedResult: Boolean
) {

    @Test
    fun isValidEmail() {
        assertThat(LoginValidationUtil().isValidEmail(input), equalTo(expectedResult))
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun emailInput() = listOf(
            arrayOf("Test@C.com", true),
            arrayOf("Tets@ccom", false),
            arrayOf("test.com", false),
            arrayOf("", false)
        )
    }
}

@RunWith(Parameterized::class)
class LoginValidationUtilIsValidPasswordTest(
    private val input: String,
    private val expectedResult: Boolean
) {

    @Test
    fun isValidEmail() {
            assertThat(LoginValidationUtil().isValidPassword(input), equalTo(expectedResult))
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun emailInput() = listOf(
            arrayOf("Password1234", true),
            arrayOf("Password123456789101112131415161718192021222324252627282930", false),
            arrayOf("PasswordPassword", false),
            arrayOf("password1234", false),
            arrayOf("Pass123", false)
        )
    }
}

@RunWith(Parameterized::class)
class LoginValidationUtilIsValidNameTest(
    private val input: String,
    private val expectedResult: Boolean
) {

    @Test
    fun isValidEmail() {
        assertThat(LoginValidationUtil().isValidName(input), equalTo(expectedResult))
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun nameInput() = listOf(
            arrayOf("Alexandria", true),
            arrayOf("alexandria", true),
            arrayOf("", true),
            arrayOf("AlexandriaSmithAlexandriaSmithAlexandriaSmithAlexandriaSmith", false),
            arrayOf("Alex", false),
            arrayOf("alex", false)
        )
    }
}
