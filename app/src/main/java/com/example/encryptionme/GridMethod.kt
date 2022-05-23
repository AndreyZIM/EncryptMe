package com.example.encryptionme

class GridMethod {

    fun encrypt(text: String): String {
        val lattice: Array<IntArray> = Array(4) { IntArray(4) { 0 } }
        lattice[0] = intArrayOf(1, 2, 3, 1)
        lattice[1] = intArrayOf(3, 4, 4, 2)
        lattice[2] = intArrayOf(2, 4, 4, 3)
        lattice[3] = intArrayOf(1, 3, 2, 1)

        val pairs = listOf(0 to 0, 1 to 3, 3 to 1, 2 to 2)
        val lengthSubstring = pairs.size * pairs.size
        val shiftStringBuilder = text.let { StringBuilder(it.length) }
        var result: String = ""
        for (indexSubString in text.indices step lengthSubstring) {
            var substringInitString: String
            if (indexSubString <= text.length - lengthSubstring) {
                substringInitString = text.substring(indexSubString, lengthSubstring)
            } else {
                substringInitString = text.substring(indexSubString)
                shiftStringBuilder.append(substringInitString)
                break
            }

            val shiftSubStringBuilder = CharArray(lengthSubstring)

            for (indexPairs in pairs.indices) {
                val initPair = pairs[indexPairs]
                var pair = pairs[indexPairs]
                var indexRotates = 0
                do {
                    shiftSubStringBuilder[pair.first * pairs.size + pair.second] =
                        substringInitString[indexPairs + pairs.size * indexRotates]
                    ++indexRotates
                    pair = rotatePair(pair, pairs.size)
                } while (initPair != pair)
            }
            val resultFor = String(shiftSubStringBuilder)
            result = resultFor
            shiftStringBuilder?.append(result)
        }
        return result
    }

    fun decrypt(text: String?): String {

        val lattice: Array<IntArray> = Array(4) { IntArray(4) { 0 } }
        lattice[0] = intArrayOf(1, 2, 3, 1)
        lattice[1] = intArrayOf(3, 4, 4, 2)
        lattice[2] = intArrayOf(2, 4, 4, 3)
        lattice[3] = intArrayOf(1, 3, 2, 1)

        val pairs = listOf(0 to 0, 1 to 3, 3 to 1, 2 to 2)
        val lengthSubstring = pairs.size * pairs.size
        val shiftStringBuilder = text?.let { StringBuilder(it.length) }
        var result = ""
        val newInitStringBuilder = shiftStringBuilder?.let { StringBuilder(it.length) }
        val shiftString = shiftStringBuilder.toString()
        for (indexSubStrings in shiftString.indices step lengthSubstring) {
            var substringShiftString: String
            if (indexSubStrings <= shiftString.length - lengthSubstring) {
                substringShiftString = shiftString.substring(indexSubStrings, lengthSubstring)
            } else {
                substringShiftString = shiftString.substring(indexSubStrings)
                newInitStringBuilder?.append(substringShiftString)
                break
            }

            val initSubStringBuilder = arrayOfNulls<Char>(lengthSubstring)
            for (indexPairs in pairs.indices) {
                val initPair = pairs[indexPairs]
                var pair = pairs[indexPairs]
                var indexRotates = 0
                do {
                    initSubStringBuilder[indexRotates * pairs.size + indexPairs] =
                        substringShiftString[pair.first * pairs.size + pair.second]
                    ++indexRotates
                    pair = rotatePair(pair, pairs.size)
                } while (initPair != pair)
            }
            var resultFor = ""
            for (c in initSubStringBuilder) {
                resultFor += c
            }
            result = resultFor
        }
        return result
    }

    private fun rotatePair(pair: Pair<Int, Int>, length: Int): Pair<Int, Int> {
        return (length - pair.second - 1) to pair.first
    }

}



