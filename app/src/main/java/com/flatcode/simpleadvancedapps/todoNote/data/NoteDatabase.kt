package com.flatcode.simpleadvancedapps.todoNote.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.flatcode.simpleadvancedapps.todoNote.util.Converters


@Database(entities = [Notes::class], version = 1)
@TypeConverters(Converters::class)
abstract class NoteDatabase :RoomDatabase() {

    abstract fun notesDao(): NoteDao
}