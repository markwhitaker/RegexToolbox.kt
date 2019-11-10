import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import uk.co.mainwave.regextoolboxkotlin.RegexBuilder
import uk.co.mainwave.regextoolboxkotlin.RegexOptions
import uk.co.mainwave.regextoolboxkotlin.RegexQuantifier

class RegexBuilderLoggingTest {
    private var logOutput: String = ""
    private lateinit var regexBuilder: RegexBuilder

    @Before
    fun setUp() {
        logOutput = ""
        regexBuilder = RegexBuilder().addLogger { message ->
            logOutput = message
        }
    }

    @Test
    fun testAddLoggerInterface() {
        val logger = mockk<RegexBuilder.Logger>(relaxUnitFun = true)

        RegexBuilder()
            .addLogger(logger)
            .text("Hello")
            .whitespace(RegexQuantifier.OneOrMore)
            .text("world", RegexQuantifier.ZeroOrOne)
            .buildRegex()

        verify { logger.log("RegexBuilder: text(\"Hello\"): Hello") }
        verify { logger.log("RegexBuilder: whitespace(OneOrMore): \\s+") }
        verify { logger.log("RegexBuilder: text(\"world\", ZeroOrOne): (?:world)?") }
        verify { logger.log("RegexBuilder: buildRegex(): Hello\\s+(?:world)?") }
    }

    @Test
    fun testAddLoggerInterfaceWithTag() {
        val logger = mockk<RegexBuilder.Logger>(relaxUnitFun = true)

        RegexBuilder()
            .addLogger("TEST", logger)
            .text("Hello")
            .whitespace(RegexQuantifier.OneOrMore)
            .text("world", RegexQuantifier.ZeroOrOne)
            .buildRegex()

        verify { logger.log("TEST: text(\"Hello\"): Hello") }
        verify { logger.log("TEST: whitespace(OneOrMore): \\s+") }
        verify { logger.log("TEST: text(\"world\", ZeroOrOne): (?:world)?") }
        verify { logger.log("TEST: buildRegex(): Hello\\s+(?:world)?") }
    }

    @Test
    fun testAddLoggerLambda() {
        val list = mutableListOf<String>()

        RegexBuilder()
            .addLogger {
                list.add(it)
            }
            .text("Hello")
            .whitespace(RegexQuantifier.OneOrMore)
            .text("world", RegexQuantifier.ZeroOrOne)
            .buildRegex()

        assertEquals(4, list.size)
        assertEquals("RegexBuilder: text(\"Hello\"): Hello", list[0])
        assertEquals("RegexBuilder: whitespace(OneOrMore): \\s+", list[1])
        assertEquals("RegexBuilder: text(\"world\", ZeroOrOne): (?:world)?", list[2])
        assertEquals("RegexBuilder: buildRegex(): Hello\\s+(?:world)?", list[3])
    }

    @Test
    fun testAddLoggerLambdaWithTag() {
        val list = mutableListOf<String>()

        RegexBuilder()
            .addLogger("TEST") {
                list.add(it)
            }
            .text("Hello")
            .whitespace(RegexQuantifier.OneOrMore)
            .text("world", RegexQuantifier.ZeroOrOne)
            .buildRegex()

        assertEquals(4, list.size)
        assertEquals("TEST: text(\"Hello\"): Hello", list[0])
        assertEquals("TEST: whitespace(OneOrMore): \\s+", list[1])
        assertEquals("TEST: text(\"world\", ZeroOrOne): (?:world)?", list[2])
        assertEquals("TEST: buildRegex(): Hello\\s+(?:world)?", list[3])
    }

    @Test
    fun testText() {
        regexBuilder.text("[a-z]")
        assertEquals("RegexBuilder: text(\"[a-z]\"): \\[a-z\\]", logOutput)
    }


    @Test
    fun testTextWithQuantifier() {
        regexBuilder.text("[a-z]", RegexQuantifier.ZeroOrMore)
        assertEquals("RegexBuilder: text(\"[a-z]\", ZeroOrMore): (?:\\[a-z\\])*", logOutput)
    }

    @Test
    fun testRegexText() {
        regexBuilder.regexText("[a-z]")
        assertEquals("RegexBuilder: regexText(\"[a-z]\"): [a-z]", logOutput)
    }

    @Test
    fun testRegexTextWithQuantifier() {
        regexBuilder.regexText("[a-z]", RegexQuantifier.ZeroOrMore)
        assertEquals("RegexBuilder: regexText(\"[a-z]\", ZeroOrMore): (?:[a-z])*", logOutput)
    }

