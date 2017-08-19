package io.github.codebandits.rebaser

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test

class bitRedistributersTest {

    @Test
    fun `redistributeBytesToInts should redistribute 3 bytes into 4 6-bit numbers`() {

        val output = redistributeBytesToInts(byteArrayOf(0b01100010, 0b01100001, 0b01110100), 6)

        assertArrayEquals(intArrayOf(0b011000, 0b100110, 0b000101, 0b110100), output)
    }

    @Test
    fun `redistributeBytesToInts should redistribute 4 bytes into 6 6-bit numbers with padding`() {

        val output = redistributeBytesToInts(byteArrayOf(0b01100010, 0b01100001, 0b01110100, 0b01101101), 6)

        assertArrayEquals(intArrayOf(0b011000, 0b100110, 0b000101, 0b110100, 0b011011, 0b010000), output)
    }

    @Test
    fun `redistributeBytesToInts should redistribute 4 bytes into 6 6-bit numbers with non-ASCII bytes`() {

        val output = redistributeBytesToInts(byteArrayOf(-16, -97, -90, -127), 6)

        assertArrayEquals(intArrayOf(0b111100, 0b001001, 0b111110, 0b100110, 0b100000, 0b010000), output)
    }

    @Test
    fun `redistributeBytesToInts should redistribute 3 bytes into 5 5-bit numbers with padding`() {

        val output = redistributeBytesToInts(byteArrayOf(0b01100010, 0b01100001, 0b01110100), 5)

        assertArrayEquals(intArrayOf(0b01100, 0b01001, 0b10000, 0b10111, 0b01000), output)
    }

    @Test
    fun `redistributeIntsToBytes should redistribute 4 6-bit numbers into 3 bytes`() {

        val output = redistributeIntsToBytes(intArrayOf(0b011000, 0b100110, 0b000101, 0b110100), 6)

        assertArrayEquals(byteArrayOf(0b01100010, 0b01100001, 0b01110100), output)
    }

    @Test
    fun `redistributeIntsToBytes should redistribute 5 5-bit numbers into 3 bytes`() {

        val output = redistributeIntsToBytes(intArrayOf(0b01100, 0b01001, 0b10000, 0b10111, 0b01000), 5)

        assertArrayEquals(byteArrayOf(0b01100010, 0b01100001, 0b01110100), output)
    }
}
