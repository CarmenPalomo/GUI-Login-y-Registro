package com.example.guiloginyregistro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class Welcome : AppCompatActivity() {

    private lateinit var saludo : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val deslog : Button = findViewById(R.id.desloguear)
        saludo = findViewById(R.id.welcome)

        // Si se conecta debería parecer el mensaje prestablecido en nuestra base de datos
        pruebaConex()

        val correo = intent.getStringExtra("email")

//        val database1 = FirebaseDatabase.getInstance()
//        val ref = database1.reference.child("usuarios")
//        ref.child(correo.toString()).addListenerForSingleValueEvent(object :
//            ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                if (dataSnapshot.exists()) {
//
//                    val nombreUsuario = dataSnapshot.child("nombre").getValue(String::class.java).toString()
//                    saludo.text = "Hola $nombreUsuario"
//                } else {
//                    saludo.text ="Usuario no encontrado"
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                saludo.text ="Error al leer datos de Firebase: ${databaseError.message}"
//            }
//        })

        saludo.text ="Hola"

        deslog.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    // Función para comprobar si conecta con la base de datos
    fun pruebaConex(){
        val database = Firebase.database
        // Escribimos en el path message nuestro mensaje
        val myRef = database.getReference ("message")
        myRef.setValue("Conexión establecida con la BD")
    }
}