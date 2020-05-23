package com.example.rozrywka


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val auth= FirebaseAuth.getInstance()
        login.setOnClickListener {
            if (email.text.toString().isEmpty()){
                email.error="Proszę wypełnić pole"
                return@setOnClickListener
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches())
            {
                email.error="Wpisz prawidłowy e-mail"
                return@setOnClickListener
            }
            if (password.text.toString().length<6)
            {
                password.error="Za krótkie hasło"
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        updateUI(user)
                    } else {

                        updateUI(null)
                    }
                }

        }
        signup.setOnClickListener{
            startActivity(Intent(this,SignUpActivity::class.java))
        }
    }
    private fun updateUI(currentUser: FirebaseUser?) {

        if (currentUser != null) {

            val intent= Intent(this, ListActivity::class.java)
            intent.putExtra("user",currentUser.uid.toString())
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(
                baseContext, "Login failed.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
