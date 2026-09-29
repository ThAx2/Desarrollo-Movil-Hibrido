package com.example.pasodedatosapp
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etInputData = findViewById<EditText>(R.id.etInputData)
        val btnSend = findViewById<Button>(R.id.btnSend)

        btnSend.setOnClickListener {
            val mensaje = etInputData.text.toString()

            if (mensaje.isNotEmpty()) {

                val intent = Intent(this, DetailActivity::class.java)

                intent.putExtra("MENSAJE_EXTRA", mensaje)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Por favor, ingresa un texto", Toast.LENGTH_SHORT).show()
            }
        }
    }
}