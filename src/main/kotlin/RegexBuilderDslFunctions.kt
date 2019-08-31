package uk.co.mainwave.regextoolboxkotlin

fun regex(vararg options: RegexOptions, buildSteps: RegexBuilder.() -> Unit) =
    RegexBuilder().apply(buildSteps).buildRegex(*options)

fun pattern(vararg options: RegexOptions, buildSteps: RegexBuilder.() -> Unit) =
    RegexBuilder().apply(buildSteps).buildPattern(*options)