package com.example.agrovida.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.entry.ChartEntry
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// Clase concreta para ChartEntry
data class CustomChartEntry(
    override val x: Float,
    override val y: Float
) : ChartEntry {
    override fun withY(y: Float) = copy(y = y)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SensorChart(
    data: List<Pair<LocalDateTime, Float>>,
    modifier: Modifier = Modifier,
    isActive: Boolean = true
) {
    // Convertir los datos en entradas del gráfico
    val chartEntries = data.mapIndexed { index, (_, value) ->
        CustomChartEntry(x = index.toFloat(), y = value)
    }
    val chartEntryModelProducer = ChartEntryModelProducer(chartEntries)

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Encabezado con indicador de estado
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sensor de Temperatura del Agua",
                    style = MaterialTheme.typography.titleMedium
                )
                StatusIndicator(isActive = isActive)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Gráfico
            ProvideChartStyle {
                Chart(
                    chart = lineChart(),
                    model = chartEntryModelProducer.getModel(),
                    startAxis = startAxis(
                        valueFormatter = { value, _ -> "${value.toInt()}°" }
                    ),
                    bottomAxis = bottomAxis(
                        valueFormatter = { value, _ ->
                            if (value.toInt() in data.indices) {
                                data[value.toInt()].first.format(
                                    DateTimeFormatter.ofPattern("HH:mm")
                                )
                            } else ""
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }
        }
    }
}

@Composable
fun StatusIndicator(isActive: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(
                    if (isActive) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.error,
                    shape = CircleShape
                )
        )
        Text(
            text = if (isActive) "Activo" else "Inactivo",
            style = MaterialTheme.typography.labelMedium,
            color = if (isActive) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.error
        )
    }
}

@Composable
fun TimeRangeSelector(
    selectedRange: String,
    onRangeSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val ranges = listOf("24h", "7d", "30d", "1y")

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ranges.forEach { range ->
            FilterChip(
                selected = range == selectedRange,
                onClick = { onRangeSelected(range) },
                label = { Text(range) }
            )
        }
    }
}
