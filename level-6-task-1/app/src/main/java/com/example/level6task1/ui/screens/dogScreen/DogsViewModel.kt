package com.example.level6task1.ui.screens.dogScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.level6task1.data.api.util.Resource
import com.example.level6task1.data.model.Dog
import com.example.level6task1.data.repository.DogsRepository
import kotlinx.coroutines.launch

class DogsViewModel(application: Application): AndroidViewModel(application) {
    private val dogsRepository = DogsRepository()

    val dogsResource: LiveData<Resource<Dog>>
        get() = _dogsResource

    private val _dogsResource: MutableLiveData<Resource<Dog>> = MutableLiveData(Resource.Empty())


    fun getDog() {
        _dogsResource.value = Resource.Loading()

        viewModelScope.launch{
            _dogsResource.value = dogsRepository.getRandomDog()
        }
    }
}