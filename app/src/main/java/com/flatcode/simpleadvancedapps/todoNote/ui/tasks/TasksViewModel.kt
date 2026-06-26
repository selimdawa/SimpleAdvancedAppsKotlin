package com.flatcode.simpleadvancedapps.todoNote.ui.tasks

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.Unit.DATA
import com.flatcode.simpleadvancedapps.todoNote.ADD_RESULT_OK
import com.flatcode.simpleadvancedapps.todoNote.EDIT_RESULT_OK
import com.flatcode.simpleadvancedapps.todoNote.data.PreferencesManager
import com.flatcode.simpleadvancedapps.todoNote.data.SortOrder
import com.flatcode.simpleadvancedapps.todoNote.data.Task
import com.flatcode.simpleadvancedapps.todoNote.data.TaskDao
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class TasksViewModel @Inject constructor(
    private val taskDao: TaskDao,
    private val preferencesManager: PreferencesManager,
    state: SavedStateHandle,
    @ApplicationContext private val context: Context
) : ViewModel() {

    val searchQuery = state.getLiveData("searchQuery", DATA.EMPTY)
    val preferencesFlow = preferencesManager.preferencesFlow

    private val taskEventChannel = Channel<TasksEvent>()
    val taskEvent = taskEventChannel.receiveAsFlow()

    private val tasksFlow = combine(
        searchQuery.asFlow(),
        preferencesFlow
    ) { query, filterPreferences ->
        Pair(query, filterPreferences)
    }.flatMapLatest { (query, filterPreferences) ->
        taskDao.getTasks(query, filterPreferences.sortOrder, filterPreferences.hideCompleted)
    }

    fun onSortOrderSelected(sortOrder: SortOrder) = viewModelScope.launch {
        preferencesManager.updateSortOrder(sortOrder)
    }

    fun onHideCompletedClick(hideCompleted: Boolean) = viewModelScope.launch {
        preferencesManager.updateHideCompleted(hideCompleted)
    }

    val tasks = tasksFlow.asLiveData()

    fun onTaskSelected(task: Task) = viewModelScope.launch {
        taskEventChannel.send(TasksEvent.NavigateToEditTaskScreen(task))
    }

    fun onTaskCheckedChanged(task: Task, isChecked: Boolean) = viewModelScope.launch {
        taskDao.update(task.copy(completed = isChecked))
    }

    fun onTaskSwiped(task: Task) = viewModelScope.launch {
        taskDao.delete(task)
        taskEventChannel.send(TasksEvent.ShowUndoDeleteTaskMessage(task))
    }

    fun onUndoDeleteClick(task: Task) = viewModelScope.launch {
        taskDao.insert(task)
    }

    fun onAddNewTaskClick() = viewModelScope.launch {
        taskEventChannel.send(TasksEvent.NavigateToAddScreen)
    }

    fun onAddEditResult(result: Int) {
        when (result) {
            ADD_RESULT_OK -> {
                val message = context.getString(R.string.msg_task_added)
                showTaskSavedConfirmationMessage(message)
            }

            EDIT_RESULT_OK -> {
                val message = context.getString(R.string.msg_task_updated)
                showTaskSavedConfirmationMessage(message)
            }
        }
    }

    fun showTaskSavedConfirmationMessage(text: String) = viewModelScope.launch {
        taskEventChannel.send(TasksEvent.ShowTaskSavedConfirmationMessage(text))
    }

    fun onDeleteAllCompletedConfirmed() = viewModelScope.launch {
        taskDao.deleteCompletedTasks()
        val message = context.getString(R.string.msg_all_completed_tasks_deleted)
        taskEventChannel.send(TasksEvent.ShowTaskSavedConfirmationMessage(message))
    }

    sealed class TasksEvent {
        data object NavigateToAddScreen : TasksEvent()
        data class NavigateToEditTaskScreen(val task: Task) : TasksEvent()
        data class ShowUndoDeleteTaskMessage(val task: Task) : TasksEvent()
        data class ShowTaskSavedConfirmationMessage(val msg: String) : TasksEvent()
        data object NavigateToDeleteAllCompletedTasksScreen : TasksEvent()
    }
}