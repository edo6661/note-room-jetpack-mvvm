package com.example.notes.presentation.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notes.ui.shared.NoteTextField


@Composable
fun DetailScreen(
  modifier : Modifier = Modifier,
  id : Long,
  assistedFactory : DetailAssistedFactory,
  navigateUp : () -> Unit
) {
  val vm = viewModel(
    modelClass = DetailViewModel::class.java,
    factory = DetailViewModelFactory(id, assistedFactory)
  )
  val state = vm.state
  val (
    _, title, content, isBookmark, createdDate, isUpdatingNote) = state

  Detail(
    modifier = modifier,
    isUpdating = isUpdatingNote,
    title = title,
    content = content,
    isBookmark = isBookmark,
    onBookmarkChange = { vm.onBookmarkChange() },
    isFormNotBlank = vm.isFormNotBlank,
    onTitleChange = { vm.onTitleChange(it) },
    onContentChange = { vm.onContentChange(it) },
    onBtnClick = {
      vm.upsert()
      navigateUp()
    },
    onNavigate = navigateUp

  )

}

@Composable
private fun Detail(
  modifier : Modifier = Modifier,
  isUpdating : Boolean,
  title : String,
  content : String,
  isBookmark : Boolean,
  onBookmarkChange : (Boolean) -> Unit,
  isFormNotBlank : Boolean,
  onTitleChange : (String) -> Unit,
  onContentChange : (String) -> Unit,
  onBtnClick : () -> Unit,
  onNavigate : () -> Unit
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      // Tambahkan system bars padding
      .windowInsetsPadding(WindowInsets.ime)
  ) {
    TopSection(
      title = title,
      isBookmark = isBookmark,
      onTitleChange = onTitleChange,
      onBookmarkChange = onBookmarkChange,
      onNavigate = onNavigate
    )
    Spacer(modifier = Modifier.height(8.dp))
    AnimatedVisibility(
      isFormNotBlank
    ) {

      Row(
        modifier = Modifier
          .fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
      ) {
        IconButton(
          onClick = onBtnClick
        ) {
          val icon = if (isUpdating) Icons.Default.Update else Icons.Default.Check
          Icon(
            imageVector = icon,
            contentDescription = if (isUpdating) "Update Note" else "Save Note"
          )
        }

      }
    }
    Spacer(modifier = Modifier.height(8.dp))
    Box(
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth()
        .imePadding()
    ) {
      NoteTextField(
        value = content,
        label = "Content",
        onValueChange = onContentChange,
        modifier = Modifier.fillMaxSize(),
      )
    }

  }
}

@Composable
fun TopSection(
  modifier : Modifier = Modifier,
  title : String,
  isBookmark : Boolean,
  onTitleChange : (String) -> Unit,
  onBookmarkChange : (Boolean) -> Unit,
  onNavigate : () -> Unit
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(
        top = 16.dp,
      ),
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically,

    ) {
    IconButton(
      onClick = onNavigate
    ) {
      Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
        contentDescription = "Back"
      )
    }
    NoteTextField(
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth(),
      value = title,
      label = "Title",
      onValueChange = onTitleChange,
      labelAlignment = TextAlign.Center
    )
    IconButton(
      onClick = {
        onBookmarkChange(! isBookmark)
      }
    ) {
      val icon = if (isBookmark) Icons.Default.BookmarkRemove else Icons.Default.Bookmark
      Icon(
        imageVector = icon,
        contentDescription = if (isBookmark) "Remove Bookmark" else "Bookmark"
      )
    }
  }
}

