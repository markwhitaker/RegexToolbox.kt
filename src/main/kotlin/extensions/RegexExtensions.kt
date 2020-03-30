package uk.co.mainwave.regextoolboxkotlin.extensions

/**
 * Remove all matches of this [Regex] from the supplied [input] string, returning the modified string.
 * If no match for the Regex is found in the input string, the input string is returned unmodified.
 */
fun Regex.remove(input: CharSequence): String = this.replace(input, "")

/**
 * Remove the first match of this [Regex] from the supplied [input] string, returning the modified string.
 * If no match for the Regex is found in the input string, the input string is returned unmodified.
 */
fun Regex.removeFirst(input: CharSequence): String = this.replaceFirst(input, "")

/**
 * Remove the last match of this [Regex] from the supplied [input] string, returning the modified string.
 * If no match for the Regex is found in the input string, the input string is returned unmodified.
 */
fun Regex.removeLast(input: CharSequence): String = this.findAll(input).let { matches ->
    when (matches.any()) {
        true -> input.removeRange(matches.last().range)
        false -> input
    }.toString()
}