package com.pkg_dot_zip.pop

/**
 * Container for headers in emails. Contains literal strings, so no conversions are made to dates etc.
 */
data class MailHeaders(
    var returnPath: String? = null,
    var received: String? = null,
    var date: String? = null,
    var from: String? = null, // Sender!
    var to: String? = null, // Recipient!
    var subject: String? = null,
    var messageID: String? = null,
    var mimeVersion: String? = null,
    var contentType: String? = null,
)