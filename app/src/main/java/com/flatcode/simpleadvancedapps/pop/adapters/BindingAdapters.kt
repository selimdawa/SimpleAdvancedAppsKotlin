package com.flatcode.simpleadvancedapps.pop.adapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.flatcode.simpleadvancedapps.pop.models.PopItem

@BindingAdapter("listData")
fun bindRecyclerView(
    recyclerView: RecyclerView,
    data: List<PopItem>?,
) {
    val adapter = recyclerView.adapter as FunkoListAdapter
    if (data != null) {
        adapter.submitList(data)
    }
}