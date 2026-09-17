package com.example.calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.calculadora.ui.theme.CalculadoraTheme
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.DarkGray
                ) {
                    Scaffold(
                        containerColor = Color.Transparent,
                        modifier = Modifier.fillMaxSize()
                    ) { innerPadding ->
                        // Llamamos únicamente a Greeting, que ya contiene la caja de texto y los botones ordenados
                        Greeting(
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    var texto by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // Caja de texto principal (Pantalla de la calculadora)
        OutlinedTextField(
            value = texto,
            onValueChange = { texto = it },
            label = { Text("Resultado") },
            textStyle = TextStyle(fontSize = 28.sp, color = Color.White),
            modifier = Modifier
                .fillMaxWidth(), // Se ajusta limpiamente al ancho de la pantalla
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.Black,
                unfocusedContainerColor = Color.Black,
                focusedBorderColor = Color.Cyan,
                unfocusedBorderColor = Color.Gray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedLabelColor = Color.Cyan,
                unfocusedLabelColor = Color.Gray,
                cursorColor = Color.Cyan
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        // CUADRÍCULA DE BOTONES

        // Fila 1: C, %, /, *
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CalcButton("C") { texto = "" }
            CalcButton("%") { texto += "%" }
            CalcButton("/") { texto += "/" }
            CalcButton("*") { texto += "*" }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Fila 2: 7, 8, 9, -
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CalcButton("7") { texto += "7" }
            CalcButton("8") { texto += "8" }
            CalcButton("9") { texto += "9" }
            CalcButton("-") { texto += "-" }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Fila 3: 4, 5, 6, +
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CalcButton("4") { texto += "4" }
            CalcButton("5") { texto += "5" }
            CalcButton("6") { texto += "6" }
            CalcButton("+") { texto += "+" }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Fila 4: 1, 2, 3, =
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CalcButton("1") { texto += "1" }
            CalcButton("2") { texto += "2" }
            CalcButton("3") { texto += "3" }
            CalcButton("=") {
                texto = "Resultado"
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Fila 5: 0
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CalcButton("0") { texto += "0" }
        }
    }
}

@Composable
fun CalcButton(symbol: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,

        modifier = Modifier.size(80.dp),
          shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
         containerColor = Color.DarkGray,
        contentColor = Color.White
        )
    ) {
        Text(text = symbol, fontSize = 20.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalculadoraTheme {
        Greeting()
    }
}