package com.example.notes.data.local.converters

import androidx.room.TypeConverter
import java.util.Date

// ! ngasih tau room kalo kita save date ke database, kita convert dulu ke long dan sebaliknya
class DateConverter {

  @TypeConverter
  fun toDate(date : Long?) : Date? {
    return date?.let {
      Date(it)
    }
  }

  @TypeConverter
  fun fromDate(date : Date?) : Long? {
    return date?.time
  }

}