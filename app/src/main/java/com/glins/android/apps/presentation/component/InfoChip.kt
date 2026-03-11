package com.glins.android.apps.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.glins.android.apps.R

@Composable
fun InfoChip(
    @DrawableRes
    iconId: Int,
    text: String,
    modifier: Modifier = Modifier,
    iconTint: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    contentDescription: String? = null
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(50),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painterResource(iconId),
                tint = iconTint,
                contentDescription = contentDescription
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = text,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InfoChipPreview() {
    InfoChip(
        iconId = R.drawable.ic_star,
        text = "52439"
    )
}