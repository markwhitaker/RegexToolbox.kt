package uk.co.mainwave.regextoolboxkotlin

/**
 * Options that can be passed to [RegexBuilder.buildRegex].
 */
enum class RegexOptions(
    internal val kotlinRegexOption: RegexOption
) {
    /**
     * Make the regex case-insensitive
     */
    IGNORE_CASE(RegexOption.IGNORE_CASE),

    /**
     * Cause [RegexBuilder.startOfString] and [RegexBuilder.endOfString] to also match line breaks within a multi-line
     * string
     */
    MULTILINE(RegexOption.MULTILINE)
}