package com.example.notes.ui.shared

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun NoteTextField(
  modifier : Modifier = Modifier,
  value : String,
  onValueChange : (String) -> Unit,
  label : String,
  labelAlignment : TextAlign? = null
) {
  OutlinedTextField(
    value = value,
    onValueChange = onValueChange,
    label = { Text(text = label, textAlign = labelAlignment) },
    modifier = modifier,
    colors = TextFieldDefaults.colors(

    ),
//    placeholder = {
//      Text(
//        text = "Insert $label",
//        textAlign = labelAlignment,
//        modifier = modifier
//          .padding(8.dp)
//          .fillMaxWidth()
//      )
//    },

  )
}