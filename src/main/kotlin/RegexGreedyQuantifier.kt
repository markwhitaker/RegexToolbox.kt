package uk.co.mainwave.regextoolboxkotlin

/**
 * A quantifier which defaults to greedy matching: in other words, if used to match a variable number of elements it
 * will match as many as possible.
 */
class RegexGreedyQuantifier internal constructor(
    regexString: String
) : RegexQuantifier(regexString) {
    /**
     * Get a non-greedy version of this quantifier: in other words, if used to match a variable number of elements it
     * will match as few as possible.
     *
     * @return A non-greedy [RegexQuantifier]
     */
    fun butAsFewAsPossible(): RegexQuantifier {
        return makeNonGreedy()
    }
}