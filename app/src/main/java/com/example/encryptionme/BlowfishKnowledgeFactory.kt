package com.example.encryptionme

import android.os.Build
import androidx.annotation.RequiresApi
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.SecretKeySpec


class BlowfishKnowledgeFactory {
    @Throws(
        NoSuchAlgorithmException::class,
        NoSuchPaddingException::class,
        InvalidKeyException::class,
        IllegalBlockSizeException::class,
        BadPaddingException::class,
        UnsupportedEncodingException::class
    )
    fun encrypt (password: String, key: String): String {
        val KeyData = key.toByteArray()
        val KS = SecretKeySpec(KeyData, "Blowfish")
        val cipher = Cipher.getInstance("Blowfish")
        cipher.init(Cipher.ENCRYPT_MODE, KS)
        return Base64.getEncoder().encodeToString(cipher.doFinal(password.toByteArray(charset("UTF-8"))))
    }
    @Throws(
        NoSuchAlgorithmException::class,
        NoSuchPaddingException::class,
        InvalidKeyException::class,
        IllegalBlockSizeException::class,
        BadPaddingException::class
    )
    fun decrypt(encryptedtext: String?, key: String): String {
        val KeyData = key.toByteArray()
        val KS = SecretKeySpec(KeyData, "Blowfish")
        val ecryptedtexttobytes = Base64.getDecoder().decode(encryptedtext)
        val cipher = Cipher.getInstance("Blowfish")
        cipher.init(Cipher.DECRYPT_MODE, KS)
        val decrypted = cipher.doFinal(ecryptedtexttobytes)
        return String(decrypted, Charset.forName("UTF-8"))
    }
}