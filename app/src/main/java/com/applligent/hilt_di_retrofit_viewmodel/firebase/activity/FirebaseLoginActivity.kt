package com.applligent.hilt_di_retrofit_viewmodel.firebase.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.applligent.hilt_di_retrofit_viewmodel.databinding.ActivityFirebaseLoginBinding
import com.applligent.hilt_di_retrofit_viewmodel.MainActivity
import com.google.firebase.auth.FirebaseAuth

class FirebaseLoginActivity : AppCompatActivity() {
    private val binding : ActivityFirebaseLoginBinding by lazy { ActivityFirebaseLoginBinding.inflate(layoutInflater) }

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        setCustomToolBar()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.tvRedirectSignUp.setOnClickListener {
            val i = Intent(this,FirebaseSignUpActivity::class.java)
            startActivity(i)
        }

        binding.btnLogin.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val email = binding.etEmailAddress.text.toString()
        val pass = binding.etPassword.text.toString()

        if (email.isBlank()) {
            binding.etEmailAddress.requestFocus()
            binding.etEmailAddress.error = "Email can't be blank"
            return
        }else if (pass.isBlank()){
            binding.etPassword.requestFocus()
            binding.etPassword.error = "Password can't be blank"
            return
        }
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, "Successfully LoggedIn", Toast.LENGTH_SHORT).show()
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
            } else
                Toast.makeText(this, "Log In failed ", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setCustomToolBar() {
        binding.customToolbar.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.customToolbar.ivNotification.setOnClickListener {
            //Toast.makeText(this, "Clicked Notification", Toast.LENGTH_SHORT).show()
        }
        binding.customToolbar.ivSetting.setOnClickListener {
            //Toast.makeText(this, "Clicked Setting", Toast.LENGTH_SHORT).show()
        }
        binding.customToolbar.tvTitle.text = "Login"
    }
}