    @Test
    fun testAnyCharacter() {
        regexBuilder.anyCharacter()
        assertEquals("RegexBuilder: anyCharacter(): .", logOutput)
    }

    @Test
    fun testAnyCharacterWithQuantifier() {
        regexBuilder.anyCharacter(RegexQuantifier.ZeroOrMore)
        assertEquals("RegexBuilder: anyCharacter(ZeroOrMore): .*", logOutput)
    }

    @Test
    fun testWhitespace() {
        regexBuilder.whitespace()
        assertEquals("RegexBuilder: whitespace(): \\s", logOutput)
    }

    @Test
    fun testWhitespaceWithQuantifier() {
        regexBuilder.whitespace(RegexQuantifier.ZeroOrMore)
        assertEquals("RegexBuilder: whitespace(ZeroOrMore): \\s*", logOutput)
    }

    @Test
    fun testNonWhitespace() {
        regexBuilder.nonWhitespace()
        assertEquals("RegexBuilder: nonWhitespace(): \\S", logOutput)
    }

    @Test
    fun testNonWhitespaceWithQuantifier() {
        regexBuilder.nonWhitespace(RegexQuantifier.ZeroOrMore)
        assertEquals("RegexBuilder: nonWhitespace(ZeroOrMore): \\S*", logOutput)
    }

    @Test
    fun testPossibleWhitespace() {
        regexBuilder.possibleWhitespace()
        assertEquals("RegexBuilder: possibleWhitespace(): \\s*", logOutput)
    }

    @Test
    fun testSpace() {
        regexBuilder.space()
        assertEquals("RegexBuilder: space():  ", logOutput)
    }

    @Test
    fun testSpaceWithQuantifier() {
        regexBuilder.space(RegexQuantifier.ZeroOrMore)
        assertEquals("RegexBuilder: space(ZeroOrMore):  *", logOutput)
    }

    @Test
    fun testTab() {
        regexBuilder.tab()
        assertEquals("RegexBuilder: tab(): \\t", logOutput)
    }

    @Test
    fun testTabWithQuantifier() {
        regexBuilder.tab(RegexQuantifier.ZeroOrMore)
        assertEquals("RegexBuilder: tab(ZeroOrMore): \\t*", logOutput)
    }

    @Test
    fun testLineFeed() {
        regexBuilder.lineFeed()
        assertEquals("RegexBuilder: lineFeed(): \\n", logOutput)
    }

    @Test
    fun testLineFeedWithQuantifier() {
        regexBuilder.lineFeed(RegexQuantifier.ZeroOrMore)
        assertEquals("RegexBuilder: lineFeed(ZeroOrMore): \\n*", logOutput)
    }

    @Test
    fun testCarriageReturn() {
        regexBuilder.carriageReturn()
        assertEquals("RegexBuilder: carriageReturn(): \\r", logOutput)
    }

    @Test
    fun testCarriageReturnWithQuantifier() {
        regexBuilder.carriageReturn(RegexQuantifier.ZeroOrMore)
        assertEquals("RegexBuilder: carriageReturn(ZeroOrMore): \\r*", logOutput)
    }

    @Test
    fun testDigit() {
        regexBuilder.digit()
        assertEquals("RegexBuilder: digit(): \\d", logOutput)
    }

    @Test
    fun testDigitWithQuantifier() {
        regexBuilder.digit(RegexQuantifier.ZeroOrMore)
        assertEquals("RegexBuilder: digit(ZeroOrMore): \\d*", logOutput)
    }

    @Test
    fun testNonDigit() {
        regexBuilder.nonDigit()
        assertEquals("RegexBuilder: nonDigit(): \\D", logOutput)
    }

    @Test
    fun testNonDigitWithQuantifier() {
        regexBuilder.nonDigit(RegexQuantifier.ZeroOrMore)
        assertEquals("RegexBuilder: nonDigit(ZeroOrMore): \\D*", logOutput)
    }

    @Test
    fun testLetter() {
        regexBuilder.letter()
        assertEquals("RegexBuilder: letter(): \\p{L}", logOutput)
    }

    @Test
    fun testLetterWithQuantifier() {
        regexBuilder.letter(RegexQuantifier.ZeroOrMore)
        assertEquals("RegexBuilder: letter(ZeroOrMore): \\p{L}*", logOutput)
    }

    @Test
    fun testNonLetter() {
        regexBuilder.nonLetter()
        assertEquals("RegexBuilder: nonLetter(): \\P{L}", logOutput)
    }

    @Test
    fun testNonLetterWithQuantifier() {
        regexBuilder.nonLetter(RegexQuantifier.ZeroOrMore)
        assertEquals("RegexBuilder: nonLetter(ZeroOrMore): \\P{L}*", logOutput)
    }

