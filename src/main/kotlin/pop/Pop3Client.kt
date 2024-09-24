package com.pkg_dot_zip.pop

import io.github.oshai.kotlinlogging.KotlinLogging
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.Socket
import javax.net.ssl.SSLSocketFactory

// Site used to get more info about individual commands: https://kb.datamotion.com/?ht_kb=how-to-utilize-pop3-protocol-to-access-datamotion-securemail-and-direct-accounts

private val logger = KotlinLogging.logger {}

class Pop3Client(
    private val server: String,
    private val port: Int,
    private val username: String,
    private val password: String
) {

    private lateinit var reader: BufferedReader
    private lateinit var writer: BufferedWriter

    fun connect() {
        try {
            // Create SSL socket connection
            val socketFactory = SSLSocketFactory.getDefault() as SSLSocketFactory
            val socket = socketFactory.createSocket(server, port) as Socket

            reader = BufferedReader(InputStreamReader(socket.getInputStream()))
            writer = BufferedWriter(OutputStreamWriter(socket.getOutputStream()))

            logger.info { "Connected to POP3 server" }

            // Read server response
            logger.info { reader.readLine() }

            // Log in
            sendCommand("USER $username")
            logger.info { reader.readLine() }

            sendCommand("PASS $password")
            val loginResponse = reader.readLine()
            logger.info { loginResponse }

            if (!loginResponse.startsWith("+OK")) {
                throw Exception("Login failed: $loginResponse")
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getMailSubjects() {
        try {
            // Send LIST command to get the list of emails
            sendCommand("LIST")
            logger.info { "LIST RESPONSE: ${reader.readLine()}" }  // Read response to LIST command

            // Fetch individual email headers (e.g., subject)
            var line: String
            while (reader.readLine().also { line = it } != null && line != ".") {
                logger.info { "Mail Item: $line" }  // Each email item
                val emailId = line.split(" ")[0]
                getSubject(emailId)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getSubject(emailId: String) {
        // Use TOP command to fetch the headers of the email (first 0 lines of the body)
        sendCommand("TOP $emailId 0")
        var line: String
        var subject = ""
        while (reader.readLine().also { line = it } != null && line != ".") {
            if (line.startsWith("Subject: ")) {
                subject = line.substring(9)
                logger.info { "Subject: $subject" }
            }
        }
    }

    private fun sendCommand(command: String) {
        logger.info { "Sending command: $command" }

        writer.write(command)
        writer.write("\r\n")
        writer.flush()
    }
}