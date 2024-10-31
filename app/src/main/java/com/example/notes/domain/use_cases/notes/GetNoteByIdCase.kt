package com.example.notes.domain.use_cases.notes

import com.example.notes.data.repo.NoteRepositoryImpl
import javax.inject.Inject

class GetNoteByIdCase @Inject constructor(
  private val repo : NoteRepositoryImpl
) {

  operator fun invoke(id : Long) = repo.getNoteById(id)
}