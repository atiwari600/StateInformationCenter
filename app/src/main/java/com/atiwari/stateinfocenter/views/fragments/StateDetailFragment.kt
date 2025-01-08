package com.atiwari.stateinfocenter.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.atiwari.stateinfocenter.R
import com.atiwari.stateinfocenter.databinding.FragmentStateDetailBinding
import com.atiwari.stateinfocenter.repository.Response
import com.atiwari.stateinfocenter.utils.AppUtils
import com.atiwari.stateinfocenter.viewModel.SharedViewModel
import com.atiwari.stateinfocenter.views.adapter.StateDetailAdapter

class StateDetailFragment : Fragment() {

    private val TAG = StateDetailFragment::class.java.simpleName
    private lateinit var binding: FragmentStateDetailBinding
    private var data: List<String> = emptyList()
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_state_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = StateDetailAdapter(data)
        binding.recyclerStateDetail.adapter = adapter

        viewModel.countiesListData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Error -> {
                    binding.progressBar.visibility = View.GONE
                    AppUtils.showToast(response.errorMessage)
                }

                is Response.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Response.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.layoutStateName.textHeading.text = getString(R.string.state_name)
                    binding.layoutStatePopulation.textHeading.text =
                        getString(R.string.state_overall_population)
                    binding.layoutCountiesCount.textHeading.text =
                        getString(R.string.number_of_counties)
                    binding.layoutCountiesCompare.textHeading.text =
                        getString(R.string.state_counties_total)
                    binding.viewModel = viewModel
                    val data = if (response.data.isNullOrEmpty()) emptyList() else response.data
                    adapter.updateDataList(data)
                }
            }

        }

        viewModel.selectedStateLD.observe(viewLifecycleOwner) { state ->
            Log.d(TAG, "$TAG selected state data -> $state")
            state?.let { viewModel.getCounties(state.counties) }
        }

    }
}