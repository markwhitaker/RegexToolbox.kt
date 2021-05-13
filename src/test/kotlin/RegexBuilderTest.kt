package uk.co.mainwave.regextoolboxkotlin

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import uk.co.mainwave.regextoolboxkotlin.RegexOptions.IGNORE_CASE
import uk.co.mainwave.regextoolboxkotlin.RegexOptions.MULTILINE
import uk.co.mainwave.regextoolboxkotlin.RegexQuantifier.AtLeast
import uk.co.mainwave.regextoolboxkotlin.RegexQuantifier.Between
import uk.co.mainwave.regextoolboxkotlin.RegexQuantifier.Exactly
import uk.co.mainwave.regextoolboxkotlin.RegexQuantifier.NoMoreThan
import uk.co.mainwave.regextoolboxkotlin.RegexQuantifier.OneOrMore
import uk.co.mainwave.regextoolboxkotlin.RegexQuantifier.ZeroOrMore
import uk.co.mainwave.regextoolboxkotlin.RegexQuantifier.ZeroOrOne
import java.util.regex.Pattern

class RegexBuilderTest {
    @Test
    fun testNoOptions() {
        val regex = regex {
            anyCharacter()
        }

        assertTrue(regex.options.isEmpty())
    }

    @Test
    fun testIgnoreCase() {
        val regex = regex(IGNORE_CASE) {
            anyCharacter()
        }

        assertEquals(1, regex.options.size)
        assertTrue(regex.options.contains(RegexOption.IGNORE_CASE))
    }

    @Test
    fun testMultiline() {
        val regex = regex(MULTILINE) {
            anyCharacter()
        }

        assertEquals(1, regex.options.size)
        assertTrue(regex.options.contains(RegexOption.MULTILINE))
    }

    @Test
    fun testIgnoreCaseAndMultiline() {
        val regex = regex(IGNORE_CASE, MULTILINE) {
            anyCharacter()
        }

        assertEquals(2, regex.options.size)
        assertTrue(regex.options.contains(RegexOption.IGNORE_CASE))
        assertTrue(regex.options.contains(RegexOption.MULTILINE))
    }

    @Test
    fun testBuildPattern() {
        val pattern = pattern(IGNORE_CASE, MULTILINE) {
            text("cat")
        }

        assertEquals("cat", pattern.toString())
        assertEquals(Pattern.CASE_INSENSITIVE, pattern.flags() and Pattern.CASE_INSENSITIVE)
        assertEquals(Pattern.MULTILINE, pattern.flags() and Pattern.MULTILINE)
    }

    @Test
    fun testEscapeCharacters() {
        val regex = regex {
            text("\\?.+*^\$()[]{}|")
        }

        assertEquals("\\\\\\?\\.\\+\\*\\^\\\$\\(\\)\\[\\]\\{\\}\\|", regex.toString())
    }

    @Test
    fun testText() {
        val regex = regex {
            text("a*b")
        }

        assertEquals("a\\*b", regex.toString())
    }

    @Test
    fun testTextWithQuantifier() {
        val regex = regex {
            text("a*b", OneOrMore)
        }

        assertEquals("(?:a\\*b)+", regex.toString())
    }

    @Test
    fun testRegexText() {
        val regex = regex {
            regexText("a*b")
        }

        assertEquals("a*b", regex.toString())
    }

    @Test
    fun testRegexTextWithQuantifier() {
        val regex = regex {
            regexText("a*b", OneOrMore)
        }

        assertEquals("(?:a*b)+", regex.toString())
    }

    @Test
    fun testAnyCharacter() {
        val regex = regex {
            anyCharacter()
        }

        assertEquals(".", regex.toString())
    }

    @Test
    fun testAnyCharacterWithQuantifier() {
        val regex = regex {
            anyCharacter(OneOrMore)
        }

        assertEquals(".+", regex.toString())
    }

    @Test
    fun testWhitespace() {
        val regex = regex {
            whitespace()
        }

        assertEquals("\\s", regex.toString())
    }

    @Test
    fun testWhitespaceWithQuantifier() {
        val regex = regex {
            whitespace(OneOrMore)
        }

        assertEquals("\\s+", regex.toString())
    }

    @Test
    fun testNonWhitespace() {
        val regex = regex {
            nonWhitespace()
        }

        assertEquals("\\S", regex.toString())
    }

    @Test
    fun testNonWhitespaceWithQuantifier() {
        val regex = regex {
            nonWhitespace(OneOrMore)
        }

        assertEquals("\\S+", regex.toString())
    }

    @Test
    fun testPossibleWhitespace() {
        val regex = regex {
            possibleWhitespace()
        }

        assertEquals("\\s*", regex.toString())
    }

    @Test
    fun testSpace() {
        val regex = regex {
            space()
        }

        assertEquals(" ", regex.toString())
    }

