package com.pkg_dot_zip

import com.pkg_dot_zip.pop.Pop3Client
import io.github.cdimascio.dotenv.Dotenv

fun main() {
    val dotenv = Dotenv.load()

    val pop3Server = "mail.privateemail.com" // Hardcoded. This is where I have my mail :D
    val pop3Port = 995
    val username = dotenv.get("MAIL_USERNAME") // I've hidden these away in a .env file, obviously.
    val password = dotenv.get("MAIL_PASSWORD") // I've hidden these away in a .env file, obviously.

    val client = Pop3Client(pop3Server, pop3Port, username, password)
    client.connect()
    client.getMailSubjects()
}