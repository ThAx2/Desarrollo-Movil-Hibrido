package com.example.practica07
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MenuScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen() {
    val context = LocalContext.current

    var expandedMenu by remember { mutableStateOf(false) }

    var mensajeSeleccionado by remember { mutableStateOf("Ninguna opción seleccionada aún") }

    Scaffold(
        topBar = {

            TopAppBar(
                title = { Text("Práctica 07 - ToolBar & Menús") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                actions = {

                    IconButton(onClick = {
                        mensajeSeleccionado = "Acción: Buscar seleccionada"
                        Toast.makeText(context, "Buscando...", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar"
                        )
                    }


                    IconButton(onClick = {
                        mensajeSeleccionado = "Acción: Favorito seleccionado"
                        Toast.makeText(context, "Añadido a favoritos", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorito"
                        )
                    }


                    IconButton(onClick = { expandedMenu = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Más opciones"
                        )
                    }


                    DropdownMenu(
                        expanded = expandedMenu,
                        onDismissRequest = { expandedMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Perfil de Usuario") },
                            onClick = {
                                expandedMenu = false
                                mensajeSeleccionado = "Menú: Perfil seleccionado"
                                Toast.makeText(context, "Abriendo Perfil...", Toast.LENGTH_SHORT).show()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Ajustes de la App") },
                            onClick = {
                                expandedMenu = false
                                mensajeSeleccionado = "Menú: Ajustes seleccionado"
                                Toast.makeText(context, "Abriendo Ajustes...", Toast.LENGTH_SHORT).show()
                            }
                        )
                        HorizontalDivider()
                        DropdownMenuItem(
                            text = { Text("Acerca de") },
                            onClick = {
                                expandedMenu = false
                                mensajeSeleccionado = "Menú: Acerca de seleccionado"
                                Toast.makeText(context, "Práctica 07 - Material Design", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Estado del Menú / ToolBar:",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = mensajeSeleccionado,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}