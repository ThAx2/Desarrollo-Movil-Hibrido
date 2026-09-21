
package com.example.calculadora

import android.os.Bundle
import androidx.compose.foundation.layout.RowScope
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.calculadora.ui.theme.CalculadoraTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            CalculadoraTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF101114)
                ) {
                    Greeting()
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
            .background(Color(0xFF101114))
            .padding(horizontal = 20.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Título
        Text(
            text = "Calculadora",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Pantalla
        OutlinedTextField(
            value = texto,
            onValueChange = { texto = it },
            label = {
                Text("Resultado")
            },
            textStyle = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            ),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp),
            shape = RoundedCornerShape(18.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF191A1F),
                unfocusedContainerColor = Color(0xFF191A1F),

                focusedBorderColor = Color(0xFF00D9FF),
                unfocusedBorderColor = Color(0xFF34363D),

                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,

                focusedLabelColor = Color(0xFF00D9FF),
                unfocusedLabelColor = Color(0xFF777A84),

                cursorColor = Color(0xFF00D9FF)
            )
        )

        Spacer(modifier = Modifier.height(35.dp))

        // Fila 1
        CalculatorRow {
            CalcButton(
                symbol = "C",
                buttonColor = Color(0xFFD9534F)
            ) {
                texto = ""
            }

            CalcButton(
                symbol = "%",
                buttonColor = Color(0xFF292B31)
            ) {
                texto += "%"
            }

            CalcButton(
                symbol = "÷",
                buttonColor = Color(0xFF292B31)
            ) {
                texto += "/"
            }

            CalcButton(
                symbol = "×",
                buttonColor = Color(0xFF292B31)
            ) {
                texto += "*"
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Fila 2
        CalculatorRow {

            CalcButton("7") {
                texto += "7"
            }

            CalcButton("8") {
                texto += "8"
            }

            CalcButton("9") {
                texto += "9"
            }

            CalcButton(
                symbol = "−",
                buttonColor = Color(0xFF292B31)
            ) {
                texto += "-"
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Fila 3
        CalculatorRow {

            CalcButton("4") {
                texto += "4"
            }

            CalcButton("5") {
                texto += "5"
            }

            CalcButton("6") {
                texto += "6"
            }

            CalcButton(
                symbol = "+",
                buttonColor = Color(0xFF292B31)
            ) {
                texto += "+"
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Fila 4
        CalculatorRow {

            CalcButton("1") {
                texto += "1"
            }

            CalcButton("2") {
                texto += "2"
            }

            CalcButton("3") {
                texto += "3"
            }

            CalcButton(
                symbol = "=",
                buttonColor = Color(0xFF00A9C7)
            ) {
                texto = calcular(texto)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Fila 5
        CalculatorRow {

            CalcButton("0") {
                texto += "0"
            }
        }
    }
}
fun calcular(expresion: String): String {

    return try {

        when {
            expresion.contains("+") -> {
                val numeros = expresion.split("+")
                val numero1 = numeros[0].toDouble()
                val numero2 = numeros[1].toDouble()

                (numero1 + numero2).toString()
            }

            expresion.contains("-") -> {
                val numeros = expresion.split("-")
                val numero1 = numeros[0].toDouble()
                val numero2 = numeros[1].toDouble()

                (numero1 - numero2).toString()
            }

            expresion.contains("*") -> {
                val numeros = expresion.split("*")
                val numero1 = numeros[0].toDouble()
                val numero2 = numeros[1].toDouble()

                (numero1 * numero2).toString()
            }

            expresion.contains("/") -> {
                val numeros = expresion.split("/")
                val numero1 = numeros[0].toDouble()
                val numero2 = numeros[1].toDouble()

                if (numero2 == 0.0) {
                    "Error"
                } else {
                    (numero1 / numero2).toString()
                }
            }

            else -> {
                expresion
            }
        }

    } catch (e: Exception) {
        "Error"
    }
}

@Composable
fun CalculatorRow(
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        content = content
    )
}

@Composable
fun CalcButton(
    symbol: String,
    buttonColor: Color = Color(0xFF202227),
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,

        modifier = Modifier.size(76.dp),

        shape = RoundedCornerShape(18.dp),

        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            contentColor = Color.White
        ),

        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 1.dp
        )
    ) {
        Text(
            text = symbol,
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

    CalculadoraTheme {
        Greeting()
    }
}
