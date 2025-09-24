package com.lopeztecnology.taraea1ap2.ui.theme.jugador

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class UiComponents {
    companion object {
        @Composable
        fun AnimatedButton(
            text: String,
            normalColor: Color,
            pressedColor: Color,
            onClick: () -> Unit,
            textColor: Color
        ) {
            var pressed by remember { mutableStateOf(false) }
            val backgroundColor by animateColorAsState(if (pressed) pressedColor else normalColor)

            Button(
                onClick = {
                    pressed = true
                    onClick()
                    pressed = false
                },
                colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = text,
                    color = textColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }

        @Composable
        fun Spacing(height: Int) {
            Spacer(modifier = Modifier.height(height.dp))
        }
    }
}
