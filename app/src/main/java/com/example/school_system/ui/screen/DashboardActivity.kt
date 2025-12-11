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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.school_system.ProfileActivityStu
import com.example.school_system.R
import com.example.school_system.ScheduleActivityStu
import com.example.school_system.ui.theme.SchoolsystemTheme
import kotlinx.coroutines.launch
//import android.content.Intent
//import androidx.compose.ui.platform.LocalContext

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

/* -------------------------------------------------------------------------- */
/*                            MAIN DASHBOARD SCREEN                           */
/* -------------------------------------------------------------------------- */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen() {

    val assignments = remember {
        listOf(
            Assignment(1, "English", "12/03/2025", "Completed"),
            Assignment(2, "English", "12/03/2025", "Not Start"),
            Assignment(3, "English", "12/03/2025", "In Process")
        )
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerMenu { selected ->
                println("Menu selected: $selected")
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            item {
                DashboardTopBar(
                    onMenuClick = { scope.launch { drawerState.open() } }
                )
            }

            item { Spacer(modifier = Modifier.height(10.dp)) }
            item { AttendanceAndScheduleSection() }
            item { DashboardStatsSection() }
            item { AssignmentSection(assignments) }
        }
    }
}

/* -------------------------------------------------------------------------- */
/*                                 DRAWER MENU                                */
/* -------------------------------------------------------------------------- */

@Composable
fun DrawerMenu(onSelect: (String) -> Unit = {}) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(270.dp)
            .background(Color(0xFF8B0000))
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column {
            DrawerItem("Dashboard", R.drawable.ic_dashboard) {
                context.startActivity(Intent(context, DashboardActivity::class.java))
            }

//            DrawerItem("Teacher", R.drawable.ic_teacher) {
//                context.startActivity(Intent(context, TeacherActivity::class.java))
//            }

            DrawerItem("Calendar", R.drawable.ic_calendar) {
                context.startActivity(Intent(context, CalendarActivity::class.java))
            }

            DrawerItem("Schedule", R.drawable.ic_schedule) {
                context.startActivity(Intent(context, ScheduleActivityStu::class.java))
            }
        }

        DrawerItem("Log Out", R.drawable.ic_logout, isLogout = true) {
            // example logout
            Toast.makeText(context, "Logged Out", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun DrawerItem(
    title: String,
    icon: Int,
    isLogout: Boolean = false,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .clip(RoundedCornerShape(40.dp))
            .background(if (isLogout) Color(0xFFFDECEC) else Color.White)
            .clickable { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = painterResource(id = icon),
            contentDescription = title,
            tint = if (isLogout) Color.Red else Color.Black
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = if (isLogout) Color.Red else Color.Black
        )
    }
}

/* -------------------------------------------------------------------------- */
/*                                 TOP BAR                                    */
/* -------------------------------------------------------------------------- */

@Composable
fun DashboardTopBar(onMenuClick: () -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF8B0000))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            Icons.Default.Menu,
            contentDescription = "Menu",
            tint = Color.White,
            modifier = Modifier.clickable { onMenuClick() }
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            Icons.Default.Notifications,
            contentDescription = "Notifications",
            tint = Color.White
        )

        Spacer(modifier = Modifier.width(16.dp))
        val context = LocalContext.current
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profile",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .clickable {
                    // Navigate to ProfileActivityStu
                    context.startActivity(
                        Intent(context, ProfileActivityStu::class.java)
                    )
                }
        )
    }
}

/* -------------------------------------------------------------------------- */
/*                        ATTENDANCE + SCHEDULE SECTION                       */
/* -------------------------------------------------------------------------- */

@Composable
fun AttendanceAndScheduleSection() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        AttendanceCard(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.width(12.dp))
        ScheduleCard(modifier = Modifier.weight(1f))
    }
}

/* ------------------------------- Attendance Card -------------------------- */

