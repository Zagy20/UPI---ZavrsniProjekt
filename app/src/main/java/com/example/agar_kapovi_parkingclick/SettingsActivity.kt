package com.example.agar_kapovi_parkingclick

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

fun setLocale(context: Context, language: String): Context {
    val locale = Locale(language)
    Locale.setDefault(locale)
    val config = Configuration(context.resources.configuration)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    } else {
        config.locale = locale
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
        return context
    }
}

class SettingsActivity : AppCompatActivity() {

    private lateinit var switchDarkTheme: Switch
    private lateinit var switchNotifications: Switch
    private lateinit var btnShareApp: Button
    private lateinit var btnAbout: Button
    private lateinit var sharedPreferences: SharedPreferences


    private val switchListener = { _: android.widget.CompoundButton, isChecked: Boolean ->
        sharedPreferences.edit().putBoolean("dark_mode", isChecked).apply()
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        val intent = intent
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

    override fun attachBaseContext(newBase: Context) {
        val prefs = newBase.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        val lang = prefs.getString("language", Locale.getDefault().language) ?: "hr"
        super.attachBaseContext(setLocale(newBase, lang))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Postavi temu prije super.onCreate
        val isDarkMode = getSharedPreferences("AppSettings", MODE_PRIVATE)
            .getBoolean("dark_mode", false)
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        switchDarkTheme = findViewById(R.id.switchDarkTheme)
        switchNotifications = findViewById(R.id.switchNotifications)
        btnShareApp = findViewById(R.id.btnShareApp)
        btnAbout = findViewById(R.id.btnAbout)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE)

        // 2. Prvo ukloni listener, postavi stanje, pa ponovno postavi listener
        switchDarkTheme.setOnCheckedChangeListener(null)
        switchDarkTheme.isChecked = isDarkMode
        switchDarkTheme.setOnCheckedChangeListener(switchListener)

        switchNotifications.isChecked = sharedPreferences.getBoolean("notifications", true)
        switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            val message = if (isChecked) "Notifikacije omogućene" else "Notifikacije isključene"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            sharedPreferences.edit().putBoolean("notifications", isChecked).apply()
        }

        btnShareApp.setOnClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Preuzmi ParkingClick aplikaciju i olakšaj si život! 🚗")
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent, "Podijeli aplikaciju preko"))
        }

        btnAbout.setOnClickListener {
            Toast.makeText(this, "ParkingClick verzija 1.0\nDeveloped by Matija Žagar 💙", Toast.LENGTH_LONG).show()
        }

        // Bottom navigation listener
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_login -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.nav_settings -> true
                else -> false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId = R.id.nav_settings
    }
}
