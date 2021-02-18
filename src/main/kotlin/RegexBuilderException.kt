package uk.co.mainwave.regextoolboxkotlin

/**
 * Exception thrown by [RegexBuilder] methods
 */
@Deprecated("RegexBuilderException will be removed in version 3.0")
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