@Composable
fun AttendanceCard(modifier: Modifier = Modifier) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Attendance", fontWeight = FontWeight.Bold, fontSize = 16.sp)

            Spacer(modifier = Modifier.height(15.dp))

            DonutChart(
                percentage = 0.80f,
                colorPresent = Color(0xFF8B87FF),
                colorAbsent = Color(0xFFFFC976)
            )

            Spacer(modifier = Modifier.height(15.dp))

            Row {
                FilterChip("Jun 2025")
                Spacer(modifier = Modifier.width(10.dp))
                FilterChip("Class M2")
            }
        }
    }
}

/* ------------------------------- Schedule Card ---------------------------- */

@Composable
fun ScheduleCard(modifier: Modifier = Modifier) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Monday", fontWeight = FontWeight.Bold)
                Text("< >", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text("7:30–9:00\nM.I.S", fontSize = 15.sp)

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp),
                thickness = 3.dp,
                color = Color.Red
            )

            Text("9:00–10:30\nW.S.A", fontSize = 15.sp)
        }
    }
}

/* -------------------------------------------------------------------------- */
/*                         DASHBOARD STATS ROWS                              */
/* -------------------------------------------------------------------------- */

@Composable
fun DashboardStatsSection() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        StatBox("80%", "Attendance", Modifier.weight(1f))
        Spacer(modifier = Modifier.width(12.dp))
        StatBox("258+", "Tasks Completed", Modifier.weight(1f))
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        StatBox("64%", "In Progress", Modifier.weight(1f))
        Spacer(modifier = Modifier.width(12.dp))
        StatBox("245", "Reward Points", Modifier.weight(1f))
    }
}

/* -------------------------------------------------------------------------- */
/*                         ASSIGNMENTS SECTION                               */
/* -------------------------------------------------------------------------- */

@Composable
fun AssignmentSection(assignments: List<Assignment>) {

    Column(modifier = Modifier.padding(12.dp)) {

        Text("Assignments", fontWeight = FontWeight.Bold, fontSize = 20.sp)

        Spacer(modifier = Modifier.height(12.dp))

        assignments.forEach {
            AssignmentItem(it)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun AssignmentItem(a: Assignment) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {

        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(a.no.toString(), modifier = Modifier.weight(0.1f))
            Text(a.subject, modifier = Modifier.weight(0.3f))
            Text(a.date, modifier = Modifier.weight(0.3f))
            StatusBadge(a.status, Modifier.weight(0.3f))
        }
    }
}

/* -------------------------------------------------------------------------- */
/*                                 UTILITIES                                  */
/* -------------------------------------------------------------------------- */

@Composable
fun StatusBadge(status: String, modifier: Modifier = Modifier) {

    val color = when (status) {
        "Completed" -> Color(0xFF89D18A)
        "Not Start" -> Color(0xFFFFBABA)
        "In Process" -> Color(0xFFB1C5FF)
        else -> Color.Gray
    }

    Box(
        modifier = modifier
            .background(color, RoundedCornerShape(20.dp))
            .padding(6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(status, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun DonutChart(percentage: Float, colorPresent: Color, colorAbsent: Color) {

    Canvas(modifier = Modifier.size(120.dp)) {

        val stroke = 18.dp.toPx()

        drawArc(
            color = colorAbsent,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(stroke)
        )

        drawArc(
            color = colorPresent,
            startAngle = -90f,
            sweepAngle = percentage * 360f,
            useCenter = false,
            style = Stroke(stroke)
        )
    }
}

@Composable
fun FilterChip(text: String) {

    Box(
        modifier = Modifier
            .background(Color(0xFFEFEFEF), RoundedCornerShape(16.dp))
            .padding(horizontal = 8.dp, vertical = 5.dp)
    ) {
        Text(text, fontSize = 12.sp)
    }
}

@Composable
fun StatBox(value: String, label: String, modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .background(Color.White, RoundedCornerShape(18.dp))
            .padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(value, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text(label, fontSize = 12.sp, color = Color.Gray)
    }
}

/* -------------------------------------------------------------------------- */
/*                                DATA MODEL                                 */
/* -------------------------------------------------------------------------- */

data class Assignment(
    val no: Int,
    val subject: String,
    val date: String,
    val status: String
)
