package com.bgabi.happyplaces

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.bgabi.happyplaces.databinding.ActivityRegisterBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.w3c.dom.Text

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_register)

        val btn_to_login = findViewById<Button>(R.id.edt_to_sign_in)
        btn_to_login.setOnClickListener {
            val intent : Intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }
        val btn_register = findViewById<Button>(R.id.btn_register)
        btn_register.setOnClickListener {
            when {
                TextUtils.isEmpty(findViewById<TextView>(R.id.edt_email).text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(findViewById<TextView>(R.id.edt_password).text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val email: String = findViewById<TextView>(R.id.edt_email).text.toString().trim { it <= ' ' }
                    val password: String = findViewById<TextView>(R.id.edt_password).text.toString().trim { it <= ' ' }
                    val confirmPassword: String =
                        findViewById<TextView>(R.id.edt_confirm_password).text.toString().trim { it <= ' ' }

                    if (password == confirmPassword) {
                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(
                                OnCompleteListener<AuthResult> { task ->
                                    if (task.isSuccessful) {
                                        val firebaseUser: FirebaseUser = task.result!!.user!!

                                        Toast.makeText(
                                            this@RegisterActivity,
                                            "Your account was successfully created!",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        val intent =
                                            Intent(this@RegisterActivity, MainActivity::class.java)
                                        intent.flags =
                                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        intent.putExtra("user_id", firebaseUser.uid)
                                        intent.putExtra("email_id", email)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        Toast.makeText(
                                            this@RegisterActivity,
                                            task.exception!!.message.toString(),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            )
                    } else {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Password is not matching",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}