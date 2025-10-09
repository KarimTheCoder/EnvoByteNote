package com.example.envobytenote.ui.screens.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.envobytenote.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    // Local TextFieldValue to preserve cursor
    var textFieldValue by remember { mutableStateOf(TextFieldValue(searchQuery)) }

    TopAppBar(
        title = {
            TextField(
                value = textFieldValue,
                onValueChange = {
                    textFieldValue = it
                    onSearchChange(it.text)
                },
                placeholder = { Text("Search EnvoByte notes") },
                singleLine = true,
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                trailingIcon = {
                    IconButton(onClick = onToggleTheme) {
                        val iconRes = if (isDarkTheme)
                            R.drawable.baseline_light_mode_24
                        else
                            R.drawable.baseline_dark_mode_24

                        Icon(
                            painter = painterResource(id = iconRes),
                            contentDescription = "Toggle Day/Night Mode"
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(24.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textStyle = TextStyle(fontSize = MaterialTheme.typography.bodyMedium.fontSize)
            )
        }
    )

    // Keep TextFieldValue in sync when external searchQuery changes
    LaunchedEffect(searchQuery) {
        if (searchQuery != textFieldValue.text) {
            textFieldValue = textFieldValue.copy(text = searchQuery)
        }
    }
}