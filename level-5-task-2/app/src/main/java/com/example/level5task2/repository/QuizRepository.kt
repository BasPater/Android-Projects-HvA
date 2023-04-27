package com.example.level5task2.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.level5task2.ui.Question
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.withTimeout

class QuizRepository {
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var quizCollection = firestore.collection("quiz")

    private val _questions: MutableLiveData<List<Question>> = MutableLiveData()

    val questions: LiveData<List<Question>> get() = _questions

    //the CreateQuizScreen can use this to see if creation succeeded
    private val _createSuccess: MutableLiveData<Boolean> = MutableLiveData()

    val createSuccess: LiveData<Boolean>
        get() = _createSuccess

    suspend fun getQuestions() {
        try {
            withTimeout(10_000) {
                quizCollection.addSnapshotListener { it, _ ->
                    if (it != null) {
                        val allQuestions = it.documents.map {
                            Question(
                                it.get("choices") as ArrayList<String>,
                                it.get("correctAnswer").toString(),
                                it.get("id").toString().toLong(),
                                it.get("question").toString(),
                            )
                        }
                        _questions.value = allQuestions
                    }
                }
            }


        } catch (e: Exception) {
            throw QuestionRetrievalError("Retrieval-firebase-task was unsuccessful", e)
        }
    }

    fun reset() {
        _createSuccess.value = false
    }

    class QuestionRetrievalError(message: String, cause: Throwable) : Exception(message, cause)


}