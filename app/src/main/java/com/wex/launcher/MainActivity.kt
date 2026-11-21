package com.wex.launcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wex.launcher.ui.WexColors
import com.wex.launcher.ui.WexTheme
import com.wex.launcher.versions.McVersion
import com.wex.launcher.versions.VersionManifest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { WexTheme { HomeScreen() } }
    }
}

@Composable
fun HomeScreen() {
    val scope = rememberCoroutineScope()
    var status by remember { mutableStateOf("Нажмите PLAY, чтобы загрузить список версий") }
    var latest by remember { mutableStateOf("—") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(WexColors.Background, Color.Black)))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("WEX", color = WexColors.Cyan, fontSize = 36.sp, fontWeight = FontWeight.Black)
        Text("LAUNCHER", color = WexColors.Purple, fontSize = 14.sp, fontWeight = FontWeight.Bold)

        Column(
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp))
                .background(WexColors.GlassFill).padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text("Minecraft Java", color = WexColors.TextMuted, fontSize = 13.sp)
            Text(latest, color = WexColors.TextPrimary, fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
            Text(status, color = WexColors.TextMuted, fontSize = 13.sp)
        }

        Button(
            onClick = {
                scope.launch {
                    status = "Загрузка манифеста…"
                    try {
                        val releases: List<McVersion> = withContext(Dispatchers.IO) { VersionManifest.fetchReleases() }
                        latest = releases.firstOrNull()?.id ?: "—"
                        status = "Версий релиза: ${releases.size}. Запуск игры ещё не реализован."
                    } catch (e: Exception) {
                        status = "Ошибка загрузки манифеста: ${e.message}"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = WexColors.Purple)
        ) { Text("PLAY", fontWeight = FontWeight.Black, fontSize = 18.sp) }
    }
}
