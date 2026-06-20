package com.example.salvadorhome.features.auth.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RoleOption(
    icon: Int,
    title: String,
    description: String,
    isSelected: Boolean,
    onClick: () -> Unit
){
    val TextColor = Color(0xFF0A1128)
    val CheckedColor = Color(0xFF3357CC)

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp)
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = title,
            modifier = Modifier.size(60.dp)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp)
        ) {
            Text(text = title, fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
            Text(text = description, fontSize = 14.sp)
        }
        Checkbox(
            checked = isSelected,
            onCheckedChange = { onClick() },
            colors = CheckboxDefaults.colors(
                uncheckedColor = TextColor,
                checkedColor = CheckedColor
            ),
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}