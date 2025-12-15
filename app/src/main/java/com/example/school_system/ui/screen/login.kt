package com.example.school_system.ui.screen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.school_system.R
import com.example.school_system.ui.theme.SchoolsystemTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SchoolsystemTheme {
                LoginAsScreen()
            }
        }
    }
}

@Composable
fun LoginAsScreen() {
    val context = LocalContext.current // <-- Get the current context for navigation

    Box(modifier = Modifier.fillMaxSize()) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.rupp_bg),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Back button (top-left corner)
        IconButton(
            onClick = {},
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 40.dp, start = 10.dp)
        ) {
            Icon(
                // You should ensure R.drawable.ic_back exists or use a Material icon
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "Back",
                tint = Color.Black
            )
        }

        // Bottom section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                )
                .padding(32.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Log In As !",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Professor Button
                Button(
                    onClick = {
                        // NAVIGATE TO PROFESSOR LOGIN ACTIVITY
                        context.startActivity(Intent(context, ProfessorLoginActivity::class.java))
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)),
                    shape = RoundedCornerShape(30.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(horizontal = 40.dp)
                ) {
                    Text(
                        text = "Professor",
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                // OR text
                Text(
                    text = "Or",
                    color = Color.Black,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                // Student Button
                Button(
                    onClick = {
                        // NAVIGATE TO PROFESSOR LOGIN ACTIVITY
                        context.startActivity(Intent(context, StudentLoginActivity::class.java))
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)),
                    shape = RoundedCornerShape(30.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(horizontal = 40.dp)
                ) {
                    Text(
                        text = "Student",
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginAsScreenPreview() {
    SchoolsystemTheme {
        LoginAsScreen()
    }
}