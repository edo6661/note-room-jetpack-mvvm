package com.example.notes.domain.use_cases.notes

import com.example.notes.data.repo.NoteRepositoryImpl
import javax.inject.Inject

class DeleteUseCase @Inject constructor(
  private val repo : NoteRepositoryImpl
) {

  suspend operator fun invoke(id : Long) = repo.delete(id)

}