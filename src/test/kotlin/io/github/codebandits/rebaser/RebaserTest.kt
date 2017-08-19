package io.github.codebandits.rebaser

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.ByteBuffer
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

    private val SIZE_13_CHARSET = charArrayOf('!', '@', '£', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9')

    private val subject = Rebaser()

    @Test
    fun `Rebaser should encode using a charset of size 2`() {
        val encodedOutput = subject.encode("batman".toByteArray(), SIZE_2_CHARSET)
        assertEquals("xooxxxoxxooxxxxoxoooxoxxxooxooxoxooxxxxoxooxooox", encodedOutput)
    }

    @Test
    fun `Rebaser should decode using a charset of size 2`() {
        val decodedOutput = String(subject.decode("xooxxxoxxooxxxxoxoooxoxxxooxooxoxooxxxxoxooxooox", SIZE_2_CHARSET))
        assertEquals("batman", decodedOutput)
    }

    @Test
    fun `Rebaser should encode using a charset of size 13`() {
        val encodedOutput = subject.encode("batman".toByteArray(), SIZE_13_CHARSET)
        assertEquals("0!13!23100£3!223", encodedOutput)
    }

    @Test
    fun `Rebaser should decode using a charset of size 13`() {
        val decodedOutput = String(subject.decode("0!13!23100£3!223", SIZE_13_CHARSET))
        assertEquals("batman", decodedOutput)
    }

    @Test
    fun `Rebaser should mimic Base64's encoding of Strings`() {
        assertBase64StringEncode("Be sure to drink your Ovaltine")
        assertBase64StringEncode("Be sure to drink your Ovaltine.")
        assertBase64StringEncode("Be sure to drink your Ovaltine..")
        assertBase64StringEncode("Be sure to drink your Ovaltine...")
        assertBase64StringEncode("Be sure to drink your Ovaltine....")
        assertBase64StringEncode("Be sure to drink your Ovaltine.....")
    }

    @Test
    fun `Rebaser should mimic Base64's decoding of Strings`() {
        assertBase64StringDecode("QmUgc3VyZSB0byBkcmluayB5b3VyIE92YWx0aW5l")
        assertBase64StringDecode("QmUgc3VyZSB0byBkcmluayB5b3VyIE92YWx0aW5lLg")
        assertBase64StringDecode("QmUgc3VyZSB0byBkcmluayB5b3VyIE92YWx0aW5lLi4")
        assertBase64StringDecode("QmUgc3VyZSB0byBkcmluayB5b3VyIE92YWx0aW5lLi4u")
        assertBase64StringDecode("QmUgc3VyZSB0byBkcmluayB5b3VyIE92YWx0aW5lLi4uLg")
        assertBase64StringDecode("QmUgc3VyZSB0byBkcmluayB5b3VyIE92YWx0aW5lLi4uLi4")
    }

    @Test
    fun `Rebaser should mimic Base64's encoding of non-ASCII data`() {
        val data = byteArrayOf(-16, 8, -50, 77)

        val expectedEncodedString = Base64.getEncoder().withoutPadding().encodeToString(data)
        val encodedOutput = subject.encode(data, BASE_64_CHARSET)
        assertEquals(expectedEncodedString, encodedOutput)
    }

    @Test
    fun `Rebaser should mimic Base64's decoding of non-ASCII data`() {
        val encodedData = "8AjOTQ"

        val expectedDecodedString = Base64.getDecoder().decode(encodedData)
        val decodedOutput = subject.decode(encodedData, BASE_64_CHARSET)
        assertArrayEquals(expectedDecodedString, decodedOutput)
    }

    @Test
    fun `Rebaser should encode and decode messages with emoji`() {

        val message = "omg 🍕 lolz 🎷"
        val messageBytes = message.toByteArray()

        val encodedOutput = subject.encode(messageBytes, BASE_64_CHARSET)
        val decodedOutput = subject.decode(encodedOutput, BASE_64_CHARSET)

        assertArrayEquals(messageBytes, decodedOutput)
        assertEquals(message, String(decodedOutput))
    }

    @Test
    fun `Rebaser should encode and decode UUIDs`() {

        val uuid = UUID.randomUUID()

        val messageBytes = ByteBuffer.allocate(16)
                .putLong(uuid.mostSignificantBits)
                .putLong(uuid.leastSignificantBits)
                .array()

        val encodedOutput = subject.encode(messageBytes, BASE_64_CHARSET)
        val decodedOutput = subject.decode(encodedOutput, BASE_64_CHARSET)
        val byteBuffer = ByteBuffer.wrap(decodedOutput)

        assertEquals(uuid, UUID(byteBuffer.long, byteBuffer.long))
    }

    private fun assertBase64StringEncode(message: String) {
        val expectedEncodedString = Base64.getEncoder().withoutPadding().encodeToString(message.toByteArray())
        val encodedOutput: String = subject.encode(message.toByteArray(), BASE_64_CHARSET)
        assertEquals(expectedEncodedString, encodedOutput)
    }

    private fun assertBase64StringDecode(message: String) {
        val expectedDecodedString = String(Base64.getDecoder().decode(message))
        val decodedOutput = String(subject.decode(message, BASE_64_CHARSET))
        assertEquals(expectedDecodedString, decodedOutput)
    }
}
