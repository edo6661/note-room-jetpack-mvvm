package com.example.notes.presentation.home

import com.example.notes.common.ScreenViewState
import com.example.notes.data.local.model.Note

data class HomeState(
  val notes : ScreenViewState<List<Note>> = ScreenViewState.Loading
)