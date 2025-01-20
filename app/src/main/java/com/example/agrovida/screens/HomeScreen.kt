package com.example.agrovida.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.agrovida.R
import kotlinx.coroutines.launch

// Modelo de datos para la granja con todos los campos necesarios
data class Farm(
    val id: String,
    val name: String,
    val type: String,
    val address: String,
    val extension: String,
    val village: String,
    val municipality: String
)

// Estado para el formulario de nueva granja
data class FarmFormState(
    val name: String = "",
    val type: String = "",
    val address: String = "",
    val extension: String = "",
    val village: String = "",
    val municipality: String = ""
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun HomeScreen(navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var farms by remember { mutableStateOf(generateSampleFarms()) }
    var showAddFarmDialog by remember { mutableStateOf(false) }
    var farmToDelete by remember { mutableStateOf<Farm?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var formState by remember { mutableStateOf(FarmFormState()) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding( horizontal = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    // Logo
                    Image(
                        painter = painterResource(id = R.drawable.logo_agrovida2),
                        contentDescription = "Logo Agrovida",
                        modifier = Modifier
                            .size(120.dp)
                            //.background(Color.Red)
                            .padding(bottom = 8.dp)
                    )

                    // Drawer items
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = null) },
                        label = { Text("Mis granjas") },
                        selected = true,
                        onClick = {
                            scope.launch { drawerState.close() }
                        }
                    )

                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.People, contentDescription = null) },
                        label = { Text("Granjas invitadas") },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                        }
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    // Cerrar sesión at the bottom
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
                                "Cerrar sesión",
                                color = MaterialTheme.colorScheme.error
                            )
                        },
                        selected = false,
                        onClick = {
                            // TODO: Implementar lógica de cierre de sesión
                            scope.launch { drawerState.close() }
                        }
                    )
                }
            }
        }
    ){
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Mis granjas") },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(Icons.Default.Menu, contentDescription = "Menú")
                        }
                    },
                    actions = {
                        Text(
                            text = "Administrador",
                            modifier = Modifier.padding(end = 16.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { showAddFarmDialog = true },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Default.Add, "Agregar granja")
                }
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(farms) { farm ->
                    FarmCard(
                        farm = farm,
                        onDeleteClick = {
                            farmToDelete = farm
                            showDeleteDialog = true
                        }
                    )
                }
            }

            if (showDeleteDialog && farmToDelete != null) {
                AlertDialog(
                    onDismissRequest = {
                        showDeleteDialog = false
                        farmToDelete = null
                    },
                    title = { Text("Confirmar eliminación") },
                    text = { Text("¿Estás seguro que deseas eliminar la granja ${farmToDelete?.name}?") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                farms = farms.filter { it.id != farmToDelete?.id }
                                showDeleteDialog = false
                                farmToDelete = null
                            }
                        ) {
                            Text("Eliminar")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showDeleteDialog = false
                                farmToDelete = null
                            }
                        ) {
                            Text("Cancelar")
                        }
                    }
                )
            }

            if (showAddFarmDialog) {
                AlertDialog(
                    onDismissRequest = { showAddFarmDialog = false },
                    title = { Text("Agregar nueva granja") },
                    text = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = formState.name,
                                onValueChange = { formState = formState.copy(name = it) },
                                label = { Text("Nombre de la granja") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            OutlinedTextField(
                                value = formState.type,
                                onValueChange = { formState = formState.copy(type = it) },
                                label = { Text("Tipo") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            OutlinedTextField(
                                value = formState.address,
                                onValueChange = { formState = formState.copy(address = it) },
                                label = { Text("Dirección") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            OutlinedTextField(
                                value = formState.extension,
                                onValueChange = { formState = formState.copy(extension = it) },
                                label = { Text("Extensión") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            OutlinedTextField(
                                value = formState.village,
                                onValueChange = { formState = formState.copy(village = it) },
                                label = { Text("Vereda") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            OutlinedTextField(
                                value = formState.municipality,
                                onValueChange = { formState = formState.copy(municipality = it) },
                                label = { Text("Municipio") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                val newFarm = Farm(
                                    id = (farms.size + 1).toString(),
                                    name = formState.name,
                                    type = formState.type,
                                    address = formState.address,
                                    extension = formState.extension,
                                    village = formState.village,
                                    municipality = formState.municipality
                                )
                                farms = farms + newFarm
                                formState = FarmFormState()
                                showAddFarmDialog = false
                            }
                        ) {
                            Text("Agregar")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { showAddFarmDialog = false }
                        ) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun FarmCard(
    farm: Farm,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = farm.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = Color.Red
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            FarmInfoRow(icon = Icons.Default.Agriculture, text = "Tipo: ${farm.type}")
            FarmInfoRow(icon = Icons.Default.Place, text = "Dirección: ${farm.address}")
            FarmInfoRow(icon = Icons.Default.GridOn, text = "Extensión: ${farm.extension}")
            FarmInfoRow(icon = Icons.Default.Terrain, text = "Vereda: ${farm.village}")
            FarmInfoRow(icon = Icons.Default.Domain, text = "Municipio: ${farm.municipality}")
        }
    }
}

@Composable
fun FarmInfoRow(
    icon: ImageVector,
    text: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = 16.sp
        )
    }
}

private fun generateSampleFarms(): List<Farm> {
    return listOf(
        Farm(
            id = "1",
            name = "Granja 1",
            type = "Acuaponica",
            address = "Carrera 20 60N 129",
            extension = "10 hectáreas",
            village = "Punta larga",
            municipality = "Popayán"
        ),
        Farm(
            id = "2",
            name = "Granja 2",
            type = "Acuaponica",
            address = "Calle 15 #23-45",
            extension = "5 hectáreas",
            village = "El Carmen",
            municipality = "Popayán"
        )
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen(navController = NavController(context = LocalContext.current))
        //
    }
}
