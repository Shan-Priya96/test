package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.myapplication.R.id.signInText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var database :FirebaseDatabase
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth =FirebaseAuth.getInstance()
        database=FirebaseDatabase.getInstance()

        val signUpName : EditText =findViewById(R.id.signUpName)
        val signUpEmail : EditText =findViewById(R.id.signUpEmail)
        val signUpPhone : EditText =findViewById(R.id.signUpPhone)
        val signUpPassword : EditText =findViewById(R.id.signUpPassword)
        val signUpCPassword : EditText =findViewById(R.id.signUpConfirmPassword)
        val signUpPasswordLayout : TextInputLayout=findViewById(R.id.signUPasswordLayout)
        val signCUpPasswordLayout : TextInputLayout=findViewById(R.id.signUPConfirmPasswordLayout)
        val signUpBtn: Button = findViewById(R.id.signupBtn)
        val signUpProgressBar : ProgressBar =findViewById(R.id.signUpProgressBar)

        val signInText: TextView = findViewById(R.id.signInText)

        signInText.setOnClickListener{
            val intent = Intent(this, SignInActivity:: class.java)
            startActivity(intent)
        }
         signUpBtn.setOnClickListener{
             val name =signUpName.text.toString()
             val email=signUpEmail.text.toString()
             val phone=signUpPhone.text.toString()
             val password=signUpPassword.text.toString()
             val cPassword =signUpCPassword.text.toString()

             signUpProgressBar.visibility = View.VISIBLE
             signUpPasswordLayout.isPasswordVisibilityToggleEnabled =true
             signCUpPasswordLayout.isPasswordVisibilityToggleEnabled =true

             if(name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()|| cPassword.isEmpty())
             {
                if (name.isEmpty())
                {
                    signUpName.error ="Enter your name"
                }
                 if (email.isEmpty())
                 {
                     signUpEmail.error ="Enter your email address"
                 }
                 if (phone.isEmpty())
                 {
                     signUpPhone.error ="Enter your phone"
                 }
                 if (password.isEmpty())
                 {
                     signCUpPasswordLayout.isPasswordVisibilityToggleEnabled =false
                     signUpPassword.error ="Enter your password"

                 }
                 if (cPassword.isEmpty())
                 {
                     signUpPasswordLayout.isPasswordVisibilityToggleEnabled=false
                     signUpCPassword.error ="Re enter your password"
                 }
                 Toast.makeText(this,"Enter valid details", Toast.LENGTH_SHORT).show()
                 signUpProgressBar.visibility=View.GONE
             }
             else if(!email.matches(emailPattern.toRegex()))
             {  signUpProgressBar.visibility=View.GONE
                 signUpEmail.error ="Enter valid email address"
                 Toast.makeText(this,"Enter valid email address", Toast.LENGTH_SHORT).show()
             }
             else if(phone.length!=10)
             {      signUpProgressBar.visibility=View.GONE
                 signUpPhone.error ="Enter valid email address"
                 Toast.makeText(this,"Enter valid phone number", Toast.LENGTH_SHORT).show()
             }
             else if(password.length <6)
             {   signUpPasswordLayout.isPasswordVisibilityToggleEnabled= false
                 signUpProgressBar.visibility=View.GONE
                 signUpPassword.error ="Enter password more than 6 characters"
                 Toast.makeText(this,"Enter password more than 6 characters", Toast.LENGTH_SHORT).show()
             }
             else if(password!= cPassword){
                 signCUpPasswordLayout.isPasswordVisibilityToggleEnabled= false
                 signUpProgressBar.visibility=View.GONE
                 signUpCPassword.error ="Password not matched, try again"
                 Toast.makeText(this,"Password not matched, try again", Toast.LENGTH_SHORT).show()
             }
             else{
                 auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
                     if(it.isSuccessful){
                         val databaseRef =database.reference.child(auth.currentUser!!.uid)
                         val users :Users =Users(name,email,phone,auth.currentUser!!.uid)
                         databaseRef.setValue(users).addOnCompleteListener {
                             if(it.isSuccessful){
                                 val intent =Intent(this, SignInActivity::class.java)
                                 startActivity(intent)
                             }
                             else{
                                 Toast.makeText(this,"Something went wrong try again",Toast.LENGTH_SHORT).show()
                             }
                         }
                     }
                     else{
                         Toast.makeText(this,"Something went wrong try again",Toast.LENGTH_SHORT).show()
                     }
                 }
             }
         }
    }
}