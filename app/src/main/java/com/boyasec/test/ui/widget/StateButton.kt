package com.boyasec.test.ui.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

enum class ButtonState {
    Normal, Loading
}


/**
 * 带loading的button,click后有loading的效果
 *
 * @author Hey
 */
@Composable
fun StateButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(0.dp),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    state: MutableState<ButtonState>
) {
    Button(
        onClick = {
            state.value = if (state.value == ButtonState.Normal) {
                ButtonState.Loading
            } else {
                ButtonState.Normal
            }
            onClick.invoke()
        },
        modifier = modifier,
        enabled = enabled,
        interactionSource = interactionSource,
        elevation = elevation,
        border = border,
        colors = colors,
        contentPadding = contentPadding,
        shape = shape,
    ) {
        if (state.value == ButtonState.Normal) {
            Text(text = text)
        } else {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp, 24.dp), strokeWidth = 2.dp, color = Color.White
            )
        }
    }
}