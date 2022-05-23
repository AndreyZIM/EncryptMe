package com.example.encryptionme

//fun cesarMethod(text: String?) {
//    if (text == null) return
//    println("Метод Цезаря")
//    println("Введите цифровой ключ: ")
//    val keyString: String? = readLine()
//    val key = Integer.parseInt(keyString)
//    println("Ключ равен: $key")
//    val encryptedText = encryptSimple(text = text, key)
//    println (encryptedText?.let {
//        "Зашифрованный текст: $encryptedText"
//    } ?: "Ошибка при шифровании. Попробуйте снова"
//    )
//    val decryptedText = com.example.encryptionme.decrypt(text = encryptedText!!, key)
//    println (decryptedText?.let {
//        "Расшифрованный текст: $decryptedText"
//    } ?: "Ошибка при дешифровании. Попробуйте снова"
//    )
//}

class CesarAlgoritm {


    fun encrypt(text: String, key: Int): String? {
        if (text.isEmpty() || key < 1) return null
        var result = ""
        for (symbol in text) {
            val encryptedSymbol = encryptSymbolSimple(symbol, key)
            encryptedSymbol?.let {
                result += encryptedSymbol
            } ?: return null
        }
        return result
    }

    private fun encryptSymbolSimple(symbol: Char, key: Int): Char? {
        val i = symbol.code
        return ((i + key).mod(255)).toChar()

    }

    fun decrypt(text: String, key: Int): String? {
        if (text.isEmpty() || key < 1) return null
        var result: String = ""
        for (symbol in text) {
            val decryptedSymbol = decryptSymbolSimple(symbol, key)
            decryptedSymbol?.let {
                result += decryptedSymbol
            } ?: return null
        }
        return result
    }

    private fun decryptSymbolSimple(symbol: Char, key: Int): Char? {
        val i = symbol.code

        return (i - key).mod(255).toChar()
    }

}