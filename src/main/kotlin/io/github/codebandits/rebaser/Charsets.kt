package io.github.codebandits.rebaser

object Charsets {
    val BASE_64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray()
    val BASE_64_URL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_".toCharArray()
    val Z_BASE_32 = "ybndrfg8ejkmcpqxot1uwisza345h769".toCharArray()
}
