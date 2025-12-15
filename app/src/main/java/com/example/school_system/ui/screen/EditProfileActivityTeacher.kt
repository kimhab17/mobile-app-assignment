package com.example.school_system.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.school_system.viewmodel.ProfileViewModelTeacher

class EditProfileActivityTeacher : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val email = intent.getStringExtra("email") ?: ""

        setContent {
            EditTeacherProfileScreen(email = email, onBack = { finish() })
        }
    }
}

@Composable
fun EditTeacherProfileScreen(email: String, onBack: () -> Unit) {

    val vm = remember { ProfileViewModelTeacher() }
    val teacher = vm.profile.value
    val loading = vm.loading.value
    val msg = vm.message.value

    LaunchedEffect(Unit) { vm.loadProfile(email) }

    if (loading) {
        Box(Modifier.fillMaxSize(), Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    teacher?.let {

        var name by remember { mutableStateOf(it.name ?: "") }
        var age by remember { mutableStateOf(it.age?.toString() ?: "") }

        Column(Modifier.padding(20.dp)) {

            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier.size(26.dp).clickable { onBack() }
            )

            Spacer(Modifier.height(20.dp))
            Text("Edit Teacher Profile", fontSize = 24.sp)

            Spacer(Modifier.height(20.dp))

            Text("Name")
            BasicTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth().height(50.dp)
                    .background(Color(0xFFEFEFEF)).padding(10.dp)
            )

            Spacer(Modifier.height(15.dp))

            Text("Age")
            BasicTextField(
                value = age,
                onValueChange = { age = it },
                modifier = Modifier.fillMaxWidth().height(50.dp)
                    .background(Color(0xFFEFEFEF)).padding(10.dp)
            )

            Spacer(Modifier.height(25.dp))

            Button(
                onClick = {
                    vm.updateProfile(
                        it.copy(
                            name = name,
                            age = age.toIntOrNull()
                        )
                    ) { onBack() }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Changes")
            }

            if (msg.isNotEmpty()) {
                Text(msg, color = Color.Green, modifier = Modifier.padding(top = 18.dp))
            }
        }
    }
}
