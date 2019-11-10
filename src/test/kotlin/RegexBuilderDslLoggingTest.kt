import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import uk.co.mainwave.regextoolboxkotlin.RegexBuilder
import uk.co.mainwave.regextoolboxkotlin.RegexQuantifier
import uk.co.mainwave.regextoolboxkotlin.regex

class RegexBuilderDslLoggingTest {
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

        regex {
            addLogger(logger)
            text("Hello")
            whitespace(RegexQuantifier.OneOrMore)
            text("world", RegexQuantifier.ZeroOrOne)
        }

        verify { logger.log("RegexBuilder: text(\"Hello\"): Hello") }
        verify { logger.log("RegexBuilder: whitespace(OneOrMore): \\s+") }
        verify { logger.log("RegexBuilder: text(\"world\", ZeroOrOne): (?:world)?") }
        verify { logger.log("RegexBuilder: buildRegex(): Hello\\s+(?:world)?") }
    }

    @Test
    fun testAddLoggerInterfaceWithTag() {
        val logger = mockk<RegexBuilder.Logger>(relaxUnitFun = true)

        regex {
            addLogger("TEST", logger)
            text("Hello")
            whitespace(RegexQuantifier.OneOrMore)
            text("world", RegexQuantifier.ZeroOrOne)
        }

        verify { logger.log("TEST: text(\"Hello\"): Hello") }
        verify { logger.log("TEST: whitespace(OneOrMore): \\s+") }
        verify { logger.log("TEST: text(\"world\", ZeroOrOne): (?:world)?") }
        verify { logger.log("TEST: buildRegex(): Hello\\s+(?:world)?") }
    }

    @Test
    fun testAddLoggerLambda() {
        val list = mutableListOf<String>()

        regex {
            addLogger {
                list.add(it)
            }
            text("Hello")
            whitespace(RegexQuantifier.OneOrMore)
            text("world", RegexQuantifier.ZeroOrOne)
        }

        assertEquals(4, list.size)
        assertEquals("RegexBuilder: text(\"Hello\"): Hello", list[0])
        assertEquals("RegexBuilder: whitespace(OneOrMore): \\s+", list[1])
        assertEquals("RegexBuilder: text(\"world\", ZeroOrOne): (?:world)?", list[2])
        assertEquals("RegexBuilder: buildRegex(): Hello\\s+(?:world)?", list[3])
    }

    @Test
    fun testAddLoggerLambdaWithTag() {
        val list = mutableListOf<String>()

        regex {
            addLogger("TEST") {
                list.add(it)
            }
            text("Hello")
            whitespace(RegexQuantifier.OneOrMore)
            text("world", RegexQuantifier.ZeroOrOne)
        }

        assertEquals(4, list.size)
        assertEquals("TEST: text(\"Hello\"): Hello", list[0])
        assertEquals("TEST: whitespace(OneOrMore): \\s+", list[1])
        assertEquals("TEST: text(\"world\", ZeroOrOne): (?:world)?", list[2])
        assertEquals("TEST: buildRegex(): Hello\\s+(?:world)?", list[3])
    }

    @Test
    fun testGroup() {
        val list = mutableListOf<String>()

        regex {
            addLogger {
                list.add(it)
            }
            group {
                text("abc")
            }
        }

        assertEquals(4, list.size)
        assertEquals("RegexBuilder: startGroup(): (", list[0])
        assertEquals("RegexBuilder: text(\"abc\"): abc", list[1])
        assertEquals("RegexBuilder: endGroup(): )", list[2])
        assertEquals("RegexBuilder: buildRegex(): (abc)", list[3])
    }

    @Test
    fun testGroupWithQuantifier() {
        val list = mutableListOf<String>()

        regex {
            addLogger {
                list.add(it)
            }
            group(RegexQuantifier.ZeroOrMore) {
                text("abc")
            }
        }

        assertEquals(4, list.size)
        assertEquals("RegexBuilder: startGroup(): (", list[0])
        assertEquals("RegexBuilder: text(\"abc\"): abc", list[1])
        assertEquals("RegexBuilder: endGroup(ZeroOrMore): )*", list[2])
        assertEquals("RegexBuilder: buildRegex(): (abc)*", list[3])
    }

    @Test
    fun testNonCapturingGroup() {
        val list = mutableListOf<String>()

        regex {
            addLogger {
                list.add(it)
            }
            nonCapturingGroup {
                text("abc")
            }
        }

        assertEquals(4, list.size)
        assertEquals("RegexBuilder: startNonCapturingGroup(): (?:", list[0])
        assertEquals("RegexBuilder: text(\"abc\"): abc", list[1])
        assertEquals("RegexBuilder: endGroup(): )", list[2])
        assertEquals("RegexBuilder: buildRegex(): (?:abc)", list[3])
    }

    @Test
    fun testNonCapturingGroupWithQuantifier() {
        val list = mutableListOf<String>()

        regex {
            addLogger {
                list.add(it)
            }
            nonCapturingGroup(RegexQuantifier.ZeroOrMore) {
                text("abc")
            }
        }

        assertEquals(4, list.size)
        assertEquals("RegexBuilder: startNonCapturingGroup(): (?:", list[0])
        assertEquals("RegexBuilder: text(\"abc\"): abc", list[1])
        assertEquals("RegexBuilder: endGroup(ZeroOrMore): )*", list[2])
        assertEquals("RegexBuilder: buildRegex(): (?:abc)*", list[3])
    }

    @Test
    fun testNamedGroup() {
        val list = mutableListOf<String>()

        regex {
            addLogger {
                list.add(it)
            }
            namedGroup("name") {
                text("abc")
            }
        }

        assertEquals(4, list.size)
        assertEquals("RegexBuilder: startNamedGroup(\"name\"): (?<name>", list[0])
        assertEquals("RegexBuilder: text(\"abc\"): abc", list[1])
        assertEquals("RegexBuilder: endGroup(): )", list[2])
        assertEquals("RegexBuilder: buildRegex(): (?<name>abc)", list[3])
    }

    @Test
    fun testNamedGroupWithQuantifier() {
        val list = mutableListOf<String>()

        regex {
            addLogger {
                list.add(it)
            }
            namedGroup("name", RegexQuantifier.ZeroOrMore) {
                text("abc")
            }
        }

        assertEquals(4, list.size)
        assertEquals("RegexBuilder: startNamedGroup(\"name\"): (?<name>", list[0])
        assertEquals("RegexBuilder: text(\"abc\"): abc", list[1])
        assertEquals("RegexBuilder: endGroup(ZeroOrMore): )*", list[2])
        assertEquals("RegexBuilder: buildRegex(): (?<name>abc)*", list[3])
    }
}