package com.myscript.iink.demo.compose.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CircleOutlinedIconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String = "",
    onClick: () -> Unit = {},
) {
    IconButton(
        modifier = modifier
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                shape = RoundedCornerShape(50)
            ),
        onClick = {
            onClick()
        },
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CircleOutlinedIconButtonPreview() {
    CircleOutlinedIconButton(
        imageVector = Icons.Default.Create,
        contentDescription = "Create",
        onClick = {},
    )
}