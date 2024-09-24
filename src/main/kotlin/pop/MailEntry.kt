package com.pkg_dot_zip.pop

/**
 * Container class for results of the LIST pop command.
 */
data class MailEntry(val mailID: Int, val mailSize: Int) {
    constructor(listEntryString: String) : this(
        listEntryString.split(" ")[0].toInt(),
        listEntryString.split(" ")[1].toInt()
    )
}