package io.github.ilyaskerbal.googleauthapp.presentation.screen.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.ilyaskerbal.googleauthapp.domain.exception.GoogleAccountNotFoundException
import io.github.ilyaskerbal.googleauthapp.domain.model.ApiRequest
import io.github.ilyaskerbal.googleauthapp.domain.model.ApiResponse
import io.github.ilyaskerbal.googleauthapp.domain.model.MessageBarState
import io.github.ilyaskerbal.googleauthapp.domain.repository.Repository
import io.github.ilyaskerbal.googleauthapp.utils.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor (
    private val repository: Repository
) : ViewModel() {

    private val _singedInSate: MutableState<Boolean> = mutableStateOf(false)
    val signedInSate: State<Boolean> = _singedInSate

    private val _messageBarState : MutableState<MessageBarState> = mutableStateOf(MessageBarState())
    val messageBarState: State<MessageBarState> = _messageBarState

    private val _apiResponse: MutableState<RequestState<ApiResponse>> = mutableStateOf(RequestState.Idle)
    val apiResponse: State<RequestState<ApiResponse>> = _apiResponse

    init {
        viewModelScope.launch {
            repository.readSignedInState().collect {
                _singedInSate.value = it
            }
        }
    }

    fun saveSignedInstate(signedInSate: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveSignedInState(signedInSate)
        }
    }

    fun updateMessageBarState() {
        _messageBarState.value = MessageBarState(error = GoogleAccountNotFoundException())
    }

    fun verifyTokenOnBackend(request: ApiRequest) {
        _apiResponse.value = RequestState.Loading
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val response = repository.verifyTokenOnBackend(request = request)
                _apiResponse.value = RequestState.Success(response)
                _messageBarState.value = MessageBarState(
                    message = response.message,
                    error = response.error,
                )
            }
        } catch (e: Exception) {
            _apiResponse.value = RequestState.Error(t = e)
            _messageBarState.value = MessageBarState(error = e)
        }
    }

}