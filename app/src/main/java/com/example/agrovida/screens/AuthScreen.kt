package com.example.agrovida.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agrovida.R

// Clase para manejar los estados de validación
data class ValidationState(
    val isValid: Boolean,
    val errorMessage: String = ""
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen() {
    // Estados para controlar la visibilidad de los formularios
    var showLoginForm by remember { mutableStateOf(false) }
    var showRegisterForm by remember { mutableStateOf(false) }

    // Estados para los campos del formulario de login
    var loginUsername by remember { mutableStateOf("") }
    var loginPassword by remember { mutableStateOf("") }

    // Estados para los errores de login
    var loginUsernameError by remember { mutableStateOf<ValidationState>(ValidationState(true)) }
    var loginPasswordError by remember { mutableStateOf<ValidationState>(ValidationState(true)) }

    // Estados para los campos del formulario de registro
    var registerUsername by remember { mutableStateOf("") }
    var registerEmail by remember { mutableStateOf("") }
    var registerPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // Estados para los errores de registro
    var registerUsernameError by remember { mutableStateOf<ValidationState>(ValidationState(true)) }
    var registerEmailError by remember { mutableStateOf<ValidationState>(ValidationState(true)) }
    var registerPasswordError by remember { mutableStateOf<ValidationState>(ValidationState(true)) }
    var confirmPasswordError by remember { mutableStateOf<ValidationState>(ValidationState(true)) }

    // Funciones de validación
    fun validateEmail(email: String): ValidationState {
        return when {
            email.isEmpty() -> ValidationState(false, "El correo es requerido")
            !email.contains("@") -> ValidationState(false, "El correo debe contener @")
            !email.contains(".") -> ValidationState(false, "El correo debe contener un dominio válido")
            else -> ValidationState(true)
        }
    }

    fun validatePassword(password: String): ValidationState {
        return when {
            password.isEmpty() -> ValidationState(false, "La contraseña es requerida")
            password.length < 6 -> ValidationState(false, "La contraseña debe tener al menos 6 caracteres")
            !password.any { it.isDigit() } -> ValidationState(false, "La contraseña debe contener al menos un número")
            !password.any { it.isUpperCase() } -> ValidationState(false, "La contraseña debe contener al menos una mayúscula")
            else -> ValidationState(true)
        }
    }

    fun validateUsername(username: String): ValidationState {
        return when {
            username.isEmpty() -> ValidationState(false, "El usuario es requerido")
            username.length < 4 -> ValidationState(false, "El usuario debe tener al menos 4 caracteres")
            else -> ValidationState(true)
        }
    }

    fun validateConfirmPassword(password: String, confirmPassword: String): ValidationState {
        return when {
            confirmPassword.isEmpty() -> ValidationState(false, "Debe confirmar la contraseña")
            password != confirmPassword -> ValidationState(false, "Las contraseñas no coinciden")
            else -> ValidationState(true)
        }
    }

    // Funciones de manejo de formularios
    fun handleLogin() {
        loginUsernameError = validateUsername(loginUsername)
        loginPasswordError = validatePassword(loginPassword)

        if (loginUsernameError.isValid && loginPasswordError.isValid) {
            // TODO: Implementar lógica de login
        }
    }

    fun handleRegister() {
        registerUsernameError = validateUsername(registerUsername)
        registerEmailError = validateEmail(registerEmail)
        registerPasswordError = validatePassword(registerPassword)
        confirmPasswordError = validateConfirmPassword(registerPassword, confirmPassword)

        if (registerUsernameError.isValid &&
            registerEmailError.isValid &&
            registerPasswordError.isValid &&
            confirmPasswordError.isValid) {
            // TODO: Implementar lógica de registro
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.fondo_welcome),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Overlay oscuro con manejo de clicks mejorado
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    if (showLoginForm || showRegisterForm) {
                        showLoginForm = false
                        showRegisterForm = false
                    }
                }
        )

        // Contenido principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .systemBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable.logo_agrovida),
                contentDescription = "Logo Agrovida",
                modifier = Modifier
                    .size(100.dp)
                    .padding(top = 48.dp, bottom = 16.dp)
            )

            // Título principal
            Text(
                text = "Hola\nInicia con tu cuenta",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                lineHeight = 36.sp
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        // Formulario de Login
        AnimatedVisibility(
            visible = showLoginForm,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(500, easing = EaseOutQuart)
            ),
            exit = slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(500, easing = EaseInQuart)
            ),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Iniciar sesión",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    OutlinedTextField(
                        value = loginUsername,
                        onValueChange = {
                            loginUsername = it
                            loginUsernameError = validateUsername(it)
                        },
                        label = { Text("Usuario") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        isError = !loginUsernameError.isValid,
                        supportingText = {
                            if (!loginUsernameError.isValid) {
                                Text(loginUsernameError.errorMessage)
                            }
                        }
                    )

                    OutlinedTextField(
                        value = loginPassword,
                        onValueChange = {
                            loginPassword = it
                            loginPasswordError = validatePassword(it)
                        },
                        label = { Text("Contraseña") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        isError = !loginPasswordError.isValid,
                        supportingText = {
                            if (!loginPasswordError.isValid) {
                                Text(loginPasswordError.errorMessage)
                            }
                        }
                    )

                    TextButton(onClick = { /* TODO */ }) {
                        Text(
                            text = "¿Olvidaste tu contraseña?",
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Button(
                        onClick = { handleLogin() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Iniciar sesión",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "¿No tienes una cuenta? ",
                            color = Color.Gray
                        )
                        Text(
                            text = "Regístrate",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                showLoginForm = false
                                showRegisterForm = true
                            }
                        )
                    }
                }
            }
        }

        // Formulario de Registro
        AnimatedVisibility(
            visible = showRegisterForm,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(500, easing = EaseOutQuart)
            ),
            exit = slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(500, easing = EaseInQuart)
            ),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Crear cuenta",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    OutlinedTextField(
                        value = registerUsername,
                        onValueChange = {
                            registerUsername = it
                            registerUsernameError = validateUsername(it)
                        },
                        label = { Text("Usuario") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        isError = !registerUsernameError.isValid,
                        supportingText = {
                            if (!registerUsernameError.isValid) {
                                Text(registerUsernameError.errorMessage)
                            }
                        }
                    )

                    OutlinedTextField(
                        value = registerEmail,
                        onValueChange = {
                            registerEmail = it
                            registerEmailError = validateEmail(it)
                        },
                        label = { Text("Correo electrónico") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        isError = !registerEmailError.isValid,
                        supportingText = {
                            if (!registerEmailError.isValid) {
                                Text(registerEmailError.errorMessage)
                            }
                        }
                    )

                    OutlinedTextField(
                        value = registerPassword,
                        onValueChange = {
                            registerPassword = it
                            registerPasswordError = validatePassword(it)
                            if (confirmPassword.isNotEmpty()) {
                                confirmPasswordError = validateConfirmPassword(it, confirmPassword)
                            }
                        },
                        label = { Text("Contraseña") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        isError = !registerPasswordError.isValid,
                        supportingText = {
                            if (!registerPasswordError.isValid) {
                                Text(registerPasswordError.errorMessage)
                            }
                        }
                    )

                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = {
                            confirmPassword = it
                            confirmPasswordError = validateConfirmPassword(registerPassword, it)
                        },
                        label = { Text("Confirmar contraseña") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        isError = !confirmPasswordError.isValid,
                        supportingText = {
                            if (!confirmPasswordError.isValid) {
                                Text(confirmPasswordError.errorMessage)
                            }
                        }
                    )

                    Button(
                        onClick = { handleRegister() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Crear cuenta",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "¿Ya tienes una cuenta? ",
                            color = Color.Gray
                        )
                        Text(
                            text = "Inicia sesión",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                showRegisterForm = false
                                showLoginForm = true
                            }
                        )
                    }
                }
            }
        }

        // Botones iniciales
        if (!showLoginForm && !showRegisterForm) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(24.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = { showLoginForm = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.White
                    ),
                    border = ButtonDefaults.outlinedButtonBorder.copy(width = 2.dp)
                ) {
                    Text(
                        text = "Iniciar sesión",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Button(
                    onClick = { showRegisterForm = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "Registrarse",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {
    MaterialTheme {
        AuthScreen()
    }
}