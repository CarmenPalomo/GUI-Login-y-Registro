package com.example.guiloginyregistro


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner

import androidx.appcompat.app.AlertDialog

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class Register : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database : FirebaseDatabase
    private lateinit var usuariosRef : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth
        database = FirebaseDatabase.getInstance()
        usuariosRef = database.getReference("usuarios")

        val registrar: ImageButton = findViewById(R.id.Registrar)
        val cancel: ImageButton = findViewById(R.id.Cancelar)
        val email: EditText = findViewById(R.id.campoEmail)
        val pass: EditText = findViewById(R.id.campoContraseña)
        val confPass: EditText = findViewById(R.id.campoRepetirContraseña)
        val nom: EditText = findViewById(R.id.campoNombre)
        val sex: RadioGroup = findViewById(R.id.Sex_radio)
        val nac: Spinner = findViewById(R.id.EscogeNacion)
        var sexo = ""
        var nacion = ""

        // Creamos un adaptador para el spinner para recoger lo seleccionado y darle un estilo
        val opcionesNac: Array<String> = resources.getStringArray(R.array.Nacionalidad)
        val adapterNacionalidad: ArrayAdapter<String> =
            ArrayAdapter(this, R.layout.spinner1, opcionesNac)
        adapterNacionalidad.setDropDownViewResource(R.layout.spinner_dropdown_item)

        nac.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                nacion = parent?.getItemAtPosition(position) as String
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }



        //  Password de 6 caracteres !!!!!!!!!!!!!

        registrar.setOnClickListener {
            if (email.text.isNotEmpty() && pass.text.isNotEmpty()) {
                // chequeamos si tenemos selección de sexo
                val sexSel : RadioButton? = findViewById(sex.checkedRadioButtonId)
                if (sexSel != null){
                    sexo = sexSel.text.toString()
                } else {

                }
                Log.d("Prueba", "Creado nuevo usuario")
                if ((pass.text.toString() == confPass.text.toString())){
                    Log.d("Prueba", "Comprobando contraseña")
                    auth.createUserWithEmailAndPassword(
                    email.text.toString(),
                    pass.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        //Log.d(TAG, "Creado nuevo usuario")
                        //El email será nuestra id
                        // Añadimos datos al usuario creado
                        datosUsuario(email.text.toString(),nom.text.toString(),sexo,nacion)
                        val registrado = Intent(this, MainActivity::class.java)
                        startActivity(registrado)
                    } else {
                        showAlert("Error creando el usuario")
                    }
                }
                } else {
                    showAlert("Error, contraseñas distintas")
                    }

            }
        }

        cancel.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showAlert(mensaje : String){
        //Log.d(TAG, "Error creando nuevo usuario")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(mensaje)
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun datosUsuario (correo : String, nombre : String,sexo : String,nacionalidad : String){
        // Creamos una variable para obtener la id del usuario que se loguea
        val usuarioActual : FirebaseUser? = auth.currentUser
        if (usuarioActual !=null) {
            // insertamos los datos del usuario actual en nuestra Base de Datos
            val user = Usuario (correo, nombre,sexo,nacionalidad)
            usuariosRef.child(usuarioActual.uid).setValue(user)
        } else {

        }

    }


}