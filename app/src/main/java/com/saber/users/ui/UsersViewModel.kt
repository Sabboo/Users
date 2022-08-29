package com.saber.users.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.saber.users.data.User
import com.saber.users.data.Result

import com.saber.users.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(repository: UsersRepository) :
    ViewModel() {

    private val _users = repository
        .getUsersFlow()
        .asLiveData(viewModelScope.coroutineContext)

    val users: LiveData<Result<List<User>>>
        get() = _users

}