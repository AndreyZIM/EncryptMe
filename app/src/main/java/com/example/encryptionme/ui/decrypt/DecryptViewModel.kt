package com.example.encryptionme.ui.decrypt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.encryptionme.*

class DecryptViewModel : ViewModel() {

    private var methodId: Int = -1
    private val _text = MutableLiveData<String>().apply {
        value = ""
    }
    val text: LiveData<String> = _text

    fun changeMethod(methodId: Int) {
        this.methodId = methodId
    }

    fun decryptText(text: String, key: Int): String? {
        return when(methodId) {
            0 -> CesarAlgoritm().decrypt(text, key)
            1 -> GridMethod().decrypt(text)
            2 -> RailwayFenceMethod().decrypt(text, key)
            3 -> DES.decrypt(text, key.toString())
            4 -> AES.decrypt(text, key.toString())
            else -> {
                "Error"
            }
        }

    }
}