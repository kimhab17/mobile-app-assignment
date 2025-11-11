package com.example.school_system

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.school_system.ui.theme.SchoolsystemTheme

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val showToast: (String) -> Unit = { msg ->
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }

        setContent {
            SchoolsystemTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    ProfileScreen(
                        onBack = { finish() },
                        onEditProfile = { showToast("Edit Profile clicked") },
                        onMenuClick = { id -> showToast("Clicked: $id") },
                        onLogout = {
                            showToast("Logged out")
                            // Example navigate to LoginActivity:
                            // startActivity(Intent(this, LoginActivity::class.java))
                            // finish()
                        }
                    )
                }
            }
        }
    }
}

data class ProfileMenuItem(val id: String, val icon: Any, val title: String)

@SuppressLint("LocalContextResourcesRead", "DiscouragedApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onBack: () -> Unit,
    onEditProfile: () -> Unit,
    onMenuClick: (String) -> Unit,
    onLogout: () -> Unit
) {
    val context = LocalContext.current

    // menu items using standard icons (avoid AutoMirrored if not available in your Compose)
    val menuItems = remember {
        listOf(
            ProfileMenuItem("my_info", Icons.Outlined.Person, "My info"),
            ProfileMenuItem("setting", Icons.AutoMirrored.Filled.HelpOutline, "Setting"),
            ProfileMenuItem("change_password", Icons.AutoMirrored.Filled.HelpOutline, "Change Password"),
            ProfileMenuItem("device", Icons.AutoMirrored.Filled.HelpOutline, "Device")
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { /* keep empty to match design */ },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        // Check if drawable resource exists before calling painterResource
                        val resId = context.resources.getIdentifier("ic_back", "drawable", context.packageName)
                        if (resId != 0) {
                            Icon(
                                painter = painterResource(id = resId),
                                contentDescription = "Back",
                                tint = Color.Unspecified
                            )
                        } else {
                            // fallback to built-in arrow
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.Black
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                // Avatar - check drawable exists (will throw if missing)
                val avatarRes = context.resources.getIdentifier("rupp_logo", "drawable", context.packageName)
                val avatarPainter: Painter? = if (avatarRes != 0) painterResource(id = avatarRes) else null

                if (avatarPainter != null) {
                    Image(
                        painter = avatarPainter,
                        contentDescription = "Avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(86.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                    )
                } else {
                    // fallback: small empty circle
                    Box(
                        modifier = Modifier
                            .size(86.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Tea Kdam Prai", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = "@kdamkprai", color = Color.Gray, fontSize = 13.sp)

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onEditProfile,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .height(40.dp)
                        .width(160.dp)
                ) {
                    Icon(imageVector = Icons.Filled.Edit, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Edit Profile", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 1.dp)
            Spacer(modifier = Modifier.height(12.dp))

            Column {
                menuItems.forEach { item ->
                    ProfileMenuRow(
                        icon = item.icon,
                        title = item.title,
                        onClick = { onMenuClick(item.id) }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 1.dp)
                Spacer(modifier = Modifier.height(8.dp))

                ProfileMenuRow(
                    icon = Icons.AutoMirrored.Filled.HelpOutline,
                    title = "Help & Support",
                    onClick = { onMenuClick("help_support") }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onLogout() }
                        .padding(vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null, tint = Color(0xFF8B0000))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = "Log out", fontWeight = FontWeight.Medium, fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun ProfileMenuRow(icon: Any, title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        when (icon) {
            is androidx.compose.ui.graphics.vector.ImageVector -> {
                Icon(imageVector = icon, contentDescription = null, tint = Color(0xFF8B0000))
            }
            is Painter -> {
                Icon(painter = icon, contentDescription = null, tint = Color(0xFF8B0000))
            }
            else -> {
                Icon(imageVector = Icons.AutoMirrored.Filled.HelpOutline, contentDescription = null, tint = Color(0xFF8B0000))
            }
        }

        Spacer(modifier = Modifier.width(14.dp))
        Text(text = title, fontSize = 16.sp, modifier = Modifier.weight(1f))
        Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = null, tint = Color.Gray)
    }
}
