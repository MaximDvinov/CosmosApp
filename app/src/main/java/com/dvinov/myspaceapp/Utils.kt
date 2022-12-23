package com.dvinov.myspaceapp

import java.util.regex.Matcher
import java.util.regex.Pattern

fun getYoutubeId(url: String?): String? {
    val pattern =
        "https?://(?:[0-9A-Z-]+\\.)?(?:youtu\\.be/|youtube\\.com\\S*[^\\w\\-\\s])([\\w\\-]{11})(?=[^\\w\\-]|$)(?![?=&+%\\w]*(?:['\"][^<>]*>|</a>))[?=&+%\\w]*"
    val compiledPattern: Pattern = Pattern.compile(
        pattern,
        Pattern.CASE_INSENSITIVE
    )
    val matcher: Matcher = compiledPattern.matcher(url.toString())
    return if (matcher.find()) {
        matcher.group(1)
    } else null /*from w  w  w.  j a  va  2 s .c om*/
}