package uk.co.mainwave.regextoolboxkotlin

/**
 * Class to build regular expressions in a more human-readable way using a fluent API.
 * <p>
 * To use, chain method calls representing the elements you want to match, and finish with [buildRegex] to build the
 * [Regex].
 * <p>
 * Example:
 * <pre>
 * val regex = RegexBuilder()
 *     .text("cat")
 *     .endOfString()
 *     .buildRegex()
 * </pre>
 */
class RegexBuilder {
    private val stringBuilder = StringBuilder()
    private var openGroupCount = 0

    /**
     * Build and return a [Regex] object from the current builder state.
     * After calling this the builder is cleared and ready to re-use.
     *
     * @param options Any number of regex options to apply to the regex
     * @return [Regex] as built
     * @throws RegexBuilderException An error occurred when building the regex
     */
    @Throws(RegexBuilderException::class)
    fun buildRegex(vararg options: RegexOptions): Regex =
        when (openGroupCount) {
            0 -> {
                val regex = Regex(
                    stringBuilder.toString(),
                    options
                        .map {
                            it.kotlinRegexOption
                        }
                        .toSet()
                )
                stringBuilder.setLength(0)
                regex
            }
            1 -> {
                throw RegexBuilderException("A group has been started but not ended", stringBuilder)
            }
            else -> {
                throw RegexBuilderException("$openGroupCount groups have been started but not ended", stringBuilder)
            }
        }

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
    fun text(text: String, quantifier: RegexQuantifier? = null) = regexText(makeSafeForRegex(text), quantifier)

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
            append(text)
        } else {
            startNonCapturingGroup()
                .regexText(text)
                .endGroup(quantifier)
        }

    /**
     * Add an element to match any character.
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun anyCharacter(quantifier: RegexQuantifier? = null) = append(".", quantifier)

    /**
     * Add an element to match any single whitespace character.
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun whitespace(quantifier: RegexQuantifier? = null) = append("\\s", quantifier)

    /**
     * Add an element to match any single non-whitespace character.
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun nonWhitespace(quantifier: RegexQuantifier? = null) = append("\\S", quantifier)

    /**
     * Add an element to match any single decimal digit (0-9).
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun digit(quantifier: RegexQuantifier? = null) = append("\\d", quantifier)

    /**
     * Add an element to match any character that is not a decimal digit (0-9).
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun nonDigit(quantifier: RegexQuantifier? = null) = append("\\D", quantifier)

    /**
     * Add an element to match any letter in the Roman alphabet (a-z, A-Z)
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun letter(quantifier: RegexQuantifier? = null) = append("[a-zA-Z]", quantifier)

    /**
     * Add an element to match any character that is not a letter in the Roman alphabet (a-z, A-Z)
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun nonLetter(quantifier: RegexQuantifier? = null) = append("[^a-zA-Z]", quantifier)

    /**
     * Add an element to match any upper-case letter in the Roman alphabet (A-Z).
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun uppercaseLetter(quantifier: RegexQuantifier? = null) = append("[A-Z]", quantifier)

    /**
     * Add an element to match any lowercase letter in the Roman alphabet (a-z)
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun lowercaseLetter(quantifier: RegexQuantifier? = null) = append("[a-z]", quantifier)

    /**
     * Add an element to match any letter in the Roman alphabet or decimal digit (a-z, A-Z, 0-9)
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun letterOrDigit(quantifier: RegexQuantifier? = null) = append("[a-zA-Z0-9]", quantifier)

    /**
     * Add an element to match any character that is not letter in the Roman alphabet or a decimal digit (a-z, A-Z, 0-9)
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun nonLetterOrDigit(quantifier: RegexQuantifier? = null) = append("[^a-zA-Z0-9]", quantifier)

    /**
     * Add an element to match any hexadecimal digit (a-f, A-F, 0-9)
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun hexDigit(quantifier: RegexQuantifier? = null) = append("[0-9A-Fa-f]", quantifier)

    /**
     * Add an element to match any uppercase hexadecimal digit (A-F, 0-9)
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun uppercaseHexDigit(quantifier: RegexQuantifier? = null) = append("[0-9A-F]", quantifier)

    /**
     * Add an element to match any lowercase hexadecimal digit (a-f, 0-9)
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun lowercaseHexDigit(quantifier: RegexQuantifier? = null) = append("[0-9a-f]", quantifier)

    /**
     * Add an element to match any character that is not a hexadecimal digit (a-f, A-F, 0-9)
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun nonHexDigit(quantifier: RegexQuantifier? = null) = append("[^0-9A-Fa-f]", quantifier)

    /**
     * Add an element to match any Roman alphabet letter, decimal digit, or underscore (a-z, A-Z, 0-9, _)
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun wordCharacter(quantifier: RegexQuantifier? = null) = append("\\w", quantifier)

    /**
     * Add an element to match any character that is not a Roman alphabet letter, decimal digit, or underscore
     * (a-z, A-Z, 0-9, _)
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun nonWordCharacter(quantifier: RegexQuantifier? = null) = append("\\W", quantifier)

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
        return append("[$safeCharacters]", quantifier)
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
        return append("[^$safeCharacters]", quantifier)
    }

    /**
     * Add a group of alternatives, to match any of the strings provided
     *
     * @param strings    A number of strings, any one of which will be matched
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun anyOf(strings: List<String>, quantifier: RegexQuantifier? = null): RegexBuilder {
        return when (strings.size) {
            0 -> this
            1 -> {
                append(makeSafeForRegex(strings[0]), quantifier)
            }
            else -> {
                val stringsSafeAndJoined = strings.joinToString("|") {
                    makeSafeForRegex(it)
                }
                startNonCapturingGroup()
                    .regexText(stringsSafeAndJoined, quantifier)
                    .endGroup(quantifier)
            }
        }
    }

    // ZERO-WIDTH ASSERTIONS

    /**
     * Add a zero-width anchor element to match the start of the string
     *
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun startOfString() = append("^")

    /**
     * Add a zero-width anchor element to match the end of the string
     *
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun endOfString() = append("$")

    /**
     * Add a zero-width anchor element to match the boundary between an alphanumeric/underscore character
     * and either a non-alphanumeric, non-underscore character or the start/end of the string.
     *
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun wordBoundary() = append("\\b")

    // GROUPS

    /**
     * Start a capture group. Capture groups have two purposes: they group part of the expression so
     * it can have quantifiers applied to it, and they capture the results of each group match and
     * allow you to access them afterwards using Match.Groups.
     *
     * If you don't want to capture the group match, use [.startNonCapturingGroup].
     *
     * Note: all groups must be ended with [.endGroup] before calling [.buildRegex].
     *
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun startGroup(): RegexBuilder {
        openGroupCount++
        return append("(")
    }

    /**
     * Start a non-capturing group. Non-capturing groups group part of the expression so
     * it can have quantifiers applied to it, but do not capture the results of each group match, meaning
     * you can't access them afterwards using Match.Groups.
     *
     * If you want to capture group results, use [.startGroup] or [.startNamedGroup].
     *
     * Note: all groups must be ended with [.endGroup] before calling [.buildRegex].
     *
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun startNonCapturingGroup(): RegexBuilder {
        openGroupCount++
        return append("(?:")
    }

    /**
     * Start a named capture group. Capture groups have two purposes: they group part of the expression so
     * it can have quantifiers applied to it, and they capture the results of each group match and
     * allow you to access them afterwards using Match.Groups. Named capture groups can be accessed by
     * indexing into Match.Groups with the assigned name as well as a numerical index.
     *
     * If you don't want to capture the group match, use [.startNonCapturingGroup].
     *
     * Note: all groups must be ended with [.endGroup] before calling [.buildRegex].
     *
     * @param name Name used to identify the group
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun startNamedGroup(name: String): RegexBuilder {
        openGroupCount++
        return append("(?<$name>")
    }

    /**
     * End the innermost group previously started with [startGroup], [startNonCapturingGroup] or
     * [startNamedGroup].
     *
     * @param quantifier Quantifier to apply to this group
     * @return The current [RegexBuilder] object, for method chaining
     * @throws RegexBuilderException A group has not been started
     */
    @Throws(RegexBuilderException::class)
    fun endGroup(quantifier: RegexQuantifier? = null): RegexBuilder {
        if (openGroupCount == 0) {
            throw RegexBuilderException(
                "Cannot call endGroup() until a group has been started with startGroup()",
                stringBuilder
            )
        }

        openGroupCount--
        return append(")", quantifier)
    }

    // PRIVATE METHODS

    private fun append(text: String, quantifier: RegexQuantifier? = null): RegexBuilder {
        stringBuilder.append(text)
        addQuantifier(quantifier)
        return this
    }

    private fun addQuantifier(quantifier: RegexQuantifier?) =
        stringBuilder.append(quantifier?.toString() ?: "")

    private fun makeSafeForCharacterClass(text: String): String {
        // Replace ] with \]
        var safeText = text.replace("]", "\\]")

        // replace ^ with \^ if it occurs at the start of the string
        if (safeText.startsWith("^")) {
            safeText = "\\" + safeText
        }

        return safeText
    }

    private fun makeSafeForRegex(s: String): String {
        return s
            // Make sure this always comes first!
            .replace("\\", "\\\\")
            .replace("?", "\\?")
            .replace(".", "\\.")
            .replace("+", "\\+")
            .replace("*", "\\*")
            .replace("^", "\\^")
            .replace("$", "\\$")
            .replace("(", "\\(")
            .replace(")", "\\)")
            .replace("[", "\\[")
            .replace("]", "\\]")
            .replace("{", "\\{")
            .replace("}", "\\}")
            .replace("|", "\\|")
    }
}