package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                MyTextBoxExample()
            }
        }
    }
}

@Composable
fun MyTextBoxExample() {

    var textState by remember { mutableStateOf("") }
    var greetingText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {

        TextField(
            value = textState,
            onValueChange = { newValue ->

                if (newValue.length <= 20) {
                    textState = newValue
                }

            },
            label = {
                Text("Escribe tu nombre")
            },
            placeholder = {
                Text("Ej: Juan Pérez")
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Button(
            onClick = {

                greetingText = "Hola! ${textState}!!"

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar")
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = greetingText
        )
    }
}