package com.boyasec.test.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * 自定义的AppBar,包含导航键和标题
 *
 * @author Hey
 */
@Composable
fun AppBar(
    title: String,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    titleColor: Color = MaterialTheme.colorScheme.onPrimary,
    navigationIcon: (@Composable () -> Unit)? = null,
    navigationClick: (() -> Unit)? = null,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .height(50.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = title,
            color = titleColor,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        navigationIcon?.let {
            IconButton(onClick = { navigationClick?.invoke() }) {
                it.invoke()
            }
        }
    }
}