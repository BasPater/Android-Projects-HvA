package com.example.level5task2.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.level5task2.repository.QuizRepository
import com.example.level5task2.ui.Question
import kotlinx.coroutines.launch

class QuizViewModel(application: Application): AndroidViewModel(application) {
    private val TAG = "FIRESTORE"
    private val quizRepository: QuizRepository = QuizRepository()

    val questions: LiveData<List<Question>> = quizRepository.questions

    val createSuccess: LiveData<Boolean> = quizRepository.createSuccess

    private val _errorText: MutableLiveData<String> = MutableLiveData()
    val errorText: LiveData<String>
        get() = _errorText

    fun getQuestions() {
        reset()
        viewModelScope.launch {
            try {
                quizRepository.getQuestions()
            } catch (ex: QuizRepository.QuestionRetrievalError) {
                val errorMsg = "Something went wrong while retrieving quiz.\n" +
                        "It could be that you still need to install your own google-services.json file from Firestore."
                Log.e(TAG, ex.message ?: errorMsg)
                _errorText.value = errorMsg
            }
        }
    }

    fun reset() {
        _errorText.value = ""
        quizRepository.reset()
    }
}