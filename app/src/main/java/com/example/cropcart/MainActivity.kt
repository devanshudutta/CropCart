package com.example.cropcart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import de.hdodenhof.circleimageview.CircleImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.topAppBar)
        val productRecyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.productsRecyclerView)
        val organicRecyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.OrganicProductsRecyclerView)

        organicRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        productRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)



        val sampleItems = listOf(
            featured_product(null,"$2.99 / kg", "Organic, freshly picked", "description here"),
            featured_product(null, "$3.50 / kg", "Crisp and juicy", "description here"),
            featured_product(null, "$1.80 / kg", "Sweet & fresh", "description here"),
        )

        val adapter = FeaturedproductAdapter(sampleItems) { item ->
            // Handle item click here
            Toast.makeText(this, "Clicked on ${item.name}", Toast.LENGTH_SHORT).show()
        }

        productRecyclerView.adapter = adapter

        organicRecyclerView.adapter = adapter

        setSupportActionBar(toolbar)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_profile -> {
//                startActivity(Intent(this, ProfilePageActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val item = menu.findItem(R.id.menu_profile)
        val actionView = item.actionView
        val profileIcon = actionView?.findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.profileIcon)

        val initials = actionView?.findViewById<TextView>(R.id.profileInitials)

        // Example: load image from URL using Glide

        Log.d("id", "${item.itemId}")
        Log.d("id", "${actionView?.id}")


    profileIcon?.let{


        val userName = "Master User"
        val userProfileUrl: String? = null // replace with real URL if available

        if (!userProfileUrl.isNullOrEmpty()) {
            // Show profile picture
            initials?.visibility = View.GONE
            profileIcon.visibility = View.VISIBLE


            Glide.with(this)
            .load("https://example.com/user/profile.jpg")  // user’s pfp URL
            .placeholder(R.drawable.person)       // fallback
            .into(profileIcon)

        } else {
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




//        profileIcon?.setOnClickListener {
//
////            startActivity(Intent(this, ProfilePageActivity::class.java))
//            Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
//
//        }

        actionView?.setOnClickListener {
             startActivity(Intent(this, ProfilePageActivity::class.java))
        }


        return super.onPrepareOptionsMenu(menu)
    }



}