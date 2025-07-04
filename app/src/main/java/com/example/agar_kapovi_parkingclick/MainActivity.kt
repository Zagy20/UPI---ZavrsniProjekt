package com.example.agar_kapovi_parkingclick

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private val currentTab = "home"
    private var availableSpots = 0 // Broj slobodnih mjesta

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val etLocation = findViewById<EditText>(R.id.etLocation)
        val btnSearch = findViewById<Button>(R.id.btnSearch)
        val tvParkingResult = findViewById<TextView>(R.id.tvParkingResult)
        val etPlate = findViewById<EditText>(R.id.etPlate)
        val etTime = findViewById<EditText>(R.id.etTime)
        val btnReserve = findViewById<Button>(R.id.btnReserve)

        // Set home as selected
        bottomNav.selectedItemId = R.id.nav_home

        btnSearch.setOnClickListener {
            val location = etLocation.text.toString()

            if (location.isEmpty()) {
                tvParkingResult.text = "Molimo unesite grad."
                return@setOnClickListener
            }

            // Nasumično generiraj broj slobodnih mjesta (npr. 5–10)
            availableSpots = Random.nextInt(5, 11)
            tvParkingResult.text = "Grad: $location\nDostupno parkirnih mjesta: $availableSpots"
        }

        btnReserve.setOnClickListener {
            val plate = etPlate.text.toString()
            val time = etTime.text.toString()

            if (plate.isEmpty() || time.isEmpty()) {
                Toast.makeText(this, "Unesite registraciju i vrijeme.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (availableSpots <= 0) {
                Toast.makeText(this, "Nema slobodnih mjesta!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Rezerviraj mjesto
            availableSpots--
            tvParkingResult.text = "Rezervirano! Preostalo: $availableSpots mjesta."

            Toast.makeText(this, "Rezervirali ste mjesto na $time min.", Toast.LENGTH_SHORT).show()

            // Simuliraj oslobađanje mjesta (nakon "vrijeme" sekundi)
            val delayMillis = 10000L // možeš staviti: time.toLong() * 1000L
            Handler(Looper.getMainLooper()).postDelayed({
                availableSpots++
                tvParkingResult.text = "Mjesto oslobođeno! Dostupno: $availableSpots mjesta."
            }, delayMillis)
        }

        // Bottom navigation
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_login -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.nav_home -> true
                R.id.nav_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    overridePendingTransition(0, 0)
                    true
                }
                else -> false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId = R.id.nav_home
    }
}
