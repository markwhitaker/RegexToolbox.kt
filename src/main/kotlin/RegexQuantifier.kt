package uk.co.mainwave.regextoolboxkotlin

/**
 * Quantifiers that can be applied to regex elements or groups
 */
open class RegexQuantifier internal constructor(
    private var regexString: String
) {
    protected fun makeNonGreedy(): RegexQuantifier {
        regexString += "?"
        return this
    }

    override fun toString(): String {
        return regexString
    }

    companion object {
        /**
         * Quantifier to match the preceding element zero or more times
         *
         * @return A greedy quantifier: use [RegexGreedyQuantifier.butAsFewAsPossible] to make it non-greedy
         */
        fun zeroOrMore() = RegexGreedyQuantifier("*")

        /**
         * Quantifier to match the preceding element one or more times
         *
         * @return A greedy quantifier: use [RegexGreedyQuantifier.butAsFewAsPossible] to make it non-greedy
         */
        fun oneOrMore() = RegexGreedyQuantifier("+")

        /**
         * Quantifier to match the preceding element once or not at all
         *
         * @return A greedy quantifier: use [RegexGreedyQuantifier.butAsFewAsPossible] to make it non-greedy
         */
        fun noneOrOne() = RegexGreedyQuantifier("?")

        /**
         * Quantifier to match an exact number of occurrences of the preceding element
         *
         * @param times The exact number of occurrences to match
         * @return A non-greedy quantifier
         */
        fun exactly(times: Int): RegexQuantifier {
            return RegexQuantifier("{$times}")
        }

        /**
         * Quantifier to match at least a minimum number of occurrences of the preceding element
         *
         * @param minimum The minimum number of occurrences to match
         * @return A greedy quantifier: use [RegexGreedyQuantifier.butAsFewAsPossible] to make it non-greedy
         */
        fun atLeast(minimum: Int): RegexGreedyQuantifier {
            return RegexGreedyQuantifier("{$minimum,}")
        }

        /**
         * Quantifier to match no more than a maximum number of occurrences of the preceding element
         *
         * @param maximum The maximum number of occurrences to match
         * @return A greedy quantifier: use [RegexGreedyQuantifier.butAsFewAsPossible] to make it non-greedy
         */
        fun noMoreThan(maximum: Int): RegexGreedyQuantifier {
            return RegexGreedyQuantifier("{0,$maximum}")
        }

        /**
         * Quantifier to match at least a minimum, and no more than a maximum, occurrences of the preceding element
         *
         * @param minimum The minimum number of occurrences to match
         * @param maximum The maximum number of occurrences to match
         * @return A greedy quantifier: use [RegexGreedyQuantifier.butAsFewAsPossible] to make it non-greedy
         */
        fun between(minimum: Int, maximum: Int): RegexGreedyQuantifier {
            return RegexGreedyQuantifier("{$minimum,$maximum}")
        }
    }
}