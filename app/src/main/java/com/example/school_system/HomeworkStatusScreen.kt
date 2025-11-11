package com.example.school_system

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.school_system.ui.theme.SchoolsystemTheme
import androidx.compose.ui.platform.LocalContext

data class StudentStatus(
    val id: String,
    val name: String,
    val status: String
)

@OptIn(ExperimentalMaterial3Api::class)
class HomeworkStatusActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SchoolsystemTheme {
                HomeworkStatusScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeworkStatusScreen() {
    val context = LocalContext.current
    val tabs = listOf("Submitted", "Completed", "Late")
    var selectedTab by remember { mutableIntStateOf(0) }

    val studentList = remember {
        listOf(
            StudentStatus("001", "Laim", "Submitted"),
            StudentStatus("002", "San Visal", "Submitted"),
            StudentStatus("003", "Sok Kimhab", "Submitted"),
            StudentStatus("004", "Oliver", "Submitted"),
            StudentStatus("005", "Seyha Channun", "Submitted"),
            StudentStatus("006", "James", "Submitted"),
            StudentStatus("007", "Suy Mengseang", "Submitted"),
            StudentStatus("008", "Henry", "Submitted"),
            StudentStatus("009", "Mithona", "Submitted")
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Homework Status", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = {
                        // when click, close this activity → return to previous page
                        (context as? Activity)?.finish()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back), // 🔙 your back icon
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
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
        ) {
            // 🔖 Tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                tabs.forEachIndexed { index, text ->
                    val selected = index == selectedTab
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .height(36.dp)
                            .background(
                                color = if (selected) Color(0xFF8B0000) else Color(0xFFF0F0F0),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { selectedTab = index }
                    ) {
                        Text(
                            text = text,
                            color = if (selected) Color.White else Color.Black,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            HorizontalDivider(color = Color.LightGray, thickness = 1.dp)

            // 🧑‍🎓 Student list
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                items(studentList) { student ->
                    HomeworkItemCard(student)
                }
            }
        }
    }
}

@Composable
fun HomeworkItemCard(student: StudentStatus) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = student.id,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.width(40.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = student.name,
                    fontSize = 16.sp
                )
            }
            Text(
                text = student.status,
                color = Color(0xFF8B0000),
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
        }
    }
}
