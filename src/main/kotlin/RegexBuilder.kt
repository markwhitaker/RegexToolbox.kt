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
class RegexBuilder {
    private val stringBuilder = StringBuilder()
    private var openGroupCount = 0
    private var logFunction: (s: String) -> Unit = {}
    private var prefix = DEFAULT_PREFIX

    /**
     * Interface to a logger attached by the client code which will receive log messages as the regex is built.
     */
    interface Logger {
        /**
         * Log a [message] to a debugger or logging framework
         */
        fun log(message: String)
    }

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
                    options.map { it.kotlinRegexOption }.toSet()
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
     * Build and return a [Pattern] object from the current builder state.
     * Note that the normal Kotlin thing to do is to use [buildRegex], but this is for legacy cases where you need a Pattern.
     * After calling this the builder is cleared and ready to re-use.
     *
     * @param options Any number of regex options to apply to the regex
     * @return [Regex] as built
     * @throws RegexBuilderException An error occurred when building the regex
     */
    @Throws(RegexBuilderException::class)
    fun buildPattern(vararg options: RegexOptions): Pattern = buildRegex(*options).toPattern()

    /**
     * Attach a [logger] to this builder using this [Logger] interface. The builder will emit logging messages to it as
     * the regex is built with the prefix "RegexBuilder".
     */
    fun addLogger(logger: Logger): RegexBuilder {
        return addLogger{
            logger.log(it)
        }
    }

    /**
     * Attach a [logger] to this builder using this [Logger] interface. The builder will emit logging messages to it as
     * the regex is built with the provided [prefix].
     */
    fun addLogger(prefix: String, logger: Logger): RegexBuilder {
        return addLogger(prefix) {
            logger.log(it)
        }
    }

    /**
     * Attach a [logger] to this builder using the provided log function. The builder will emit logging messages to it
     * as the regex is built with the prefix "RegexBuilder".
     */
    fun addLogger(logFunction: (s: String) -> Unit): RegexBuilder {
        this.logFunction = logFunction
        return this
    }

    /**
     * Attach a [logger] to this builder using the provided log function. The builder will emit logging messages to it
     * as the regex is built with the provided [prefix].
     */
    fun addLogger(prefix: String, logFunction: (s: String) -> Unit): RegexBuilder {
        this.logFunction = logFunction
        this.prefix = prefix
        return this
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
        val safeText = makeSafeForRegex(text)
        return if (quantifier == null) {
            addPart("text(\"$text\")", safeText)
        } else {
            addPartInNonCapturingGroup("text(\"$text, ${quantifier.name}\")", safeText, quantifier)
        }
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
            addPart("regexText(\"$text\")", text)
        } else {
            addPartInNonCapturingGroup("regexText(\"$text, ${quantifier.name}\")", text, quantifier)
        }

    /**
     * Add an element to match any character.
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun anyCharacter(quantifier: RegexQuantifier? = null) =
        addPart("anyCharacter(${quantifier?.name})", ".", quantifier)

    /**
     * Add an element to match any single whitespace character.
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun whitespace(quantifier: RegexQuantifier? = null) =
        addPart("whitespace(${quantifier?.name})", "\\s", quantifier)

    /**
     * Add an element to match any single non-whitespace character.
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun nonWhitespace(quantifier: RegexQuantifier? = null) =
        addPart("nonWhitespace(${quantifier?.name})", "\\S", quantifier)

    /**
     * Add an element to represent any amount of white space, including none. This is just a convenient alias for
     * `whitespace(RegexQuantifier.zeroOrMore())`.
     */
    fun possibleWhitespace() =
        addPart("possibleWhitespace()", "\\s", RegexQuantifier.ZeroOrMore)

