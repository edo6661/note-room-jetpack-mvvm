package com.example.notes.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notes.common.ScreenViewState
import com.example.notes.data.local.model.Note
import com.example.notes.ui.shared.CircularCenteredProgressIndicator
import com.example.notes.ui.shared.ErrorText
import com.example.notes.ui.shared.note.NoteCard
import java.util.Date

@Composable
fun HomeScreen(
  modifier : Modifier = Modifier,
  state : HomeState,
  onBookmarkChange : (Note) -> Unit,
  onDeleteNote : (Long) -> Unit,
  onNoteClick : (Long) -> Unit,
) {

  when (state.notes) {
    is ScreenViewState.Loading -> {
      CircularCenteredProgressIndicator()
    }

    is ScreenViewState.Success -> {
      val notes = state.notes.data
      Home(
        notes = notes,
        modifier = modifier,
        onBookmarkChange = onBookmarkChange,
        onDeleteNote = onDeleteNote,
        onNoteClick = onNoteClick
      )
    }

    is ScreenViewState.Error   -> {
      val msg = state.notes.message ?: "An unexpected error occurred"

      ErrorText(msg = msg)
    }

  }

}

@Composable
fun Home(
  notes : List<Note>,
  modifier : Modifier,
  onBookmarkChange : (Note) -> Unit,
  onDeleteNote : (Long) -> Unit,
  onNoteClick : (Long) -> Unit
) {

  LazyVerticalStaggeredGrid(
    columns = StaggeredGridCells.Fixed(2),
    contentPadding = PaddingValues(
      start = 8.dp,
      end = 8.dp,
      top = 8.dp,
      // avoiding the bottom navigation bar
      bottom = 56.dp
    ),
    modifier = modifier.padding(
      WindowInsets.navigationBars.asPaddingValues()
    )
  ) {
    itemsIndexed(notes) { i, note ->
      NoteCard(
        i = i,
        note = note,
        onBookmarkChange = onBookmarkChange,
        onDeleteNote = onDeleteNote,
        onNoteClick = onNoteClick
      )
    }
  }
}


@Composable
@Preview(showSystemUi = true)
fun PrevHome() {
  val notes = (0..10).map {
    Note(
      id = it.toLong(),
      title = "Title $it",
      content = "Content $it",
      isBookMarked = it % 2 == 0,
      createdDate = Date(
        System.currentTimeMillis()
      )
    )
  }
  HomeScreen(
    state = HomeState(
      notes = ScreenViewState.Success(
        notes
      )
    ),
    onBookmarkChange = {},
    onDeleteNote = {},
    onNoteClick = {}
  )
}