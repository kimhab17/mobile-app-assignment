package com.example.school_system.ui.screen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.school_system.R
import com.example.school_system.utils.uriToFile
import com.example.school_system.viewmodel.ProfileViewModelTeacher

class ProfileActivityTeacher : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val email = intent.getStringExtra("email") ?: ""

        setContent {
            ProfileScreenTeacher(
                email = email,
                onBack = { finish() },
                onEditProfile = {
                    startActivity(
                        Intent(this, EditProfileActivityTeacher::class.java)
                            .putExtra("email", email)
                    )
                }
            )
        }
    }
}

@Composable
fun ProfileScreenTeacher(
    email: String,
    onBack: () -> Unit,
    onEditProfile: () -> Unit
) {
    val vm = remember { ProfileViewModelTeacher() }
    val profile = vm.profile.value
    val loading = vm.loading.value
    val msg = vm.message.value

    val selectedImage = remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            selectedImage.value = uri.toString()
            val file = uriToFile(uri, context)
            vm.uploadProfileImage(file, email)
        }
    }

    LaunchedEffect(Unit) { vm.loadProfile(email) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.size(26.dp).clickable { onBack() }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (loading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return
        }

        profile?.let { teacher ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                        .clickable { imagePicker.launch("image/*") }
                ) {
                    if (selectedImage.value != null) {
                        AsyncImage(
                            model = selectedImage.value,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.profile),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(teacher.name ?: "", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text(teacher.email ?: "", fontSize = 15.sp, color = Color.Gray)
                Text("Role: Teacher", fontSize = 15.sp, color = Color.Gray)

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onEditProfile,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.height(45.dp).width(180.dp)
                ) {
                    Text("Edit Profile", color = Color.White, fontSize = 15.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Divider()
            Spacer(modifier = Modifier.height(12.dp))

            ProfileMenuTeacher("Teacher Info", R.drawable.ic_info) {}
            ProfileMenuTeacher("Settings", R.drawable.ic_setting) {}
            ProfileMenuTeacher("Device", R.drawable.ic_device) {}

            Spacer(modifier = Modifier.height(8.dp))
            Divider()
            Spacer(modifier = Modifier.height(8.dp))

            ProfileMenuTeacher("Help & Support", R.drawable.ic_help) {}
            ProfileMenuTeacher("Logout", R.drawable.ic_logout, Color.Red, Color.Red) {}

            if (msg.isNotEmpty()) {
                Text(msg, color = Color.Green, modifier = Modifier.padding(top = 18.dp))
            }
        }
    }
}

@Composable
fun ProfileMenuTeacher(
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
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painterResource(icon),
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.width(16.dp))
        Text(title, fontSize = 17.sp, color = textColor, modifier = Modifier.weight(1f))
        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
    }
}
