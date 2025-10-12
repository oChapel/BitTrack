package com.example.bittrack.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.bittrack.R
import com.example.bittrack.ui.theme.BitTrackTheme
import com.example.bittrack.ui.theme.LocalSpacing
import com.example.bittrack.ui.theme.Success

@Composable
fun BaseButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    backgroundColor: Color = Success,
    borderColor: Color = Success,
    @DrawableRes iconRes: Int? = null,
    iconSize: Dp = 20.dp,
    iconTint: Color = Color.Black,
    text: String,
    textColor: Color = Color.Black,
    onClick: () -> Unit
) {
    val iconPainter = iconRes?.let { painterResource(id = it) }
    val spacing = LocalSpacing.current

    Button(
        modifier = modifier,
        enabled = enabled,
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors().copy(containerColor = backgroundColor),
        border = BorderStroke(width = 0.5.dp, color = borderColor),
        onClick = { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            iconPainter?.let {
                Icon(
                    modifier = Modifier
                        .size(iconSize)
                        .padding(spacing.xs.dp),
                    painter = it,
                    contentDescription = null,
                    tint = iconTint
                )
            }
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = textColor
            )
        }
    }
}

@Preview
@Composable
fun BaseButtonPreview() {
    BitTrackTheme {
        BaseButton(
            backgroundColor = MaterialTheme.colorScheme.secondary,
            iconRes = R.drawable.add_icon,
            iconSize = 24.dp,
            iconTint = Success,
            text = "Add Transaction",
            textColor = MaterialTheme.colorScheme.onSurface,
        ) {}
    }
}