    /**
     * Add an element to match a single space character. If you want to match any kind of white space, use
     * [whitespace].
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun space(quantifier: RegexQuantifier? = null) =
        addPart("space(${quantifier?.name})", " ", quantifier)

    /**
     * Add an element to match a single tab character.
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun tab(quantifier: RegexQuantifier? = null) =
        addPart("tab(${quantifier?.name})", "\\t", quantifier)

    /**
     * Add an element to match a single line feed character.
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun lineFeed(quantifier: RegexQuantifier? = null) =
        addPart("lineFeed(${quantifier?.name})", "\\n", quantifier)

    /**
     * Add an element to match a single carriage return character.
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun carriageReturn(quantifier: RegexQuantifier? = null) =
        addPart("carriageReturn(${quantifier?.name})", "\\r", quantifier)

    /**
     * Add an element to match any single decimal digit (0-9).
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun digit(quantifier: RegexQuantifier? = null) =
        addPart("digit(${quantifier?.name})", "\\d", quantifier)

    /**
     * Add an element to match any character that is not a decimal digit (0-9).
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun nonDigit(quantifier: RegexQuantifier? = null) =
        addPart("nonDigit(${quantifier?.name})", "\\D", quantifier)

    /**
     * Add an element to match any Unicode letter.
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun letter(quantifier: RegexQuantifier? = null) =
        addPart("letter(${quantifier?.name})", "\\p{L}", quantifier)

    /**
     * Add an element to match any character that is not a Unicode letter.
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun nonLetter(quantifier: RegexQuantifier? = null) =
        addPart("nonLetter(${quantifier?.name})", "\\P{L}", quantifier)

    /**
     * Add an element to match any upper-case Unicode letter.
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun uppercaseLetter(quantifier: RegexQuantifier? = null) =
        addPart("uppercaseLetter(${quantifier?.name})", "\\p{Lu}", quantifier)

    /**
     * Add an element to match any lowercase Unicode letter.
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun lowercaseLetter(quantifier: RegexQuantifier? = null) =
        addPart("lowercaseLetter(${quantifier?.name})", "\\p{Ll}", quantifier)

    /**
     * Add an element to match any Unicode letter or decimal digit.
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun letterOrDigit(quantifier: RegexQuantifier? = null) =
        addPart("letterOrDigit(${quantifier?.name})", "[\\p{L}0-9]", quantifier)

    /**
     * Add an element to match any character that is not a Unicode letter or a decimal digit.
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun nonLetterOrDigit(quantifier: RegexQuantifier? = null) =
        addPart("nonLetterOrDigit(${quantifier?.name})", "[^\\p{L}0-9]", quantifier)

    /**
     * Add an element to match any hexadecimal digit (a-f, A-F, 0-9)
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun hexDigit(quantifier: RegexQuantifier? = null) =
        addPart("hexDigit(${quantifier?.name})", "[0-9A-Fa-f]", quantifier)

    /**
     * Add an element to match any uppercase hexadecimal digit (A-F, 0-9)
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun uppercaseHexDigit(quantifier: RegexQuantifier? = null) =
        addPart("uppercaseHexDigit(${quantifier?.name})", "[0-9A-F]", quantifier)

    /**
     * Add an element to match any lowercase hexadecimal digit (a-f, 0-9)
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun lowercaseHexDigit(quantifier: RegexQuantifier? = null) =
        addPart("lowercaseHexDigit(${quantifier?.name})", "[0-9a-f]", quantifier)

    /**
     * Add an element to match any character that is not a hexadecimal digit (a-f, A-F, 0-9)
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun nonHexDigit(quantifier: RegexQuantifier? = null) =
        addPart("nonHexDigit(${quantifier?.name})", "[^0-9A-Fa-f]", quantifier)

    /**
     * Add an element to match any Unicode letter, decimal digit or underscore
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun wordCharacter(quantifier: RegexQuantifier? = null) =
        addPart("wordCharacter(${quantifier?.name})", "[\\p{L}0-9_]", quantifier)

    /**
     * Add an element to match any character that is not a Unicode letter, decimal digit or underscore
     *
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun nonWordCharacter(quantifier: RegexQuantifier? = null) =
        addPart("nonWordCharacter(${quantifier?.name})", "[^\\p{L}0-9_]", quantifier)

    /**
     * Add an element (a character class) to match any of the characters provided.
     *
     * @param characters String containing all characters to include in the character class
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun anyCharacterFrom(characters: String, quantifier: RegexQuantifier? = null): RegexBuilder {
        val method = if (quantifier == null) {
            "anyCharacterFrom(\"$characters\")"
        } else {
            "anyCharacterFrom(\"$characters\", ${quantifier.name})"
        }
        // Build a character class, remembering to escape any ] character if passed in
        val safeCharacters = makeSafeForCharacterClass(characters)
        return addPart(method, "[$safeCharacters]", quantifier)
    }

    /**
     * Add an element (a character class) to match any character except those provided.
     *
     * @param characters String containing all characters to exclude from the character class
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun anyCharacterExcept(characters: String, quantifier: RegexQuantifier? = null): RegexBuilder {
        val method = if (quantifier == null) {
            "anyCharacterExcept(\"$characters\")"
        } else {
            "anyCharacterExcept(\"$characters\", ${quantifier.name})"
        }
        // Build a character class, remembering to escape any ] character if passed in
        val safeCharacters = makeSafeForCharacterClass(characters)
        return addPart(method, "[^$safeCharacters]", quantifier)
    }

    /**
     * Add a group of alternatives, to match any of the strings provided
     *
     * @param strings    A number of strings, any one of which will be matched
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun anyOf(strings: List<String>, quantifier: RegexQuantifier? = null): RegexBuilder {
        return if (strings.isEmpty()) {
            log("AnyOf()", "strings list is empty, so doing nothing")
            this
        } else if (strings.size == 1 && quantifier == null) {
            addPart("anyOf(\"${strings.first()}\")", makeSafeForRegex(strings.first()))
        } else {
            val stringsJoinedForLogging = strings.joinToString(", ") {
                "\"$it\""
            }
            val method = if (quantifier == null) {
                "anyOf($stringsJoinedForLogging)"
            } else {
                "anyOf($stringsJoinedForLogging, ${quantifier.name})"
            }
            val stringsSafeAndJoined = strings.joinToString("|") {
                makeSafeForRegex(it)
            }
            addPartInNonCapturingGroup(method, stringsSafeAndJoined, quantifier)
        }
    }

    /**
     * Add a group of alternatives, to match any of the strings provided
     *
     * @param strings    A number of strings, any one of which will be matched
     * @param quantifier Quantifier to apply to this element
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun anyOf(vararg strings: String, quantifier: RegexQuantifier? = null): RegexBuilder = anyOf(strings.toList(), quantifier)

    // ZERO-WIDTH ASSERTIONS

    /**
     * Add a zero-width anchor element to match the start of the string
     *
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun startOfString() = addPart("startOfString()", "^")

    /**
     * Add a zero-width anchor element to match the end of the string
     *
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun endOfString() = addPart("endOfString()", "$")

    /**
     * Add a zero-width anchor element to match the boundary between an alphanumeric/underscore character
     * and either a non-alphanumeric, non-underscore character or the start/end of the string.
     *
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun wordBoundary() = addPart("wordBoundary()", "\\b")

    // GROUPS

    /**
     * Start a capture group. Capture groups have two purposes: they group part of the expression so
     * it can have quantifiers applied to it, and they capture the results of each group match and
     * allow you to access them afterwards using [MatchResult.groups].
     *
     * If you don't want to capture the group match, use [startNonCapturingGroup].
     *
     * Note: all groups must be ended with [endGroup] before calling [buildRegex].
     *
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun startGroup(): RegexBuilder {
        openGroupCount++
        return addPart("startGroup()", "(")
    }

    /**
     * Start a non-capturing group. Non-capturing groups group part of the expression so
     * it can have quantifiers applied to it, but do not capture the results of each group match, meaning
     * you can't access them afterwards using [MatchResult.groups].
     *
     * If you want to capture group results, use [startGroup] or [startNamedGroup].
     *
     * Note: all groups must be ended with [endGroup] before calling [buildRegex].
     *
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun startNonCapturingGroup(): RegexBuilder {
        openGroupCount++
        return addPart("startNonCapturingGroup()", "(?:")
    }

    /**
     * Start a named capture group. Capture groups have two purposes: they group part of the expression so
     * it can have quantifiers applied to it, and they capture the results of each group match and
     * allow you to access them afterwards using [MatchResult.groups]. Named capture groups can be accessed by
     * indexing into [MatchResult.groups] with the assigned name as well as a numerical index.
     *
     * If you don't want to capture the group match, use [startNonCapturingGroup].
     *
     * Note: all groups must be ended with [endGroup] before calling [buildRegex].
     *
     * @param name Name used to identify the group
     * @return The current [RegexBuilder] object, for method chaining
     */
    fun startNamedGroup(name: String): RegexBuilder {
        openGroupCount++
        return addPart("startNamedGroup(\"$name\")", "(?<$name>")
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
        return addPart("endGroup(${quantifier?.name})", ")", quantifier)
    }

