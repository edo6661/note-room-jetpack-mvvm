package com.example.notes.presentation.bookmark

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.notes.common.ScreenViewState
import com.example.notes.data.local.model.Note
import com.example.notes.ui.shared.CircularCenteredProgressIndicator
import com.example.notes.ui.shared.ErrorText
import com.example.notes.ui.shared.note.NoteCard

@Composable
fun BookmarkScreen(
  state : BookmarkState,
  onBookmarkChange : (Note) -> Unit,
  onDeleteNote : (Long) -> Unit,
  onNoteClicked : (Long) -> Unit
) {
  when (state.notes) {
    is ScreenViewState.Loading -> {
      CircularCenteredProgressIndicator()
    }

    is ScreenViewState.Success -> {
      val notes = state.notes.data
      LazyColumn(
        contentPadding = PaddingValues(8.dp),
      ) {
        itemsIndexed(notes) { i, note ->
          NoteCard(
            note = note,
            onBookmarkChange = onBookmarkChange,
            onDeleteNote = onDeleteNote,
            onNoteClick = onNoteClicked,
            i = i
          )
        }

      }
    }

    is ScreenViewState.Error   -> {
      val msg = state.notes.message ?: "An unexpected error occurred"
      ErrorText(msg = msg)
    }
  }

}
