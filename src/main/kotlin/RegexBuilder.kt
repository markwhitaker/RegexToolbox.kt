package uk.co.mainwave.regextoolboxkotlin

import java.util.regex.Pattern

/**
 * Class to build regular expressions in a more human-readable way using a fluent API.
 * <p>
 * To build a regex, use the [regex] builder function.
 * <p>
 * Example:
 * <pre>
 * val regex = regex {
 *     text("cat")
 *     endOfString()
 * }
 * </pre>
 */
class RegexBuilder internal constructor() {
    private val stringBuilder = StringBuilder()

    /**
     * Add text to the regex. Any regex special characters will be escaped as necessary
     * so there's no need to do that yourself.
     *
     * Example:
     *
     * "Hello (world)" will be converted to "Hello \(world\)" so the brackets are treated
     * as normal, human-readable brackets, not regex grouping brackets.
     * It WILL match the string literal "Hello (world)".
     * It WILL NOT match the string literal "Hello world".
     *
     * @param text       Text to add
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun text(text: String, quantifier: RegexQuantifier? = null): RegexBuilder =
        if (quantifier == null) {
            addPart(makeSafeForRegex(text))
        } else {
            addPartInNonCapturingGroup(makeSafeForRegex(text), quantifier)
        }

    /**
     * Add literal regex text to the regex. Regex special characters will NOT be escaped.
     * Only call this if you're comfortable with regex syntax.
     *
     * Example:
     *
     * "Hello (world)" will be left as "Hello (world)", meaning that when the regex is built
     * the brackets will be treated as regex grouping brackets rather than normal, human-readable
     * brackets.
     * It WILL match the string literal "Hello world" (and capture the word "world" as a group).
     * It WILL NOT match the string literal "Hello (world)".
     *
     * @param text       regex text to add
     * @param quantifier Quantifier to apply to the whole string
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun regexText(text: String, quantifier: RegexQuantifier? = null): RegexBuilder =
        if (quantifier == null) {
            addPart(text)
        } else {
            addPartInNonCapturingGroup(text, quantifier)
        }

    /**
     * Add an element to match any character.
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun anyCharacter(quantifier: RegexQuantifier? = null) =
        addPart(".", quantifier)

    /**
     * Add an element to match any single whitespace character.
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun whitespace(quantifier: RegexQuantifier? = null) =
        addPart("\\s", quantifier)

    /**
     * Add an element to match any single non-whitespace character.
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun nonWhitespace(quantifier: RegexQuantifier? = null) =
        addPart("\\S", quantifier)

    /**
     * Add an element to represent any amount of white space, including none. This is just a convenient alias for
     * `whitespace(RegexQuantifier.zeroOrMore())`.
     */
    fun possibleWhitespace() =
        addPart("\\s", RegexQuantifier.ZeroOrMore)

