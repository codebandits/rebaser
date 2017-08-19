package io.github.codebandits.rebaser

private val TWOS_COMPLEMENT_MASK = 0b11111111

internal fun redistributeBytesToInts(bytes: ByteArray, bitsPerInt: Int): IntArray {

    val bitsPerByte = 8

    var bucket = 0
    var bitsInTheBucket = 0
    val intList = mutableListOf<Int>()

    for (byte in bytes) {

        bucket = bucket shl bitsPerByte
        bucket += byte.toInt() and TWOS_COMPLEMENT_MASK

        bitsInTheBucket += bitsPerByte

        while (bitsInTheBucket >= bitsPerInt) {
            val shift = bitsInTheBucket - bitsPerInt
            val mask = 2.pow(bitsInTheBucket) - 2.pow(shift)
            intList.add(bucket and mask shr shift)
            bitsInTheBucket -= bitsPerInt
        }
    }

    if (bitsInTheBucket > 0) {
        val shift = bitsPerInt - bitsInTheBucket
        val mask = 2.pow(bitsPerInt) - 1
        intList.add(bucket shl shift and mask)
    }

    return intList.toIntArray()
}

internal fun redistributeIntsToBytes(ints: IntArray, bitsPerInt: Int): ByteArray {

    val bitsPerByte = 8

    var bucket = 0
    var bitsInTheBucket = 0
    val byteList = mutableListOf<Byte>()

    for (int in ints) {

        bucket = bucket shl bitsPerInt
        bucket += int

        bitsInTheBucket += bitsPerInt

        while (bitsInTheBucket >= bitsPerByte) {
            val shift = bitsInTheBucket - bitsPerByte
            val mask = 2.pow(bitsInTheBucket) - 2.pow(shift)
            byteList.add((bucket and mask shr shift).toByte())
            bitsInTheBucket -= bitsPerByte
        }
    }

    return byteList.toByteArray()
}

internal fun Int.pow(power: Int): Int = Math.pow(this.toDouble(), power.toDouble()).toInt()
