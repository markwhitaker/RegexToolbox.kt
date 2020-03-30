package uk.co.mainwave.regextoolboxkotlin.extensions

/**
 * Remove all matches of the supplied [regex] from this string, returning the modified string.
 * If no match for the Regex is found in this string, an unmodified copy of this string is returned.
 */
fun CharSequence.remove(regex: Regex): String = regex.remove(this)

/**
 * Remove the first match of the supplied [regex] from this string, returning the modified string.
 * If no match for the Regex is found in this string, an unmodified copy of this string is returned.
 */
fun CharSequence.removeFirst(regex: Regex): String = regex.removeFirst(this)

/**
 * Remove the last match of the supplied [regex] from this string, returning the modified string.
 * If no match for the Regex is found in this string, an unmodified copy of this string is returned.
 */
fun CharSequence.removeLast(regex: Regex): String = regex.removeLast(this)
