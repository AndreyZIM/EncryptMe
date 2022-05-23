package com.example.encryptionme

class RailwayFenceMethod {

    fun encrypt(text: String, k: Int): String {
        var rail = Array(k) { Array(text.length) { '\n' } }
        var dirDown = false
        var row = 0
        var col = 0
        for (i in text.indices) {
            if (row == 0 || row == k - 1) {
                dirDown = !dirDown
            }
            rail[row][col++] = text[i]
            if (dirDown) row++ else row--
        }
        val out = StringBuilder()
        for (i in 0 until k)
            for (j in text.indices)
                if (rail[i][j] != '\n')
                    out.append(rail[i][j])
        return out.toString()
    }

    fun decrypt(text: String, k: Int): String {
        var rail = Array(k) { Array(text.length) { '\n' } }
        var dirDown = false
        var row = 0
        var col = 0
        for (i in text.indices) {
            if (row == 0)
                dirDown = true
            if (row == k - 1)
                dirDown = false
            rail[row][col++] = '*';
            if (dirDown) row++ else row--
        }
        var index = 0
        for (i in 0 until k)
            for (j in text.indices)
                if (rail[i][j] == '*' && index < text.length)
                    rail[i][j] = text[index++]
        val out = StringBuilder()
        row = 0
        col = 0
        for (i in text.indices) {
            if (row == 0)
                dirDown = true
            if (row == k - 1)
                dirDown = false
            if (rail[row][col] != '*')
                out.append(rail[row][col++])
            if (dirDown) row++ else row--
        }
        return out.toString()
    }
}

