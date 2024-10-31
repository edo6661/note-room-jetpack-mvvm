package com.example.notes.data.local

import androidx.room.*
import com.example.notes.data.local.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

  //  ! yang get ga pakai suspend karena kita return flow, flow itu handles its own quarantined thread
  @Query("SELECT * FROM notes ORDER BY createdDate")
  fun getAllNotes() : Flow<List<Note>>

  @Query("SELECT * FROM notes where id = :id ORDER BY createdDate")
  fun getNoteById(id : Long) : Flow<Note>
  @Query("SELECT * FROM notes where isBookMarked = 1 ORDER BY createdDate")
  fun getBookMarkedNotes():Flow<List<Note>>

  @Insert(
    onConflict = OnConflictStrategy.REPLACE
  )
  suspend fun insertNote(note : Note)

  @Update(
    onConflict = OnConflictStrategy.REPLACE
  )
  suspend fun updateNote(note : Note)

  @Query("DELETE FROM notes where id = :id")
  suspend fun deleteNote(id : Long)

}