package com.example.school_system

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class AttendanceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AttendanceScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceScreen() {
    val context = LocalContext.current  // ✅ moved here instead of null

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Student Attendance", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = {
                        // ✅ Go back to previous screen
                        (context as? Activity)?.finish()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back), // 🖼 PNG icon
                            contentDescription = "Back",
                            tint = Color.Unspecified // Keep original PNG color
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // 🔹 Date & Class Filters
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("06-Oct-2025")
                }

                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Class M2-103B")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 🔹 Student List
            val students = listOf(
                Student("Andy Raj Kapoor", "07:30 am", "11:45 am", "P"),
                Student("Alex Martin", "07:30 am", "11:45 am", "A"),
                Student("San Visal", "07:30 am", "11:45 am", "P"),
                Student("Seyha Channun", "07:30 am", "11:45 am", "A"),
                Student("Suy Mengseang", "07:30 am", "11:45 am", "P"),
                Student("Sok Kimbab", "07:30 am", "11:45 am", "P")
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(students.size) { index ->
                    val student = students[index]
                    StudentRow(student)
                }
            }
        }
    }
}

data class Student(
    val name: String,
    val timeIn: String,
    val timeOut: String,
    val status: String
)

@Composable
fun StudentRow(student: Student) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF4F4F4), RoundedCornerShape(12.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.rupp_logo),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(student.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("in : ${student.timeIn}   out : ${student.timeOut}", fontSize = 13.sp)
            }
        }

        // 🔹 Attendance Status Box
        val color = if (student.status == "A") Color(0xFF4CAF50) else Color(0xFFD0B3FF)
        Box(
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(color),
            contentAlignment = Alignment.Center
        ) {
            Text(student.status, color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}