    @Test
    fun testSpaceWithQuantifier() {
        val regex = regex {
            space(OneOrMore)
        }

        assertEquals(" +", regex.toString())
    }

    @Test
    fun testTab() {
        val regex = regex {
            tab()
        }

        assertEquals("\\t", regex.toString())
    }

    @Test
    fun testTabWithQuantifier() {
        val regex = regex {
            tab(OneOrMore)
        }

        assertEquals("\\t+", regex.toString())
    }

    @Test
    fun testLineFeed() {
        val regex = regex {
            lineFeed()
        }

        assertEquals("\\n", regex.toString())
    }

    @Test
    fun testLineFeedWithQuantifier() {
        val regex = regex {
            lineFeed(OneOrMore)
        }

        assertEquals("\\n+", regex.toString())
    }

    @Test
    fun testCarriageReturn() {
        val regex = regex {
            carriageReturn()
        }

        assertEquals("\\r", regex.toString())
    }

    @Test
    fun testCarriageReturnWithQuantifier() {
        val regex = regex {
            carriageReturn(OneOrMore)
        }

        assertEquals("\\r+", regex.toString())
    }

    @Test
    fun testDigit() {
        val regex = regex {
            digit()
        }

        assertEquals("\\d", regex.toString())
    }

    @Test
    fun testDigitWithQuantifier() {
        val regex = regex {
            digit(OneOrMore)
        }

        assertEquals("\\d+", regex.toString())
    }

    @Test
    fun testNonDigit() {
        val regex = regex {
            nonDigit()
        }

        assertEquals("\\D", regex.toString())
    }

    @Test
    fun testNonDigitWithQuantifier() {
        val regex = regex {
            nonDigit(OneOrMore)
        }

        assertEquals("\\D+", regex.toString())
    }

    @Test
    fun testLetter() {
        val regex = regex {
            letter()
        }

        assertEquals("\\p{L}", regex.toString())
    }

    @Test
    fun testLetterWithQuantifier() {
        val regex = regex {
            letter(OneOrMore)
        }

        assertEquals("\\p{L}+", regex.toString())
    }

    @Test
    fun testNonLetter() {
        val regex = regex {
            nonLetter()
        }

        assertEquals("\\P{L}", regex.toString())
    }

    @Test
    fun testNonLetterWithQuantifier() {
        val regex = regex {
            nonLetter(OneOrMore)
        }

        assertEquals("\\P{L}+", regex.toString())
    }

    @Test
    fun testUppercaseLetter() {
        val regex = regex {
            uppercaseLetter()
        }

        assertEquals("\\p{Lu}", regex.toString())
    }

    @Test
    fun testUppercaseLetterWithQuantifier() {
        val regex = regex {
            uppercaseLetter(OneOrMore)
        }

        assertEquals("\\p{Lu}+", regex.toString())
    }

    @Test
    fun testLowercaseLetter() {
        val regex = regex {
            lowercaseLetter()
        }

        assertEquals("\\p{Ll}", regex.toString())
    }

    @Test
    fun testLowercaseLetterWithQuantifier() {
        val regex = regex {
            lowercaseLetter(OneOrMore)
        }

        assertEquals("\\p{Ll}+", regex.toString())
    }

    @Test
    fun testLetterOrDigit() {
        val regex = regex {
            letterOrDigit()
        }

        assertEquals("[\\p{L}0-9]", regex.toString())
    }

    @Test
    fun testLetterOrDigitWithQuantifier() {
        val regex = regex {
            letterOrDigit(OneOrMore)
        }

        assertEquals("[\\p{L}0-9]+", regex.toString())
    }

    @Test
    fun testNonLetterOrDigit() {
        val regex = regex {
            nonLetterOrDigit()
        }

        assertEquals("[^\\p{L}0-9]", regex.toString())
    }

    @Test
    fun testNonLetterOrDigitWithQuantifier() {
        val regex = regex {
            nonLetterOrDigit(OneOrMore)
        }

        assertEquals("[^\\p{L}0-9]+", regex.toString())
    }

    @Test
    fun testHexDigit() {
        val regex = regex {
            hexDigit()
        }

        assertEquals("[0-9A-Fa-f]", regex.toString())
    }

    @Test
    fun testHexDigitWithQuantifier() {
        val regex = regex {
            hexDigit(OneOrMore)
        }

        assertEquals("[0-9A-Fa-f]+", regex.toString())
    }

    @Test
    fun testUppercaseHexDigit() {
        val regex = regex {
            uppercaseHexDigit()
        }

        assertEquals("[0-9A-F]", regex.toString())
    }

    @Test
    fun testUppercaseHexDigitWithQuantifier() {
        val regex = regex {
            uppercaseHexDigit(OneOrMore)
        }

        assertEquals("[0-9A-F]+", regex.toString())
    }

