package com.example.school_system.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.school_system.R
import com.example.school_system.ui.theme.SchoolsystemTheme

class CalendarActivityStu : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SchoolsystemTheme {
                CalendarScreen(
//                    onBack = { finish() }
                )
            }
        }
    }
}


@Composable
fun CalendarScreenStu(onBack: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        /* ------------------- Top Bar ------------------- */
        CalendarTopBarStu(onBack)

        Spacer(modifier = Modifier.height(10.dp))

        /* ------------------- Month Navigation ------------------- */
        MonthHeader()

        Spacer(modifier = Modifier.height(16.dp))

        /* ------------------- Calendar Grid ------------------- */
        CalendarStaticGrid()

        Spacer(modifier = Modifier.height(20.dp))

        /* ------------------- Event Cards ------------------- */
        EventCard(
            title = "Festival & Holidays",
            count = "01",
            borderColor = Color(0xFF00A651),
            dotColor = Color(0xFF00A651)
        )

        Spacer(modifier = Modifier.height(12.dp))

        EventCard(
            title = "Absent",
            count = "02",
            borderColor = Color(0xFFB00020),
            dotColor = Color(0xFFB00020)
        )
    }
}

/* ================= TOP BAR ================= */

@Composable
fun CalendarTopBarStu(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF8B0000))
            .padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = Color.White,
            modifier = Modifier
                .size(28.dp)
                .clickable { onBack() }
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(id = R.drawable.ic_bell),
            contentDescription = "Notifications",
            tint = Color.White,
            modifier = Modifier.size(26.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Icon(
            painter = painterResource(id = R.drawable.ic_setting),
            contentDescription = "Settings",
            tint = Color.White,
            modifier = Modifier.size(26.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profile",
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
        )
    }
}

/* ================= MONTH HEADER ================= */

@Composable
fun MonthHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.Black)

        Text(
            text = "OCTOBER 2025",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Icon(Icons.Default.ArrowForward, contentDescription = null, tint = Color.Black)
    }
}

/* ================= CALENDAR GRID ================= */

@Composable
fun CalendarStaticGrid() {

    val daysOfWeek = listOf("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su")

    val rows = listOf(
        listOf("", "", "30", "1", "2", "3", "4"),
        listOf("5", "6", "7", "8", "9", "10", "11"),
        listOf("12", "13", "14", "15", "16", "17", "18"),
        listOf("19", "20", "21", "22", "23", "24", "25"),
        listOf("26", "27", "28", "29", "30", "31", "1")
    )

    // special colors
    val redDays = setOf("8", "23")
    val greenDays = setOf("20")
    val sundayDays = setOf("4", "11", "18", "25", "1")

    Column(modifier = Modifier.fillMaxWidth()) {

        /* Week Header */
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            daysOfWeek.forEach {
                Text(it, color = Color.Gray, fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        /* Calendar Rows */
        rows.forEach { week ->

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                week.forEach { day ->

                    if (day.isBlank()) {
                        Box(modifier = Modifier.size(40.dp)) {}
                    } else {

                        val bgColor =
                            when {
                                redDays.contains(day) -> Color(0xFF8B0000)
                                greenDays.contains(day) -> Color(0xFF00A651)
                                sundayDays.contains(day) -> Color(0xFFE3ECFF)
                                else -> Color.Transparent
                            }

                        val textColor =
                            when {
                                redDays.contains(day) -> Color.White
                                greenDays.contains(day) -> Color.White
                                sundayDays.contains(day) -> Color(0xFF3366CC)
                                else -> Color.Black
                            }

                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(bgColor),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(day, color = textColor, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}

/* ================= EVENT CARD ================= */

@Composable
fun EventCard(title: String, count: String, borderColor: Color, dotColor: Color) {
    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .border(2.dp, borderColor, RoundedCornerShape(16.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .height(40.dp)
                .width(10.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(borderColor)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(title, fontSize = 16.sp, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(dotColor.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Text(count, color = dotColor, fontWeight = FontWeight.Bold)
        }
    }
}
