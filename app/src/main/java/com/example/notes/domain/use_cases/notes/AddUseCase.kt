package com.example.notes.domain.use_cases.notes

import com.example.notes.data.local.model.Note
import com.example.notes.data.repo.NoteRepositoryImpl
import javax.inject.Inject

class AddUseCase @Inject constructor(
  private val repo : NoteRepositoryImpl
) {

  suspend operator fun invoke(note : Note) = repo.insert(note)

}