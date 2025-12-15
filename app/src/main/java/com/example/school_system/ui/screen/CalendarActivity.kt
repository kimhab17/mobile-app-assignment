package com.example.school_system.ui.screen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
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
import com.example.school_system.R
import com.example.school_system.ui.theme.SchoolsystemTheme
import kotlinx.coroutines.launch

class CalendarActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SchoolsystemTheme {
                CalendarScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen() {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            // ✅ FIX: reuse DrawerMenu WITHOUT onSelect
//            DrawerMenu(
//
//            )
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            CalendarTopBar(
                onMenuClick = { scope.launch { drawerState.open() } }
            )

            StaticCalendarUI()
        }
    }
}

/* ======================= TOP BAR ======================= */

@Composable
fun CalendarTopBar(onMenuClick: () -> Unit) {

    val context = LocalContext.current

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
            modifier = Modifier
                .size(28.dp)
                .clickable { onMenuClick() }
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            Icons.Default.Notifications,
            contentDescription = "Notifications",
            tint = Color.White
        )

        Spacer(modifier = Modifier.width(16.dp))

        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profile",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .clickable {
                    context.startActivity(
                        Intent(context, ProfileActivityStu::class.java)
                    )
                }
        )
    }
}

/* ======================= CALENDAR UI ======================= */

@Composable
fun StaticCalendarUI() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("<", fontSize = 20.sp)
            Text("OCTOBER 2025", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(">", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            listOf("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su").forEach {
                Text(it, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        val weekRows = listOf(
            listOf("", "", "", "1", "2", "3", "4"),
            listOf("5", "6", "7", "8", "9", "10", "11"),
            listOf("12", "13", "14", "15", "16", "17", "18"),
            listOf("19", "20", "21", "22", "23", "24", "25"),
            listOf("26", "27", "28", "29", "30", "31", "1")
        )

        weekRows.forEach { week ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                week.forEach { day ->
                    StaticCalendarDay(day)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))

        StaticCalendarEvents()
    }
}

/* ======================= DAY CELL ======================= */

@Composable
fun StaticCalendarDay(day: String) {

    val backgroundColor = when (day) {
        "20" -> Color(0xFF19C300)
        "8", "23" -> Color(0xFF8B0000)
        "4", "11", "18", "25" -> Color(0xFFE0EAFF)
        else -> Color.Transparent
    }

    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        if (day.isNotEmpty()) {
            Text(
                day,
                fontWeight = FontWeight.Bold,
                color = if (backgroundColor == Color.Transparent ||
                    backgroundColor == Color(0xFFE0EAFF)
                ) Color.Black else Color.White
            )
        }
    }
}

/* ======================= EVENTS ======================= */

@Composable
fun StaticCalendarEvents() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF00CC55))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Festival & Holidays", color = Color.White, fontWeight = FontWeight.Bold)
        Text("01", color = Color.White, fontWeight = FontWeight.Bold)
    }

    Spacer(modifier = Modifier.height(12.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFB20000))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Absent", color = Color.White, fontWeight = FontWeight.Bold)
        Text("02", color = Color.White, fontWeight = FontWeight.Bold)
    }
}
