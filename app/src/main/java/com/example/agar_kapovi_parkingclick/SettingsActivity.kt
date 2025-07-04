package com.example.agar_kapovi_parkingclick

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class SettingsActivity : AppCompatActivity() {
    private val currentTab = "settings"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val etChangePassword = findViewById<EditText>(R.id.etChangePassword)
        val btnSaveSettings = findViewById<Button>(R.id.btnSaveSettings)

        btnSaveSettings.setOnClickListener {
            val newPassword = etChangePassword.text.toString()
            Toast.makeText(this, "Nova lozinka spremljena: $newPassword", Toast.LENGTH_SHORT).show()
        }
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNav.setOnItemSelectedListener { item ->
            // Highlight selected item
            bottomNav.menu.findItem(item.itemId)?.isChecked = true

            when (item.itemId) {
                R.id.nav_login -> {
                    if (currentTab != "login") {
                        startActivity(Intent(this, LoginActivity::class.java))
                        overridePendingTransition(0, 0)
                    }
                    true
                }
                R.id.nav_home -> {
                    if (currentTab != "home") {
                        startActivity(Intent(this, MainActivity::class.java))
                        overridePendingTransition(0, 0)
                    }
                    true
                }
                R.id.nav_settings -> {
                    if (currentTab != "settings") {
                        startActivity(Intent(this, SettingsActivity::class.java))
                        overridePendingTransition(0, 0)
                    }
                    true
                }
                else -> false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Ensure the correct tab is highlighted when returning to activity
        findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId = R.id.nav_settings
    }
}


