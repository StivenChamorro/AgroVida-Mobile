package com.example.agrovida.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.agrovida.components.DrawerNavigator
import com.example.agrovida.components.SensorChart
import com.example.agrovida.components.TimeRangeSelector
import com.example.agrovida.models.SensorReading
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FarmScreen(
    navController: NavController,
    onMenuClick: () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedTimeRange by remember { mutableStateOf("24h") }
    var sensorData by remember { mutableStateOf(generateSampleData()) }

    DrawerNavigator(
        navController = navController,
        drawerState = drawerState,
        onCloseDrawer = {
            scope.launch {
                drawerState.close()
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Gráficos de Sensores") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Abrir Menú")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Selector de rango de tiempo
                TimeRangeSelector(
                    selectedRange = selectedTimeRange,
                    onRangeSelected = { range ->
                        selectedTimeRange = range
                        // Actualizar datos basados en el rango seleccionado
                    }
                )

                // Gráfico de temperatura del agua
                SensorChart(
                    data = sensorData.map { it.timestamp to it.temperature },
                    isActive = sensorData.lastOrNull()?.isActive ?: false
                )

                // Estadísticas
                StatisticsCard(sensorData)
            }
        }
    }
}

@Composable
fun StatisticsCard(data: List<SensorReading>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Estadísticas",
                style = MaterialTheme.typography.titleMedium
            )

            val stats = calculateStats(data)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatItem("Mínima", "${stats.min}°")
                StatItem("Promedio", "${stats.average}°")
                StatItem("Máxima", "${stats.max}°")
            }
        }
    }
}

@Composable
fun StatItem(label: String, value: String) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

data class Stats(
    val min: Int,
    val max: Int,
    val average: Int
)

private fun calculateStats(data: List<SensorReading>): Stats {
    if (data.isEmpty()) return Stats(0, 0, 0)

    return Stats(
        min = data.minOf { it.temperature.toInt() },
        max = data.maxOf { it.temperature.toInt() },
        average = data.map { it.temperature.toInt() }.average().toInt()
    )
}

@RequiresApi(Build.VERSION_CODES.O)
private fun generateSampleData(): List<SensorReading> {
    val now = LocalDateTime.now()
    return List(24) { hour ->
        SensorReading(
            timestamp = now.minus(23L - hour, ChronoUnit.HOURS),
            temperature = Random.nextFloat() * 15 + 20, // Valores entre 20 y 35
            isActive = true
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GraphScreenPreview() {
    MaterialTheme {
        FarmScreen(navController = NavController(context = LocalContext.current), onMenuClick = {})
    }
}
