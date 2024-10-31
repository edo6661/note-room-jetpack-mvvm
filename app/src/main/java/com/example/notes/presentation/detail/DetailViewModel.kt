package com.example.notes.presentation.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notes.data.local.model.Note
import com.example.notes.domain.use_cases.notes.AddUseCase
import com.example.notes.domain.use_cases.notes.GetNoteByIdCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collectLatest
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


@AssistedFactory
interface DetailAssistedFactory {

  fun create(id : Long) : DetailViewModel
}

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(
  private val id : Long,
  private val assistedFactory : DetailAssistedFactory
) : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass : Class<T>) : T {
    return assistedFactory.create(id) as T
  }
}


class DetailViewModel @AssistedInject constructor(
  private val addUseCase : AddUseCase,
  private val getNoteByIdCase : GetNoteByIdCase,
  @Assisted private val id : Long
) : ViewModel() {

  var state by mutableStateOf(DetailState())
    private set

  val isFormNotBlank get() = state.title.isNotEmpty() && state.content.isNotEmpty()

  init {
    initialize()
  }
  private val note : Note
    get() = state.run {

      Note(
        id = id,
        title = title,
        content = content,
        isBookMarked = isBookMarked,
        createdDate = createdDate
      )
    }

  private fun getNoteById() {
    viewModelScope.launch {
      getNoteByIdCase(id).collectLatest {
        state = state.copy(
          id = it.id,
          title = it.title,
          content = it.content,
          isBookMarked = it.isBookMarked,
          createdDate = it.createdDate
        )
      }
    }
  }

  private fun initialize() {
    val isUpdatingNote = id != - 1L
    state = state.copy(
      isUpdatingNote = isUpdatingNote
    )
    if (isUpdatingNote) getNoteById()

  }

  fun onTitleChange(title : String) {
    state = state.copy(title = title)
  }

  fun onContentChange(content : String) {
    state = state.copy(content = content)
  }
  fun onBookmarkChange() {
    state = state.copy(isBookMarked = ! state.isBookMarked)
  }

  fun upsert() = viewModelScope.launch {
    addUseCase(note)
  }

}