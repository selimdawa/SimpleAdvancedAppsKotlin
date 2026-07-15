package com.flatcode.simpleadvancedapps.rickAndMorty.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.flatcode.simpleadvancedapps.rickAndMorty.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment() {

    private var _binding: VB? = null
    open val binding get() = _binding!!

    protected abstract val viewModel: VM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupListener()
        setupSubscribe()
    }

    protected open fun initialize() {}
    protected open fun setupListener() {}
    protected open fun setupSubscribe() {}

    protected fun <T> Flow<Resource<T>>.collectResource(
        onLoading: (Boolean) -> Unit = {},
        onError: (String) -> Unit = {},
        onSuccess: (T) -> Unit
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                this@collectResource.collectLatest { resource ->
                    when (resource) {
                        is Resource.Loading -> onLoading(true)
                        is Resource.Success -> {
                            onLoading(false)
                            resource.data?.let { onSuccess(it) }
                        }
                        is Resource.Error -> {
                            onLoading(false)
                            onError(resource.message.toString())
                        }
                    }
                }
            }
        }
    }

    protected fun RecyclerView.onScrollEnd(isLoading: Boolean, onLoadNext: () -> Unit) {
        this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView.canScrollVertically(1) && !isLoading) onLoadNext()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}