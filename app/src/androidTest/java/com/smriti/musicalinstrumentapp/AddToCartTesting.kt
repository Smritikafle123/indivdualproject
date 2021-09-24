package com.smriti.musicalinstrumentapp

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.smriti.musicalinstrumentapp.adapter.ProductAdapter
import com.smriti.musicalinstrumentapp.api.ServiceBuilder
import com.smriti.musicalinstrumentapp.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@LargeTest
@RunWith(JUnit4::class)
class AddToCartTesting {
    @get:Rule
    val testRule = ActivityScenarioRule(DashboardActivity::class.java)

    @Test
    fun addToCart()
    {
        runBlocking {
            var userRepo = UserRepository()
            ServiceBuilder.token = "Bearer "+userRepo.checkUser("smreety","1234").token
        }
        Espresso.onView(withId(R.id.navigation_home))
            .perform(ViewActions.click())
        Thread.sleep(3000)

        Espresso.onView(withId(R.id.rvProducts))
            .perform(RecyclerViewActions.actionOnItemAtPosition<ProductAdapter.ProductViewHolder>(0,CustomAction.clickItemWithId(R.id.constraint)))

        Thread.sleep(400)

        Espresso.onView(withId(R.id.btnAdd))
            .perform(ViewActions.click())

        Thread.sleep(5000)

        Espresso.onView(ViewMatchers.withText("Product Added to Cart"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

}