package com.example.pmdm2


import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

//variable publica donde se guardara el dato del Intent
const val EXTRA_MESSAGE = "com.joel.intentaplication.MESSAGE"

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val signUpButton: Button = findViewById(R.id.signUpButton)
        val loginButton: Button = findViewById(R.id.loginButton)

        //Inicializo la instancia con la Autenticación
        auth = Firebase.auth
        //Ponemos las escuchas de los botones
        signUpButton.setOnClickListener {
            sign()
        }
        loginButton.setOnClickListener {
            login()
        }
    }

    /**
     * Metodo para registrar usuarios en Firebase
     */
    private fun sign(){
        val emailText: EditText = findViewById(R.id.emailText)
        val passwordText: EditText = findViewById(R.id.passwordText)
        //Metodo que crea un usuario a traves del email y la contraseña
        auth.createUserWithEmailAndPassword(emailText.text.toString(), passwordText.text.toString())
            //Añade una escucha que detecta si los email y las contraseñas estan completas y cumplen los requisitos para registrarlos
            .addOnCompleteListener(this) { task ->
                //Si la tarea enviada ha sido ejecutada correctamente
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    //Devuelve el usuario actual
                    val user = auth.currentUser
                    Log.d(TAG, user.toString())
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    /**
     * Metodo para conectar usuarios en Firebase
     */
    private fun login(){
        val emailText: EditText = findViewById(R.id.emailText)
        val passwordText: EditText = findViewById(R.id.passwordText)
        //Funciona igual que el de crear usuario pero esta vez comprueba si el email con su contraseña estan registrados
        auth.signInWithEmailAndPassword(emailText.text.toString(), passwordText.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    Toast.makeText(baseContext, "Login succesful", Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
                    Log.d(TAG, user.toString())
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        val intent = Intent(this, MapsActivity::class.java).apply {}
        startActivity(intent)
    }
}