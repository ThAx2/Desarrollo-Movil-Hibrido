package com.example.pasodedatosapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val tvReceivedData = findViewById<TextView>(R.id.tvReceivedData)


        val mensajeRecibido = intent.getStringExtra("MENSAJE_EXTRA")


        if (mensajeRecibido != null) {
            tvReceivedData.text = "Recibiste: $mensajeRecibido"
        }
    }
}