    // GROUPS (DSL)

    fun group(quantifier: RegexQuantifier? = null, buildSteps: RegexBuilder.() -> Unit) =
        startGroup().apply(buildSteps).endGroup(quantifier)

    fun namedGroup(name: String, quantifier: RegexQuantifier? = null, buildSteps: RegexBuilder.() -> Unit) =
        startNamedGroup(name).apply(buildSteps).endGroup(quantifier)

    fun nonCapturingGroup(quantifier: RegexQuantifier? = null, buildSteps: RegexBuilder.() -> Unit) =
        startNonCapturingGroup().apply(buildSteps).endGroup(quantifier)

    // PRIVATE METHODS

    private fun addPart(method: String, part: String, quantifier: RegexQuantifier? = null): RegexBuilder {
        log(method, "$part$quantifier")
        stringBuilder
            .append(part)
            .append(quantifier?.toString() ?: "")
        return this
    }

    private fun addPartInNonCapturingGroup(
        method: String,
        part: String,
        quantifier: RegexQuantifier? = null): RegexBuilder = addPart(method, "(?:$part)", quantifier)

    private fun log(method: String, message: String) {
        logFunction("$prefix: $method: $message")
    }

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

    companion object {
        private const val DEFAULT_PREFIX = "RegexBuilder"
    }
}