    @Test
    fun testUppercaseLetter() {
        regexBuilder.uppercaseLetter()
        assertEquals("RegexBuilder: uppercaseLetter(): \\p{Lu}", logOutput)
    }

    @Test
    fun testUppercaseLetterWithQuantifier() {
        regexBuilder.uppercaseLetter(RegexQuantifier.ZeroOrMore)
        assertEquals("RegexBuilder: uppercaseLetter(ZeroOrMore): \\p{Lu}*", logOutput)
    }

    @Test
    fun testLowercaseLetter() {
        regexBuilder.lowercaseLetter()
        assertEquals("RegexBuilder: lowercaseLetter(): \\p{Ll}", logOutput)
    }

    @Test
    fun testLowercaseLetterWithQuantifier() {
        regexBuilder.lowercaseLetter(RegexQuantifier.ZeroOrMore)
        assertEquals("RegexBuilder: lowercaseLetter(ZeroOrMore): \\p{Ll}*", logOutput)
    }

    @Test
    fun testLetterOrDigit() {
        regexBuilder.letterOrDigit()
        assertEquals("RegexBuilder: letterOrDigit(): [\\p{L}0-9]", logOutput)
    }

    @Test
    fun testLetterOrDigitWithQuantifier() {
        regexBuilder.letterOrDigit(RegexQuantifier.ZeroOrMore)
        assertEquals("RegexBuilder: letterOrDigit(ZeroOrMore): [\\p{L}0-9]*", logOutput)
    }

    @Test
    fun testNonLetterOrDigit() {
        regexBuilder.nonLetterOrDigit()
        assertEquals("RegexBuilder: nonLetterOrDigit(): [^\\p{L}0-9]", logOutput)
    }

    @Test
    fun testNonLetterOrDigitWithQuantifier() {
        regexBuilder.nonLetterOrDigit(RegexQuantifier.ZeroOrMore)
        assertEquals("RegexBuilder: nonLetterOrDigit(ZeroOrMore): [^\\p{L}0-9]*", logOutput)
    }

    @Test
    fun testHexDigit() {
        regexBuilder.hexDigit()
        assertEquals("RegexBuilder: hexDigit(): [0-9A-Fa-f]", logOutput)
    }

    @Test
    fun testHexDigitWithQuantifier() {
        regexBuilder.hexDigit(RegexQuantifier.ZeroOrMore)
        assertEquals("RegexBuilder: hexDigit(ZeroOrMore): [0-9A-Fa-f]*", logOutput)
    }

    @Test
    fun testUppercaseHexDigit() {
        regexBuilder.uppercaseHexDigit()
        assertEquals("RegexBuilder: uppercaseHexDigit(): [0-9A-F]", logOutput)
    }

    @Test
    fun testUppercaseHexDigitWithQuantifier() {
        regexBuilder.uppercaseHexDigit(RegexQuantifier.ZeroOrMore)
        assertEquals("RegexBuilder: uppercaseHexDigit(ZeroOrMore): [0-9A-F]*", logOutput)
    }

    @Test
    fun testLowercaseHexDigit() {
        regexBuilder.lowercaseHexDigit()
        assertEquals("RegexBuilder: lowercaseHexDigit(): [0-9a-f]", logOutput)
    }

    @Test
    fun testLowercaseHexDigitWithQuantifier() {
        regexBuilder.lowercaseHexDigit(RegexQuantifier.ZeroOrMore)
        assertEquals("RegexBuilder: lowercaseHexDigit(ZeroOrMore): [0-9a-f]*", logOutput)
    }

    @Test
    fun testNonHexDigit() {
        regexBuilder.nonHexDigit()
        assertEquals("RegexBuilder: nonHexDigit(): [^0-9A-Fa-f]", logOutput)
    }

    @Test
    fun testNonHexDigitWithQuantifier() {
        regexBuilder.nonHexDigit(RegexQuantifier.ZeroOrMore)
        assertEquals("RegexBuilder: nonHexDigit(ZeroOrMore): [^0-9A-Fa-f]*", logOutput)
    }

    @Test
    fun testWordCharacter() {
        regexBuilder.wordCharacter()
        assertEquals("RegexBuilder: wordCharacter(): [\\p{L}0-9_]", logOutput)
    }

    @Test
    fun testWordCharacterWithQuantifier() {
        regexBuilder.wordCharacter(RegexQuantifier.ZeroOrMore)
        assertEquals("RegexBuilder: wordCharacter(ZeroOrMore): [\\p{L}0-9_]*", logOutput)
    }

