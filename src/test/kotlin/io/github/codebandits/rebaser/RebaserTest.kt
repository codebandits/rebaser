package io.github.codebandits.rebaser

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

class RebaserTest {

    private val subject = Rebaser()

    @Test
    fun `should mimic Base64 encode`() {
        assertEncode("Be sure to drink your Ovaltine")
        assertEncode("Be sure to drink your Ovaltine.")
        assertEncode("Be sure to drink your Ovaltine..")
        assertEncode("Be sure to drink your Ovaltine...")
        assertEncode("Be sure to drink your Ovaltine....")
        assertEncode("Be sure to drink your Ovaltine.....")
    }

    @Test
    fun `should mimic Base64 decode`() {
        assertDecode("QmUgc3VyZSB0byBkcmluayB5b3VyIE92YWx0aW5l")
        assertDecode("QmUgc3VyZSB0byBkcmluayB5b3VyIE92YWx0aW5lLg")
        assertDecode("QmUgc3VyZSB0byBkcmluayB5b3VyIE92YWx0aW5lLi4")
        assertDecode("QmUgc3VyZSB0byBkcmluayB5b3VyIE92YWx0aW5lLi4u")
        assertDecode("QmUgc3VyZSB0byBkcmluayB5b3VyIE92YWx0aW5lLi4uLg")
        assertDecode("QmUgc3VyZSB0byBkcmluayB5b3VyIE92YWx0aW5lLi4uLi4")
    }

    private fun assertEncode(message: String) {
        val expectedEncodedString = Base64.getEncoder().withoutPadding().encodeToString(message.toByteArray())
        val encodedOutput: String = subject.encode(message.toByteArray())
        assertEquals(expectedEncodedString, encodedOutput)
    }

    private fun assertDecode(message: String) {
        val expectedDecodedString = String(Base64.getDecoder().decode(message))
        val decodedOutput = String(subject.decode(message))
        assertEquals(expectedDecodedString, decodedOutput)
    }
}
