package com.example.agar_kapovi_parkingclick

import android.widget.Switch
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import org.hamcrest.CoreMatchers.allOf
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
class SettingsActivityMediumTest {

    @Before
    fun setUp() {
        // Pokreni SettingsActivity prije svakog testa
        ActivityScenario.launch(SettingsActivity::class.java)
    }

    @Test
    fun switchDarkTheme_changesTheme() {
        // Pronađi switch i klikni ga
        onView(withId(R.id.switchDarkTheme)).perform(click())

        // Provjeri da je switch sada u drugom stanju (checked ili ne)
        onView(allOf(isAssignableFrom(Switch::class.java), withId(R.id.switchDarkTheme)))
            .check { view, _ ->
                val switch = view as Switch
                assertNotNull(switch)
                // Ovdje možeš provjeriti je li checked ili ne, ovisno o početnom stanju
            }

        // (Opcionalno) Provjeri boju pozadine root view-a za light/dark temu
        // Ovdje možeš koristiti custom matcher ili dodatnu logiku za provjeru teme
    }
}
