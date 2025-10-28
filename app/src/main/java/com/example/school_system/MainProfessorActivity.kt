package com.example.school_system

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
//import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.school_system.ui.theme.SchoolsystemTheme
//import kotlinx.coroutines.launch

val primary: Color = Color(0xFF8B0000)
val ExamCardColor = Color(0xFFD4E6F1)
val HomeworkCardColor = Color(0xFF85C1E9)
val ScheduleCardColor = Color(0xFFF7DC6F)
val AttendanceCardColor = Color(0xFFE5E7E9)

class MainProfessorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SchoolsystemTheme {
                MainProfessorScreen(onNavigate = { target ->
                    when (target) {
                        "Exam" -> startActivity(Intent(this, ExamScreen::class.java))
                        "Homework" -> startActivity(Intent(this, HomeworkActivity::class.java))
//                        "Schedule" -> startActivity(Intent(this, ScheduleScreen::class.java))
//                        "Attendance" -> startActivity(Intent(this, AttendanceScreen::class.java))
                    }
                })
            }
        }
    }
}

@Composable
fun MainProfessorScreen(onNavigate: (String) -> Unit) {
    Scaffold(
        topBar = { ProfessorTopBar() },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            ProfessorWelcomeHeader()
            Spacer(modifier = Modifier.height(24.dp))
            DashboardGrid(onNavigate)
        }
    }
}

@Composable
fun ProfessorTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(primary)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White, modifier = Modifier.size(28.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = Color.White)
            Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color.White)
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable { /* profile click */ }
            )
        }
    }
}

@Composable
fun ProfessorWelcomeHeader() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Class M2 103", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Icon(Icons.Default.ArrowDropDown, contentDescription = "Change Class", tint = primary)
            Text(
                text = "Change class",
                fontSize = 14.sp,
                color = primary,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .clickable { /* handle class change */ }
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(Color.LightGray)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("Welcome.", fontSize = 16.sp, color = Color.Gray)
                Text("Tea. Kdam Prai", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("Mobile App", fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun DashboardGrid(onNavigate: (String) -> Unit) {
    val items = listOf(
        DashboardItem("Exam", ExamCardColor, { ProfessorCardIconPlaceholder(IconType.Exam) }) { onNavigate("Exam") },
        DashboardItem("Homework", HomeworkCardColor, { ProfessorCardIconPlaceholder(IconType.Homework) }) { onNavigate("Homework") },
        DashboardItem("Schedule", ScheduleCardColor, { ProfessorCardIconPlaceholder(IconType.Schedule) }) { onNavigate("Schedule") },
        DashboardItem("Attendance", AttendanceCardColor, { ProfessorCardIconPlaceholder(IconType.Attendance) }) { onNavigate("Attendance") }
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(items.size) { index ->
            DashboardCard(items[index])
        }
    }
}

data class DashboardItem(
    val title: String,
    val color: Color,
    val iconContent: @Composable () -> Unit,
    val onClick: () -> Unit
)

enum class IconType { Exam, Homework, Schedule, Attendance }

@Composable
fun ProfessorCardIconPlaceholder(type: IconType) {
    Box(
        modifier = Modifier.size(80.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = when (type) {
                IconType.Exam -> "🧾"
                IconType.Homework -> "📚"
                IconType.Schedule -> "📅"
                IconType.Attendance -> "✅"
            },
            fontSize = 40.sp
        )
    }
}

@Composable
fun DashboardCard(item: DashboardItem) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = item.color),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .height(180.dp)
            .fillMaxWidth()
            .clickable(onClick = item.onClick)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            item.iconContent()
            Text(item.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainProfessorScreenPreview() {
    SchoolsystemTheme {
        MainProfessorScreen(onNavigate = {})
    }
}
