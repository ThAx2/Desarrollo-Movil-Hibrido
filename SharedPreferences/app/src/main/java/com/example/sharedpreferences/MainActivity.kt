package com.example.sharedpreferences // Asegúrate de ajustar tu paquete

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.materialswitch.MaterialSwitch

class MainActivity : AppCompatActivity() {

    // Nombre del archivo interno XML donde se guardarán los pares clave-valor
    private val PREFS_NAME = "MisPreferenciasApp"
    private val KEY_USUARIO = "key_usuario"
    private val KEY_RECORDAR = "key_recordar_sesion"

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar SharedPreferences en MODO PRIVADO (solo legible por esta app)
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        val etUsuario = findViewById<EditText>(R.id.etUsuario)
        val switchRecordar = findViewById<MaterialSwitch>(R.id.switchRecordar)
        val btnGuardar = findViewById<MaterialButton>(R.id.btnGuardar)
        val btnCargar = findViewById<MaterialButton>(R.id.btnCargar)
        val btnEliminar = findViewById<MaterialButton>(R.id.btnEliminar)
        val tvDatosGuardados = findViewById<TextView>(R.id.tvDatosGuardados)

        // Cargar automáticamente las preferencias guardadas al iniciar la aplicación
        recuperarDatos(etUsuario, switchRecordar, tvDatosGuardados)

        // 1. GUARDAR O MODIFICAR DATOS
        btnGuardar.setOnClickListener {
            val usuario = etUsuario.text.toString().trim()
            val recordar = switchRecordar.isChecked

            if (usuario.isNotEmpty()) {
                val editor = sharedPreferences.edit()
                editor.putString(KEY_USUARIO, usuario)
                editor.putBoolean(KEY_RECORDAR, recordar)
                editor.apply() // .apply() guarda de forma asíncrona en segundo plano

                Toast.makeText(this, "Preferencias guardadas exitosamente", Toast.LENGTH_SHORT).show()
                recuperarDatos(etUsuario, switchRecordar, tvDatosGuardados)
            } else {
                Toast.makeText(this, "Por favor escribe un nombre de usuario", Toast.LENGTH_SHORT).show()
            }
        }

        // 2. RECUPERAR DATOS MANUALMENTE
        btnCargar.setOnClickListener {
            recuperarDatos(etUsuario, switchRecordar, tvDatosGuardados)
            Toast.makeText(this, "Datos actualizados desde memoria", Toast.LENGTH_SHORT).show()
        }

        // 3. ELIMINAR REGISTROS
        btnEliminar.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.clear() // Limpia todas las claves guardadas en este archivo
            editor.apply()

            etUsuario.text?.clear()
            switchRecordar.isChecked = false
            tvDatosGuardados.text = "Estado: No hay datos guardados"

            Toast.makeText(this, "SharedPreferences limpiadas", Toast.LENGTH_SHORT).show()
        }
    }

    private fun recuperarDatos(
        etUsuario: EditText,
        switchRecordar: MaterialSwitch,
        tvDatosGuardados: TextView
    ) {
        // Leer valores con valor por defecto si la clave no existe
        val usuarioGuardado = sharedPreferences.getString(KEY_USUARIO, null)
        val recordarGuardado = sharedPreferences.getBoolean(KEY_RECORDAR, false)

        if (usuarioGuardado != null) {
            etUsuario.setText(usuarioGuardado)
            switchRecordar.isChecked = recordarGuardado

            tvDatosGuardados.text = """
                • Usuario: $usuarioGuardado
                • Sesión Activa: ${if (recordarGuardado) "Sí" else "No"}
            """.trimIndent()
        } else {
            tvDatosGuardados.text = "Estado: No existen preferencias guardadas"
        }
    }
}