package io.github.codebandits.rebaser

import kotlin.math.log

class Rebaser {

    private val base64Charset = charArrayOf(
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
    )

    fun encode(input: ByteArray): String {
        val bitsPerCharacter = bitsPerCharacter(base64Charset.size)
        val bytesAsInts = redistributeBytesToInts(bytes = input, bitsPerInt = bitsPerCharacter)
        return bytesAsInts
                .map { base64Charset[it] }
                .joinToString("")
    }

    fun decode(input: String): ByteArray {
        val stringAsInts = input.toCharArray().map(base64Charset::indexOf).toIntArray()
        return redistributeIntsToBytes(ints = stringAsInts, bitsPerInt = bitsPerCharacter(base64Charset.size))
    }

    private fun bitsPerCharacter(charsetSize: Int) = log(a = charsetSize.toDouble(), base = 2.0).toInt()
}
