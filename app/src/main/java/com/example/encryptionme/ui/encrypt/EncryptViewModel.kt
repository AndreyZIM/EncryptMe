package com.example.encryptionme.ui.encrypt

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.encryptionme.*
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.spec.PKCS8EncodedKeySpec
import java.util.*

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
        val keyFactory = KeyFactory.getInstance("RSA")
        val privateKeyStr = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCe9JHlVKjEJ2FyBDMaSnzNII5NQI/ftjJWUnyrORFLHE2EHk54o4/Q6OGCUCSfu/yLpIBHlkNjDItfpjdb37HkKgnltXc+kTOuriqG/kkYEATur/KVHjzL/cChv8vTj7gS7MWSXMUgsRKZvuqxrnIqq1I7wBy+nYDLSMlvMfpmaEuS9bkLv7KiP9GcZaLGinQR7PGNXvCYjZoIdQDHitTNtKgCGyicRgJEpLswfHW3Yze8kF2KeyckRnby0tdJ2dNStbM4rWDBgt9VbkTFAIuA58bLB5DzrKWBMOJ2OZ5PiamtqpAfKwtf+2/QUWGlSfHMrs/oMNZLdbR6n6jp4G9dAgMBAAECggEAekLTFPmQ9Y70vKXOSKKSa5Rm37SQ9RlGjm7TxT8XthYG6WAGK3Ri4eS9z2WlRddp4F6e7HD/U+gKK3/FhL0CLuTGyBBTr6QvhGQiAcMEpaVspcpfY6LmUGDVEZRcSlV419WWAYWpegO4stjN2+y5k2hC0AJsGZE7xyAtD4H5f1nvEbob0z/c9vlEYIUVZGsBf2PC7IPev64FLuGQE7/AsoM6jFh2etiCEkAbOUzHbhH3RAHajO7GdIPLBvFKGwYNKUh8lAKxQ11uZRjUQ2CgsawkgILV/DyqYoXJupsa0Q+ss0NVOkDf3tRbLc0fAmnpOjG78AgUIXNodOGzIEskYQKBgQDrFE8OV6k1rriW5O84kIxlU1J/jHl67C1R/fjrdZzJ7YzjqKwQo0GQhUiWyAJtoqGmMhaLgVi3VrbGUCPGJiaN/NuTPRWxGi/VXaJMPs+K2yErSyMEWmo6lcGjD2kOW7ZMUDoWMp3loNlliZPjMZ8Y3mZ3WeIv/vuxhpt+F0GgrwKBgQCtGfe7qLFxqZfpICebM4MV9tjWwr2q9boWFWuNg0DxTw0WyWQHQfNa+0afOIj6qMIxBJlvzV0Bet71XW5XKTmWbuQhYcbeoUcBBFB0DdM2Glret1A46KJOzxMz3HNAdkVhK028hr784DlE+Tg1SEWAYWidSlC9u80L49XPZgd7swKBgD7sMrT+Fda+q74IDVgwqMO+Z8ioSyPx77eQqX4s/wi1ww506YmSiUwrwOBLLQs3itk3cv1oY9y/IzE15j11nMBIvGVO5m1/Oup7o6OQ9HCQcvJprDfQE7sWtrv0tgQX3FXU65dheQ4r3cTl7GXVtGYtsXOk5Xw/XhOImjpH81MVAoGAQgIX/OB8Icq5GfXgBIflIdgKogKKzwl7F3a9l64IcrxhUmIjmbzlbrlJGeg2G9eEjaqiVAbsw2a2ZLxnGienRR0uMyiU7Ep1yAZ8I3UuKIBuTGV82uajFghS20DiVh+Dn2Ui9JQxej6KuCmM7IyNrEH44Zn4JhHaRAFyg+71RY8CgYEAmZVTkXFJXoIw8TAcV4968Uf/hfTwqWXc6uHAc/TAP4JNyi2+7TJ4xRziNenETcT9JByV8iw3iBjGwgeXr8BOzvPzs91foyG9elCK+WHBfT5XC9EssTwFXNTGdtcxG3fusHomwZlJnsuaHcBe0bX5HB0MXxJ3n4PjhvVQ+8qwuv0="
        val privateKey = keyFactory.generatePrivate(PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyStr)))
        return when(methodId) {
            0 -> CesarAlgoritm().encrypt(text, keyInt)
            1 -> GridMethod().encrypt(text)
            2 -> RailwayFenceMethod().encrypt(text, keyInt)
            3 -> DES.encrypt(text, key)
            4 -> AES.encrypt(text, key)
            5 -> BlowfishKnowledgeFactory().encrypt(text, key)
            6 -> RSA.encryptByPrivateKey(text, privateKey)
            else -> {
                "Error"
            }
        }
    }

}