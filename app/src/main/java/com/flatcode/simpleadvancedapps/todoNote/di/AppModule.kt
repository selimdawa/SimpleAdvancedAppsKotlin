package com.flatcode.simpleadvancedapps.todoNote.di

import android.app.Application
import androidx.room.Room
import com.flatcode.simpleadvancedapps.todoNote.data.NoteDatabase
import com.flatcode.simpleadvancedapps.todoNote.data.NoteDao
import com.flatcode.simpleadvancedapps.todoNote.data.TaskDao
import com.flatcode.simpleadvancedapps.todoNote.data.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application, callBack: TaskDatabase.Callback): TaskDatabase =
        Room.databaseBuilder(app, TaskDatabase::class.java, "task_database")
            .fallbackToDestructiveMigration()
            .addCallback(callBack)
            .build()

    @Provides
    fun providesDao(db: TaskDatabase): TaskDao = db.taskDao()

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase =
        Room.databaseBuilder(app, NoteDatabase::class.java, "note_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun providesNoteDao(db: NoteDatabase): NoteDao = db.notesDao()

    @Provides
    @Singleton
    fun provideApplicationScope(): CoroutineScope = CoroutineScope(SupervisorJob())
}

@Qualifier
@Target(AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationScope