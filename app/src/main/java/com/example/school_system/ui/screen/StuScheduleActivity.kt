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

class StuScheduleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SchoolsystemTheme {
                ScheduleScreen { finish() }
            }
        }
    }
}

@Composable
fun ScheduleScreen(onBack: () -> Unit) {
    Column {
        SimpleTopBar("Schedule", onBack)

        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(4) {
                ScheduleItem("08:00 - 09:30", "Mathematics")
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun ScheduleItem(time: String, subject: String) {
    Card {
        Row(modifier = Modifier.padding(16.dp)) {
            Text(time, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(16.dp))
            Text(subject)
        }
    }
}
