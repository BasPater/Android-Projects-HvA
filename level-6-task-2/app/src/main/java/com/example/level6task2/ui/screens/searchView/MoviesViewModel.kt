package com.example.level6task2.ui.screens.searchView

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.level6task2.data.api.util.Resource
import com.example.level6task2.data.model.Movie
import com.example.level6task2.data.model.MovieList
import com.example.level6task2.data.repository.MovieRepository
import kotlinx.coroutines.launch

class MoviesViewModel(application: Application): AndroidViewModel(application) {
    private val movieRepository = MovieRepository()

    val movieResource: LiveData<Resource<MovieList>>
        get() = _movieResource

    private val _movieResource: MutableLiveData<Resource<MovieList>> = MutableLiveData(Resource.Empty())


    var selectedMovie: Movie? = null
        set

    fun getMoviesByName(name: String) {
        _movieResource.value = Resource.Loading()

        viewModelScope.launch{
            _movieResource.value = movieRepository.getMoviesByName(name)
        }
    }
}