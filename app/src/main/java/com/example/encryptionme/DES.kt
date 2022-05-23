package com.example.encryptionme

import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
import java.security.Key
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec

object DES {

    fun encrypt(input:String, password:String): String {
        try {


            // 1. Создать шифр
            val c = Cipher.getInstance("DES")
            // 2. Инициализировать шифр (параметр 1: режим шифрования / дешифрования)
            val kf = SecretKeyFactory.getInstance("DES")
            val keySpec = DESKeySpec(password.toByteArray())

            val key: Key? = kf.generateSecret(keySpec)
            c.init(Cipher.ENCRYPT_MODE, key)
            // 3. Шифрование / дешифрование
            val encrypt = c.doFinal(input.toByteArray())
            return android.util.Base64.encodeToString(encrypt, android.util.Base64.DEFAULT)
        } catch (e: Exception) {
            return "Error"
        }
    }
    // дешифрование
    fun decrypt(input:String, password:String): String {
        try {

            // 1. Создать шифр
            val c = Cipher.getInstance("DES")
            // 2. Инициализировать шифр (параметр 1: режим шифрования / дешифрования)
            val kf = SecretKeyFactory.getInstance("DES")
            val keySpec = DESKeySpec(password.toByteArray())

            val key: Key? = kf.generateSecret(keySpec)
            c.init(Cipher.DECRYPT_MODE, key)
            // 3. Шифрование / дешифрование
            val encrypt = c.doFinal(Base64.decode(input, android.util.Base64.DEFAULT))
            return String(encrypt)
        } catch (e: Exception) {
            return "Error"
        }
    }

}