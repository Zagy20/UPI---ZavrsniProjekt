package com.example.agar_kapovi_parkingclick

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.containsString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testEndToEndReservationFlow() {
        // 1. Unesi lokaciju i pokreni pretragu
        onView(withId(R.id.etLocation))
            .perform(typeText("Zagreb"), closeSoftKeyboard())

        onView(withId(R.id.btnSearch))
            .perform(click())

        // 2. Provjeri prikaz slobodnih mjesta
        onView(withId(R.id.tvParkingResult))
            .check(matches(withText(containsString("Dostupno parkirnih mjesta"))))

        // 3. Unesi registraciju i vrijeme
        onView(withId(R.id.etPlate))
            .perform(typeText("ZG1234AB"), closeSoftKeyboard())

        onView(withId(R.id.etTime))
            .perform(typeText("1"), closeSoftKeyboard()) // 1 min (ili 1 sekunda simulirano)

        // 4. Pokreni rezervaciju
        onView(withId(R.id.btnReserve))
            .perform(click())

        // 5. Provjeri da je rezervacija evidentirana
        onView(withId(R.id.tvParkingResult))
            .check(matches(withText(containsString("Rezervirano"))))

        // 6. Sačekaj automatsko oslobađanje (simulirano vrijeme je 10 sekundi)
        Thread.sleep(11000)

        // 7. Provjeri da je mjesto ponovno oslobođeno
        onView(withId(R.id.tvParkingResult))
            .check(matches(withText(containsString("Mjesto oslobođeno"))))
    }
}
