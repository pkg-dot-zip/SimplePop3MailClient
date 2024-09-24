package com.pkg_dot_zip

import com.pkg_dot_zip.pop.Pop3Client
import io.github.cdimascio.dotenv.Dotenv
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

const val pop3Server = "mail.privateemail.com" // Hardcoded. This is where I have my mail :D
const val pop3Port = 995

fun main() {
    val dotenv = Dotenv.load()

    // I've hidden these away in a .env file, obviously. 😎
    val username = dotenv.get("MAIL_USERNAME")
    val password = dotenv.get("MAIL_PASSWORD")

    val client = Pop3Client(pop3Server, pop3Port, username, password)
    client.connect()

    val mails = client.getMails() // Runs LIST and retrieves ids and sizes.

    mails.forEach { mail ->
        logger.info { client.getHeaders(mail).subject } // Retrieves all headers and then prints out the subject.
    }
}