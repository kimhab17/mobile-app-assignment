package com.example.school_system.ui.screen

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.school_system.R
import com.example.school_system.ui.theme.SchoolsystemTheme
import kotlinx.coroutines.launch

class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SchoolsystemTheme {
                DashboardScreen()
            }
        }
    }
}

/* ============================ MAIN SCREEN ============================ */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen() {

    val context = LocalContext.current
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val assignments = remember {
        listOf(
            Assignment(1, "English", "12/03/2025", "Completed"),
            Assignment(2, "Math", "15/03/2025", "Not Start"),
            Assignment(3, "Science", "18/03/2025", "In Process")
        )
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerMenu(
                onClose = { scope.launch { drawerState.close() } }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF6F6F6))
        ) {
            item {
                DashboardTopBar {
                    scope.launch { drawerState.open() }
                }
            }

            item { Spacer(modifier = Modifier.height(12.dp)) }
            item { DashboardQuickCards() }
            item { AttendanceAndScheduleSection() }
            item { AssignmentSection(assignments) }
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

/* ============================ DRAWER ============================ */

@Composable
fun DrawerMenu(onClose: () -> Unit) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(260.dp)
            .background(Color(0xFF8B0000))
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column {
            DrawerItem("Dashboard", Icons.Default.Dashboard) {
                onClose()
            }

            DrawerItem("Exams", Icons.Default.Event) {
                context.startActivity(Intent(context, StuExamActivity::class.java))
            }

            DrawerItem("Schedule", Icons.Default.Schedule) {
                context.startActivity(Intent(context, StuScheduleActivity::class.java))
            }

            DrawerItem("Homework", Icons.AutoMirrored.Filled.MenuBook) {
                context.startActivity(Intent(context, StuHomeworkActivity::class.java))
            }

            DrawerItem("Attendance", Icons.Default.CheckCircle) {
                context.startActivity(Intent(context, StuAttendanceActivity::class.java))
            }
        }

        DrawerItem(
            "Log Out",
            Icons.AutoMirrored.Filled.Logout,
            isLogout = true
        ) {
            Toast.makeText(context, "Logged Out", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun DrawerItem(
    title: String,
    icon: ImageVector,
    isLogout: Boolean = false,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(if (isLogout) Color(0xFFFDECEC) else Color.White)
            .clickable { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = title, tint = if (isLogout) Color.Red else Color.Black)
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, fontWeight = FontWeight.Bold, color = if (isLogout) Color.Red else Color.Black)
    }
}

/* ============================ TOP BAR ============================ */

@Composable
fun DashboardTopBar(onMenuClick: () -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF8B0000))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.Menu, null, tint = Color.White, modifier = Modifier.clickable { onMenuClick() })
        Spacer(modifier = Modifier.weight(1f))
        Icon(Icons.Default.Notifications, null, tint = Color.White)
        Spacer(modifier = Modifier.width(16.dp))
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = null,
            modifier = Modifier.size(36.dp).clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}

/* ============================ QUICK CARDS ============================ */

@Composable
fun DashboardQuickCards() {

    val context = LocalContext.current

    Column(modifier = Modifier.padding(12.dp)) {

        Text("Quick Access", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            QuickCard("Exams", "2 Upcoming", Icons.Default.Event, Color(0xFFB1C5FF), Modifier.weight(1f)) {
                context.startActivity(Intent(context, StuExamActivity::class.java))
            }
            QuickCard("Schedule", "Today", Icons.Default.Schedule, Color(0xFF89D18A), Modifier.weight(1f)) {
                context.startActivity(Intent(context, StuScheduleActivity::class.java))
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            QuickCard("Homework", "3 Pending", Icons.AutoMirrored.Filled.MenuBook, Color(0xFFFFC976), Modifier.weight(1f)) {
                context.startActivity(Intent(context, StuHomeworkActivity::class.java))
            }
            QuickCard("Attendance", "80%", Icons.Default.CheckCircle, Color(0xFFFFBABA), Modifier.weight(1f)) {
                context.startActivity(Intent(context, StuAttendanceActivity::class.java))
            }
        }
    }
}

@Composable
fun QuickCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    bgColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(120.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(bgColor)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(icon, null, modifier = Modifier.size(28.dp))
            Column {
                Text(title, fontWeight = FontWeight.Bold)
                Text(subtitle, fontSize = 12.sp)
            }
        }
    }
}

/* ============================ OTHER SECTIONS (UNCHANGED) ============================ */

@Composable fun AttendanceAndScheduleSection() {}
@Composable fun AssignmentSection(assignments: List<Assignment>) {}
@Composable fun DonutChart(percentage: Float, present: Color, absent: Color) {}
data class Assignment(val no: Int, val subject: String, val date: String, val status: String)
