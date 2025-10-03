package com.example.cropcart

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth

class ProfilePageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile_page)

        val back = findViewById<ImageView>(R.id.ivBack)

        val logoutButton = findViewById<com.google.android.material.button.MaterialButton>(R.id.logoutButton)

        val profileIcon = findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.profileIcon)

        setUpPFP(profileIcon)



        logoutButton.setOnClickListener {

            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()

        }


        back.setOnClickListener {

            finish()

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setUpPFP(profileIcon : de.hdodenhof.circleimageview.CircleImageView)
    {
        val initials = findViewById<TextView>(R.id.profileInitials)

        val userName = "Master User"
        val userProfileUrl: String? = null // replace with real URL if available

        if (!userProfileUrl.isNullOrEmpty()) {

            initials.visibility = View.GONE
            profileIcon.visibility = View.VISIBLE

            Glide.with(this)
                .load("https://example.com/user/profile.jpg")  // user’s pfp URL
                .placeholder(R.drawable.person)       // fallback
                .into(profileIcon)

        }else {
            // Show initials
            profileIcon.visibility = View.GONE
            initials?.visibility = View.VISIBLE

            fun getInitials(name: String): String {
                val parts = name.trim().split(" ")
                return if (parts.size == 1) {
                    parts[0].first().uppercaseChar().toString()
                } else {
                    "${parts[0].first().uppercaseChar()}${parts[1].first().uppercaseChar()}"
                }
            }

            initials?.text = getInitials(userName)
        }

    }


}