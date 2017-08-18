package io.github.codebandits.rebaser

class Rebaser {

    private val base64Charset = charArrayOf(
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
    )

    fun encode(input: ByteArray): String {

        return input.asList().chunked(3)
                .map { it.toByteArray() }
                .map { mergeBytes(it) }
                .map { splitToNumbers(it) }
                .flatMap(IntArray::toList)
                .map { base64Charset.get(it) }
                .joinToString("")
    }

    fun decode(input: String): ByteArray {

        return input.toCharArray()
                .map(base64Charset::indexOf)
                .chunked(4)
                .map { mergeNumbers(it.toIntArray()) }
                .flatMap { splitToBytes(it).toList() }
                .toByteArray()
    }
}
