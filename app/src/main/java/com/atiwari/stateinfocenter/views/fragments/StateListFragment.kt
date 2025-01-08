package com.atiwari.stateinfocenter.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.atiwari.stateinfocenter.R
import com.atiwari.stateinfocenter.databinding.FragmentStateListBinding
import com.atiwari.stateinfocenter.models.StateData
import com.atiwari.stateinfocenter.repository.Response
import com.atiwari.stateinfocenter.utils.AppUtils
import com.atiwari.stateinfocenter.viewModel.SharedViewModel
import com.atiwari.stateinfocenter.views.adapter.StateListAdapter

class StateListFragment : Fragment() {

    private lateinit var binding: FragmentStateListBinding
    private val viewModel: SharedViewModel by activityViewModels()
    private var stateData: List<StateData> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_state_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = StateListAdapter(stateData, viewModel)
        binding.recyclerStateList.adapter = adapter

        // Observing the state list live data
        viewModel.stateListData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Error -> AppUtils.showToast(response.errorMessage)
                is Response.Success -> response.data?.let { adapter.updateStateList(it) }
                else -> {}
            }
        }

        viewModel.selectedPosLD.observe(viewLifecycleOwner) { selectedItem ->
            adapter.notifyDataSetChanged()
        }
    }
}