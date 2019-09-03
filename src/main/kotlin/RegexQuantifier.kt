package uk.co.mainwave.regextoolboxkotlin

/**
 * Quantifiers that can be applied to regex elements or groups
 */
sealed class RegexQuantifier(
    private val regexString: String
) {
    override fun toString() = regexString

    interface Greedy {
        val butAsFewAsPossible: RegexQuantifier

        @Deprecated(
            message = "Replace with butAsFewAsPossible",
            replaceWith = ReplaceWith(
                "butAsFewAsPossible",
                "uk.co.mainwave.regextoolboxkotlin.RegexQuantifier"
            )
        )
        fun butAsFewAsPossible() = butAsFewAsPossible
    }

    /**
     * Quantifier to match the preceding element zero or more times
     *
     * @return A greedy quantifier: use [butAsFewAsPossible] to make it lazy
     */
    object ZeroOrMore : RegexQuantifier("*"), Greedy {
        override val butAsFewAsPossible: RegexQuantifier = LazyZeroOrMore
    }

    /**
     * Quantifier to match the preceding element one or more times
     *
     * @return A greedy quantifier: use [butAsFewAsPossible] to make it lazy
     */
    object OneOrMore : RegexQuantifier("+"), Greedy {
        override val butAsFewAsPossible: RegexQuantifier = LazyOneOrMore
    }

    /**
     * Quantifier to match the preceding element once or not at all
     *
     * @return A greedy quantifier: use [butAsFewAsPossible] to make it lazy
     */
    object ZeroOrOne : RegexQuantifier("?"), Greedy {
        override val butAsFewAsPossible: RegexQuantifier = LazyZeroOrOne
    }

    /**
     * Quantifier to match an exact number of occurrences of the preceding element
     *
     * @param times The exact number of occurrences to match
     * @return A quantifier that is neither lazy nor greedy: it will match the exact number of element occurrences
     * specified
     */
    class Exactly(times: Int) : RegexQuantifier("{$times}")

    /**
     * Quantifier to match at least a minimum number of occurrences of the preceding element
     *
     * @param minimum The minimum number of occurrences to match
     * @return A greedy quantifier: use [butAsFewAsPossible] to make it lazy
     */
    class AtLeast(minimum: Int) : RegexQuantifier("{$minimum,}"), Greedy {
        override val butAsFewAsPossible: RegexQuantifier = LazyAtLeast(minimum)
    }

    /**
     * Quantifier to match no more than a maximum number of occurrences of the preceding element
     *
     * @param maximum The maximum number of occurrences to match
     * @return A greedy quantifier: use [butAsFewAsPossible] to make it lazy
     */
    class NoMoreThan(maximum: Int) : RegexQuantifier("{0,$maximum}"), Greedy {
        override val butAsFewAsPossible: RegexQuantifier = LazyNoMoreThan(maximum)
    }

    /**
     * Quantifier to match at least a minimum, and no more than a maximum, occurrences of the preceding element
     *
     * @param minimum The minimum number of occurrences to match
     * @param maximum The maximum number of occurrences to match
     * @return A greedy quantifier: use [butAsFewAsPossible] to make it lazy
     */
    class Between(minimum: Int, maximum: Int) : RegexQuantifier("{$minimum,$maximum}"), Greedy {
        override val butAsFewAsPossible: RegexQuantifier = LazyBetween(minimum, maximum)
    }

    private object LazyZeroOrMore : RegexQuantifier("*?")
    private object LazyOneOrMore : RegexQuantifier("+?")
    private object LazyZeroOrOne : RegexQuantifier("??")
    private class LazyAtLeast(minimum: Int) : RegexQuantifier("{$minimum,}?")
    private class LazyNoMoreThan(maximum: Int) : RegexQuantifier("{0,$maximum}?")
    private class LazyBetween(minimum: Int, maximum: Int) : RegexQuantifier("{$minimum,$maximum}?")

    companion object {
        @Deprecated(
            message = "Replace with ZeroOrOne",
            replaceWith = ReplaceWith(
                "ZeroOrOne",
                "uk.co.mainwave.regextoolboxkotlin.RegexQuantifier.ZeroOrOne"
            )
        )
        fun oneOrNone() = ZeroOrOne

        @Deprecated(
            message = "Replace with ZeroOrOne",
            replaceWith = ReplaceWith(
                "ZeroOrOne",
                "uk.co.mainwave.regextoolboxkotlin.RegexQuantifier.ZeroOrOne"
            )
        )
        fun zeroOrOne() = ZeroOrOne

        @Deprecated(
            message = "Replace with ZeroOrMore",
            replaceWith = ReplaceWith(
                "ZeroOrMore",
                "uk.co.mainwave.regextoolboxkotlin.RegexQuantifier.ZeroOrMore"
            )
        )
        fun zeroOrMore() = ZeroOrMore

        @Deprecated(
            message = "Replace with OneOrMore",
            replaceWith = ReplaceWith(
                "OneOrMore",
                "uk.co.mainwave.regextoolboxkotlin.RegexQuantifier.OneOrMore"
            )
        )
        fun oneOrMore() = OneOrMore

        @Deprecated(
            message = "Replace with Exactly",
            replaceWith = ReplaceWith(
                "Exactly(times)",
                "uk.co.mainwave.regextoolboxkotlin.RegexQuantifier.Exactly"
            )
        )
        fun exactly(times: Int) = Exactly(times)

        @Deprecated(
            message = "Replace with AtLeast",
            replaceWith = ReplaceWith(
                "AtLeast(minimum)",
                "uk.co.mainwave.regextoolboxkotlin.RegexQuantifier.AtLeast"
            )
        )
        fun atLeast(minimum: Int) = AtLeast(minimum)

        @Deprecated(
            message = "Replace with NoMoreThan",
            replaceWith = ReplaceWith(
                "NoMoreThan(maximum)",
                "uk.co.mainwave.regextoolboxkotlin.RegexQuantifier.NoMoreThan"
            )
        )
        fun noMoreThan(maximum: Int) = NoMoreThan(maximum)

        @Deprecated(
            message = "Replace with Between",
            replaceWith = ReplaceWith(
                "Between(minimum, maximum)",
                "uk.co.mainwave.regextoolboxkotlin.RegexQuantifier.Between"
            )
        )
        fun between(minimum: Int, maximum: Int) = Between(minimum, maximum)
    }
}
