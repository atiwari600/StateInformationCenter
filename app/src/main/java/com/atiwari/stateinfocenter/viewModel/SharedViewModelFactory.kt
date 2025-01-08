package com.atiwari.stateinfocenter.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.atiwari.stateinfocenter.repository.StateRepository

class SharedViewModelFactory(private val repository: StateRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SharedViewModel(repository) as T
    }
}