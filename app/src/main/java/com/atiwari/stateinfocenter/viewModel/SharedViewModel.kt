package com.atiwari.stateinfocenter.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atiwari.stateinfocenter.models.StateData
import com.atiwari.stateinfocenter.repository.Response
import com.atiwari.stateinfocenter.repository.StateRepository
import kotlinx.coroutines.launch

class SharedViewModel(private val stateRepository: StateRepository) : ViewModel() {

    private val TAG = SharedViewModel::class.java.simpleName
    private var lastClickTime = 0L
    private val doubleClickThreshold: Long = 300  // 300ms for double-click threshold

    val stateListData: LiveData<Response<List<StateData>>> get() = stateRepository.stateLiveData

    val countiesListData: LiveData<Response<List<String>>> get() = stateRepository.countiesLiveData

    fun getStates() {
        viewModelScope.launch {
            stateRepository.fetchStates()
        }
    }

    fun getCounties(counties: Int) {
        viewModelScope.launch {
            stateRepository.fetchCounties(counties)
        }
    }

    // LiveData to hold the selected state
    private val _selectedStateLD = MutableLiveData<StateData?>()
    val selectedStateLD: MutableLiveData<StateData?> get() = _selectedStateLD

    // LiveData to hold the position of selected state
    private val _selectedPosLD = MutableLiveData<Set<String>>(emptySet())
    val selectedPosLD: LiveData<Set<String>> get() = _selectedPosLD

    fun setStateDetail(stateData: StateData) {
        Log.d(TAG, "$TAG selected state data -> $stateData")
        val currentClickTime = System.currentTimeMillis()
        if (currentClickTime - lastClickTime < doubleClickThreshold) {
            _selectedPosLD.value = _selectedPosLD.value?.toMutableSet()?.apply {
                if (contains(stateData.state)) remove(stateData.state) else add(stateData.state)
            }
        }
        lastClickTime = currentClickTime
        _selectedStateLD.value = stateData
    }

    fun verifyStateCountiesCount(): String {
        return "YES"
    }

    // LiveData to hold the filter string
    private val _filterTextLD = MutableLiveData<String>()
    val filterTextLD: LiveData<String> get() = _filterTextLD

    fun setInputFilterText(filterText: String) {
        _filterTextLD.value = filterText
    }

    // Open In Second Screen Button Navigation
    private val _navigationLD = MutableLiveData<Boolean>()
    val navigationLD: LiveData<Boolean> get() = _navigationLD
    fun navigateToDetailScreen() {
        if (selectedStateLD.value != null)
            _navigationLD.value = true
    }
}