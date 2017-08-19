package io.github.codebandits.rebaser

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.charset.Charset
import java.util.*

class RebaserTest {

    private val BASE_64_CHARSET = charArrayOf(
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
    )

    private val SIZE_2_CHARSET = charArrayOf('x', 'o')

    private val SIZE_13_CHARSET = charArrayOf('!', '*', '@', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9')

    private val subject = Rebaser()

    @Test
    fun `encode should encode using a charset of size 2`() {
        val encodedOutput = subject.encode("batman".toByteArray(), SIZE_2_CHARSET)
        assertEquals("xooxxxoxxooxxxxoxoooxoxxxooxooxoxooxxxxoxooxooox", encodedOutput)
    }

    @Test
    fun `decode should decode using a charset of size 2`() {
        val decodedOutput = String(subject.decode("xooxxxoxxooxxxxoxoooxoxxxooxooxoxooxxxxoxooxooox", SIZE_2_CHARSET))
        assertEquals("batman", decodedOutput)
    }

    @Test
    fun `encode should encode using a charset of size 13`() {
        val encodedOutput = subject.encode("batman".toByteArray(), SIZE_13_CHARSET)
        assertEquals("0!13!23100@3!223", encodedOutput)
    }

    @Test
    fun `decode should decode using a charset of size 13`() {
        val decodedOutput = String(subject.decode("0!13!23100@3!223", SIZE_13_CHARSET))
        assertEquals("batman", decodedOutput)
    }

    @Test
    fun `should mimic Base64 encode`() {
        assertBase64Encode("Be sure to drink your Ovaltine")
        assertBase64Encode("Be sure to drink your Ovaltine.")
        assertBase64Encode("Be sure to drink your Ovaltine..")
        assertBase64Encode("Be sure to drink your Ovaltine...")
        assertBase64Encode("Be sure to drink your Ovaltine....")
        assertBase64Encode("Be sure to drink your Ovaltine.....")
    }

    @Test
    fun `should mimic Base64 decode`() {
        assertBase64Decode("QmUgc3VyZSB0byBkcmluayB5b3VyIE92YWx0aW5l")
        assertBase64Decode("QmUgc3VyZSB0byBkcmluayB5b3VyIE92YWx0aW5lLg")
        assertBase64Decode("QmUgc3VyZSB0byBkcmluayB5b3VyIE92YWx0aW5lLi4")
        assertBase64Decode("QmUgc3VyZSB0byBkcmluayB5b3VyIE92YWx0aW5lLi4u")
        assertBase64Decode("QmUgc3VyZSB0byBkcmluayB5b3VyIE92YWx0aW5lLi4uLg")
        assertBase64Decode("QmUgc3VyZSB0byBkcmluayB5b3VyIE92YWx0aW5lLi4uLi4")
    }

    private fun assertBase64Encode(message: String) {
        val expectedEncodedString = Base64.getEncoder().withoutPadding().encodeToString(message.toByteArray())
        val encodedOutput: String = subject.encode(message.toByteArray(), BASE_64_CHARSET)
        assertEquals(expectedEncodedString, encodedOutput)
    }

    private fun assertBase64Decode(message: String) {
        val expectedDecodedString = String(Base64.getDecoder().decode(message))
        val decodedOutput = String(subject.decode(message, BASE_64_CHARSET))
        assertEquals(expectedDecodedString, decodedOutput)
    }
}
