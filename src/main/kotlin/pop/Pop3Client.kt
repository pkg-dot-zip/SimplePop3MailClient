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

/**
 * Very simplistic Pop3 client example made for university.
 */
class Pop3Client(
    private val server: String,
    private val port: Int,
    private val username: String,
    private val password: String
) {

    private lateinit var reader: BufferedReader
    private lateinit var writer: BufferedWriter

    /**
     * Establishes an SSL socket connection.
     * Then runs [login].
     */
    fun connect() {
        try {
            // Create SSL socket connection.
            val socketFactory = SSLSocketFactory.getDefault() as SSLSocketFactory
            val socket = socketFactory.createSocket(server, port) as Socket

            reader = BufferedReader(InputStreamReader(socket.getInputStream()))
            writer = BufferedWriter(OutputStreamWriter(socket.getOutputStream()))

            logger.info { "Connected to POP3 server" }

            // Read server response.
            logger.info { reader.readLine() }

            login()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // TODO: Make it return a boolean false on fail instead of throwing an exception.
    /**
     * Sends `USER` and `PASS` to the server with [username] & [password] respectively.
     *
     * @throws Exception If login failed.
     */
    private fun login() {
        sendCommand("USER $username")
        logger.info { reader.readLine() }

        sendCommand("PASS $password")
        val loginResponse = reader.readLine()
        logger.info { loginResponse }

        if (!loginResponse.startsWith("+OK")) {
            throw Exception("Login failed: $loginResponse")
        }
    }

    /**
     * Sends `LIST` to the server, then parses all the output as [MailEntry]s.
     */
    fun getMails(): List<MailEntry> {
        val listToReturn = mutableListOf<MailEntry>()

        try {
            // Send LIST command to get the list of emails.
            sendCommand("LIST")

            val listResponse = reader.readLine()
            logger.info { "LIST RESPONSE: $listResponse" }  // Read response to LIST command so we know how many responses well get.

            // Return empty list of no messages were found.
            if (listResponse.startsWith("+OK 0")) {
                logger.info { "No messages in the inbox. Returning." }
                return emptyList()
            }

            // Keep looping as long as there is input and there is no end (POP ends multiline with dots!!!)
            var line: String
            while (reader.readLine().also { line = it } != null && line != ".") {
                logger.trace { line }
                listToReturn.add(MailEntry(line))
            }

            return listToReturn
        } catch (e: Exception) {
            logger.error(e) { "Error retrieving mail ids" }
            throw e
        }
    }

    /**
     * Returns an instance of [MailHeaders] for the specified [email].
     *
     * @param email Email to retrieve id from.
     */
    fun getHeaders(email: MailEntry): MailHeaders = getHeaders(email.mailID)
    private fun getHeaders(emailId: Int): MailHeaders {
        val headers = MailHeaders()

        // Use TOP command to fetch the headers of the email. 0 prints all the headers! 😀
        sendCommand("TOP $emailId 0")
        logger.info { "Fetching headers for message ID: $emailId" }

        // Again, multiline responses end in dots.
        var line: String
        while (reader.readLine().also { line = it } != null && line != ".") {
            when {
                line.startsWith("Subject: ") -> {
                    headers.subject = line.substring(9).trim()
                    logger.trace { "Subject: ${headers.subject}" }
                }

                line.startsWith("From: ") -> {
                    headers.from = line.substring(6).trim()
                    logger.trace { "From: ${headers.from}" }
                }

                line.startsWith("To: ") -> {
                    headers.to = line.substring(4).trim()
                    logger.trace { "To: ${headers.to}" }
                }

                line.startsWith("Date: ") -> {
                    headers.date = line.substring(6).trim()
                    logger.trace { "Date: ${headers.date}" }
                }

                line.startsWith("Return-Path: ") -> {
                    headers.returnPath = line.substring(14).trim()
                    logger.trace { "Return-Path: ${headers.returnPath}" }
                }

                line.startsWith("Received: ") -> {
                    headers.received = line.substring(10).trim()
                    logger.trace { "Received: ${headers.received}" }
                }

                line.startsWith("Message-ID: ") -> {
                    headers.messageID = line.substring(12).trim()
                    logger.trace { "Message-ID: ${headers.messageID}" }
                }

                line.startsWith("MIME-Version: ") -> {
                    headers.mimeVersion = line.substring(14).trim()
                    logger.trace { "MIME-Version: ${headers.mimeVersion}" }
                }

                line.startsWith("Content-Type: ") -> {
                    headers.contentType = line.substring(14).trim()
                    logger.trace { "Content-Type: ${headers.contentType}" }
                }
            }
        }

        return headers
    }

    /**
     * Writes [command] to the [writer]. In practice this means it'll be send to the server.
     *
     * @param command Command to send.
     */
    private fun sendCommand(command: String) {
        logger.info { "Sending command: $command" }

        writer.write(command)
        writer.write("\r\n")
        writer.flush()
    }
}