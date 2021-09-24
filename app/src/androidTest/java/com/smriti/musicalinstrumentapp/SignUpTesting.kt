package com.smriti.musicalinstrumentapp

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@LargeTest
@RunWith(JUnit4::class)
class SignUpTesting {
    @get:Rule
    val testRule = ActivityScenarioRule(SignUpActivity::class.java)

    @Test
    fun checkSignUpUI()
    {
        Espresso.onView(withId(R.id.etName))
                .perform(ViewActions.typeText("Al Pacino"))
        Espresso.closeSoftKeyboard()
        Thread.sleep(200)
        Espresso.onView(withId(R.id.etPhone))
                .perform(ViewActions.typeText("9821012345"))
        Espresso.closeSoftKeyboard()
        Thread.sleep(200)
        Espresso.onView(withId(R.id.etUsername))
                .perform(ViewActions.typeText("Pacino"))
        Espresso.closeSoftKeyboard()
        Thread.sleep(200)
        Espresso.onView(withId(R.id.etPassword))
                .perform(ViewActions.typeText("1234"))
        Espresso.closeSoftKeyboard()
        Thread.sleep(200)
        Espresso.onView(withId(R.id.etConfirm))
                .perform(ViewActions.typeText("1234"))
        Espresso.closeSoftKeyboard()
        Thread.sleep(200)
        Espresso.onView(withId(R.id.etEmail))
                .perform(ViewActions.typeText("Alpacino@gmail.com"))
        Espresso.closeSoftKeyboard()
        Thread.sleep(200)
        Espresso.onView(withId(R.id.etAddress))
                .perform(ViewActions.typeText("Kathmandu"))
        Espresso.closeSoftKeyboard()
        Thread.sleep(200)

        Espresso.onView(withId(R.id.btnSignUp))
                .perform(ViewActions.click())

        Thread.sleep(2000)

        Espresso.onView(withId(R.id.btnLogin))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

}