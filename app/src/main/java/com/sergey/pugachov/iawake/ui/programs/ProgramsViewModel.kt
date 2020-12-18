package com.sergey.pugachov.iawake.ui.programs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergey.pugachov.iawake.domain.model.common.Result
import com.sergey.pugachov.iawake.domain.model.programs.ProgramModel
import com.sergey.pugachov.iawake.domain.repository.ProgramsRepository
import kotlinx.coroutines.launch

class ProgramsViewModel(private val repository: ProgramsRepository) : ViewModel() {

    private val _programs: MutableLiveData<List<ProgramModel>> = MutableLiveData()
    val programs: LiveData<List<ProgramModel>> = _programs

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadPrograms()
    }

    fun loadPrograms() {
        viewModelScope.launch {
            _isLoading.value = true

            val result = repository.getPrograms()

            _isLoading.value = false
            _programs.value = if (result is Result.Success) result.data else emptyList()
        }
    }
}