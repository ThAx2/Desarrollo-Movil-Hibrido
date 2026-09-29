package com.example.pasodedatosapp

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.pasodedatosapp.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.slider.Slider
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var fechaCapturada: String = "Ninguna"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinnerRol = findViewById<AutoCompleteTextView>(R.id.spinnerRol)
        val rgContacto = findViewById<RadioGroup>(R.id.rgContacto)
        val switchNotificaciones = findViewById<MaterialSwitch>(R.id.switchNotificaciones)
        val sliderNivel = findViewById<Slider>(R.id.sliderNivel)
        val tvNivelExpertise = findViewById<TextView>(R.id.tvNivelExpertise)
        val btnSeleccionarFecha = findViewById<MaterialButton>(R.id.btnSeleccionarFecha)
        val tvFecha = findViewById<TextView>(R.id.tvFecha)
        val cbTerminos = findViewById<MaterialCheckBox>(R.id.cbTerminos)
        val btnProcesar = findViewById<MaterialButton>(R.id.btnProcesar)
        val cardResultados = findViewById<MaterialCardView>(R.id.cardResultados)
        val tvResultados = findViewById<TextView>(R.id.tvResultados)

        val roles = arrayOf("Desarrollador Junior", "Desarrollador Mid", "Arquitecto de Software", "Pentester")
        val adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, roles)
        spinnerRol.setAdapter(adaptador)

        sliderNivel.addOnChangeListener { _, value, _ ->
            tvNivelExpertise.text = "Nivel de experiencia: ${value.toInt()}%"
        }

        btnSeleccionarFecha.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Selecciona tu fecha")
                .build()

            datePicker.addOnPositiveButtonClickListener { selection ->
                val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                calendar.timeInMillis = selection
                val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                fechaCapturada = format.format(calendar.time)
                tvFecha.text = "Fecha: $fechaCapturada"
            }

            datePicker.show(supportFragmentManager, "MATERIAL_DATE_PICKER")
        }

        btnProcesar.setOnClickListener {
            if (!cbTerminos.isChecked) {
                Toast.makeText(this, "Debes aceptar los términos y condiciones", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val rolSeleccionado = spinnerRol.text.toString().ifEmpty { "No especificado" }
            val idRadioSeleccionado = rgContacto.checkedRadioButtonId
            val metodoContacto = if (idRadioSeleccionado == R.id.rbEmail) "Email" else "Teléfono"
            val notificacionesActivas = if (switchNotificaciones.isChecked) "Sí" else "No"
            val nivel = sliderNivel.value.toInt()

            val resumen = """
                • Rol: $rolSeleccionado
                • Contacto: $metodoContacto
                • Notificaciones Push: $notificacionesActivas
                • Experiencia: $nivel%
                • Fecha Nacimiento: $fechaCapturada
            """.trimIndent()

            tvResultados.text = resumen
            cardResultados.visibility = View.VISIBLE
        }
    }
}