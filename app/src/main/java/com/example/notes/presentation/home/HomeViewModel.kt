package com.example.notes.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.common.ScreenViewState
import com.example.notes.data.local.model.Note
import com.example.notes.domain.use_cases.notes.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val getAllNotes : GetAllNotesCase,
  private val deleteUseCase : DeleteUseCase,
  private val updateUseCase : UpdateUseCase
) : ViewModel() {

  private val _state = MutableStateFlow(HomeState())
   val state get() = _state.asStateFlow()

  init {
    getBookmarkNotes()
  }

  private fun getBookmarkNotes() {
    getAllNotes()
      .onEach {
        _state.value = HomeState(notes = ScreenViewState.Success(it))
      }.catch {
        _state.value = HomeState(notes = ScreenViewState.Error(it.message))
      }.launchIn(viewModelScope)
  }

  fun onDeleteNote(id : Long) = viewModelScope.launch {
    deleteUseCase(id)
  }

  fun onBookMarkChange(note : Note) = viewModelScope.launch {
    updateUseCase(note.copy(isBookMarked = ! note.isBookMarked))
  }

}

