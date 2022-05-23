package com.example.encryptionme

import android.media.tv.TvContract.Programs.Genres.encode
import android.net.Uri.encode
import android.util.Base64.encode
import java.io.ByteArrayOutputStream
import java.net.URLEncoder.encode
import java.security.PrivateKey
import java.security.PublicKey
import java.util.*
import javax.crypto.Cipher

object RSA {
    val transformation = "RSA"
    val ENCRYPT_MAX_SIZE = 117
    val DECRYPT_MAX_SIZE = 256
    /**
     * Шифрование с закрытым ключом
     */
    fun encryptByPrivateKey(str: String, privateKey: PrivateKey): String {
        val byteArray = str.toByteArray()
        val cipher = Cipher.getInstance(transformation)
        cipher.init(Cipher.ENCRYPT_MODE, privateKey)

        // Определяем буфер
        var temp: ByteArray? = null
        // Текущее смещение
        var offset = 0

        val outputStream = ByteArrayOutputStream()

        while (byteArray.size - offset > 0) {
            // Оставшаяся часть больше максимального поля шифрования, тогда максимальная длина 117 байтов зашифрована
            if (byteArray.size - offset >= ENCRYPT_MAX_SIZE) {
                temp = cipher.doFinal(byteArray, offset, ENCRYPT_MAX_SIZE)
                // Смещение увеличено на 117
                offset += ENCRYPT_MAX_SIZE
            } else {
                // Если количество оставшихся байтов меньше 117, зашифровываем все остальные
                temp = cipher.doFinal(byteArray, offset, (byteArray.size - offset))
                offset = byteArray.size
            }
            outputStream.write(temp)
        }
        outputStream.close()
        return Base64.getEncoder().encodeToString(outputStream.toByteArray())
    }

    /**
     * Шифрование с открытым ключом
     */
    fun encryptByPublicKey(str: String, publicKey: PublicKey): String {
        val byteArray = str.toByteArray()
        val cipher = Cipher.getInstance(transformation)
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)

        var temp: ByteArray? = null
        var offset = 0

        val outputStream = ByteArrayOutputStream()

        while (byteArray.size - offset > 0) {
            if (byteArray.size - offset >= ENCRYPT_MAX_SIZE) {
                temp = cipher.doFinal(byteArray, offset, ENCRYPT_MAX_SIZE)
                offset += ENCRYPT_MAX_SIZE
            } else {
                temp = cipher.doFinal(byteArray, offset, (byteArray.size - offset))
                offset = byteArray.size
            }
            outputStream.write(temp)
        }

        outputStream.close()
        return Base64.getEncoder().encodeToString(outputStream.toByteArray())
    }

    /**
     * Расшифровка секретного ключа
     * Примечание Исключение в потоке "main" javax.crypto.IllegalBlockSizeException:
     * Data must not be longer than 256 bytes
     * Я также сбит с толку относительно того, что это 128 байтов или 256. Я написал эту ошибку, когда я написал 128, и она изменилась на 256, и все было в порядке
     */
    fun decryptByPrivateKey(str: String, privateKey: PrivateKey): String {
        val byteArray = Base64.getDecoder().decode(str)
        val cipher = Cipher.getInstance(transformation)
        cipher.init(Cipher.DECRYPT_MODE, privateKey)

        // Определяем буфер
        var temp: ByteArray? = null
        // Текущее смещение
        var offset = 0

        val outputStream = ByteArrayOutputStream()

        while (byteArray.size - offset > 0) {
            // Оставшаяся часть больше максимального поля дешифрования, затем максимальная длина ограничения шифрования
            if (byteArray.size - offset >= DECRYPT_MAX_SIZE) {
                temp = cipher.doFinal(byteArray, offset, DECRYPT_MAX_SIZE)
                // Смещение увеличено на 128
                offset += DECRYPT_MAX_SIZE
            } else {
                // Если количество оставшихся байтов меньше максимальной длины, расшифровываем все оставшиеся
                temp = cipher.doFinal(byteArray, offset, (byteArray.size - offset))
                offset = byteArray.size
            }
            outputStream.write(temp)
        }
        outputStream.close()
        return String(outputStream.toByteArray())
    }

    /**
     * Расшифровка открытого ключа
     */
    fun decryptByPublicKey(str: String, publicKey: PublicKey): String {
        val byteArray = Base64.getDecoder().decode(str)
        val cipher = Cipher.getInstance(transformation)
        cipher.init(Cipher.DECRYPT_MODE, publicKey)

        var temp: ByteArray? = null
        var offset = 0

        val outputStream = ByteArrayOutputStream()

        while (byteArray.size - offset > 0) {
            if (byteArray.size - offset >= DECRYPT_MAX_SIZE) {
                temp = cipher.doFinal(byteArray, offset, DECRYPT_MAX_SIZE)
                offset += DECRYPT_MAX_SIZE
            } else {
                temp = cipher.doFinal(byteArray, offset, (byteArray.size - offset))
                offset = byteArray.size
            }
            outputStream.write(temp)
        }
        outputStream.close()
        return String(outputStream.toByteArray())
    }
}