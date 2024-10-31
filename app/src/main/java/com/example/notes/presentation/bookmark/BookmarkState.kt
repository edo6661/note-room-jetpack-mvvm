package com.example.notes.presentation.bookmark

import com.example.notes.common.ScreenViewState
import com.example.notes.data.local.model.Note

data class BookmarkState(
  val notes : ScreenViewState<List<Note>> = ScreenViewState.Loading
)