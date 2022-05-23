package com.example.encryptionme.ui.decrypt

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.encryptionme.*
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*

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
        val keyFactory = KeyFactory.getInstance("RSA")
        val publicKeyStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnvSR5VSoxCdhcgQzGkp8zSCOTUCP37YyVlJ8qzkRSxxNhB5OeKOP0OjhglAkn7v8i6SAR5ZDYwyLX6Y3W9+x5CoJ5bV3PpEzrq4qhv5JGBAE7q/ylR48y/3Aob/L04+4EuzFklzFILESmb7qsa5yKqtSO8Acvp2Ay0jJbzH6ZmhLkvW5C7+yoj/RnGWixop0EezxjV7wmI2aCHUAx4rUzbSoAhsonEYCRKS7MHx1t2M3vJBdinsnJEZ28tLXSdnTUrWzOK1gwYLfVW5ExQCLgOfGyweQ86ylgTDidjmeT4mpraqQHysLX/tv0FFhpUnxzK7P6DDWS3W0ep+o6eBvXQIDAQAB"
        val publicKey = keyFactory.generatePublic(X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyStr)))
        return when(methodId) {
            0 -> CesarAlgoritm().decrypt(text, key)
            1 -> GridMethod().decrypt(text)
            2 -> RailwayFenceMethod().decrypt(text, key)
            3 -> DES.decrypt(text, key.toString())
            4 -> AES.decrypt(text, key.toString())
            5 -> BlowfishKnowledgeFactory().decrypt(text, key.toString())
            6 -> RSA.decryptByPublicKey(text, publicKey)
            else -> {
                "Error"
            }
        }

    }
}