    @Test
    fun testLowercaseHexDigit() {
        val regex = regex {
            lowercaseHexDigit()
        }

        assertEquals("[0-9a-f]", regex.toString())
    }

    @Test
    fun testLowercaseHexDigitWithQuantifier() {
        val regex = regex {
            lowercaseHexDigit(OneOrMore)
        }

        assertEquals("[0-9a-f]+", regex.toString())
    }

    @Test
    fun testNonHexDigit() {
        val regex = regex {
            nonHexDigit()
        }

        assertEquals("[^0-9A-Fa-f]", regex.toString())
    }

    @Test
    fun testNonHexDigitWithQuantifier() {
        val regex = regex {
            nonHexDigit(OneOrMore)
        }

        assertEquals("[^0-9A-Fa-f]+", regex.toString())
    }

    @Test
    fun testWordCharacter() {
        val regex = regex {
            wordCharacter()
        }

        assertEquals("[\\p{L}0-9_]", regex.toString())
    }

    @Test
    fun testWordCharacterWithQuantifier() {
        val regex = regex {
            wordCharacter(OneOrMore)
        }

        assertEquals("[\\p{L}0-9_]+", regex.toString())
    }

    @Test
    fun testNonWordCharacter() {
        val regex = regex {
            nonWordCharacter()
        }

        assertEquals("[^\\p{L}0-9_]", regex.toString())
    }

    @Test
    fun testNonWordCharacterWithQuantifier() {
        val regex = regex {
            nonWordCharacter(OneOrMore)
        }

        assertEquals("[^\\p{L}0-9_]+", regex.toString())
    }

    @Test
    fun testAnyCharacterFrom() {
        val regex = regex {
            anyCharacterFrom("^]")
        }

        assertEquals("[\\^\\]]", regex.toString())
    }

    @Test
    fun testAnyCharacterFromWithQuantifier() {
        val regex = regex {
            anyCharacterFrom("^]", OneOrMore)
        }

        assertEquals("[\\^\\]]+", regex.toString())
    }

    @Test
    fun testAnyCharacterFromWithCaretAtStart() {
        val regex = regex {
            anyCharacterFrom("^abc")
        }

        assertEquals("[\\^abc]", regex.toString())
    }

    @Test
    fun testAnyCharacterFromWithHyphen() {
        val regex = regex {
            anyCharacterFrom("a-f")
        }

        assertEquals("[a\\-f]", regex.toString())
    }

    @Test
    fun testAnyCharacterFromWithCaretNotAtStart() {
        val regex = regex {
            anyCharacterFrom("a^bc")
        }

        assertEquals("[a^bc]", regex.toString())
    }

    @Test
    fun testAnyCharacterExcept() {
        val regex = regex {
            anyCharacterExcept("^]")
        }

        assertEquals("[^\\^\\]]", regex.toString())
    }

    @Test
    fun testAnyCharacterExceptWithQuantifier() {
        val regex = regex {
            anyCharacterExcept("^]", OneOrMore)
        }

        assertEquals("[^\\^\\]]+", regex.toString())
    }

    @Test
    fun testAnyOf() {
        val regex = regex {
            anyOf(listOf("cat", "dog", "|"))
        }

        assertEquals("(?:cat|dog|\\|)", regex.toString())
    }

    @Test
    fun testAnyOfWithQuantifier() {
        val regex = regex {
            anyOf(listOf("cat", "dog", "|"), Exactly(2))
        }

        assertEquals("(?:cat|dog|\\|){2}", regex.toString())
    }

    @Test
    fun testVarargAnyOf() {
        val regex = regex {
            anyOf("cat", "dog", "|")
        }

        assertEquals("(?:cat|dog|\\|)", regex.toString())
    }

    @Test
    fun testVarargAnyOfWithQuantifier() {
        val regex = regex {
            anyOf("cat", "dog", "|", quantifier = Exactly(2))
        }

        assertEquals("(?:cat|dog|\\|){2}", regex.toString())
    }

    @Test
    fun testAnyOfEmptyOrSingle() {
        val anyOfEmptyRegex = regex {
            anyOf(emptyList())
        }

        val anyOfSingleRegex = regex {
            anyOf(listOf("cat"))
        }

        assertEquals("", anyOfEmptyRegex.toString())
        assertEquals("cat", anyOfSingleRegex.toString())
    }

    @Test
    fun testStartOfString() {
        val regex = regex {
            startOfString()
        }

        assertEquals("^", regex.toString())
    }

    @Test
    fun testEndOfString() {
        val regex = regex {
            endOfString()
        }

        assertEquals("$", regex.toString())
    }

    @Test
    fun testWordBoundary() {
        val regex = regex {
            wordBoundary()
        }

        assertEquals("\\b", regex.toString())
    }

