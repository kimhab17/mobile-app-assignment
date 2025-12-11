package com.example.school_system.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.school_system.data.model.User
import com.example.school_system.viewmodel.UserViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TestApiScreen(
    viewModel: UserViewModel = viewModel()
) {

    Column(Modifier.padding(20.dp)) {

        Button(onClick = {
            viewModel.createUser(
                User(
                    name = "Visal",
                    email = "Visal@gmail.com",
                    role = "Student",
                    age = 20
                )
            )
        }) {
            Text("Create User")
        }

        Spacer(Modifier.height(20.dp))

        Button(onClick = {
            viewModel.loadUsers()
        }) {
            Text("Load Users")
        }

        Spacer(Modifier.height(20.dp))

        Text("Message: ${viewModel.message.value}")

        Spacer(Modifier.height(10.dp))

        viewModel.users.value.forEach { u ->
            Text("${u.name} - ${u.email} - ${u.role} - ${u.age}")
        }
    }
}
