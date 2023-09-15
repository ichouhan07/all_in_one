package com.applligent.hilt_di_retrofit_viewmodel.firebase.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.applligent.hilt_di_retrofit_viewmodel.databinding.ActivityFirebaseSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FirebaseSignUpActivity : AppCompatActivity() {
    private val binding : ActivityFirebaseSignUpBinding by lazy { ActivityFirebaseSignUpBinding.inflate(layoutInflater) }

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        setCustomToolBar()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.tvRedirectLogin.setOnClickListener {
            val i = Intent(this,FirebaseLoginActivity::class.java)
            startActivity(i)
        }

        binding.btnSSigned.setOnClickListener {
            signUpUser()
        }
    }

    private fun signUpUser() {
        val email = binding.etSEmailAddress.text.toString()
        val pass = binding.etSPassword.text.toString()
        val confirmPass = binding.etSConfPassword.text.toString()

        if (email.isBlank()) {
            binding.etSEmailAddress.requestFocus()
            binding.etSEmailAddress.error = "Email can't be blank"
            return
        }else if (pass.isBlank()){
            binding.etSPassword.requestFocus()
            binding.etSPassword.error = "Password can't be blank"
            return
        }
        else if (pass.length < 6){
            binding.etSPassword.requestFocus()
            binding.etSPassword.error = "Password must be 6 digit"
            return
        }
        else if (confirmPass.isBlank()){
            binding.etSConfPassword.requestFocus()
            binding.etSConfPassword.error = "Confirm password can't be blank"
            return
        }
        else if (pass != confirmPass){
            binding.etSConfPassword.requestFocus()
            binding.etSConfPassword.error = "Password and Confirm Password do not match"
            return
        }
        firebaseAuth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user: FirebaseUser? = firebaseAuth.currentUser
                    Toast.makeText(this, "Successfully Singed Up", Toast.LENGTH_SHORT).show()
                    val i = Intent(this,FirebaseLoginActivity::class.java)
                    startActivity(i)
                } else {
                    val errorMessage = task.exception?.message
                    Toast.makeText(this, "Singed Up Failed!", Toast.LENGTH_SHORT).show()
                }
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
        binding.customToolbar.tvTitle.text = "SignUp"
    }
}