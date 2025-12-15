package com.example.school_system.ui.screen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.school_system.R
import com.example.school_system.ui.theme.SchoolsystemTheme
import com.example.school_system.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
class StudentLoginActivity : ComponentActivity() {
    private val viewModel = LoginViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SchoolsystemTheme {
                StudentLoginScreen(
                    onBack = { finish() },
                    onLogin = { email, password ->
                        viewModel.loginStudent(email, password) { role ->
                            if (role == "student") {
                                startActivity(Intent(this, DashboardActivity::class.java))
                            }
                        }
                    },
                    onNavigateToRegister = {
                        startActivity(Intent(this, RegisterActivity::class.java))
                    },
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun StudentLoginScreen(
    onBack: () -> Unit = {},
    onLogin: (String, String) -> Unit = { _, _ -> },
    onNavigateToRegister: () -> Unit = {},
    viewModel: LoginViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.rupp_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().height(260.dp),
            contentScale = ContentScale.Crop
        )

        IconButton(onClick = onBack, modifier = Modifier.padding(16.dp).align(Alignment.TopStart)) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.Black)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp))
                .background(Color.White)
                .padding(25.dp)
        ) {
            Text("Log In As Student", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(22.dp))

            Text("Email", fontWeight = FontWeight.SemiBold)
            OutlinedTextField(value = email, onValueChange = { email = it }, placeholder = { Text("Email") }, modifier = Modifier.fillMaxWidth())

            Spacer(Modifier.height(20.dp))
            Text("Password", fontWeight = FontWeight.SemiBold)
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(30.dp))
            Button(
                onClick = { onLogin(email, password) },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)),
                shape = RoundedCornerShape(25.dp)
            ) { Text("Log In", color = Color.White) }

            Spacer(Modifier.height(10.dp))
            TextButton(onClick = { onNavigateToRegister() }) {
                Text("Don't have an account? Register", color = Color(0xFF8B0000))
            }

            Spacer(Modifier.height(10.dp))
            Text(viewModel.message.value, color = Color.Red)
        }
    }
}
