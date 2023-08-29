package com.shypolarbear.presentation.ui.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shypolarbear.domain.model.HttpError
import com.shypolarbear.domain.model.quiz.Quiz
import com.shypolarbear.domain.model.quiz.SolvedData
import com.shypolarbear.domain.usecase.quiz.QuizReviewUseCase
import com.shypolarbear.domain.usecase.quiz.QuizSolvedUseCase
import com.shypolarbear.domain.usecase.quiz.QuizSubmitMultiUseCase
import com.shypolarbear.domain.usecase.quiz.QuizSubmitOXUseCase
import com.shypolarbear.domain.usecase.quiz.QuizUseCase
import com.shypolarbear.domain.usecase.tokens.GetAccessTokenUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizUseCase: QuizUseCase,
    private val dailyQuizSolvedUseCase: QuizSolvedUseCase,
    private val reviewQuizUsecase: QuizReviewUseCase,
    private val submitOXUseCase: QuizSubmitOXUseCase,
    private val submitMultiUseCase: QuizSubmitMultiUseCase,
    private val accessTokenUseCase: GetAccessTokenUseCase,
) : BaseViewModel() {

    private val _tokens = MutableLiveData<String?>()
    val tokens: LiveData<String?> = _tokens
    private val _quizResponse = MutableLiveData<Quiz>()
    val quizResponse: LiveData<Quiz> = _quizResponse
    private val _dailyQuizSolvedState = MutableLiveData<SolvedData>()
    val dailyQuizSolvedState: LiveData<SolvedData> = _dailyQuizSolvedState
    private val _submitBtnState = MutableLiveData<Boolean>()
    val submitBtnState: LiveData<Boolean> = _submitBtnState
    private val _answer = MutableLiveData<String>()
    val answer: LiveData<String> = _answer

    fun getAccessToken() {
        viewModelScope.launch {
            _tokens.value = accessTokenUseCase()
        }
    }

    fun requestQuiz() {
        viewModelScope.launch {
            val responseQuiz = quizUseCase()

            responseQuiz.onSuccess { response ->
                _quizResponse.value = response.data
                Timber.tag("QUIZ").d("${_quizResponse.value}")
            }
                .onFailure { error ->
                    if (error is HttpError) {
                        val errorBodyData = JSONObject(error.errorBody)
                        Timber.tag("ERROR").d("${errorBodyData.get("code")}")
                    }
                }
        }
    }

    fun requestDailyQuizSolvedState() {
        viewModelScope.launch {
            val responseState = dailyQuizSolvedUseCase()

            responseState.onSuccess { response ->
                _dailyQuizSolvedState.value = response.data
            }.onFailure { error ->
                if (error is HttpError) {
                    val errorBodyData = JSONObject(error.errorBody)
                    Timber.tag("ERROR").d("${errorBodyData.get("code")}")
                }
            }
        }
    }

    fun setAnswer(answer: String) {
        _answer.value = answer
    }

    fun setSubmitBtnState() {
        _submitBtnState.value = true
    }
}