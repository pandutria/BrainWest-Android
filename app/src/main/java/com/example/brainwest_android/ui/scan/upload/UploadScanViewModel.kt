package com.example.brainwest_android.ui.scan.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.brainwest_android.data.model.Prediction
import com.example.brainwest_android.data.repository.DetectionRepository
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.utils.Helper
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class UploadScanViewModel(val repo: DetectionRepository): ViewModel() {
    private val _predictionResult = MutableLiveData<State<Prediction>>()
    val predictionResult: LiveData<State<Prediction>> get() = _predictionResult

    fun uploadScan(image: MultipartBody.Part) {
        viewModelScope.launch {
            _predictionResult.postValue(State.Loading)
            try {
                val res = repo.prediction(image)

                if (res.isSuccessful) _predictionResult.postValue(State.Success(res.body()!!, "Get predict successfully!"))
                else _predictionResult.postValue(State.Error("Error get prediction"))
            } catch (e: Exception) {
                _predictionResult.postValue(State.Error(e.message!!))
                Helper.showErrorLog(e.message!!)
            }
        }
    }
}

class UploadScanViewModelFactory(private val repo: DetectionRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UploadScanViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UploadScanViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}