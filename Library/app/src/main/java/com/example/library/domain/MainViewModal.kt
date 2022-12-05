package com.example.library.domain

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.library.repository.BookModal

class MainViewModal : ViewModel() {
    val liveDataCurrent = MutableLiveData<BookModal>()
}