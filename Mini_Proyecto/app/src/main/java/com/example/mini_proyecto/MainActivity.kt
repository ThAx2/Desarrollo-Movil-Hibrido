package com.example.mini_proyecto

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "form_screen") {
        composable("form_screen") {
            FormScreen(navController)
        }
        composable(
            route = "detail_screen/{matricula}/{nombre}/{carrera}/{turno}/{estatus}",
            arguments = listOf(
                navArgument("matricula") { type = NavType.StringType },
                navArgument("nombre") { type = NavType.StringType },
                navArgument("carrera") { type = NavType.StringType },
                navArgument("turno") { type = NavType.StringType },
                navArgument("estatus") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val matricula = backStackEntry.arguments?.getString("matricula") ?: ""
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            val carrera = backStackEntry.arguments?.getString("carrera") ?: ""
            val turno = backStackEntry.arguments?.getString("turno") ?: ""
            val estatus = backStackEntry.arguments?.getString("estatus") ?: ""

            DetailScreen(navController, matricula, nombre, carrera, turno, estatus)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(navController: NavController) {
    val context = LocalContext.current
    val sharedPreferences = remember {
        context.getSharedPreferences("RegistroPrefs", Context.MODE_PRIVATE)
    }

    var matricula by remember { mutableStateOf(sharedPreferences.getString("KEY_MATRICULA", "") ?: "") }
    var nombre by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }
    val carreras = listOf("Ingeniería en Sistemas", "Licenciatura en Administración", "Diseño Gráfico", "Derecho")
    var carreraSeleccionada by remember { mutableStateOf(carreras[0]) }

    val turnos = listOf("Matutino", "Vespertino")
    var turnoSeleccionado by remember { mutableStateOf(turnos[0]) }

    var estatusActivo by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Sistema de Registro",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        OutlinedTextField(
            value = matricula,
            onValueChange = { matricula = it },
            label = { Text("Matrícula") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre Completo") },
            modifier = Modifier.fillMaxWidth()
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = carreraSeleccionada,
                onValueChange = {},
                readOnly = true,
                label = { Text("Carrera") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                carreras.forEach { carrera ->
                    DropdownMenuItem(
                        text = { Text(carrera) },
                        onClick = {
                            carreraSeleccionada = carrera
                            expanded = false
                        }
                    )
                }
            }
        }

        Text(text = "Turno:", style = MaterialTheme.typography.bodyLarge)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            turnos.forEach { turno ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    RadioButton(
                        selected = (turnoSeleccionado == turno),
                        onClick = { turnoSeleccionado = turno }
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = turno)
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = if (estatusActivo) "Estatus: Activo" else "Estatus: Inactivo")
            Switch(
                checked = estatusActivo,
                onCheckedChange = { estatusActivo = it }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                sharedPreferences.edit().apply {
                    putString("KEY_MATRICULA", matricula)
                    apply()
                }

                val estatusStr = if (estatusActivo) "Activo" else "Inactivo"
                navController.navigate("detail_screen/$matricula/$nombre/$carreraSeleccionada/$turnoSeleccionado/$estatusStr")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Registrar y Enviar Datos")
        }
    }
}

@Composable
fun DetailScreen(
    navController: NavController,
    matricula: String,
    nombre: String,
    carrera: String,
    turno: String,
    estatus: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Datos del Estudiante Registrado",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.secondary
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(text = "• Matrícula: $matricula", style = MaterialTheme.typography.bodyLarge)
                Text(text = "• Nombre: $nombre", style = MaterialTheme.typography.bodyLarge)
                Text(text = "• Carrera: $carrera", style = MaterialTheme.typography.bodyLarge)
                Text(text = "• Turno: $turno", style = MaterialTheme.typography.bodyLarge)
                Text(text = "• Estatus: $estatus", style = MaterialTheme.typography.bodyLarge)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Volver al Formulario")
        }
    }
}