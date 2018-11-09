package com.example.cristinazapata.tictactoe

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.runner.AndroidJUnit4
import android.util.Log

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private val totalCells = 9
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.example.cristinazapata.tictactoe", appContext.packageName)
    }

    @Test
    fun testButtonsClick() {
        Log.e("@Test","Performing buttons clicked test")

        onView(withId(R.id.button1)).perform(ViewActions.click())
        onView(withId(R.id.button2)).perform(ViewActions.click())
        onView(withId(R.id.button3)).perform(ViewActions.click())
        onView(withId(R.id.button4)).perform(ViewActions.click())
        onView(withId(R.id.button5)).perform(ViewActions.click())
        onView(withId(R.id.button6)).perform(ViewActions.click())
        onView(withId(R.id.button7)).perform(ViewActions.click())
        onView(withId(R.id.button8)).perform(ViewActions.click())
        onView(withId(R.id.button9)).perform(ViewActions.click())
        onView(withId(R.id.buttonReset)).perform(ViewActions.click())
    }
}