    @Test
    fun testGroup() {
        val regex = regex {
            group {
                text("a")
                digit()
            }
        }

        assertEquals("(a\\d)", regex.toString())
    }

    @Test
    fun testGroupWithQuantifier() {
        val regex = regex {
            group(OneOrMore) {
                text("a")
                digit()
            }
        }

        assertEquals("(a\\d)+", regex.toString())
    }

    @Test
    fun testNestedGroup() {
        val regex = regex {
            group {
                text("a")
                group {
                    digit()
                }
            }
        }

        assertEquals("(a(\\d))", regex.toString())
    }

    @Test
    fun testNonCapturingGroup() {
        val regex = regex {
            nonCapturingGroup {
                text("a")
                digit()
            }
        }

        assertEquals("(?:a\\d)", regex.toString())
    }

    @Test
    fun testNonCapturingGroupWithQuantifier() {
        val regex = regex {
            nonCapturingGroup(OneOrMore) {
                text("a")
                digit()
            }
        }

        assertEquals("(?:a\\d)+", regex.toString())
    }

    @Test
    fun testNestedNonCapturingGroup() {
        val regex = regex {
            nonCapturingGroup {
                text("a")
                nonCapturingGroup {
                    digit()
                }
            }
        }

        assertEquals("(?:a(?:\\d))", regex.toString())
    }

    @Test
    fun testNamedGroup() {
        val regex = regex {
            namedGroup("group") {
                text("a")
                digit()
            }
        }

        assertEquals("(?<group>a\\d)", regex.toString())
    }

    @Test
    fun testNamedGroupWithQuantifier() {
        val regex = regex {
            namedGroup("group", OneOrMore) {
                text("a")
                digit()
            }
        }

        assertEquals("(?<group>a\\d)+", regex.toString())
    }

    @Test
    fun testNestedNamedGroup() {
        val regex = regex {
            namedGroup("group1") {
                text("a")
                namedGroup("group2") {
                    digit()
                }
            }
        }

        assertEquals("(?<group1>a(?<group2>\\d))", regex.toString())
    }

    @Test
    fun testOneOrMoreQuantifier() {
        val regexOneOrMore = regex {
            digit(OneOrMore)
        }
        val regexOneOrMoreButAsFewAsPossible = regex {
            digit(OneOrMore.butAsFewAsPossible)
        }

        assertEquals("\\d+", regexOneOrMore.toString())
        assertEquals("\\d+?", regexOneOrMoreButAsFewAsPossible.toString())
    }

    @Test
    fun testZeroOrMoreQuantifier() {
        val regexZeroOrMore = regex {
            digit(ZeroOrMore)
        }
        val regexZeroOrMoreButAsFewAsPossible = regex {
            digit(ZeroOrMore.butAsFewAsPossible)
        }

        assertEquals("\\d*", regexZeroOrMore.toString())
        assertEquals("\\d*?", regexZeroOrMoreButAsFewAsPossible.toString())
    }

    @Test
    fun testZeroOrOneQuantifier() {
        val regexZeroOrOne = regex {
            digit(ZeroOrOne)
        }
        val regexZeroOrOneButAsFewAsPossible = regex {
            digit(ZeroOrOne.butAsFewAsPossible)
        }

        assertEquals("\\d?", regexZeroOrOne.toString())
        assertEquals("\\d??", regexZeroOrOneButAsFewAsPossible.toString())
    }

    @Test
    fun testAtLeastQuantifier() {
        val regexAtLeast = regex {
            digit(AtLeast(1))
        }
        val regexAtLeastButAsFewAsPossible = regex {
            digit(AtLeast(1).butAsFewAsPossible)
        }

        assertEquals("\\d{1,}", regexAtLeast.toString())
        assertEquals("\\d{1,}?", regexAtLeastButAsFewAsPossible.toString())
    }

    @Test
    fun testNoMoreThanQuantifier() {
        val regexNoMoreThan = regex {
            digit(NoMoreThan(2))
        }
        val regexNoMoreThanButAsFewAsPossible = regex {
            digit(NoMoreThan(2).butAsFewAsPossible)
        }

        assertEquals("\\d{0,2}", regexNoMoreThan.toString())
        assertEquals("\\d{0,2}?", regexNoMoreThanButAsFewAsPossible.toString())
    }

    @Test
    fun testBetweenQuantifier() {
        val regexBetween = regex {
            digit(Between(1, 2))
        }
        val regexBetweenButAsFewAsPossible = regex {
            digit(Between(1, 2).butAsFewAsPossible)
        }

        assertEquals("\\d{1,2}", regexBetween.toString())
        assertEquals("\\d{1,2}?", regexBetweenButAsFewAsPossible.toString())
    }

    @Test
    fun testExactlyQuantifier() {
        val regexExactly = regex {
            digit(Exactly(2))
        }

        assertEquals("\\d{2}", regexExactly.toString())
    }
}