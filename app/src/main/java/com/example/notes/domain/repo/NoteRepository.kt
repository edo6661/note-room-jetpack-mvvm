package com.example.notes.domain.repo

import com.example.notes.data.local.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
  fun getAllNotes(): Flow<List<Note>>
  fun getNoteById(id: Long): Flow<Note>
  fun getBookMarkedNotes(): Flow<List<Note>>
  suspend fun insert(note: Note)
  suspend fun update(note: Note)
  suspend fun delete(id: Long)
}