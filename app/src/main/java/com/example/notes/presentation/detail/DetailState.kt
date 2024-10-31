package com.example.notes.presentation.detail

import java.util.Date


data class DetailState(
  val id : Long = 0,
  val title : String = "",
  val content : String = "",
  val isBookMarked : Boolean = false,
  val createdDate : Date = Date(),
  val isUpdatingNote : Boolean = false
)