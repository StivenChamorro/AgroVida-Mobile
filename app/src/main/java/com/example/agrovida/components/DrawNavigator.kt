package com.example.agrovida.components


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Modelo para los items del drawer
data class DrawerItem(
    val title: String,
    val icon: ImageVector,
    val route: String
    //val badge: Int? = null // Opcional: para mostrar notificaciones
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerNavigator(
    navController: NavController,
    drawerState: DrawerState,
    onCloseDrawer: () -> Unit,
    content: @Composable () -> Unit
) {
    // Lista de items del drawer
    val drawerItems = listOf(
        DrawerItem("Mis Granjas", Icons.Default.Agriculture, "home"),
        DrawerItem("Granjas invitadas", Icons.Default.Group, "invitedFarms"),
        DrawerItem("Graficos de sensores", Icons.Default.AutoGraph, "graphs"),
        DrawerItem("Tareas", Icons.Default.Task, "tasks"),
        DrawerItem("Sensores", Icons.Default.Sensors, "sensors"),
        DrawerItem("Usuarios", Icons.Default.Share, "users")
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                // Header del drawer
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp, horizontal = 16.dp)
                ) {
                    Column {
                        // Logo o imagen de perfil
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Nombre de usuario
                        Text(
                            text = "Usuario",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        // Email
                        Text(
                            text = "usuario@example.com",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Divider()
                Spacer(modifier = Modifier.height(8.dp))

                // Items del drawer
                drawerItems.forEach { item ->
                    NavigationDrawerItem(
                        icon = {
                            Icon(item.icon, contentDescription = null)
                        },
                        label = { Text(item.title) },
                        selected = false,
                        onClick = {
                            navController.navigate(item.route) {
                                // Configuración de navegación
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                            onCloseDrawer()
                        },
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Botón de cerrar sesión en la parte inferior
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            Icons.Default.ExitToApp,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error
                        )
                    },
                    label = {
                        Text(
                            "Cerrar Sesión",
                            color = MaterialTheme.colorScheme.error
                        )
                    },
                    selected = false,
                    onClick = {
                        // TODO: Implementar lógica de cierre de sesión
                        onCloseDrawer()
                    },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 24.dp)
                )
            }
        }
    ) {
        content()
    }
}