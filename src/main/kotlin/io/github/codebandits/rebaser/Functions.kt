package io.github.codebandits.rebaser

internal fun splitToNumbers(joinedBytes: Long): IntArray =
        intArrayOf(
                (joinedBytes shr 18 and 63).toInt(),
                (joinedBytes shr 12 and 63).toInt(),
                (joinedBytes shr 6 and 63).toInt(),
                (joinedBytes and 63).toInt()
        )

internal fun mergeBytes(bytes: ByteArray): Long =
        (bytes[0].toLong() shl 16) + (bytes[1].toLong() shl 8) + bytes[2].toLong()

internal fun splitToBytes(input: Long): ByteArray =
        byteArrayOf((input shr 16).toByte(), (input shr 8).toByte(), (input).toByte())

internal fun mergeNumbers(ints: IntArray): Long =
        (ints[0] shl 18).toLong() + (ints[1] shl 12).toLong() + (ints[2] shl 6).toLong() + ints[3].toLong()