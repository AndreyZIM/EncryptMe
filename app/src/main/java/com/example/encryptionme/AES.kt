package com.example.encryptionme

import android.util.Base64
import java.lang.Exception
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object AES {

    fun encrypt(input:String,password:String): String {
        try {

            // Создать зашифрованный объект
            val cipher = Cipher.getInstance("AES")
            // Инициализация: шифрование / дешифрование
            val keySpec: SecretKeySpec = SecretKeySpec(password.toByteArray(), "AES")
            cipher.init(Cipher.ENCRYPT_MODE, keySpec)
            // шифрование
            val encrypt = cipher.doFinal(input.toByteArray())
            return android.util.Base64.encodeToString(encrypt, android.util.Base64.DEFAULT)
        } catch (e: Exception) {
            return "Error"
        }
    }
    // Расшифровать
    fun decrypt(input:String,password:String): String {
        try {
            // Создать зашифрованный объект
            val cipher = Cipher.getInstance("AES")
            // Инициализация: шифрование / дешифрование
            val keySpec: SecretKeySpec = SecretKeySpec(password.toByteArray(), "AES")
            cipher.init(Cipher.DECRYPT_MODE, keySpec)
            // Поскольку переданная строка зашифрована Base64, расшифровка Base64
            val encrypt = cipher.doFinal(Base64.decode(input, android.util.Base64.DEFAULT))
            return String(encrypt)
        } catch (e: Exception) {
            return "Error"
        }
    }

}