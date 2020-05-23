package com.example.rozrywka

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PatternMatcher
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {

    val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        signup.setOnClickListener {
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
            auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString()).addOnCompleteListener{task->
                if(task.isComplete){
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }
                else{
                    Toast.makeText(this, "Błąd", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
