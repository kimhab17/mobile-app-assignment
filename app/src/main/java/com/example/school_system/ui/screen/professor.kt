package com.example.school_system

import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.school_system.ui.theme.SchoolsystemTheme

// Custom dark red color
val DarkRed = Color(0xFF8B0000)

@OptIn(ExperimentalMaterial3Api::class)
class ProfessorLoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SchoolsystemTheme {
                ProfessorLoginScreen(
                    onBackClicked = { finish() }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessorLoginScreen(
    onBackClicked: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Custom colors for TextField
    val customTextFieldColors = TextFieldDefaults.colors(
        focusedIndicatorColor = DarkRed,
        unfocusedIndicatorColor = DarkRed.copy(alpha = 0.5f),
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        cursorColor = DarkRed
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image (Top)
        Image(
            painter = painterResource(id = R.drawable.rupp_bg),
            contentDescription = "University Building",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        )

        // Back button
        IconButton(
            onClick = onBackClicked,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 40.dp, start = 20.dp)
                .background(Color.White.copy(alpha = 0.5f), RoundedCornerShape(50))
                .size(40.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black
            )
        }

        // Login card
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
            Text(
                text = "Log In As Professor",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Email field
            Text(
                text = "Input Your Email",
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                shape = RoundedCornerShape(30.dp),
                colors = customTextFieldColors,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(bottom = 24.dp)
            )

            // Password field
            Text(
                text = "Input Your Password",
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                shape = RoundedCornerShape(40.dp),
                colors = customTextFieldColors,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(bottom = 24.dp)
            )

            // Login button
            Button(
                onClick = {
                    context.startActivity(Intent(context, MainProfessorActivity::class.java))
                },
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
        ProfessorLoginScreen(onBackClicked = {})
    }
}
