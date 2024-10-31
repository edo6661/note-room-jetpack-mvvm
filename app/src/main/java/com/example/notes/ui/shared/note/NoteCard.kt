package com.example.notes.ui.shared.note

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.notes.data.local.model.Note

@Composable
fun NoteCard(
  i : Int,
  note : Note,
  onBookmarkChange : (Note) -> Unit,
  onDeleteNote : (Long) -> Unit,
  onNoteClick : (Long) -> Unit
) {

  val isEven = i % 2 == 0
  val shape = when {
    isEven -> RoundedCornerShape(
      topStart = 50f,
      bottomEnd = 50f
    )

    else   -> {
      RoundedCornerShape(
        topEnd = 50f,
        bottomStart = 50f
      )
    }
  }
  val icon = if (note.isBookMarked) Icons.Default.BookmarkRemove
  else Icons.Outlined.BookmarkAdd

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(8.dp),
    shape = shape,
    onClick = { onNoteClick(note.id) },
    elevation = CardDefaults.cardElevation(
      defaultElevation = 8.dp,
      pressedElevation = 16.dp,
    )

  ) {
    Column(
      modifier = Modifier.padding(8.dp)
    ) {
      Text(

        text = note.title,
        fontWeight = FontWeight.ExtraBold,
        maxLines = 1,
        style = MaterialTheme.typography.titleMedium
      )
      Spacer(modifier = Modifier.size(16.dp))
      Text(
        text = note.content,
        maxLines = 4,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.bodyMedium
      )
      Spacer(modifier = Modifier.size(4.dp))
      Text(
        text = note.createdDate.toString(),
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        overflow = TextOverflow.Ellipsis,

        )
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(
          onClick = { onDeleteNote(note.id) }
        ) {
          Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete Note"
          )
        }
        IconButton(
          onClick = { onBookmarkChange(note) }
        ) {
          Icon(
            imageVector = icon,
            contentDescription = "Bookmark Note"
          )
        }
      }
    }
  }


}
