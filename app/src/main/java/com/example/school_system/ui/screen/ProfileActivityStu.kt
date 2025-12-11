package com.example.school_system

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.school_system.ui.theme.SchoolsystemTheme

class ProfileActivityStu : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SchoolsystemTheme {
                ProfileScreen(
                    onBack = { finish() },
                    onEditProfile = {
                        // 👉 Navigate to edit profile screen
                        // startActivity(Intent(this, EditProfileActivity::class.java))
                    }
                )
            }
        }
    }
}

/* ---------------------------------------------------------- */
/*                      MAIN PROFILE SCREEN                   */
/* ---------------------------------------------------------- */

@Composable
fun ProfileScreen(
    onBack: () -> Unit,
    onEditProfile: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {

        /* -------- Top Bar -------- */
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .size(26.dp)
                    .clickable { onBack() }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        /* -------- Profile Header -------- */
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {

            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text("San Wixksal", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("@sanwixksal", fontSize = 14.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onEditProfile,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .height(42.dp)
                    .width(140.dp)
            ) {
                Text("Edit Profile", color = Color.White, fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        /* -------- Divider -------- */
        DividerLine()

        Spacer(modifier = Modifier.height(12.dp))

        /* -------- Menu Items -------- */
        ProfileMenuItem(
            title = "My info",
            icon = R.drawable.ic_info,
            onClick = {}
        )

        ProfileMenuItem(
            title = "Setting",
            icon = R.drawable.ic_setting,
            onClick = {}
        )

        ProfileMenuItem(
            title = "Device",
            icon = R.drawable.ic_device,
            onClick = {}
        )

        Spacer(modifier = Modifier.height(8.dp))
        DividerLine()
        Spacer(modifier = Modifier.height(8.dp))

        ProfileMenuItem(
            title = "Help & Support",
            icon = R.drawable.ic_help,
            onClick = {}
        )

        ProfileMenuItem(
            title = "Log out",
            icon = R.drawable.ic_logout,
            textColor = Color.Red,
            iconTint = Color.Red,
            onClick = {
                // TODO: show logout dialog
            }
        )
    }
}

/* ---------------------------------------------------------- */
/*                      MENU ITEM COMPONENT                   */
/* ---------------------------------------------------------- */

@Composable
fun ProfileMenuItem(
    title: String,
    icon: Int,
    textColor: Color = Color.Black,
    iconTint: Color = Color.Black,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = painterResource(id = icon),
            contentDescription = title,
            tint = iconTint,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            title,
            fontSize = 16.sp,
            color = textColor,
            modifier = Modifier.weight(1f)
        )

        Icon(
            Icons.Default.ArrowForward,
            contentDescription = "Go",
            tint = Color.Black.copy(alpha = 0.6f)
        )
    }
}

/* ---------------------------------------------------------- */
/*                          DIVIDER                           */
/* ---------------------------------------------------------- */

@Composable
fun DividerLine() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.LightGray.copy(alpha = 0.5f))
    )
}
