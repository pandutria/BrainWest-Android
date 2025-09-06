package com.example.brainwest_android.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.brainwest_android.R
import com.example.brainwest_android.data.model.User
import com.example.brainwest_android.data.repository.AuthRepository
import com.example.brainwest_android.utils.ApiErrorHandler
import com.example.brainwest_android.utils.Helper
import com.example.brainwest_android.utils.State
import kotlinx.coroutines.launch
import org.json.JSONObject

class RegisterViewModel(private val repo: AuthRepository): ViewModel() {
    private val _registerResult = MutableLiveData<State<User>>()
    val registerResult: LiveData<State<User>> get() = _registerResult

    fun register(
        username: String,
        fullname: String,
        password: String
    ) {
        if (username == "" || fullname == "" || password == "") {
            _registerResult.postValue(State.Error("Semua input wajib di isi!"))
            return
        }

        viewModelScope.launch {
            _registerResult.postValue(State.Loading)
            try {
                val res = repo.regsiter(username, fullname, password)

                if (res.isSuccessful) _registerResult.postValue(State.Success(res.body()!!.data, res.body()!!.message!!))
                else _registerResult.postValue(State.Error(ApiErrorHandler.parseError(res)))
            } catch (err: Exception) {
                _registerResult.postValue(State.Error("${err.message}"))
                Helper.showErrorLog(err.message ?: "Error tanpa pesan")
            }
        }
    }
}

class RegisterViewModelFactory(private val repo: AuthRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}