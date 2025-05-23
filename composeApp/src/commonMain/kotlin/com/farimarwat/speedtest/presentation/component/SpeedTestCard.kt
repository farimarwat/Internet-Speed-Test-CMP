package com.farimarwat.speedtest.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.farimarwat.speedtest.domain.model.SpeedTest
import com.farimarwat.speedtest.utils.to2DecimalString
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource
import speedtest.composeapp.generated.resources.Res
import speedtest.composeapp.generated.resources.arrow_down_circle
import speedtest.composeapp.generated.resources.arrow_up_circle
import speedtest.composeapp.generated.resources.ic_map_pin

@Composable
fun SpeedTestCard(
    test: SpeedTest,
    modifier: Modifier = Modifier,
    onMapClicked: (SpeedTest) -> Unit = {},
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Header with provider and server
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = test.providerName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Server: ${test.serverName}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Map button
                IconButton(
                    onClick = {
                        onMapClicked(test)
                    },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_map_pin),
                        contentDescription = "View on map",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Speed indicators
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SpeedIndicator(
                    value = test.downSpeed,
                    type = "Download",
                    icon = painterResource(Res.drawable.arrow_down_circle),
                    unit = "Mbps"
                )

                SpeedIndicator(
                    value = test.upSpeed,
                    type = "Upload",
                    icon = painterResource(Res.drawable.arrow_up_circle),
                    unit = "Mbps"
                )
            }

            // Timestamp
            Text(
                text = "Tested at ${test.performedAt.toFormattedString()}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Composable
private fun SpeedIndicator(
    value: Double,
    type: String,
    icon: Painter,
    unit: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = type,
                style = MaterialTheme.typography.labelMedium
            )
        }
        Text(
            text = "${value.toFloat().to2DecimalString()} ${unit}",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
    }
}
// In commonMain/kotlin/utils/DateTimeExtensions.kt
fun Instant.toFormattedString(): String {
    val localDateTime = this.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${localDateTime.month.name.take(3)} ${localDateTime.dayOfMonth}, " +
            "${localDateTime.hour.toString().padStart(2, '0')}:" +
            "${localDateTime.minute.toString().padStart(2, '0')} " +
            if (localDateTime.hour < 12) "AM" else "PM"
}

// Alternative using multiplatform libraries (if you need more formatting options)
@OptIn(ExperimentalStdlibApi::class)
fun Instant.toFormattedStringAdvanced(): String {
    val localDateTime = this.toLocalDateTime(TimeZone.currentSystemDefault())
    return buildString {
        append(localDateTime.month.name.take(3))
        append(" ")
        append(localDateTime.dayOfMonth)
        append(", ")
        append(localDateTime.hour % 12)
        append(":")
        append(localDateTime.minute.toString().padStart(2, '0'))
        append(" ")
        append(if (localDateTime.hour < 12) "AM" else "PM")
    }
}


