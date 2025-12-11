package com.example.school_system.ui.screen

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.school_system.R
import com.example.school_system.ui.theme.SchoolsystemTheme

class ExamScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SchoolsystemTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ExamScreenContent()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamScreenContent() {
    val context = LocalContext.current
    var selectedExamType by remember { mutableStateOf("Final") }
    val examTypes = listOf("Midterm", "Final")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Exam",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        // ✅ ត្រឡប់ទៅទំព័រចាស់
                        (context as? Activity)?.finish()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back), // 🎯 PNG icon
                            contentDescription = "Back",
                            tint = Color.Unspecified // រក្សាពណ៌ដើមរបស់ PNG
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
                .padding(16.dp)
        ) {
            // Dropdown Menu
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 16.dp)
            ) {
                var expanded by remember { mutableStateOf(false) }

                OutlinedButton(
                    onClick = { expanded = true },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color(0xFF8B0000),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(selectedExamType)
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(Color.White)
                ) {
                    examTypes.forEach { type ->
                        DropdownMenuItem(
                            text = { Text(type) },
                            onClick = {
                                selectedExamType = type
                                expanded = false
                            }
                        )
                    }
                }
            }

            // Exam List
            val exams = listOf(
                ExamItem(R.drawable.kotlin_logo, "Final Exam Semester 1 : Create the mobileapp using Kotlin", "Oct 6"),
                ExamItem(R.drawable.flutter_logo, "Final Exam Semester 2 : Create the mobileapp using Flutter", "Oct 6"),
                ExamItem(R.drawable.kotlin_logo, "Final Exam Semester 1 : Create the mobileapp using JS", "Oct 6"),
                ExamItem(R.drawable.js_logo, "Final Exam Semester 1 : Create the mobileapp using Kotlin", "Oct 6"),
                ExamItem(R.drawable.laravel_logo, "Final Exam Semester 2 : Create the mobileapp using Laravel", "Oct 6")
            )

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                exams.forEach { exam ->
                    ExamCard(exam)
                }
            }
        }
    }
}

data class ExamItem(val imageRes: Int, val title: String, val date: String)

@Composable
fun ExamCard(exam: ExamItem) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF9F9F9))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = exam.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 12.dp)
            )
            Column {
                Text(exam.title, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text(exam.date, fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}
