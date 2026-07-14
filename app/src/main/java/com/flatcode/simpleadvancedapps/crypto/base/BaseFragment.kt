package com.flatcode.simpleadvancedapps.crypto.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.flatcode.simpleadvancedapps.crypto.utils.collectLatestLifecycleFlow
import kotlinx.coroutines.flow.Flow

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel>(
    private val bindingInflater: (
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean,
    ) -> VB,
) : Fragment() {

    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding
            ?: throw IllegalStateException("Binding is only accessible between onCreateView and onDestroyView")

    protected abstract val viewModel: VM
    protected abstract fun onCreateFinished()
    protected abstract fun initializeListeners()
    protected abstract fun observeEvents()

    protected fun <T> collectLifecycleFlow(flow: Flow<T>, collect: suspend (T) -> Unit) {
        collectLatestLifecycleFlow(flow, collect)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCreateFinished()
        initializeListeners()
        observeEvents()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}