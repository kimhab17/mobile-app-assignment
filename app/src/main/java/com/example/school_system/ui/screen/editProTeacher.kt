package com.example.school_system.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.school_system.R
import com.example.school_system.ui.theme.SchoolsystemTheme

class EditProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SchoolsystemTheme {
                EditProfileScreen(onBack = { finish() })
            }
        }
    }
}

@Composable
fun EditProfileScreen(onBack: () -> Unit) {

    var fullName by remember { mutableStateOf("Kdam Prai") }
    var gender by remember { mutableStateOf("Male") }
    var birthday by remember { mutableStateOf("05-01-2001") }
    var phone by remember { mutableStateOf("(+885) 012345678") }
    var email by remember { mutableStateOf("kdamiprai@gmail.com") }
    var username by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        // BACK + TITLE
        Row(verticalAlignment = Alignment.CenterVertically) {

            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .size(28.dp)
                    .clickable { onBack() }
            )
            Spacer(modifier = Modifier.width(16.dp))

            Text(
                "Edit Profile",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // PROFILE AVATAR
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {

            // Main Profile Pic
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .border(3.dp, Color(0xFF8B0000), CircleShape)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Camera Overlay
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(1.dp, Color.Gray, CircleShape)
                    .clickable { /* TODO: open picker */ },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.CameraAlt,
                    contentDescription = "Edit Photo",
                    tint = Color(0xFF8B0000)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Name & Username
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tea Kdam Prai", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("@kdamiprai", fontSize = 14.sp, color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(20.dp))

        HorizontalDivider(
            thickness = 1.dp,
            color = Color.LightGray
        )

        Spacer(modifier = Modifier.height(20.dp))

        // FULL NAME
        InputField("Full Name", fullName) { fullName = it }

        Spacer(modifier = Modifier.height(16.dp))

        // 2 COLUMN LAYOUT (Gender / Birthday)
        Row(modifier = Modifier.fillMaxWidth()) {

            InputFieldHalf("Gender", gender) { gender = it }

            Spacer(modifier = Modifier.width(12.dp))

            InputFieldHalf("Birthday", birthday) { birthday = it }
        }

        Spacer(modifier = Modifier.height(16.dp))

        InputField("Phone number", phone) { phone = it }
        Spacer(modifier = Modifier.height(16.dp))

        InputField("Email", email) { email = it }
        Spacer(modifier = Modifier.height(16.dp))

        InputField("Username", username) { username = it }
        Spacer(modifier = Modifier.height(30.dp))

        // SAVE BUTTON
        Button(
            onClick = { /* TODO Save Profile */ },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF8B0000)
            ),
            shape = RoundedCornerShape(25.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        ) {
            Text("Save", color = Color.White, fontSize = 18.sp)
        }
    }
}


/* ---- FULL-WIDTH INPUT FIELD ---- */

@Composable
fun InputField(label: String, value: String, onValueChange: (String) -> Unit) {

    Column {
        Text(label, fontSize = 14.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))

        TextField(
            value = value,
            onValueChange = onValueChange,
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color(0xFF8B0000),
                unfocusedIndicatorColor = Color(0xFF8B0000)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(2.dp, Color(0xFF8B0000), RoundedCornerShape(20.dp))
        )
    }
}


/* ---- HALF-WIDTH INPUT FIELD ---- */

@Composable
fun InputFieldHalf(label: String, value: String, onValueChange: (String) -> Unit) {

    Column(modifier = Modifier) {

        Text(label, fontSize = 14.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))

        TextField(
            value = value,
            onValueChange = onValueChange,
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color(0xFF8B0000),
                unfocusedIndicatorColor = Color(0xFF8B0000)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(2.dp, Color(0xFF8B0000), RoundedCornerShape(20.dp))
        )
    }
}
