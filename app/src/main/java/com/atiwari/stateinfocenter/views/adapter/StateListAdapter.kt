package com.atiwari.stateinfocenter.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.atiwari.stateinfocenter.R
import com.atiwari.stateinfocenter.databinding.ItemStateListBinding
import com.atiwari.stateinfocenter.models.StateData
import com.atiwari.stateinfocenter.viewModel.SharedViewModel


class StateListAdapter(
    private var data: List<StateData>,
    private val viewModel: SharedViewModel
) :
    RecyclerView.Adapter<StateListAdapter.ViewHolder>() {

    private var filteredList: List<StateData> = emptyList()

    inner class ViewHolder(val binding: ItemStateListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: ItemStateListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_state_list,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = filteredList[position]
        holder.binding.data = item
        holder.binding.viewModel = viewModel
        holder.binding.isSelected = viewModel.selectedPosLD.value?.contains(item.state) ?: false
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    fun updateStateList(stateList: List<StateData>) {
        data = stateList
        filteredList = stateList
        notifyDataSetChanged()
    }

    // Filter method that updates the filtered list and notifies the adapter
    fun filterList(query: String) {
        if (query.isEmpty()) {
            filteredList = data
        } else {
            val filtered = data.filter {
                it.state.lowercase().contains(query.lowercase())
            }
            filteredList = filtered
        }
        notifyDataSetChanged()
    }
}