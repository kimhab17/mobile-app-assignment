package com.example.school_system.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.school_system.ui.theme.SchoolsystemTheme

class StuHomeworkActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SchoolsystemTheme {
                HomeworkScreen { finish() }
            }
        }
    }
}

@Composable
fun HomeworkScreen(onBack: () -> Unit) {
    Column {
        SimpleTopBar("Homework", onBack)

        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(3) {
                HomeworkItem("English Essay", "Due: 25 Oct")
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun HomeworkItem(title: String, due: String) {
    Card {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontWeight = FontWeight.Bold)
            Text(due)
        }
    }
}
