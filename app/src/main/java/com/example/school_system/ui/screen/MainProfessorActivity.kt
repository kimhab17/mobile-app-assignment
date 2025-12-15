package com.example.school_system.ui.screen

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
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.school_system.*
import kotlinx.coroutines.launch
import com.example.school_system.viewmodel.ProfessorViewModel
import com.example.school_system.R

// Theme Colors
private val primaryColor = Color(0xFF8B0000)

class MainProfessorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val email = intent.getStringExtra("email") ?: ""

        setContent {
            MainProfessorScreen(
                email = email,
                onNavigate = { target ->
                    when (target) {
                        "Exam" -> startActivity(Intent(this, ExamScreen::class.java))
                        "Homework" -> startActivity(Intent(this, HomeworkActivity::class.java))
                        "Schedule" -> startActivity(Intent(this, ScheduleActivity::class.java))
                        "Attendance" -> startActivity(Intent(this, AttendanceActivity::class.java))
                    }
                }
            )
        }
    }
}

/* ------------------------------------------------------------- */
/*                     MAIN PROFESSOR SCREEN                     */
/* ------------------------------------------------------------- */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainProfessorScreen(
    email: String,
    onNavigate: (String) -> Unit
) {
    val vm = remember { ProfessorViewModel() }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val profile = vm.profile.value
    val loading = vm.loading.value
    val error = vm.error.value

    LaunchedEffect(Unit) { vm.loadProfile(email) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ProfessorDrawerMenu { item ->
                when (item) {
                    "Home" -> {}
                    "Exam" -> context.startActivity(Intent(context, ExamScreen::class.java))
                    "Attendance" -> context.startActivity(Intent(context, AttendanceActivity::class.java))
                    "Schedule" -> context.startActivity(Intent(context, ScheduleActivity::class.java))

                    "Setting" -> context.startActivity(
                        Intent(context, ProfileActivityTeacher::class.java)
                            .putExtra("email", profile?.email ?: "")
                    )

                    "Logout" -> {
                        val intent = Intent(context, ProfessorLoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        context.startActivity(intent)
                    }
                }
                scope.launch { drawerState.close() }
            }
        }
    ) {
        Scaffold(
            topBar = {
                ProfessorTopBar(
                    onMenuClick = { scope.launch { drawerState.open() } },
                    onProfileClick = {
                        context.startActivity(
                            Intent(context, ProfileActivityTeacher::class.java)
                                .putExtra("email", profile?.email ?: "")
                        )
                    }
                )
            }
        ) { padding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                when {
                    loading -> Text("កំពុងទាញទិន្នន័យ...")
                    error.isNotEmpty() -> Text("កំហុស: $error", color = Color.Red)
                    profile != null -> {
                        ProfessorWelcomeHeader(
                            name = profile.name ?: "",
                            email = profile.email ?: "",
                            role = profile.role ?: "",
                            onProfileClick = {
                                context.startActivity(
                                    Intent(context, ProfileActivityTeacher::class.java)
                                        .putExtra("email", profile.email ?: "")
                                )
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                DashboardGrid(onNavigate)
            }
        }
    }
}

/* ------------------------------------------------------------- */
/*                     WELCOME HEADER SECTION                    */
/* ------------------------------------------------------------- */

@Composable
fun ProfessorWelcomeHeader(
    name: String,
    email: String,
    role: String,
    onProfileClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        Text("Role: $role", fontSize = 16.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(12.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {

            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .clickable { onProfileClick() },
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text("ស្វាគមន៍!", fontSize = 17.sp, color = Color.Gray)
                Text(name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text(email, fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}

/* ------------------------------------------------------------- */
/*                            TOP BAR                              */
/* ------------------------------------------------------------- */

@Composable
fun ProfessorTopBar(
    onMenuClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(primaryColor)
            .padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_dashboard),
            contentDescription = "Menu",
            modifier = Modifier.size(28.dp).clickable { onMenuClick() }
        )

        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.drawable.ic_bell),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Image(
            painter = painterResource(id = R.drawable.ic_setting),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profile",
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .clickable { onProfileClick() },
            contentScale = ContentScale.Crop
        )
    }
}

/* ------------------------------------------------------------- */
/*                        DRAWER MENU                             */
/* ------------------------------------------------------------- */

@Composable
fun ProfessorDrawerMenu(onSelect: (String) -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(260.dp)
            .background(primaryColor)
            .padding(16.dp)
    ) {
        Text(
            "RUPP",
            color = Color.White,
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp, top = 40.dp)
        )

        DrawerButton(R.drawable.ic_home, "Home") { onSelect("Home") }
        DrawerButton(R.drawable.ic_exam, "Exam") { onSelect("Exam") }
        DrawerButton(R.drawable.ic_attendance, "Attendance") { onSelect("Attendance") }
        DrawerButton(R.drawable.ic_schedule, "Schedule") { onSelect("Schedule") }
        DrawerButton(R.drawable.ic_setting, "Setting") { onSelect("Setting") }
        DrawerButton(R.drawable.ic_logout, "Logout") { onSelect("Logout") }
    }
}

@Composable
fun DrawerButton(icon: Int, title: String, onClick: () -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .clip(RoundedCornerShape(40.dp))
            .background(Color.White.copy(alpha = 0.15f))
            .clickable { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = icon),
            contentDescription = title,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(title, fontSize = 17.sp, color = Color.White)
    }
}

/* ------------------------------------------------------------- */
/*                     DASHBOARD (PNG ICONS)                      */
/* ------------------------------------------------------------- */

@Composable
fun DashboardGrid(onNavigate: (String) -> Unit) {

    val items = listOf(
        DashboardItem("Exam", R.drawable.ic_exam) { onNavigate("Exam") },
        DashboardItem("Homework", R.drawable.ic_add) { onNavigate("Homework") },
        DashboardItem("Schedule", R.drawable.ic_calendar) { onNavigate("Schedule") },
        DashboardItem("Attendance", R.drawable.ic_attendance) { onNavigate("Attendance") }
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        itemsIndexed(items) { _, item ->
            DashboardCard(item)
        }
    }
}

@Composable
fun DashboardCard(item: DashboardItem) {

    Card(
        modifier = Modifier
            .height(170.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable { item.onClick() },
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = item.icon),
                contentDescription = null,
                modifier = Modifier.size(60.dp)
            )

            Text(
                item.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

data class DashboardItem(
    val title: String,
    val icon: Int,
    val onClick: () -> Unit
)
