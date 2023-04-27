package com.example.level6task1.ui.screens.catsScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.level6task1.data.api.util.Resource
import com.example.level6task1.data.model.Cat
import com.example.level6task1.data.repository.CatsRepository
import kotlinx.coroutines.launch

class CatsViewModel(application: Application): AndroidViewModel(application) {
    private val catsRepository = CatsRepository()

    val catsResource: LiveData<Resource<Cat>>
        get() = _catsResource

    private val _catsResource: MutableLiveData<Resource<Cat>> = MutableLiveData(Resource.Empty())


    fun getCat() {
        _catsResource.value = Resource.Loading()

        viewModelScope.launch{
            _catsResource.value = catsRepository.getRandomCat()
        }
    }
}