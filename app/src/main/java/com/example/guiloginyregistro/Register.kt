package com.example.guiloginyregistro

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class Register : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth
        val registrar: ImageButton = findViewById(R.id.Registrar)
        val cancel: ImageButton = findViewById(R.id.Cancelar)
        val email: EditText = findViewById(R.id.campoEmail)
        val pass: EditText = findViewById(R.id.campoContraseña)
        val confPass: EditText = findViewById(R.id.campoRepetirContraseña)

//        val opcionesNac: Array<String> = resources.getStringArray(R.array.Nacionalidad)
//        val adapterNacionalidad: ArrayAdapter<String> =
//            ArrayAdapter(this, R.layout.spinner1, opcionesNac)
//        adapterNacionalidad.setDropDownViewResource(R.layout.spinner2)

        //  Password de 6 caracteres !!!!!!!!!!!!!

        registrar.setOnClickListener {
            if (email.text.isNotEmpty() && pass.text.isNotEmpty()) {
                Log.d("Prueba", "Creado nuevo usuario")
                if ((pass.text.toString() == confPass.text.toString())){
                    Log.d("Prueba", "Comprobando contraseña")
                    auth.createUserWithEmailAndPassword(
                    email.text.toString(),
                    pass.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        //Log.d(TAG, "Creado nuevo usuario")
                        val registrado = Intent(this, MainActivity::class.java)
                        startActivity(registrado)
                    } else {
                        showAlert()
                    }
                }
                }
            }
        }

        cancel.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showAlert(){
        //Log.d(TAG, "Error creando nuevo usuario")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}