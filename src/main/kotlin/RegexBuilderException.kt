package uk.co.mainwave.regextoolboxkotlin

/**
 * Exception thrown by [RegexBuilder] methods
 */
class RegexBuilderException(
    message: String,
    stringBuilder: StringBuilder
) : Exception(message) {
    /**
     * The regex string at the time the exception was thrown
     */
    @Suppress("unused")
    val regexString = stringBuilder.toString()
}