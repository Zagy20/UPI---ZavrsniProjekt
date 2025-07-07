package com.example.agar_kapovi_parkingclick

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        dbHelper = DatabaseHelper(this)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.tvRegister)

        // PRIJAVA
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Unesite email i lozinku", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val isValid = dbHelper.checkUserCredentials(email, password)
            if (isValid) {
                Toast.makeText(this, "Prijava uspješna!", Toast.LENGTH_SHORT).show()
                Log.d("LoginActivity", "Korisnik $email uspješno prijavljen.")


                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Pogrešan email ili lozinka!", Toast.LENGTH_SHORT).show()
                Log.d("LoginActivity", "Neuspješna prijava za: $email")
            }
        }
        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}