    @Test
    fun testNonWordCharacter() {
        regexBuilder.nonWordCharacter()
        assertEquals("RegexBuilder: nonWordCharacter(): [^\\p{L}0-9_]", logOutput)
    }

    @Test
    fun testNonWordCharacterWithQuantifier() {
        regexBuilder.nonWordCharacter(RegexQuantifier.ZeroOrMore)
        assertEquals("RegexBuilder: nonWordCharacter(ZeroOrMore): [^\\p{L}0-9_]*", logOutput)
    }

    @Test
    fun testAnyCharacterFrom() {
        regexBuilder.anyCharacterFrom("abc")
        assertEquals("RegexBuilder: anyCharacterFrom(\"abc\"): [abc]", logOutput)
    }

    @Test
    fun testAnyCharacterFromWithQuantifier() {
        regexBuilder.anyCharacterFrom("abc", RegexQuantifier.ZeroOrMore)
        assertEquals("RegexBuilder: anyCharacterFrom(\"abc\", ZeroOrMore): [abc]*", logOutput)
    }

    @Test
    fun testAnyCharacterExcept() {
        regexBuilder.anyCharacterExcept("abc")
        assertEquals("RegexBuilder: anyCharacterExcept(\"abc\"): [^abc]", logOutput)
    }

    @Test
    fun testAnyCharacterExceptWithQuantifier() {
        regexBuilder.anyCharacterExcept("abc", RegexQuantifier.ZeroOrMore)
        assertEquals("RegexBuilder: anyCharacterExcept(\"abc\", ZeroOrMore): [^abc]*", logOutput)
    }

    @Test
    fun testAnyOfEmpty() {
        regexBuilder.anyOf(emptyList())
        assertEquals("RegexBuilder: anyOf(): strings list is empty, so doing nothing", logOutput)
    }

    @Test
    fun testAnyOfEmptyWithQuantifier() {
        regexBuilder.anyOf(emptyList(), RegexQuantifier.ZeroOrMore)
        assertEquals("RegexBuilder: anyOf(): strings list is empty, so doing nothing", logOutput)
    }

    @Test
    fun testAnyOfSingle() {
        regexBuilder.anyOf(listOf("abc"))
        assertEquals("RegexBuilder: anyOf(\"abc\"): abc", logOutput)
    }

    @Test
    fun testAnyOfSingleWithQuantifier() {
        regexBuilder.anyOf(listOf("abc"), RegexQuantifier.ZeroOrMore)
        assertEquals("RegexBuilder: anyOf(\"abc\", ZeroOrMore): (?:abc)*", logOutput)
    }

    @Test
    fun testAnyOfParamsSingle() {
        regexBuilder.anyOf("abc")
        assertEquals("RegexBuilder: anyOf(\"abc\"): abc", logOutput)
    }

    @Test
    fun testAnyOfMultiple() {
        regexBuilder.anyOf(listOf("abc", "def"))
        assertEquals("RegexBuilder: anyOf(\"abc\", \"def\"): (?:abc|def)", logOutput)
    }

    @Test
    fun testAnyOfMultipleWithQuantifier() {
        regexBuilder.anyOf(listOf("abc", "def"), RegexQuantifier.ZeroOrMore)
        assertEquals("RegexBuilder: anyOf(\"abc\", \"def\", ZeroOrMore): (?:abc|def)*", logOutput)
    }

    @Test
    fun testAnyOfParamsMultiple() {
        regexBuilder.anyOf("abc", "def")
        assertEquals("RegexBuilder: anyOf(\"abc\", \"def\"): (?:abc|def)", logOutput)
    }

    @Test
    fun testStartOfString() {
        regexBuilder.startOfString()
        assertEquals("RegexBuilder: startOfString(): ^", logOutput)
    }

    @Test
    fun testEndOfString() {
        regexBuilder.endOfString()
        assertEquals("RegexBuilder: endOfString(): $", logOutput)
    }

    @Test
    fun testWordBoundary() {
        regexBuilder.wordBoundary()
        assertEquals("RegexBuilder: wordBoundary(): \\b", logOutput)
    }

    @Test
    fun testStartGroup() {
        regexBuilder.startGroup()
        assertEquals("RegexBuilder: startGroup(): (", logOutput)
    }

    @Test
    fun testStartNonCapturingGroup() {
        regexBuilder.startNonCapturingGroup()
        assertEquals("RegexBuilder: startNonCapturingGroup(): (?:", logOutput)
    }

    @Test
    fun testNamedGroup() {
        regexBuilder.startNamedGroup("bert")
        assertEquals("RegexBuilder: startNamedGroup(\"bert\"): (?<bert>", logOutput)
    }

