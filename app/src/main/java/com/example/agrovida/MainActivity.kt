package com.example.agrovida

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.agrovida.screens.AuthScreen
import com.example.agrovida.screens.FarmScreen
import com.example.agrovida.screens.HomeScreen
import com.example.agrovida.ui.theme.AgroVidaTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AgroVidaTheme {
                FarmScreen(navController = rememberNavController(), onMenuClick = {})
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigator() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "graph_screen" // Ruta inicial
    ) {
        // Ruta de la pantalla de gráficos
        composable("graph_screen") {
            FarmScreen(
                navController = navController,
                onMenuClick = {
                    // Aquí puedes manejar la lógica del menú
                }
            )
        }
        // Agrega otras pantallas si es necesario
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AgroVidaTheme {
        Greeting("Android")
    }
}
