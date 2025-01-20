//package com.example.agrovida.screens
//
//import android.annotation.SuppressLint
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.agrovida.models.*
//import java.time.LocalDateTime
//import java.time.format.DateTimeFormatter
//
//@SuppressLint("RememberReturnType")
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SensorsScreen() {
//    // Estado para la lista de sensores (esto vendría de la API)
//    var sensors by remember { mutableStateOf(generateSampleSensors()) }
//
//    Scaffold(
//        // Barra superior con título
//        topBar = {
//            TopAppBar(
//                title = {
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.spacedBy(8.dp)
//                    ) {
//                        Icon(
//                            imageVector = Icons.Default.Memory,
//                            contentDescription = null,
//                            tint = MaterialTheme.colorScheme.primary
//                        )
//                        Text("Sensores")
//                    }
//                }
//            )
//        }
//    ) { paddingValues ->
//        // Grid de sensores
//        LazyVerticalGrid(
//            columns = GridCells.Adaptive(minSize = 300.dp),
//            contentPadding = paddingValues,
//            modifier = Modifier.fillMaxSize(),
//            horizontalArrangement = Arrangement.spacedBy(16.dp),
//            verticalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            items(sensors) { sensor ->
//                SensorCard(
//                    sensor = sensor,
//                    onStatusChange = { newStatus ->
//                        // Actualizar el estado del sensor
//                        sensors = sensors.map {
//                            if (it.id == sensor.id) it.copy(status = newStatus)
//                            else it
//                        }
//                    }
//                )
//            }
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SensorCard(
//    sensor: Sensor,
//    onStatusChange: (SensorStatus) -> Unit
//) {
//    var showStatusMenu by remember { mutableStateOf(false) }
//
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            // Cabecera del sensor con nombre y estado
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                // Icono y nombre del sensor
//                Row(
//                    horizontalArrangement = Arrangement.spacedBy(8.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Icon(
//                        imageVector = when(sensor.type) {
//                            SensorType.PH -> Icons.Default.Science
//                            SensorType.DISSOLVED_OXYGEN -> Icons.Default.Water
//                            SensorType.WATER_TEMPERATURE -> Icons.Default.Thermostat
//                        },
//                        contentDescription = null,
//                        tint = MaterialTheme.colorScheme.primary
//                    )
//                    Text(
//                        text = sensor.name,
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.Medium
//                    )
//                }
//
//                // Indicador de estado
//                Chip(
//                    onClick = { },
//                    colors = ChipDefaults.chipColors(
//                        containerColor = when(sensor.status) {
//                            SensorStatus.ACTIVE -> Color(0xFF4CAF50)
//                            SensorStatus.INACTIVE -> Color(0xFFFF5252)
//                            SensorStatus.MAINTENANCE -> Color(0xFFFFB74D)
//                        }
//                    )
//                ) {
//                    Text(
//                        text = when(sensor.status) {
//                            SensorStatus.ACTIVE -> "Activo"
//                            SensorStatus.INACTIVE -> "Inactivo"
//                            SensorStatus.MAINTENANCE -> "En mantenimiento"
//                        },
//                        color = Color.White
//                    )
//                }
//            }
//
//            // Selector de estado
//            OutlinedCard(
//                onClick = { showStatusMenu = true }
//            ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text("Estado del Sensor")
//                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Cambiar estado")
//                }
//
//                DropdownMenu(
//                    expanded = showStatusMenu,
//                    onDismissRequest = { showStatusMenu = false }
//                ) {
//                    SensorStatus.values().forEach { status ->
//                        DropdownMenuItem(
//                            text = {
//                                Text(
//                                    when(status) {
//                                        SensorStatus.ACTIVE -> "Activo"
//                                        SensorStatus.INACTIVE -> "Inactivo"
//                                        SensorStatus.MAINTENANCE -> "En mantenimiento"
//                                    }
//                                )
//                            },
//                            onClick = {
//                                onStatusChange(status)
//                                showStatusMenu = false
//                            }
//                        )
//                    }
//                }
//            }
//
//            // Última actualización
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.Start,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Update,
//                    contentDescription = null,
//                    modifier = Modifier.size(16.dp),
//                    tint = MaterialTheme.colorScheme.onSurfaceVariant
//                )
//                Spacer(modifier = Modifier.width(4.dp))
//                Text(
//                    text = "Última actualización: ${
//                        sensor.lastUpdate.format(
//                            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
//                        )
//                    }",
//                    color = MaterialTheme.colorScheme.onSurfaceVariant,
//                    fontSize = 14.sp
//                )
//            }
//        }
//    }
//}
//
//// Función para generar datos de ejemplo (reemplazar con llamada a API)
//private fun generateSampleSensors(): List<Sensor> {
//    return listOf(
//        Sensor(
//            id = "1",
//            name = "Sensor de pH",
//            type = SensorType.PH,
//            status = SensorStatus.ACTIVE,
//            lastUpdate = LocalDateTime.now()
//        ),
//        Sensor(
//            id = "2",
//            name = "Sensor de Oxígeno Disuelto",
//            type = SensorType.DISSOLVED_OXYGEN,
//            status = SensorStatus.ACTIVE,
//            lastUpdate = LocalDateTime.now()
//        ),
//        Sensor(
//            id = "3",
//            name = "Sensor de Temperatura del Agua",
//            type = SensorType.WATER_TEMPERATURE,
//            status = SensorStatus.ACTIVE,
//            lastUpdate = LocalDateTime.now()
//        )
//    )
//}