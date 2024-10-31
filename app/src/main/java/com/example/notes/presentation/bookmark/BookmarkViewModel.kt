package com.example.notes.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.common.ScreenViewState
import com.example.notes.data.local.model.Note
import com.example.notes.domain.use_cases.notes.DeleteUseCase
import com.example.notes.domain.use_cases.notes.FilteredBookmarkNotesCase
import com.example.notes.domain.use_cases.notes.UpdateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
  private val updateUseCase : UpdateUseCase,
  private val filteredBookmarkNotesCase : FilteredBookmarkNotesCase,
  private val deleteUseCase : DeleteUseCase
) : ViewModel() {

  init {
    getBookmarkedNotes()
  }

  private val _state = MutableStateFlow(BookmarkState())
  val state get() = _state

  private fun getBookmarkedNotes() {
    filteredBookmarkNotesCase()
      .onEach {
        _state.value = BookmarkState(
          notes = ScreenViewState.Success(it)
        )
      }.catch {
        _state.value = BookmarkState(
          notes = ScreenViewState.Error(it.message)
        )
      }.launchIn(viewModelScope)
  }

  fun onBookmarkChange(note : Note) {

    viewModelScope.launch {
      updateUseCase(
        note.copy(
          isBookMarked = ! note.isBookMarked
        )
      )
    }
  }

  fun onDeleteNote(id : Long) {
    viewModelScope.launch {
      deleteUseCase(id)
    }
  }
}

