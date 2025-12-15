package com.example.school_system.ui.screen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.school_system.R
import com.example.school_system.ui.theme.SchoolsystemTheme
import com.example.school_system.viewmodel.LoginViewModel

val DarkRed = Color(0xFF8B0000)

@OptIn(ExperimentalMaterial3Api::class)
class ProfessorLoginActivity : ComponentActivity() {
    private val viewModel = LoginViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SchoolsystemTheme {
                ProfessorLoginScreen(
                    onBackClicked = { finish() },
                    onLogin = { email, password ->
                        viewModel.loginStudent(email, password) { role ->
                            if (role == "teacher") {
                                val intent = Intent(this, MainProfessorActivity::class.java)
                                intent.putExtra("email", email) // ✅ បញ្ជូន email ទៅ Activity
                                startActivity(intent)
                                finish()
                            }
                        }
                    },
                    onNavigateToRegister = {
                        // ✅ Move to register screen
                        startActivity(Intent(this, RegisterActivity::class.java))
                    },
                    viewModel = viewModel
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessorLoginScreen(
    onBackClicked: () -> Unit,
    onLogin: (String, String) -> Unit,
    onNavigateToRegister: () -> Unit,
    viewModel: LoginViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        // 🏫 Background image
        Image(
            painter = painterResource(id = R.drawable.rupp_bg),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        )

        // 🔙 Back button
        IconButton(
            onClick = onBackClicked,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.Black)
        }

        // 🧾 Login Form
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(Color.White, shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                .padding(32.dp)
        ) {
            Text("Log In As Professor", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(20.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(20.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(30.dp))

            Button(
                onClick = { onLogin(email, password) },
                colors = ButtonDefaults.buttonColors(containerColor = DarkRed),
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Log In", color = Color.White)
            }

            Spacer(Modifier.height(10.dp))
            TextButton(onClick = { onNavigateToRegister() }) {
                Text("Don't have an account? Register", color = DarkRed)
            }

            Spacer(Modifier.height(10.dp))
            Text(viewModel.message.value, color = Color.Red)
        }
    }
}
