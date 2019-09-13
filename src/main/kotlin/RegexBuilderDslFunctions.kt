package uk.co.mainwave.regextoolboxkotlin

import java.util.regex.Pattern

/**
 * Wrapper around [RegexBuilder] that provides a Kotlin type-safe builder syntax for building [Regex] objects.
 */
fun regex(vararg options: RegexOptions, buildSteps: RegexBuilder.() -> Unit): Regex =
    RegexBuilder().apply(buildSteps).buildRegex(*options)

/**
 * Wrapper around [RegexBuilder] that provides a Kotlin type-safe builder syntax for building Java [Pattern] objects.
 */
fun pattern(vararg options: RegexOptions, buildSteps: RegexBuilder.() -> Unit): Pattern =
    RegexBuilder().apply(buildSteps).buildPattern(*options)