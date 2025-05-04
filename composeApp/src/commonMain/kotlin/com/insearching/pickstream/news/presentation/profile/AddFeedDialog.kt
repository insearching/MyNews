package com.insearching.pickstream.news.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun AddFeedDialog(
    onDismiss: () -> Unit,
    onConfirm: (ChannelUi) -> Unit
) {
    var channelUrl by remember { mutableStateOf("") }
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        val shape = MaterialTheme.shapes.medium
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clip(shape)
                .background(MaterialTheme.colorScheme.background)
                .padding(vertical = 16.dp, horizontal = 24.dp),
        ) {
            Text(
                text = "Add channel",
                style = MaterialTheme.typography.titleLarge
            )
            OutlinedTextField(
                value = channelUrl,
                onValueChange = { channelUrl = it },
                label = { Text("Channel URL") },
                singleLine = true
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(22.dp),
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = "Cancel",
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.clickable(onClick = onDismiss)
                )
                Text(
                    text = "OK",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable {
                        onConfirm(ChannelUi(channelUrl))
                    }
                )
            }
        }
    }
}
