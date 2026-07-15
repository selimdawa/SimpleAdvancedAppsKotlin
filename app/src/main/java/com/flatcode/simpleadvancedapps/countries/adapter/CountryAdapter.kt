package com.flatcode.simpleadvancedapps.countries.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.flatcode.simpleadvancedapps.countries.fragment.DashboardFragmentDirections
import com.flatcode.simpleadvancedapps.countries.model.Country
import com.flatcode.simpleadvancedapps.databinding.ItemCountryBinding
import com.flatcode.simpleadvancedapps.utils.downloadFromUrl
import com.flatcode.simpleadvancedapps.utils.placeholderProgressBar

class CountryAdapter(private val countryList: ArrayList<Country>) :
    RecyclerView.Adapter<CountryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val model = countryList[position]
        val context = holder.binding.root.context

        with(holder.binding) {
            cName.text = model.countryName
            dName.text = model.countryRegion

            item.setOnClickListener { view ->
                val action =
                    DashboardFragmentDirections.actionDashboardFragmentToDetailFragment(model.uuid)
                Navigation.findNavController(view).navigate(action)
            }

            imageName.downloadFromUrl(
                false, model.imageURL, placeholderProgressBar(context)
            )

            imageBlur.downloadFromUrl(
                true, model.imageURL, placeholderProgressBar(context)
            )
        }
    }

    override fun getItemCount(): Int = countryList.size

    fun updateCountryList(newCountryList: List<Country>) {
        val diffCallback = CountryDiffCallback(countryList, newCountryList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        countryList.clear()
        countryList.addAll(newCountryList)
        diffResult.dispatchUpdatesTo(this)
    }
}

class CountryViewHolder(val binding: ItemCountryBinding) : RecyclerView.ViewHolder(binding.root)

class CountryDiffCallback(
    private val oldList: List<Country>, private val newList: List<Country>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].uuid == newList[newItemPosition].uuid
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}