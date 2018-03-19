package com.home.kotlinkoan


import android.support.test.espresso.ViewInteraction
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.test.suitebuilder.annotation.LargeTest
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withClassName
import android.support.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.`is`

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    public var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        val frameLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.list),
                                childAtPosition(
                                        IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java),
                                        0)),
                        2),
                        isDisplayed()))
        frameLayout.check(matches(isDisplayed()))

        val frameLayout2 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.list),
                                childAtPosition(
                                        IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java),
                                        0)),
                        2),
                        isDisplayed()))
        frameLayout2.check(matches(isDisplayed()))

        val recyclerView = onView(
                allOf(withId(R.id.list),
                        childAtPosition(
                                withClassName(`is`("android.widget.FrameLayout")),
                                0)))
        recyclerView.perform(actionOnItemAtPosition<CustomAdapter.ViewHolder>(3, click()))

        val imageView = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(android.R.id.content),
                                0),
                        0),
                        isDisplayed()))
        imageView.check(matches(isDisplayed()))

    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return (parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position))
            }
        }
    }
}
