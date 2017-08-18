package io.github.codebandits.rebaser

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class FunctionsTest {

    @Test
    fun `should split a 24-bit number into 6-bit numbers`() {

        val input = 6447476L

        val output = splitToNumbers(input)

        Assertions.assertArrayEquals(intArrayOf(24, 38, 5, 52), output)
    }

    @Test
    fun `should merge 3 bytes into a 24-bit number`() {

        val bytes = "bat".toByteArray()

        val output = mergeBytes(bytes)

        Assertions.assertEquals(6447476L, output)
    }

    @Test
    fun `should split a 24-bit number into 3 bytes`() {

        val output = splitToBytes(6447476L)

        Assertions.assertArrayEquals("bat".toByteArray(), output)
    }

    @Test
    fun `should merge 6-bit numbers into a 24-bit number`() {
        val output = mergeNumbers(intArrayOf(24, 38, 5, 52))

        Assertions.assertEquals(6447476L, output)
    }
}