package com.example.tasktwo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find Views
        val loginLayout = findViewById<RelativeLayout>(R.id.loginLayout)
        val profileCard = findViewById<RelativeLayout>(R.id.profileCard)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val ivLogo = findViewById<ImageView>(R.id.ivLogo)
        val tvTitle = findViewById<TextView>(R.id.tvTitle)

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        val tvForgot = findViewById<TextView>(R.id.tvForgotPassword)

        // Forgot Password Logic
        tvForgot.setOnClickListener {
            Toast.makeText(this, "Password reset link sent to your email", Toast.LENGTH_SHORT).show()
        }

        // Login Logic
        btnLogin.setOnClickListener {
            val user = etUsername.text.toString()
            val pass = etPassword.text.toString()

            if (user == "admin" && pass == "1234") {
                // Show Progress Bar
                progressBar.visibility = View.VISIBLE
                loginLayout.visibility = View.GONE
                ivLogo.visibility = View.GONE
                tvTitle.visibility = View.GONE

                // Simulate loading for 1.5 seconds
                Handler(Looper.getMainLooper()).postDelayed({
                    progressBar.visibility = View.GONE
                    profileCard.visibility = View.VISIBLE
                }, 1500)

            } else {
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
            }
        }

        // Logout Logic
        btnLogout.setOnClickListener {
            // Reset UI
            profileCard.visibility = View.GONE
            loginLayout.visibility = View.VISIBLE
            ivLogo.visibility = View.VISIBLE
            tvTitle.visibility = View.VISIBLE

            // Clear fields
            etUsername.text.clear()
            etPassword.text.clear()
        }
    }
}