package com.atiwari.stateinfocenter.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.atiwari.stateinfocenter.R
import com.atiwari.stateinfocenter.databinding.AdapterStateDetailBinding

class StateDetailAdapter(private var counties: List<String>) :
    RecyclerView.Adapter<StateDetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: AdapterStateDetailBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.adapter_state_detail,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return counties.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = counties[position]
        holder.binding.counties = item
        holder.binding.executePendingBindings()
    }

    fun updateDataList(data: List<String>) {
        counties = data
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: AdapterStateDetailBinding) :
        RecyclerView.ViewHolder(binding.root)

}