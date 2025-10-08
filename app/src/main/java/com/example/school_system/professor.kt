package com.example.school_system

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.school_system.ui.theme.SchoolsystemTheme // Assuming your theme file path

// Define the dark red color used in the buttons/borders (0xFF8B0000)
val DarkRed = Color(0xFF8B0000)

class ProfessorLoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SchoolsystemTheme {
                ProfessorLoginScreen(
                    // TODO: Replace with actual navigation logic (e.g., using a NavController)
                    onBackClicked = { finish() },
                    onLoginClicked = { email, password ->
                        // TODO: Implement actual login logic here
                        println("Professor Login attempt: $email / $password")
                    }
                )
            }
        }
    }
}

@Composable
fun ProfessorLoginScreen(
    onBackClicked: () -> Unit,
    onLoginClicked: (String, String) -> Unit
) {
    // State for input fields
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Custom TextField colors to match the design (dark red border)
    val customTextFieldColors = TextFieldDefaults.colors(
        focusedIndicatorColor = DarkRed,
        unfocusedIndicatorColor = DarkRed.copy(alpha = 0.5f),
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        cursorColor = DarkRed
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // 1. Background Image (Top Section)
        // NOTE: The image in the design is only visible at the very top.
        // We use the same resource ID for consistency.
        Image(
            // Replace R.drawable.bg_university with your actual resource ID
            painter = painterResource(id = R.drawable.bg_university),
            contentDescription = "University Building",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f) // Occupy top 30% of the height
                .align(Alignment.TopCenter)
        )

        // 2. Back button (Top-left corner)
        IconButton(
            onClick = onBackClicked,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .background(Color.White.copy(alpha = 0.5f), RoundedCornerShape(50))
                .size(40.dp)
        ) {
            // Using a standard Material Icon for clarity, but you can use your custom one
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black
            )
        }

        // 3. Login Card (Bottom Section)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                )
                .padding(horizontal = 32.dp, vertical = 40.dp)
        ) {
            // Title
            Text(
                text = "Log In As Professor",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Email Input Field
            Text(
                text = "Input Your Email",
                fontSize = 14.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Username") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                shape = RoundedCornerShape(30.dp),
                colors = customTextFieldColors,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(bottom = 24.dp)
            )

            // Password Input Field
            Text(
                text = "Input Your Password",
                fontSize = 14.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                shape = RoundedCornerShape(30.dp),
                colors = customTextFieldColors,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(bottom = 40.dp)
            )

            // Log In Button
            Button(
                onClick = { onLoginClicked(email, password) },
                colors = ButtonDefaults.buttonColors(containerColor = DarkRed),
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Log In",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfessorLoginScreenPreview() {
    SchoolsystemTheme {
        ProfessorLoginScreen(
            onBackClicked = {},
            onLoginClicked = { _, _ -> }
        )
    }
}