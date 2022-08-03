package io.github.ilyaskerbal.googleauthapp.presentation.screen.profile

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.ilyaskerbal.googleauthapp.domain.model.ApiResponse
import io.github.ilyaskerbal.googleauthapp.domain.model.MessageBarState
import io.github.ilyaskerbal.googleauthapp.domain.model.User
import io.github.ilyaskerbal.googleauthapp.domain.model.UserUpdateRequest
import io.github.ilyaskerbal.googleauthapp.domain.repository.Repository
import io.github.ilyaskerbal.googleauthapp.utils.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _user: MutableState<User?> = mutableStateOf(null)
    val user: State<User?> = _user

    private val _firstName: MutableState<String> = mutableStateOf("")
    val firstName: State<String> = _firstName

    private val _lasttName: MutableState<String> = mutableStateOf("")
    val lastName: State<String> = _lasttName

    private val _apiResponse: MutableState<RequestState<ApiResponse>> = mutableStateOf(RequestState.Idle)
    val apiResponse: State<RequestState<ApiResponse>> = _apiResponse

    private val _messageBarState: MutableState<MessageBarState> = mutableStateOf(MessageBarState())
    val messageBarState: State<MessageBarState> = _messageBarState

    private val _clearSessionResponse: MutableState<RequestState<ApiResponse>> =
        mutableStateOf(RequestState.Idle)
    val clearSessionResponse: State<RequestState<ApiResponse>> = _clearSessionResponse

    init {
        getUserInfo()
    }

    private fun getUserInfo() {
        viewModelScope.launch {
            _apiResponse.value = RequestState.Loading
            try {
                val response = withContext(Dispatchers.IO) {
                    repository.getUserInfo()
                }
                _apiResponse.value = RequestState.Success(response)
                _messageBarState.value = MessageBarState(
                    message = response.message,
                    error = response.error,
                )
                response.user?.let {
                    _user.value = it
                    _firstName.value = fullNameToFirstAndLast(it.name).first
                    _lasttName.value = fullNameToFirstAndLast(it.name).second
                }
            } catch (e: Exception) {
                _apiResponse.value = RequestState.Error(e)
                _messageBarState.value = MessageBarState(error = e)
            }
        }
    }

    fun updateFirstName(firstName: String) {
        if (firstName.length < 20){
            _firstName.value = firstName
        }
    }

    fun updateLastName(lastName: String) {
        if (lastName.length < 20){
            _lasttName.value = lastName
        }
    }

    fun updateUserInfo() {
        _apiResponse.value = RequestState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (user.value != null) {
                    val response = repository.getUserInfo()
                    verifyAndUpdate(currentUser = response)
                }
            } catch (e: Exception) {
                _apiResponse.value = RequestState.Error(e)
                _messageBarState.value = MessageBarState(error = e)
            }
        }
    }

    private fun verifyAndUpdate(currentUser: ApiResponse) {
        val (verified, exception) = if (firstName.value.isEmpty() || lastName.value.isEmpty()) {
            false to EmptyFieldException()
        } else {
            if (currentUser?.user?.name?.let { fullNameToFirstAndLast(it).first } == firstName.value &&
                currentUser?.user?.name?.let { fullNameToFirstAndLast(it).second } == lastName.value
            ) {
                Pair(false, NothingToUpdateException())
            } else {
                Pair(true, null)
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            if (verified) {
                try {
                    val response = repository.updateUserInfo(
                        request = UserUpdateRequest(
                            firstName = firstName.value,
                            lastName = lastName.value
                        )
                    )
                    _apiResponse.value = RequestState.Success(response)
                    _messageBarState.value = MessageBarState(
                        message = response.message,
                        error = response.error
                    )
                } catch (e: Exception) {
                    _apiResponse.value = RequestState.Error(e)
                    _messageBarState.value = MessageBarState(error = e)
                }
            } else {
                _apiResponse.value = RequestState.Success(
                    ApiResponse(success = false, error = exception)
                )
                _messageBarState.value = MessageBarState(error = exception)
            }
        }
    }

    fun clearSession() {
        _clearSessionResponse.value = RequestState.Loading
        _apiResponse.value = RequestState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.signOut()
                _clearSessionResponse.value = RequestState.Success(response)
                _apiResponse.value = RequestState.Success(response)
                _messageBarState.value = MessageBarState(
                    message = response.message,
                    error = response.error
                )
            } catch (e: Exception) {
                _clearSessionResponse.value = RequestState.Error(e)
                _apiResponse.value = RequestState.Error(e)
                _messageBarState.value = MessageBarState(error = e)
            }
        }
    }

    fun saveSignedInState(signedIn: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveSignedInState(signedIn = signedIn)
        }
    }

    fun deleteUser() {
        _clearSessionResponse.value = RequestState.Loading
        _apiResponse.value = RequestState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.deleteUser()
                _clearSessionResponse.value = RequestState.Success(response)
                _apiResponse.value = RequestState.Success(response)
                _messageBarState.value = MessageBarState(
                    message = response.message,
                    error = response.error
                )
            } catch (e: Exception) {
                _clearSessionResponse.value = RequestState.Error(e)
                _apiResponse.value = RequestState.Error(e)
                _messageBarState.value = MessageBarState(error = e)
            }
        }
    }

}

private fun fullNameToFirstAndLast(fullName: String): Pair<String, String> {
    val nameParts = fullName.split(" ")
    if (nameParts.size == 2) {
        return nameParts.first() to nameParts.last()
    } else if (nameParts.size > 2) {
        var accum = ""
        nameParts.forEachIndexed { index, s ->
            if (index < nameParts.size - 1) {
                accum += s
            }
            if (index < nameParts.size - 2) {
                accum += " "
            }
        }
        return accum to nameParts.last()
    } else if(nameParts.size == 1) {
        return nameParts.first() to ""
    } else {
        return "" to  ""
    }
}

class EmptyFieldException(
    override val message: String = "Empty Input Field."
) : Exception()

class NothingToUpdateException(
    override val message: String = "Nothing to Update."
) : Exception()