    /**
     * Add an element to match a single space character. If you want to match any kind of white space, use
     * [whitespace].
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun space(quantifier: RegexQuantifier? = null) =
        addPart(" ", quantifier)

    /**
     * Add an element to match a single tab character.
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun tab(quantifier: RegexQuantifier? = null) =
        addPart("\\t", quantifier)

    /**
     * Add an element to match a single line feed character.
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun lineFeed(quantifier: RegexQuantifier? = null) =
        addPart("\\n", quantifier)

    /**
     * Add an element to match a single carriage return character.
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun carriageReturn(quantifier: RegexQuantifier? = null) =
        addPart("\\r", quantifier)

    /**
     * Add an element to match any single decimal digit (0-9).
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun digit(quantifier: RegexQuantifier? = null) =
        addPart("\\d", quantifier)

    /**
     * Add an element to match any character that is not a decimal digit (0-9).
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun nonDigit(quantifier: RegexQuantifier? = null) =
        addPart("\\D", quantifier)

    /**
     * Add an element to match any Unicode letter.
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun letter(quantifier: RegexQuantifier? = null) =
        addPart("\\p{L}", quantifier)

    /**
     * Add an element to match any character that is not a Unicode letter.
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun nonLetter(quantifier: RegexQuantifier? = null) =
        addPart("\\P{L}", quantifier)

    /**
     * Add an element to match any upper-case Unicode letter.
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun uppercaseLetter(quantifier: RegexQuantifier? = null) =
        addPart("\\p{Lu}", quantifier)

    /**
     * Add an element to match any lowercase Unicode letter.
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun lowercaseLetter(quantifier: RegexQuantifier? = null) =
        addPart("\\p{Ll}", quantifier)

    /**
     * Add an element to match any Unicode letter or decimal digit.
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun letterOrDigit(quantifier: RegexQuantifier? = null) =
        addPart("[\\p{L}0-9]", quantifier)

    /**
     * Add an element to match any character that is not a Unicode letter or a decimal digit.
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun nonLetterOrDigit(quantifier: RegexQuantifier? = null) =
        addPart("[^\\p{L}0-9]", quantifier)

    /**
     * Add an element to match any hexadecimal digit (a-f, A-F, 0-9)
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun hexDigit(quantifier: RegexQuantifier? = null) =
        addPart("[0-9A-Fa-f]", quantifier)

    /**
     * Add an element to match any uppercase hexadecimal digit (A-F, 0-9)
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun uppercaseHexDigit(quantifier: RegexQuantifier? = null) =
        addPart("[0-9A-F]", quantifier)

    /**
     * Add an element to match any lowercase hexadecimal digit (a-f, 0-9)
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun lowercaseHexDigit(quantifier: RegexQuantifier? = null) =
        addPart("[0-9a-f]", quantifier)

    /**
     * Add an element to match any character that is not a hexadecimal digit (a-f, A-F, 0-9)
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun nonHexDigit(quantifier: RegexQuantifier? = null) =
        addPart("[^0-9A-Fa-f]", quantifier)

    /**
     * Add an element to match any Unicode letter, decimal digit or underscore
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun wordCharacter(quantifier: RegexQuantifier? = null) =
        addPart("[\\p{L}0-9_]", quantifier)

    /**
     * Add an element to match any character that is not a Unicode letter, decimal digit or underscore
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun nonWordCharacter(quantifier: RegexQuantifier? = null) =
        addPart("[^\\p{L}0-9_]", quantifier)

    /**
     * Add an element (a character class) to match any of the characters provided.
     *
     * @param characters String containing all characters to include in the character class
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun anyCharacterFrom(characters: String, quantifier: RegexQuantifier? = null): RegexBuilder {
        // Build a character class, remembering to escape any ] character if passed in
        val safeCharacters = makeSafeForCharacterClass(characters)
        return addPart("[$safeCharacters]", quantifier)
    }

    /**
     * Add an element (a character class) to match any character except those provided.
     *
     * @param characters String containing all characters to exclude from the character class
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun anyCharacterExcept(characters: String, quantifier: RegexQuantifier? = null): RegexBuilder {
        // Build a character class, remembering to escape any ] character if passed in
        val safeCharacters = makeSafeForCharacterClass(characters)
        return addPart("[^$safeCharacters]", quantifier)
    }

    /**
     * Add a group of alternatives, to match any of the strings provided
     *
     * @param strings    A number of strings, any one of which will be matched
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun anyOf(strings: List<String>, quantifier: RegexQuantifier? = null): RegexBuilder =
        when {
            strings.isEmpty() -> {
                this
            }
            strings.size == 1 && quantifier == null -> {
                addPart(makeSafeForRegex(strings.first()))
            }
            else -> {
                val stringsSafeAndJoined = strings.joinToString("|") {
                    makeSafeForRegex(it)
                }
                addPartInNonCapturingGroup(stringsSafeAndJoined, quantifier)
            }
        }

    /**
     * Add a group of alternatives, to match any of the strings provided
     *
     * @param strings    A number of strings, any one of which will be matched
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun anyOf(vararg strings: String, quantifier: RegexQuantifier? = null): RegexBuilder =
        anyOf(strings.toList(), quantifier)

    // ZERO-WIDTH ASSERTIONS

    /**
     * Add a zero-width anchor element to match the start of the string
     *
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun startOfString() = addPart("^")

    /**
     * Add a zero-width anchor element to match the end of the string
     *
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun endOfString() = addPart("$")

    /**
     * Add a zero-width anchor element to match the boundary between an alphanumeric/underscore character
     * and either a non-alphanumeric, non-underscore character or the start/end of the string.
     *
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun wordBoundary() = addPart("\\b")

    // GROUPS

    /**
     * Create a capture group. Capture groups have two purposes: they group part of the expression so it can have
     * quantifiers applied to it, and they capture the results of each group match and allow you to access them
     * afterwards using [MatchResult.groups].
     *
     * If you don't want to capture the group match, use [nonCapturingGroup].
     *
     * @param quantifier Quantifier to apply to this group
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun group(quantifier: RegexQuantifier? = null, buildSteps: RegexBuilder.() -> Unit) =
        startGroup().apply(buildSteps).endGroup(quantifier)

    /**
     * Create a named capture group. Capture groups have two purposes: they group part of the expression so it can have
     * quantifiers applied to it, and they capture the results of each group match and allow you to access them
     * afterwards using [MatchResult.groups]. Named capture groups can be accessed by indexing into [MatchResult.groups]
     * with the assigned name or a numerical index.
     *
     * If you don't want to capture the group match, use [nonCapturingGroup].
     *
     * @param name Name used to identify the group
     * @param quantifier Quantifier to apply to this group
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun namedGroup(name: String, quantifier: RegexQuantifier? = null, buildSteps: RegexBuilder.() -> Unit) =
        startNamedGroup(name).apply(buildSteps).endGroup(quantifier)

    /**
     * Create a non-capturing group. Non-capturing groups group part of the expression so it can have quantifiers
     * applied to it, but do not capture the results of each group match, meaning you can't access them afterwards using
     * [MatchResult.groups].
     *
     * If you want to capture group results, use [group] or [namedGroup].
     *
     * @param quantifier Quantifier to apply to this group
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun nonCapturingGroup(quantifier: RegexQuantifier? = null, buildSteps: RegexBuilder.() -> Unit) =
        startNonCapturingGroup().apply(buildSteps).endGroup(quantifier)

    // INTERNAL METHODS

    internal fun buildRegex(vararg options: RegexOptions): Regex {
        val stringBuilt = stringBuilder.toString()
        val regex = Regex(
            stringBuilt,
            options.map { it.kotlinRegexOption }.toSet()
        )
        stringBuilder.setLength(0)
        return regex
    }

    internal fun buildPattern(vararg options: RegexOptions): Pattern = buildRegex(*options).toPattern()

    // PRIVATE METHODS

    private fun startGroup(): RegexBuilder = addPart("(")

    private fun startNonCapturingGroup(): RegexBuilder = addPart("(?:")

    private fun startNamedGroup(name: String): RegexBuilder = addPart("(?<$name>")

    private fun endGroup(quantifier: RegexQuantifier? = null): RegexBuilder = addPart(")", quantifier)

    private fun addPart(part: String, quantifier: RegexQuantifier? = null): RegexBuilder =
        apply {
            stringBuilder
                .append(part)
                .append(quantifier?.toString() ?: "")
        }

    private fun addPartInNonCapturingGroup(part: String, quantifier: RegexQuantifier? = null): RegexBuilder =
        addPart("(?:$part)", quantifier)

    private fun makeSafeForCharacterClass(text: String): String {
        var safeText = text
            // Replace ] with \]
            .replace("]", "\\]")
            // Replace - with \-
            .replace("-", "\\-")

        // replace ^ with \^ if it occurs at the start of the string
        if (safeText.startsWith("^")) {
            safeText = "\\" + safeText
        }

        return safeText
    }

    private fun makeSafeForRegex(s: String): String =
        UNSAFE_CHARACTERS.fold(s) { safe, unsafe ->
            safe.replace(unsafe, "\\$unsafe")
        }

    private companion object {
        private val UNSAFE_CHARACTERS = listOf("\\", "?", ".", "+", "*", "^", "$", "(", ")", "[", "]", "{", "}", "|")
    }
}