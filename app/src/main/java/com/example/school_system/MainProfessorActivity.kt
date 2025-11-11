package com.example.school_system

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.school_system.ui.theme.SchoolsystemTheme
import androidx.compose.foundation.ExperimentalFoundationApi

// Colors
private val primaryColor: Color = Color(0xFF8B0000)
private val ExamCardColor = Color(0xFFD4E6F1)
private val HomeworkCardColor = Color(0xFF85C1E9)
private val ScheduleCardColor = Color(0xFFF7DC6F)
private val AttendanceCardColor = Color(0xFFE5E7E9)

@OptIn(ExperimentalMaterial3Api::class)
class MainProfessorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SchoolsystemTheme {
                // pass both navigation and profile-click callbacks
                MainProfessorScreen(
                    onNavigate = { target ->
                        when (target) {
                            "Exam" -> startActivity(Intent(this, ExamScreen::class.java))
                            "Homework" -> startActivity(Intent(this, HomeworkActivity::class.java))
                            "Schedule" -> startActivity(Intent(this, ScheduleActivity::class.java))
                            "Attendance" -> startActivity(Intent(this, AttendanceActivity::class.java))
                        }
                    },
                    onProfileClick = {
                        // open profile activity/screen
                        startActivity(Intent(this, ProfileActivity::class.java))
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainProfessorScreen(
    onNavigate: (String) -> Unit,
    onProfileClick: () -> Unit
) {
    Scaffold(
        topBar = { ProfessorTopBar(onProfileClick = onProfileClick) },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            ProfessorWelcomeHeader(onProfileClick = onProfileClick)
            Spacer(modifier = Modifier.height(24.dp))
            DashboardGrid(onNavigate = onNavigate)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessorTopBar(onProfileClick: () -> Unit) {
    // top bar using Row styled with primary color
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(primaryColor)
            .padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            Icons.Default.Menu,
            contentDescription = "Menu",
            tint = Color.White,
            modifier = Modifier.size(28.dp)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = Color.White)
            Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color.White)

            // profile image box (clickable)
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .clickable { onProfileClick() }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile), // ensure this drawable exists
                    contentDescription = "User Avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun ProfessorWelcomeHeader(onProfileClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            Text("Class M2 103", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Icon(Icons.Default.ArrowDropDown, contentDescription = "Change Class", tint = primaryColor)
            Text(
                text = "Change class",
                fontSize = 14.sp,
                color = primaryColor,
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
                    .clickable { onProfileClick() } // click avatar to profile
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "User Avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.clickable { onProfileClick() }) { // click name to profile
                Text("Welcome.", fontSize = 16.sp, color = Color.Gray)
                Text("Tea.Lamin Yamal", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("Mobile App", fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
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
        itemsIndexed(items) { _, item ->
            DashboardCard(item)
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
        modifier = Modifier
            .size(80.dp)
            .fillMaxWidth(),
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
        MainProfessorScreen(onNavigate = {}, onProfileClick = {})
    }
}
