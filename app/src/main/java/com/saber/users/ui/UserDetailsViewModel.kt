package com.saber.users.ui

import androidx.lifecycle.*
import com.saber.users.data.User
import com.saber.users.data.Result

import com.saber.users.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    state: SavedStateHandle,
    repository: UsersRepository

) :
    ViewModel() {

    private val _userDetails = repository
        .getUserDetailsFlow(state.get<Int>("userID")!!)
        .asLiveData(viewModelScope.coroutineContext)

    val userDetails: LiveData<Result<User>>
        get() = _userDetails

}