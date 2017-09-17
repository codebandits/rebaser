package io.github.codebandits.rebaser


class Rebaser {

    fun encode(input: ByteArray, charset: CharArray): String {

        val bitsPerCharacter = bitsPerCharacter(charset.size)
        val bytesAsInts = redistributeBytesToInts(bytes = input, bitsPerInt = bitsPerCharacter)
        return bytesAsInts
                .map { charset[it] }
                .joinToString("")
    }

    fun decode(input: String, charset: CharArray): ByteArray {

        val stringAsInts = input.toCharArray().map(charset::indexOf).toIntArray()
        return redistributeIntsToBytes(ints = stringAsInts, bitsPerInt = bitsPerCharacter(charset.size))
    }

    private fun bitsPerCharacter(charsetSize: Int): Int = (Math.log(charsetSize.toDouble()) / Math.log(2.0)).toInt()
}
