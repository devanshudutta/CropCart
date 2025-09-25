package com.example.cropcart

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val emailField: EditText = findViewById(R.id.email)
        val passwordField: EditText = findViewById(R.id.etPassword)
        val usernameField: EditText = findViewById(R.id.username)
        val registerBtn: Button = findViewById(R.id.btnRegister)

        registerBtn.setOnClickListener {

            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()
            val username = usernameField.text.toString().trim()

            if (email.isEmpty() || password.isEmpty() || username.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = mAuth.currentUser?.uid ?: return@addOnCompleteListener
                        val user = hashMapOf(
                            "username" to username,
                            "email" to email
                        )


                        Toast.makeText(this, "Account created!", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()

                        db.collection("users").document(userId).set(user)
                            .addOnSuccessListener {
                                Log.d("RegisterActivity", "Username saved successfully")
                            }
                            .addOnFailureListener {
                                Log.e("RegisterActivity", "Error saving username", it)
                                Toast.makeText(this, "Failed to save username", Toast.LENGTH_SHORT).show()
                            }

                    } else {
                        Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }

        }


        val loginpage = findViewById<TextView>(R.id.loginpage)

        loginpage.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}