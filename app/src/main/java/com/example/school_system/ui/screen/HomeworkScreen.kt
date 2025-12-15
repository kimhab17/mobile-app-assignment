package com.example.school_system

import android.os.Bundle
import android.app.Activity
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
class HomeworkActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeworkScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeworkScreen() {
    val context = LocalContext.current

    val homeworkList = listOf(
        "Homework: Create the mobile app using Kotlin",
        "Homework: Create the mobile app using Flutter",
        "Homework: Create the mobile app using JS",
        "Homework: Create the mobile app using Laravel",
        "Homework: Create the mobile app using PHP"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Homework",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        // ត្រឡប់ទៅទំព័រចាស់ (MainProfessorActivity ឬទំព័រមុន)
                        (context as? Activity)?.finish()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back), // 🖼 PNG icon
                            contentDescription = "Back",
                            tint = Color.Unspecified // រក្សាពណ៌ដើម PNG
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                // 🖼 Banner Image
                Image(
                    painter = painterResource(id = R.drawable.school_image),
                    contentDescription = "School Banner",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 🧧 New Homework Button
                Button(
                    onClick = {
                        // Example: open NewHomeworkActivity if exists
                        // context.startActivity(Intent(context, NewHomeworkActivity::class.java))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    // Optional Add icon
                    // Icon(
                    //     painter = painterResource(id = R.drawable.ic_add),
                    //     contentDescription = null,
                    //     tint = Color.White
                    // )
                    // Spacer(modifier = Modifier.width(8.dp))
                    Text("New Homework", color = Color.White, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 🗒 Homework List
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(homeworkList) { item ->
                        HomeworkCard(
                            title = item,
                            date = "Oct 6",
                            onClick = {
                                // ចូលទៅទំព័រ HomeworkStatusActivity
                                context.startActivity(
                                    Intent(context, HomeworkStatusActivity::class.java)
                                )
                            }
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun HomeworkCard(title: String, date: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5), RoundedCornerShape(12.dp))
            .padding(12.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Use PNG icon
        Image(
            painter = painterResource(id = R.drawable.ic_question_mark),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .padding(end = 10.dp)
        )
        Column {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(text = date, fontSize = 12.sp, color = Color.Gray)
        }
    }
}