    @Test
    fun testEndGroup() {
        regexBuilder.startGroup().endGroup()
        assertEquals("RegexBuilder: endGroup(): )", logOutput)
    }

    @Test
    fun testQuantifierZeroOrMore() {
        regexBuilder.anyCharacter(RegexQuantifier.ZeroOrMore)
        assertEquals("RegexBuilder: anyCharacter(ZeroOrMore): .*", logOutput)
    }

    @Test
    fun testQuantifierZeroOrMoreButAsFewAsPossible() {
        regexBuilder.anyCharacter(RegexQuantifier.ZeroOrMore.butAsFewAsPossible)
        assertEquals("RegexBuilder: anyCharacter(ZeroOrMore.butAsFewAsPossible): .*?", logOutput)
    }

    @Test
    fun testQuantifierOneOrMore() {
        regexBuilder.anyCharacter(RegexQuantifier.OneOrMore)
        assertEquals("RegexBuilder: anyCharacter(OneOrMore): .+", logOutput)
    }

    @Test
    fun testQuantifierOneOrMoreButAsFewAsPossible() {
        regexBuilder.anyCharacter(RegexQuantifier.OneOrMore.butAsFewAsPossible)
        assertEquals("RegexBuilder: anyCharacter(OneOrMore.butAsFewAsPossible): .+?", logOutput)
    }

    @Test
    fun testQuantifierZeroOrOne() {
        regexBuilder.anyCharacter(RegexQuantifier.ZeroOrOne)
        assertEquals("RegexBuilder: anyCharacter(ZeroOrOne): .?", logOutput)
    }

    @Test
    fun testQuantifierZeroOrOneButAsFewAsPossible() {
        regexBuilder.anyCharacter(RegexQuantifier.ZeroOrOne.butAsFewAsPossible)
        assertEquals("RegexBuilder: anyCharacter(ZeroOrOne.butAsFewAsPossible): .??", logOutput)
    }

    @Test
    fun testQuantifierExactly() {
        regexBuilder.anyCharacter(RegexQuantifier.Exactly(10))
        assertEquals("RegexBuilder: anyCharacter(Exactly(10)): .{10}", logOutput)
    }

    @Test
    fun testQuantifierAtLeast() {
        regexBuilder.anyCharacter(RegexQuantifier.AtLeast(10))
        assertEquals("RegexBuilder: anyCharacter(AtLeast(10)): .{10,}", logOutput)
    }

    @Test
    fun testQuantifierAtLeastButAsFewAsPossible() {
        regexBuilder.anyCharacter(RegexQuantifier.AtLeast(10).butAsFewAsPossible)
        assertEquals("RegexBuilder: anyCharacter(AtLeast(10).butAsFewAsPossible): .{10,}?", logOutput)
    }

    @Test
    fun testQuantifierNoMoreThan() {
        regexBuilder.anyCharacter(RegexQuantifier.NoMoreThan(10))
        assertEquals("RegexBuilder: anyCharacter(NoMoreThan(10)): .{0,10}", logOutput)
    }

    @Test
    fun testQuantifierNoMoreThanButAsFewAsPossible() {
        regexBuilder.anyCharacter(RegexQuantifier.NoMoreThan(10).butAsFewAsPossible)
        assertEquals("RegexBuilder: anyCharacter(NoMoreThan(10).butAsFewAsPossible): .{0,10}?", logOutput)
    }

    @Test
    fun testQuantifierBetween() {
        regexBuilder.anyCharacter(RegexQuantifier.Between(10, 20))
        assertEquals("RegexBuilder: anyCharacter(Between(10, 20)): .{10,20}", logOutput)
    }

    @Test
    fun testQuantifierBetweenButAsFewAsPossible() {
        regexBuilder.anyCharacter(RegexQuantifier.Between(10, 20).butAsFewAsPossible)
        assertEquals("RegexBuilder: anyCharacter(Between(10, 20).butAsFewAsPossible): .{10,20}?", logOutput)
    }

    @Test
    fun testBuildRegex() {
        regexBuilder
            .text("hello")
            .whitespace(RegexQuantifier.OneOrMore)
            .text("world")
            .buildRegex()
        assertEquals("RegexBuilder: buildRegex(): hello\\s+world", logOutput)
    }

    @Test
    fun testBuildRegexWithOptions() {
        regexBuilder
            .text("hello")
            .whitespace(RegexQuantifier.OneOrMore)
            .text("world")
            .buildRegex(RegexOptions.MULTILINE, RegexOptions.IGNORE_CASE)
        assertEquals("RegexBuilder: buildRegex(): hello\\s+world", logOutput)
    }
}