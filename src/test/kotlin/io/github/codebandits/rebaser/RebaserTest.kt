package io.github.codebandits.rebaser

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

class RebaserTest {

    private val subject = Rebaser()

    @Test
    fun `should mimic Base64 encode and decode`() {
        val message = "Be sure to drink your Ovaltine"
        val expectedEncodedString = Base64.getEncoder().encodeToString(message.toByteArray())

        val encodedOutput: String = subject.encode(message.toByteArray())
        assertEquals(expectedEncodedString, encodedOutput)

        val decodedOutput = String(subject.decode(encodedOutput))
        assertEquals(message, decodedOutput)
    }
}
