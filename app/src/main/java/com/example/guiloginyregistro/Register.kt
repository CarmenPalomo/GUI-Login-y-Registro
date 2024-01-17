package com.example.guiloginyregistro

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Register : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Setup
        setup()
    }

    private fun setup(){

        title = "Autenticación"

        val registrar : ImageButton = findViewById(R.id.Registrar)
        val email : EditText = findViewById(R.id.campoEmail)
        val pass : EditText = findViewById(R.id.campoContraseña)
        val confPass : EditText = findViewById(R.id.campoRepetirContraseña)

        registrar.setOnClickListener{
            if (email.text.isNotEmpty() && pass.text.isNotEmpty() && (pass.text == confPass.text)){

                auth.createUserWithEmailAndPassword(email.text.toString(),
                    pass.text.toString()).addOnCompleteListener {
                        if (it.isSuccessful){
                            Log.d(TAG, "Creado nuevo usuario")
                            val registrado : Intent = Intent (this, MainActivity::class.java)
                        } else {
                            showAlert()
                        }
                }
            }
        }
    }

    private fun showAlert(){
        Log.d(TAG, "Error creando nuevo usuario")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}