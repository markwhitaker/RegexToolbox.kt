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
    private var stringBuilder = StringBuilder()
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
    fun buildRegex(vararg options: RegexOptions): Regex {
        if (openGroupCount > 0) {
            throw if (openGroupCount == 1)
                RegexBuilderException("A group has been started but not ended", stringBuilder)
            else
                RegexBuilderException("$openGroupCount groups have been started but not ended", stringBuilder)
        }

        val regex = Regex(stringBuilder.toString(), options.map { it.kotlinRegexOption }.toSet())
        stringBuilder = StringBuilder()
        return regex
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
    fun text(text: String, quantifier: RegexQuantifier? = null): RegexBuilder {
        return regexText(makeSafeForRegex(text), quantifier)
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
    fun regexText(text: String, quantifier: RegexQuantifier? = null): RegexBuilder {
        if (quantifier == null) {
            stringBuilder.append(text)
            return this
        }

        return startNonCapturingGroup()
            .regexText(text, null)
            .endGroup(quantifier)
    }

    /**
     * Add an element to match any character.
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun anyCharacter(quantifier: RegexQuantifier? = null): RegexBuilder {
        stringBuilder.append(".")
        addQuantifier(quantifier)
        return this
    }

    /**
     * Add an element to match any single whitespace character.
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun whitespace(quantifier: RegexQuantifier? = null): RegexBuilder {
        stringBuilder.append("\\s")
        addQuantifier(quantifier)
        return this
    }

    /**
     * Add an element to match any single non-whitespace character.
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun nonWhitespace(quantifier: RegexQuantifier? = null): RegexBuilder {
        stringBuilder.append("\\S")
        addQuantifier(quantifier)
        return this
    }

    /**
     * Add an element to match any single decimal digit (0-9).
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun digit(quantifier: RegexQuantifier? = null): RegexBuilder {
        stringBuilder.append("\\d")
        addQuantifier(quantifier)
        return this
    }

    /**
     * Add an element to match any character that is not a decimal digit (0-9).
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun nonDigit(quantifier: RegexQuantifier? = null): RegexBuilder {
        stringBuilder.append("\\D")
        addQuantifier(quantifier)
        return this
    }

    /**
     * Add an element to match any letter in the Roman alphabet (a-z, A-Z)
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun letter(quantifier: RegexQuantifier? = null): RegexBuilder {
        stringBuilder.append("[a-zA-Z]")
        addQuantifier(quantifier)
        return this
    }

    /**
     * Add an element to match any character that is not a letter in the Roman alphabet (a-z, A-Z)
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun nonLetter(quantifier: RegexQuantifier? = null): RegexBuilder {
        stringBuilder.append("[^a-zA-Z]")
        addQuantifier(quantifier)
        return this
    }

    /**
     * Add an element to match any upper-case letter in the Roman alphabet (A-Z).
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun uppercaseLetter(quantifier: RegexQuantifier? = null): RegexBuilder {
        stringBuilder.append("[A-Z]")
        addQuantifier(quantifier)
        return this
    }

    /**
     * Add an element to match any lowercase letter in the Roman alphabet (a-z)
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun lowercaseLetter(quantifier: RegexQuantifier? = null): RegexBuilder {
        stringBuilder.append("[a-z]")
        addQuantifier(quantifier)
        return this
    }

    /**
     * Add an element to match any letter in the Roman alphabet or decimal digit (a-z, A-Z, 0-9)
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun letterOrDigit(quantifier: RegexQuantifier? = null): RegexBuilder {
        stringBuilder.append("[a-zA-Z0-9]")
        addQuantifier(quantifier)
        return this
    }

    /**
     * Add an element to match any character that is not letter in the Roman alphabet or a decimal digit (a-z, A-Z, 0-9)
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun nonLetterOrDigit(quantifier: RegexQuantifier? = null): RegexBuilder {
        stringBuilder.append("[^a-zA-Z0-9]")
        addQuantifier(quantifier)
        return this
    }

    /**
     * Add an element to match any hexadecimal digit (a-f, A-F, 0-9)
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun hexDigit(quantifier: RegexQuantifier? = null): RegexBuilder {
        stringBuilder.append("[0-9A-Fa-f]")
        addQuantifier(quantifier)
        return this
    }

    /**
     * Add an element to match any uppercase hexadecimal digit (A-F, 0-9)
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun uppercaseHexDigit(quantifier: RegexQuantifier? = null): RegexBuilder {
        stringBuilder.append("[0-9A-F]")
        addQuantifier(quantifier)
        return this
    }

    /**
     * Add an element to match any lowercase hexadecimal digit (a-f, 0-9)
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun lowercaseHexDigit(quantifier: RegexQuantifier? = null): RegexBuilder {
        stringBuilder.append("[0-9a-f]")
        addQuantifier(quantifier)
        return this
    }

    /**
     * Add an element to match any character that is not a hexadecimal digit (a-f, A-F, 0-9)
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun nonHexDigit(quantifier: RegexQuantifier? = null): RegexBuilder {
        stringBuilder.append("[^0-9A-Fa-f]")
        addQuantifier(quantifier)
        return this
    }

    /**
     * Add an element to match any Roman alphabet letter, decimal digit, or underscore (a-z, A-Z, 0-9, _)
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun wordCharacter(quantifier: RegexQuantifier? = null): RegexBuilder {
        stringBuilder.append("\\w")
        addQuantifier(quantifier)
        return this
    }

    /**
     * Add an element to match any character that is not a Roman alphabet letter, decimal digit, or underscore
     * (a-z, A-Z, 0-9, _)
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun nonWordCharacter(quantifier: RegexQuantifier? = null): RegexBuilder {
        stringBuilder.append("\\W")
        addQuantifier(quantifier)
        return this
    }

    /**
     * Add an element (a character class) to match any of the characters provided.
     *
     * @param characters String containing all characters to include in the character class
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun anyCharacterFrom(characters: String, quantifier: RegexQuantifier? = null): RegexBuilder {
        // Build a character class, remembering to escape any ] character if passed in
        stringBuilder
            .append("[")
            .append(makeSafeForCharacterClass(characters))
            .append("]")
        addQuantifier(quantifier)
        return this
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
        stringBuilder
            .append("[^")
            .append(makeSafeForCharacterClass(characters))
            .append("]")
        addQuantifier(quantifier)
        return this
    }

    /**
     * Add a group of alternatives, to match any of the strings provided
     *
     * @param strings    A number of strings, any one of which will be matched
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun anyOf(strings: List<String>, quantifier: RegexQuantifier? = null): RegexBuilder {
        if (strings.isEmpty()) {
            return this
        }

        if (strings.size == 1) {
            stringBuilder.append(makeSafeForRegex(strings[0]))
            addQuantifier(quantifier)
            return this
        }

        val safeStrings = arrayOfNulls<String>(strings.size)
        for (i in strings.indices) {
            safeStrings[i] = makeSafeForRegex(strings[i])
        }

        return startNonCapturingGroup()
            .regexText(safeStrings.joinToString("|"), quantifier)
            .endGroup(quantifier)
    }

    // ZERO-WIDTH ASSERTIONS

    /**
     * Add a zero-width anchor element to match the start of the string
     *
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun startOfString(): RegexBuilder {
        stringBuilder.append("^")
        return this
    }

    /**
     * Add a zero-width anchor element to match the end of the string
     *
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun endOfString(): RegexBuilder {
        stringBuilder.append("$")
        return this
    }

    /**
     * Add a zero-width anchor element to match the boundary between an alphanumeric/underscore character
     * and either a non-alphanumeric, non-underscore character or the start/end of the string.
     *
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun wordBoundary(): RegexBuilder {
        stringBuilder.append("\\b")
        return this
    }

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
        stringBuilder.append("(")
        openGroupCount++
        return this
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
        stringBuilder.append("(?:")
        openGroupCount++
        return this
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
        stringBuilder
            .append("(?<")
            .append(name)
            .append(">")
        openGroupCount++
        return this
    }

    /**
     * End the innermost group previously started with [.startGroup], [.startNonCapturingGroup] or
     * [.startNamedGroup].
     *
     * @return The current [RegexBuilder] object, for method chaining
     * @throws RegexBuilderException A group has not been started
     */
    @Throws(RegexBuilderException::class)
    fun endGroup(): RegexBuilder {
        return endGroup(null)
    }

    /**
     * End the innermost group previously started with [.startGroup], [.startNonCapturingGroup] or
     * [.startNamedGroup].
     *
     * @param quantifier Quantifier to apply to this group
     * @return The current [RegexBuilder] object, for method chaining
     * @throws RegexBuilderException A group has not been started
     */
    @Throws(RegexBuilderException::class)
    fun endGroup(quantifier: RegexQuantifier?): RegexBuilder {
        if (openGroupCount == 0) {
            throw RegexBuilderException(
                "Cannot call endGroup() until a group has been started with startGroup()",
                stringBuilder
            )
        }

        stringBuilder.append(")")
        addQuantifier(quantifier)
        openGroupCount--
        return this
    }

    // PRIVATE METHODS

    private fun addQuantifier(quantifier: RegexQuantifier?) {
        if (quantifier != null) {
            stringBuilder.append(quantifier.toString())
        }
    }

    private fun makeSafeForCharacterClass(s: String): String {
        // Replace ] with \]
        var result = s.replace("]", "\\]")

        // replace ^ with \^ if it occurs at the start of the string
        if (result.startsWith("^")) {
            result = "\\" + result
        }

        return result
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