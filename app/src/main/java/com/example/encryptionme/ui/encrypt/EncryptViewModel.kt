package com.example.encryptionme.ui.encrypt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.encryptionme.*

class EncryptViewModel : ViewModel() {

    private var methodId: Int = -1
    private val _text = MutableLiveData<String>().apply {
        value = ""
    }
    val text: LiveData<String> = _text

    fun changeMethod(methodId: Int) {
       this.methodId = methodId
    }

    fun encryptText(text: String, key: String): String? {
        val keyInt = Integer.parseInt(key)
        return when(methodId) {
            0 -> CesarAlgoritm().encrypt(text, keyInt)
            1 -> GridMethod().encrypt(text)
            2 -> RailwayFenceMethod().encrypt(text, keyInt)
            3 -> DES.encrypt(text, key)
            4 -> AES.encrypt(text, key)
            else -> {
                "Error"
            }
        }
    }

}