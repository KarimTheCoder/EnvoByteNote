package com.example.envobytenote.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NotesFilterSegmentedButtons(
    onAllSelected: () -> Unit,
    onFavoriteSelected: () -> Unit
) {
    var selectedIndex by remember { mutableIntStateOf(0) }

    val options = listOf(
        OptionItem("All", onAllSelected),
        OptionItem("Favorite",  onFavoriteSelected)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp, top = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        SingleChoiceSegmentedButtonRow {
            options.forEachIndexed { index, option ->
                SegmentedButton(
                    selected = selectedIndex == index,
                    onClick = {
                        selectedIndex = index
                        option.onClick()
                    },
                    border = BorderStroke(0.5.dp, Color.LightGray),
                    shape = SegmentedButtonDefaults.itemShape(index, options.size),
                    modifier = Modifier.defaultMinSize(minWidth = 100.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        Text(
                            option.name,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }
    }
}

data class OptionItem(
    val name: String,
    val onClick: () -> Unit
)