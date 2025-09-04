package com.example.ecom.presentation.utils

import android.inputmethodservice.Keyboard
import android.media.Image
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun CustomTextField(
    value: String,
    onValueChange:(String) -> Unit,
    lable: String,
    modifier : Modifier = Modifier,
    singleLine:Boolean = true,
    leadingIcon: ImageVector? = null,//null safety
    visualTransformation: VisualTransformation = VisualTransformation.None,//?
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default



) {

    OutlinedTextField(
        value = value, onValueChange = onValueChange,
        label = { Text(text = lable) },
        modifier = modifier,
        singleLine = singleLine,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        leadingIcon = leadingIcon?.let {
            {Icon(imageVector = it, contentDescription = null)}
        }

    )


}