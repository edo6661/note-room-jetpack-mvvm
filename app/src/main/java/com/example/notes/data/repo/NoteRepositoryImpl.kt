package com.example.notes.data.repo

import com.example.notes.data.local.NoteDao
import com.example.notes.data.local.model.Note
import com.example.notes.domain.repo.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
  private val dao : NoteDao
) : NoteRepository {

  override fun getAllNotes() : Flow<List<Note>> {
    return dao.getAllNotes()
  }

  override fun getNoteById(id : Long) : Flow<Note> {
    return dao.getNoteById(id)
  }

  override fun getBookMarkedNotes() : Flow<List<Note>> {
    return dao.getBookMarkedNotes()
  }

  override suspend fun insert(note : Note) {
    dao.insertNote(note)
  }

  override suspend fun update(note : Note) {
    dao.updateNote(note)
  }

  override suspend fun delete(id : Long) {
    dao.deleteNote(id)
  }
}
