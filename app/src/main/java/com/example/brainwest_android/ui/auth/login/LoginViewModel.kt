package com.example.brainwest_android.ui.auth.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.brainwest_android.data.model.User
import com.example.brainwest_android.data.repository.AuthRepository
import com.example.brainwest_android.utils.ApiErrorHandler
import com.example.brainwest_android.utils.Helper
import com.example.brainwest_android.data.state.State
import kotlinx.coroutines.launch

class LoginViewModel(private val repo: AuthRepository): ViewModel() {
    private val _loginResult = MutableLiveData<State<User>>()
    val loginResult: LiveData<State<User>> get() = _loginResult

    fun login(
        username: String,
        password: String,
        context: Context
    ) {
        if (username == "" || password == "") {
            _loginResult.postValue(State.Error("Semua input wajib di isi!"))
            return
        }

        viewModelScope.launch {
            _loginResult.postValue(State.Loading)
            try {
                val res = repo.login(username, password, context)

                if (res.isSuccessful) _loginResult.postValue(State.Success(res.body()!!.data, res.body()!!.message!!))
                else _loginResult.postValue(State.Error(ApiErrorHandler.parseError(res)))
            } catch (err: Exception) {
                _loginResult.postValue(State.Error("${err.message}"))
                Helper.showErrorLog(err.message ?: "Error tanpa pesan")
            }
        }
    }
}


class LoginViewModelFactory(private val repo: AuthRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}