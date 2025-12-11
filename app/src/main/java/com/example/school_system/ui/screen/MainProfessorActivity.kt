package com.example.school_system

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.school_system.ui.screen.AttendanceActivity
import com.example.school_system.ui.screen.ExamScreen
import com.example.school_system.ui.theme.SchoolsystemTheme
import kotlinx.coroutines.launch

// Colors
private val primaryColor = Color(0xFF8B0000)
private val ExamCardColor = Color(0xFFD4E6F1)
private val HomeworkCardColor = Color(0xFF85C1E9)
private val ScheduleCardColor = Color(0xFFF7DC6F)
private val AttendanceCardColor = Color(0xFFE5E7E9)

class MainProfessorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SchoolsystemTheme {
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
                        startActivity(Intent(this, ProfileActivity::class.java))
                    }
                )
            }
        }
    }
}

/* ------------------------------------------------------------- */
/*                      MAIN PROFESSOR SCREEN                    */
/* ------------------------------------------------------------- */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainProfessorScreen(
    onNavigate: (String) -> Unit,
    onProfileClick: () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val showLogoutDialog = remember { mutableStateOf(false) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ProfessorDrawerMenu(
                onSelect = { item ->
                    when (item) {
                        "Home" -> { /* already here */ }
                        "Exam" -> context.startActivity(Intent(context, ExamScreen::class.java))
                        "Attendance" -> context.startActivity(Intent(context, AttendanceActivity::class.java))
                        "Schedule" -> context.startActivity(Intent(context, ScheduleActivity::class.java))
                        "Setting" -> context.startActivity(Intent(context, ProfileActivity::class.java))
                        "Logout" -> {
                            showLogoutDialog.value = true
                            scope.launch { drawerState.close() }   // CLOSE DRAWER AUTOMATICALLY
                        }
                    }
                }
            )
        }
    ) {

        Scaffold(
            topBar = {
                ProfessorTopBar(
                    onMenuClick = { scope.launch { drawerState.open() } },
                    onProfileClick = onProfileClick
                )
            }
        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                ProfessorWelcomeHeader(onProfileClick = onProfileClick)
                Spacer(modifier = Modifier.height(24.dp))
                DashboardGrid(onNavigate)
            }
        }
        if (showLogoutDialog.value) {
            LogoutConfirmationDialog(
                onCancel = { showLogoutDialog.value = false },
                onConfirm = {
                    showLogoutDialog.value = false
                    // TODO: navigate to login screen
                    val intent = Intent(context, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(intent)
                }
            )
        }
    }
}

@Composable
fun LogoutConfirmationDialog(
    onCancel: () -> Unit,
    onConfirm: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x80000000)) // Dimmed background
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {

        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Red Warning Icon
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .background(Color.Red, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("!", color = Color.White, fontSize = 40.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    "Are you sure to logout?",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(30.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    // Cancel Button (Text)
                    Text(
                        text = "Cancel",
                        color = Color.Blue,
                        fontSize = 16.sp,
                        modifier = Modifier.clickable { onCancel() }
                    )

                    // Confirm button
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(primaryColor)
                            .clickable { onConfirm() }
                            .padding(vertical = 10.dp, horizontal = 24.dp)
                    ) {
                        Text("Yes", color = Color.White, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

/* ------------------------------------------------------------- */
/*                     CUSTOM PROFESSOR DRAWER                   */
/* ------------------------------------------------------------- */

@Composable
fun ProfessorDrawerMenu(onSelect: (String) -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(270.dp)
            .background(primaryColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        // Logo
        Text(
            text = "RUPP",
            color = Color.White,
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 20.dp, top = 40.dp)
        )

        DrawerButton(icon = R.drawable.ic_home, title = "Home") { onSelect("Home") }
        DrawerButton(icon = R.drawable.ic_exam, title = "Exam") { onSelect("Exam") }
        DrawerButton(icon = R.drawable.ic_attendance, title = "Attendance") { onSelect("Attendance") }
        DrawerButton(icon = R.drawable.ic_schedule, title = "Schedule") { onSelect("Schedule") }
        DrawerButton(icon = R.drawable.ic_setting, title = "Setting") { onSelect("Setting") }
        DrawerButton(icon = R.drawable.ic_logout, title = "Logout") { onSelect("Logout") }
    }
}

@Composable
fun DrawerButton(icon: Int, title: String, onClick: () -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .clip(RoundedCornerShape(50.dp))
            .background(Color(0xFFBDBDBD).copy(alpha = 0.4f))
            .clickable { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = painterResource(id = icon),
            contentDescription = title,
            tint = Color.White
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(text = title, fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Bold)

    }
}

/* ------------------------------------------------------------- */
/*                          TOP BAR                              */
/* ------------------------------------------------------------- */

@Composable
fun ProfessorTopBar(onMenuClick: () -> Unit, onProfileClick: () -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(primaryColor)
            .padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            Icons.Default.Menu,
            contentDescription = "Menu",
            tint = Color.White,
            modifier = Modifier
                .size(28.dp)
                .clickable { onMenuClick() }
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(Icons.Default.Notifications, contentDescription = null, tint = Color.White)
        Spacer(modifier = Modifier.width(12.dp))

        Icon(Icons.Default.Settings, contentDescription = null, tint = Color.White)

        Spacer(modifier = Modifier.width(12.dp))

        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .clickable { onProfileClick() }
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

/* ------------------------------------------------------------- */
/*                       WELCOME HEADER                          */
/* ------------------------------------------------------------- */

@Composable
fun ProfessorWelcomeHeader(onProfileClick: () -> Unit) {

    Column(modifier = Modifier.fillMaxWidth()) {

        Row(verticalAlignment = Alignment.CenterVertically) {

            Text("Class M2 103", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = primaryColor)

            Text(
                "Change class",
                color = primaryColor,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .clickable { /* TODO */ }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {

            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .clickable { onProfileClick() }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.clickable { onProfileClick() }) {
                Text("Welcome.", fontSize = 16.sp, color = Color.Gray)
                Text("Tea.Lamin Yamal", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("Mobile App", fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}

/* ------------------------------------------------------------- */
/*                        DASHBOARD GRID                         */
/* ------------------------------------------------------------- */

@Composable
fun DashboardGrid(onNavigate: (String) -> Unit) {

    val items = listOf(
        DashboardItem("Exam", ExamCardColor, { DashboardIcon("🧾") }) { onNavigate("Exam") },
        DashboardItem("Homework", HomeworkCardColor, { DashboardIcon("📚") }) { onNavigate("Homework") },
        DashboardItem("Schedule", ScheduleCardColor, { DashboardIcon("📅") }) { onNavigate("Schedule") },
        DashboardItem("Attendance", AttendanceCardColor, { DashboardIcon("📝") }) { onNavigate("Attendance") }
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(items) { _, item -> DashboardCard(item) }
    }
}

@Composable
fun DashboardIcon(emoji: String) {
    Box(
        modifier = Modifier.size(80.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = emoji, fontSize = 40.sp)
    }
}

@Composable
fun DashboardCard(item: DashboardItem) {

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(item.color),
        modifier = Modifier
            .height(180.dp)
            .fillMaxWidth()
            .clickable { item.onClick() }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            item.iconContent()
            Text(item.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

data class DashboardItem(
    val title: String,
    val color: Color,
    val iconContent: @Composable () -> Unit,
    val onClick: () -> Unit
)
