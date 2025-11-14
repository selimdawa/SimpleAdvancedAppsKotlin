package com.flatcode.simpleadvancedapps.todoNote.ui.todo.tasks.delete

import androidx.lifecycle.ViewModel
import com.flatcode.simpleadvancedapps.todoNote.data.TaskDao
import com.flatcode.simpleadvancedapps.todoNote.di.ApplicationScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DeleteAllCompletedViewModel @Inject constructor(
    private val dao: TaskDao, @ApplicationScope private val applicationScope: CoroutineScope,
) : ViewModel() {

    fun confirmClick() = applicationScope.launch {
        dao.deleteCompletedTasks()
    }
}