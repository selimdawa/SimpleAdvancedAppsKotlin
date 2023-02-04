package com.flatcode.simpleadvancedapps.todoNote.ui.notes.delete

import androidx.lifecycle.ViewModel
import com.flatcode.simpleadvancedapps.todoNote.data.NoteDao
import com.flatcode.simpleadvancedapps.todoNote.di.ApplicationScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteAllNotesViewModel @Inject constructor(
    private val dao: NoteDao,
    @ApplicationScope private val applicationScope: CoroutineScope,
) : ViewModel() {

    fun confirmClick() = applicationScope.launch {
        dao.deleteAllNotes()
    }
}