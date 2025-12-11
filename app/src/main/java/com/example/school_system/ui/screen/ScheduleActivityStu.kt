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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.school_system.ui.theme.SchoolsystemTheme

class ScheduleActivityStu : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SchoolsystemTheme {
                ScheduleScreen(onBack = { finish() })
            }
        }
    }
}

/* --------------------------------------------------------- */
/*                        MAIN SCREEN                        */
/* --------------------------------------------------------- */

@Composable
fun ScheduleScreen(onBack: () -> Unit) {

    Column(modifier = Modifier.fillMaxSize()) {

        ScheduleTopBar(onBack)

        Spacer(modifier = Modifier.height(8.dp))

        val scheduleList = List(6) { // Static repeated item
            ScheduleItemData(
                day = "Monday",
                subject = "Windows Server Admin",
                teacher = "Dr. Ouk Khean",
                time = "9:00–10:30"
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(scheduleList) { item ->
                ScheduleCard(item)
            }
        }
    }
}

/* --------------------------------------------------------- */
/*                         TOP BAR                           */
/* --------------------------------------------------------- */

@Composable
fun ScheduleTopBar(onBack: () -> Unit) {

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
                .clip(CircleShape)
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

/* --------------------------------------------------------- */
/*                      SCHEDULE CARD                        */
/* --------------------------------------------------------- */

data class ScheduleItemData(
    val day: String,
    val subject: String,
    val teacher: String,
    val time: String
)

@Composable
fun ScheduleCard(item: ScheduleItemData) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Box(
                modifier = Modifier
                    .background(Color(0xFF8B0000), RoundedCornerShape(12.dp))
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Text(
                    text = item.day,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column {
                    Text(item.subject, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(item.teacher, fontSize = 14.sp, color = Color.Gray)
                }

                Text(
                    item.time,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Horizontal divider (Red Dot)
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(2.dp)
                        .background(Color(0xFF8B0000))
                )

                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF8B0000))
                )
            }
        }
    }
}
