package com.example.notes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.notes.data.local.converters.DateConverter
import com.example.notes.data.local.model.Note

@TypeConverters(
  value = [
    DateConverter::class
  ]
)
@Database(
  entities = [
    Note::class
  ],
  version = 1,
  exportSchema = false
)
abstract class NoteDatabase : RoomDatabase() {
  object Constants {
    const val DATABASE_NAME = "notes_db"
  }
  abstract val noteDao : NoteDao
}