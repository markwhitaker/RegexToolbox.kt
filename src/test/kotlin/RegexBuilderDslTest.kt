package uk.co.mainwave.regextoolboxkotlin

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
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

class RegexBuilderDslTest {
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
    fun testSimpleText() {
        val regex = regex {
            text("cat")
        }

        assertEquals("cat", regex.toString())
        assertTrue(regex.matches("cat"))
        assertTrue(regex.containsMatchIn("scatter"))
        assertFalse(regex.matches("Cat"))
        assertFalse(regex.matches("dog"))

        assertFalse(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertFalse(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertFalse(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertFalse(regex.containsMatchIn(IPV6_ADDRESS))
        assertFalse(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testSimpleTextWithQuantifier() {
        val regex = regex {
            text("cat", Exactly(2))
        }

        assertEquals("(?:cat){2}", regex.toString())
        assertFalse(regex.containsMatchIn("cat"))
        assertTrue(regex.matches("catcat"))
        assertTrue(regex.containsMatchIn("catcatcat"))
        assertFalse(regex.containsMatchIn("scatter"))
        assertFalse(regex.containsMatchIn("Cat"))
        assertFalse(regex.containsMatchIn("dog"))

        assertFalse(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertFalse(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertFalse(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertFalse(regex.containsMatchIn(IPV6_ADDRESS))
        assertFalse(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testSimpleTextCaseInsensitive() {
        val regex = regex(IGNORE_CASE) {
            text("cat")
        }

        assertEquals("cat", regex.toString())
        assertTrue(regex.matches("cat"))
        assertTrue(regex.containsMatchIn("scatter"))
        assertTrue(regex.matches("Cat"))
        assertFalse(regex.containsMatchIn("dog"))

        assertFalse(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertFalse(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertFalse(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertFalse(regex.containsMatchIn(IPV6_ADDRESS))
        assertFalse(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testSimpleTextWithRegexCharacters() {
        val regex = regex {
            text("\\.+*?[]{}()|^$")
        }

        assertEquals("\\\\\\.\\+\\*\\?\\[\\]\\{\\}\\(\\)\\|\\^\\$", regex.toString())
        assertTrue(regex.containsMatchIn("\\.+*?[]{}()|^$"))

        assertFalse(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertFalse(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertFalse(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertFalse(regex.containsMatchIn(IPV6_ADDRESS))
        assertFalse(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testRegexText() {
        val regex = regex {
            regexText("^\\scat\\b")
        }

        assertEquals("^\\scat\\b", regex.toString())
        assertTrue(regex.containsMatchIn(" cat"))
        assertTrue(regex.containsMatchIn(" cat."))
        assertTrue(regex.containsMatchIn("\tcat "))
        assertTrue(regex.containsMatchIn(" cat-"))
        assertTrue(regex.containsMatchIn(" cat "))
        assertFalse(regex.containsMatchIn("cat"))
        assertFalse(regex.containsMatchIn(" catheter"))

        assertFalse(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertFalse(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertFalse(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertFalse(regex.containsMatchIn(IPV6_ADDRESS))
        assertFalse(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testAnyCharacter() {
        val regex = regex {
            anyCharacter()
        }

        assertEquals(".", regex.toString())
        assertTrue(regex.containsMatchIn(" "))
        assertTrue(regex.containsMatchIn("a"))
        assertTrue(regex.containsMatchIn("1"))
        assertTrue(regex.containsMatchIn("\\"))
        assertFalse(regex.containsMatchIn(""))
        assertFalse(regex.containsMatchIn("\n"))

        assertTrue(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(DECIMAL_DIGITS))
        assertTrue(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(SYMBOLS))
        assertTrue(regex.containsMatchIn(WHITE_SPACE))
        assertTrue(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertTrue(regex.containsMatchIn(SIMPLE_NAME))
        assertTrue(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertTrue(regex.containsMatchIn(IPV4_ADDRESS))
        assertTrue(regex.containsMatchIn(IPV6_ADDRESS))
        assertTrue(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testWhitespace() {
        val regex = regex {
            whitespace()
        }

        assertEquals("\\s", regex.toString())
        assertTrue(regex.containsMatchIn(" "))
        assertTrue(regex.containsMatchIn("\t"))
        assertTrue(regex.containsMatchIn("\r"))
        assertTrue(regex.containsMatchIn("\n"))
        assertTrue(regex.containsMatchIn("\r\n"))
        assertTrue(regex.containsMatchIn("\t \t"))
        assertTrue(regex.containsMatchIn("                hi!"))
        assertFalse(regex.containsMatchIn("cat"))

        assertFalse(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertFalse(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertTrue(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertTrue(regex.containsMatchIn(SIMPLE_NAME))
        assertFalse(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertFalse(regex.containsMatchIn(IPV6_ADDRESS))
        assertFalse(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testNonWhitespace() {
        val regex = regex {
            nonWhitespace()
        }

        assertEquals("\\S", regex.toString())
        assertTrue(regex.containsMatchIn("a"))
        assertTrue(regex.containsMatchIn("1"))
        assertTrue(regex.containsMatchIn("-"))
        assertTrue(regex.containsMatchIn("*"))
        assertTrue(regex.containsMatchIn("abc"))
        assertTrue(regex.containsMatchIn("                hi!"))
        assertFalse(regex.containsMatchIn(" "))
        assertFalse(regex.containsMatchIn("\t"))
        assertFalse(regex.containsMatchIn("\r"))
        assertFalse(regex.containsMatchIn("\n"))
        assertFalse(regex.containsMatchIn("\t\t\r\n   "))

        assertTrue(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(DECIMAL_DIGITS))
        assertTrue(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertTrue(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertTrue(regex.containsMatchIn(SIMPLE_NAME))
        assertTrue(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertTrue(regex.containsMatchIn(IPV4_ADDRESS))
        assertTrue(regex.containsMatchIn(IPV6_ADDRESS))
        assertTrue(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testPossibleWhitespace() {
        val regex = regex {
            nonWhitespace()
            possibleWhitespace()
            nonWhitespace()
        }

        assertEquals("\\S\\s*\\S", regex.toString())
        assertFalse(regex.containsMatchIn("1"))
        assertFalse(regex.containsMatchIn("0"))
        assertTrue(regex.containsMatchIn("999"))
        assertTrue(regex.containsMatchIn("there's a digit in here s0mewhere"))
        assertFalse(regex.containsMatchIn(" "))
        assertTrue(regex.containsMatchIn("abc"))
        assertTrue(regex.containsMatchIn("xFFF"))

        assertTrue(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(DECIMAL_DIGITS))
        assertTrue(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertTrue(regex.containsMatchIn(SIMPLE_NAME))
        assertTrue(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertTrue(regex.containsMatchIn(IPV4_ADDRESS))
        assertTrue(regex.containsMatchIn(IPV6_ADDRESS))
        assertTrue(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testSpace() {
        val regex = regex {
            space()
        }

        assertEquals(" ", regex.toString())
        assertTrue(regex.containsMatchIn(" "))
        assertFalse(regex.containsMatchIn("\t"))
        assertFalse(regex.containsMatchIn("\r"))
        assertFalse(regex.containsMatchIn("\n"))
        assertFalse(regex.containsMatchIn("\r\n"))
        assertTrue(regex.containsMatchIn("\t \t"))
        assertTrue(regex.containsMatchIn("                hi!"))
        assertFalse(regex.containsMatchIn("cat"))

        assertFalse(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertFalse(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertTrue(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertTrue(regex.containsMatchIn(SIMPLE_NAME))
        assertFalse(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertFalse(regex.containsMatchIn(IPV6_ADDRESS))
        assertFalse(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testTab() {
        val regex = regex {
            tab()
        }

        assertEquals("\\t", regex.toString())
        assertFalse(regex.containsMatchIn(" "))
        assertTrue(regex.containsMatchIn("\t"))
        assertFalse(regex.containsMatchIn("\r"))
        assertFalse(regex.containsMatchIn("\n"))
        assertFalse(regex.containsMatchIn("\r\n"))
        assertTrue(regex.containsMatchIn("\t \t"))
        assertFalse(regex.containsMatchIn("                hi!"))
        assertFalse(regex.containsMatchIn("cat"))

        assertFalse(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertFalse(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertTrue(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertFalse(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertFalse(regex.containsMatchIn(IPV6_ADDRESS))
        assertFalse(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testLineFeed() {
        val regex = regex {
            lineFeed()
        }

        assertEquals("\\n", regex.toString())
        assertFalse(regex.containsMatchIn(" "))
        assertFalse(regex.containsMatchIn("\t"))
        assertFalse(regex.containsMatchIn("\r"))
        assertTrue(regex.containsMatchIn("\n"))
        assertTrue(regex.containsMatchIn("\r\n"))
        assertFalse(regex.containsMatchIn("\t \t"))
        assertFalse(regex.containsMatchIn("                hi!"))
        assertFalse(regex.containsMatchIn("cat"))

        assertFalse(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertFalse(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertTrue(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertFalse(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertFalse(regex.containsMatchIn(IPV6_ADDRESS))
        assertFalse(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testCarriageReturn() {
        val regex = regex {
            carriageReturn()
        }

        assertEquals("\\r", regex.toString())
        assertFalse(regex.containsMatchIn(" "))
        assertFalse(regex.containsMatchIn("\t"))
        assertTrue(regex.containsMatchIn("\r"))
        assertFalse(regex.containsMatchIn("\n"))
        assertTrue(regex.containsMatchIn("\r\n"))
        assertFalse(regex.containsMatchIn("\t \t"))
        assertFalse(regex.containsMatchIn("                hi!"))
        assertFalse(regex.containsMatchIn("cat"))

        assertFalse(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertFalse(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertTrue(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertFalse(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertFalse(regex.containsMatchIn(IPV6_ADDRESS))
        assertFalse(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testDigit() {
        val regex = regex {
            digit()
        }

        assertEquals("\\d", regex.toString())
        assertTrue(regex.containsMatchIn("1"))
        assertTrue(regex.containsMatchIn("0"))
        assertTrue(regex.containsMatchIn("999"))
        assertTrue(regex.containsMatchIn("there's a digit in here s0mewhere"))
        assertFalse(regex.containsMatchIn(" "))
        assertFalse(regex.containsMatchIn("abc"))
        assertFalse(regex.containsMatchIn("xFFF"))

        assertFalse(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(DECIMAL_DIGITS))
        assertTrue(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertFalse(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertTrue(regex.containsMatchIn(IPV4_ADDRESS))
        assertTrue(regex.containsMatchIn(IPV6_ADDRESS))
        assertTrue(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testNonDigit() {
        val regex = regex {
            nonDigit()
        }

        assertEquals("\\D", regex.toString())
        assertTrue(regex.containsMatchIn(" 1"))
        assertTrue(regex.containsMatchIn("a0"))
        assertTrue(regex.containsMatchIn("999_"))
        assertTrue(regex.containsMatchIn("1,000"))
        assertTrue(regex.containsMatchIn("there's a digit in here s0mewhere"))
        assertFalse(regex.containsMatchIn("1"))
        assertFalse(regex.containsMatchIn("0"))
        assertFalse(regex.containsMatchIn("999"))

        assertTrue(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertTrue(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(SYMBOLS))
        assertTrue(regex.containsMatchIn(WHITE_SPACE))
        assertTrue(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertTrue(regex.containsMatchIn(SIMPLE_NAME))
        assertTrue(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertTrue(regex.containsMatchIn(IPV4_ADDRESS))
        assertTrue(regex.containsMatchIn(IPV6_ADDRESS))
        assertTrue(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testLetter() {
        val regex = regex {
            letter()
        }

        assertEquals("\\p{L}", regex.toString())
        assertTrue(regex.containsMatchIn("a"))
        assertTrue(regex.containsMatchIn("A"))
        assertTrue(regex.containsMatchIn("        z"))
        assertTrue(regex.containsMatchIn("text with spaces"))
        assertFalse(regex.containsMatchIn(" "))
        assertFalse(regex.containsMatchIn("1"))
        assertFalse(regex.containsMatchIn("%"))
        assertFalse(regex.containsMatchIn(""))

        assertTrue(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertTrue(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertTrue(regex.containsMatchIn(SIMPLE_NAME))
        assertTrue(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertTrue(regex.containsMatchIn(IPV6_ADDRESS))
        assertTrue(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testNonLetter() {
        val regex = regex {
            nonLetter()
        }

        assertEquals("\\P{L}", regex.toString())
        assertTrue(regex.containsMatchIn(" 1"))
        assertTrue(regex.containsMatchIn("0"))
        assertTrue(regex.containsMatchIn("999_"))
        assertTrue(regex.containsMatchIn("1,000"))
        assertTrue(regex.containsMatchIn("text with spaces"))
        assertFalse(regex.containsMatchIn("a"))
        assertFalse(regex.containsMatchIn("ZZZ"))
        assertFalse(regex.containsMatchIn(""))

        assertFalse(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(DECIMAL_DIGITS))
        assertTrue(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(SYMBOLS))
        assertTrue(regex.containsMatchIn(WHITE_SPACE))
        assertTrue(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertTrue(regex.containsMatchIn(SIMPLE_NAME))
        assertTrue(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertTrue(regex.containsMatchIn(IPV4_ADDRESS))
        assertTrue(regex.containsMatchIn(IPV6_ADDRESS))
        assertTrue(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testUppercaseLetter() {
        val regex = regex {
            uppercaseLetter()
        }

        assertEquals("\\p{Lu}", regex.toString())
        assertTrue(regex.containsMatchIn("A"))
        assertTrue(regex.containsMatchIn("        Z"))
        assertTrue(regex.containsMatchIn("text with Spaces"))
        assertFalse(regex.containsMatchIn(" "))
        assertFalse(regex.containsMatchIn("1"))
        assertFalse(regex.containsMatchIn("%"))
        assertFalse(regex.containsMatchIn("s"))
        assertFalse(regex.containsMatchIn(""))

        assertTrue(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertTrue(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertTrue(regex.containsMatchIn(SIMPLE_NAME))
        assertFalse(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertFalse(regex.containsMatchIn(IPV6_ADDRESS))
        assertFalse(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testLowercaseLetter() {
        val regex = regex {
            lowercaseLetter()
        }

        assertEquals("\\p{Ll}", regex.toString())
        assertTrue(regex.containsMatchIn("a"))
        assertTrue(regex.containsMatchIn("        z"))
        assertTrue(regex.containsMatchIn("text with Spaces"))
        assertFalse(regex.containsMatchIn(" "))
        assertFalse(regex.containsMatchIn("1"))
        assertFalse(regex.containsMatchIn("%"))
        assertFalse(regex.containsMatchIn("S"))
        assertFalse(regex.containsMatchIn(""))

        assertTrue(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertTrue(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertTrue(regex.containsMatchIn(SIMPLE_NAME))
        assertTrue(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertTrue(regex.containsMatchIn(IPV6_ADDRESS))
        assertTrue(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testLetterOrDigit() {
        val regex = regex {
            letterOrDigit()
        }

        assertEquals("[\\p{L}0-9]", regex.toString())
        assertTrue(regex.containsMatchIn("A"))
        assertTrue(regex.containsMatchIn("        Z"))
        assertTrue(regex.containsMatchIn("text with Spaces"))
        assertFalse(regex.containsMatchIn(" "))
        assertTrue(regex.containsMatchIn("1"))
        assertFalse(regex.containsMatchIn("%"))
        assertFalse(regex.containsMatchIn("_"))
        assertTrue(regex.containsMatchIn("s"))
        assertFalse(regex.containsMatchIn(""))

        assertTrue(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(DECIMAL_DIGITS))
        assertTrue(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertTrue(regex.containsMatchIn(SIMPLE_NAME))
        assertTrue(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertTrue(regex.containsMatchIn(IPV4_ADDRESS))
        assertTrue(regex.containsMatchIn(IPV6_ADDRESS))
        assertTrue(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testNonLetterOrDigit() {
        val regex = regex {
            nonLetterOrDigit()
        }

        assertEquals("[^\\p{L}0-9]", regex.toString())
        assertFalse(regex.containsMatchIn("A"))
        assertTrue(regex.containsMatchIn("        Z"))
        assertTrue(regex.containsMatchIn("text with Spaces"))
        assertTrue(regex.containsMatchIn(" "))
        assertFalse(regex.containsMatchIn("1"))
        assertTrue(regex.containsMatchIn("%"))
        assertTrue(regex.containsMatchIn("_"))
        assertFalse(regex.containsMatchIn("s"))
        assertFalse(regex.containsMatchIn(""))

        assertFalse(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertFalse(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(SYMBOLS))
        assertTrue(regex.containsMatchIn(WHITE_SPACE))
        assertTrue(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertTrue(regex.containsMatchIn(SIMPLE_NAME))
        assertTrue(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertTrue(regex.containsMatchIn(IPV4_ADDRESS))
        assertTrue(regex.containsMatchIn(IPV6_ADDRESS))
        assertTrue(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testHexDigit() {
        val regex = regex {
            hexDigit()
        }

        assertEquals("[0-9A-Fa-f]", regex.toString())
        assertTrue(regex.containsMatchIn("A"))
        assertTrue(regex.containsMatchIn("        f"))
        assertTrue(regex.containsMatchIn("text with Spaces"))
        assertFalse(regex.containsMatchIn(" "))
        assertTrue(regex.containsMatchIn("1"))
        assertFalse(regex.containsMatchIn("%"))
        assertFalse(regex.containsMatchIn("_"))
        assertFalse(regex.containsMatchIn("s"))
        assertFalse(regex.containsMatchIn(""))

        assertTrue(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(DECIMAL_DIGITS))
        assertTrue(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertTrue(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertTrue(regex.containsMatchIn(IPV4_ADDRESS))
        assertTrue(regex.containsMatchIn(IPV6_ADDRESS))
        assertTrue(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testUppercaseHexDigit() {
        val regex = regex {
            uppercaseHexDigit()
        }

        assertEquals("[0-9A-F]", regex.toString())
        assertTrue(regex.containsMatchIn("A"))
        assertFalse(regex.containsMatchIn("        f"))
        assertFalse(regex.containsMatchIn("text with Spaces"))
        assertFalse(regex.containsMatchIn(" "))
        assertTrue(regex.containsMatchIn("1"))
        assertFalse(regex.containsMatchIn("%"))
        assertFalse(regex.containsMatchIn("_"))
        assertFalse(regex.containsMatchIn("s"))
        assertFalse(regex.containsMatchIn(""))

        assertTrue(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(DECIMAL_DIGITS))
        assertTrue(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertFalse(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertTrue(regex.containsMatchIn(IPV4_ADDRESS))
        assertTrue(regex.containsMatchIn(IPV6_ADDRESS))
        assertTrue(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testLowercaseHexDigit() {
        val regex = regex {
            lowercaseHexDigit()
        }

        assertEquals("[0-9a-f]", regex.toString())
        assertFalse(regex.containsMatchIn("A"))
        assertTrue(regex.containsMatchIn("        f"))
        assertTrue(regex.containsMatchIn("text with Spaces"))
        assertFalse(regex.containsMatchIn(" "))
        assertTrue(regex.containsMatchIn("1"))
        assertFalse(regex.containsMatchIn("%"))
        assertFalse(regex.containsMatchIn("_"))
        assertFalse(regex.containsMatchIn("s"))
        assertFalse(regex.containsMatchIn(""))

        assertTrue(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(DECIMAL_DIGITS))
        assertTrue(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertTrue(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertTrue(regex.containsMatchIn(IPV4_ADDRESS))
        assertTrue(regex.containsMatchIn(IPV6_ADDRESS))
        assertTrue(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testNonHexDigit() {
        val regex = regex {
            nonHexDigit()
        }

        assertEquals("[^0-9A-Fa-f]", regex.toString())
        assertFalse(regex.containsMatchIn("A"))
        assertTrue(regex.containsMatchIn("        f"))
        assertTrue(regex.containsMatchIn("text with Spaces"))
        assertTrue(regex.containsMatchIn(" "))
        assertFalse(regex.containsMatchIn("1"))
        assertTrue(regex.containsMatchIn("%"))
        assertTrue(regex.containsMatchIn("_"))
        assertTrue(regex.containsMatchIn("s"))
        assertFalse(regex.containsMatchIn(""))

        assertTrue(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertFalse(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(SYMBOLS))
        assertTrue(regex.containsMatchIn(WHITE_SPACE))
        assertTrue(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertTrue(regex.containsMatchIn(SIMPLE_NAME))
        assertTrue(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertTrue(regex.containsMatchIn(IPV4_ADDRESS))
        assertTrue(regex.containsMatchIn(IPV6_ADDRESS))
        assertTrue(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testWordCharacter() {
        val regex = regex {
            wordCharacter()
        }

        assertEquals("[\\p{L}0-9_]", regex.toString())
        assertTrue(regex.containsMatchIn("A"))
        assertTrue(regex.containsMatchIn("        Z"))
        assertTrue(regex.containsMatchIn("text with Spaces"))
        assertFalse(regex.containsMatchIn(" "))
        assertTrue(regex.containsMatchIn("1"))
        assertFalse(regex.containsMatchIn("%"))
        assertTrue(regex.containsMatchIn("_"))
        assertTrue(regex.containsMatchIn("s"))
        assertFalse(regex.containsMatchIn(""))

        assertTrue(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(DECIMAL_DIGITS))
        assertTrue(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertTrue(regex.containsMatchIn(SIMPLE_NAME))
        assertTrue(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertTrue(regex.containsMatchIn(IPV4_ADDRESS))
        assertTrue(regex.containsMatchIn(IPV6_ADDRESS))
        assertTrue(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testNonWordCharacter() {
        val regex = regex {
            nonWordCharacter()
        }

        assertEquals("[^\\p{L}0-9_]", regex.toString())
        assertFalse(regex.containsMatchIn("A"))
        assertTrue(regex.containsMatchIn("        Z"))
        assertTrue(regex.containsMatchIn("text with Spaces"))
        assertTrue(regex.containsMatchIn(" "))
        assertFalse(regex.containsMatchIn("1"))
        assertTrue(regex.containsMatchIn("%"))
        assertFalse(regex.containsMatchIn("_"))
        assertFalse(regex.containsMatchIn("s"))
        assertFalse(regex.containsMatchIn(""))

        assertFalse(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertFalse(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(SYMBOLS))
        assertTrue(regex.containsMatchIn(WHITE_SPACE))
        assertTrue(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertTrue(regex.containsMatchIn(SIMPLE_NAME))
        assertTrue(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertTrue(regex.containsMatchIn(IPV4_ADDRESS))
        assertTrue(regex.containsMatchIn(IPV6_ADDRESS))
        assertTrue(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testAnyCharacterFrom() {
        val regex = regex {
            anyCharacterFrom("cat")
        }

        assertEquals("[cat]", regex.toString())
        assertTrue(regex.containsMatchIn("cat"))
        assertTrue(regex.containsMatchIn("parrot"))
        assertTrue(regex.containsMatchIn("tiger"))
        assertTrue(regex.containsMatchIn("cow"))
        assertFalse(regex.containsMatchIn("CAT"))
        assertFalse(regex.containsMatchIn("dog"))
        assertFalse(regex.containsMatchIn(" "))
        assertFalse(regex.containsMatchIn(""))

        assertTrue(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertTrue(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertTrue(regex.containsMatchIn(SIMPLE_NAME))
        assertTrue(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertTrue(regex.containsMatchIn(IPV6_ADDRESS))
        assertTrue(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testAnyCharacterFromWithCaretAtStart() {
        val regex = regex {
            anyCharacterFrom("^abc")
        }

        assertEquals("[\\^abc]", regex.toString())
        assertTrue(regex.containsMatchIn("jazz"))
        assertTrue(regex.containsMatchIn("_^_"))
        assertTrue(regex.containsMatchIn("oboe"))
        assertTrue(regex.containsMatchIn("cue"))
        assertFalse(regex.containsMatchIn("CAT"))
        assertFalse(regex.containsMatchIn("dog"))
        assertFalse(regex.containsMatchIn(" "))
        assertFalse(regex.containsMatchIn(""))

        assertTrue(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertTrue(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertTrue(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertTrue(regex.containsMatchIn(IPV6_ADDRESS))
        assertTrue(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testAnyCharacterFromWithHyphen() {
        val regex = regex {
            anyCharacterFrom("a-f")
        }

        assertEquals("[a\\-f]", regex.toString())
        assertTrue(regex.containsMatchIn("a"))
        assertTrue(regex.containsMatchIn("-"))
        assertTrue(regex.containsMatchIn("f"))
        assertFalse(regex.containsMatchIn("c"))
    }

    @Test
    fun testAnyCharacterFromWithCaretNotAtStart() {
        val regex = regex {
            anyCharacterFrom("a^bc")
        }

        assertEquals("[a^bc]", regex.toString())
        assertTrue(regex.containsMatchIn("jazz"))
        assertTrue(regex.containsMatchIn("_^_"))
        assertTrue(regex.containsMatchIn("oboe"))
        assertTrue(regex.containsMatchIn("cue"))
        assertFalse(regex.containsMatchIn("CAT"))
        assertFalse(regex.containsMatchIn("dog"))
        assertFalse(regex.containsMatchIn(" "))
        assertFalse(regex.containsMatchIn(""))

        assertTrue(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertTrue(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertTrue(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertTrue(regex.containsMatchIn(IPV6_ADDRESS))
        assertTrue(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testAnyCharacterExcept() {
        val regex = regex {
            anyCharacterExcept("cat")
        }

        assertEquals("[^cat]", regex.toString())
        assertFalse(regex.containsMatchIn("cat"))
        assertFalse(regex.containsMatchIn("tata"))
        assertTrue(regex.containsMatchIn("parrot"))
        assertTrue(regex.containsMatchIn("tiger"))
        assertTrue(regex.containsMatchIn("cow"))
        assertTrue(regex.containsMatchIn("CAT"))
        assertTrue(regex.containsMatchIn("dog"))
        assertTrue(regex.containsMatchIn(" "))
        assertFalse(regex.containsMatchIn(""))

        assertTrue(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(DECIMAL_DIGITS))
        assertTrue(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(SYMBOLS))
        assertTrue(regex.containsMatchIn(WHITE_SPACE))
        assertTrue(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertTrue(regex.containsMatchIn(SIMPLE_NAME))
        assertTrue(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertTrue(regex.containsMatchIn(IPV4_ADDRESS))
        assertTrue(regex.containsMatchIn(IPV6_ADDRESS))
        assertTrue(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testAnyOf() {
        val regex = regex {
            anyOf(listOf("cat", "dog", "|"))
        }

        assertEquals("(?:cat|dog|\\|)", regex.toString())
        assertFalse(regex.containsMatchIn("ca do"))
        assertTrue(regex.containsMatchIn("cat"))
        assertTrue(regex.containsMatchIn("dog"))
        assertTrue(regex.containsMatchIn("|"))

        assertFalse(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertFalse(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertFalse(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertFalse(regex.containsMatchIn(IPV6_ADDRESS))
        assertFalse(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testAnyOfWithQuantifier() {
        val regex = regex {
            anyOf(listOf("cat", "dog", "|"), Exactly(2))
        }

        assertEquals("(?:cat|dog|\\|){2}", regex.toString())
        assertTrue(regex.containsMatchIn("catdog"))
        assertTrue(regex.containsMatchIn("cat|dog"))
        assertFalse(regex.containsMatchIn("cat"))
        assertTrue(regex.containsMatchIn("catcat"))
        assertTrue(regex.containsMatchIn("catcatcat"))
        assertFalse(regex.containsMatchIn("dog"))
        assertTrue(regex.containsMatchIn("dogdog"))
        assertTrue(regex.containsMatchIn("dogdogdog"))
        assertFalse(regex.containsMatchIn("|"))
        assertTrue(regex.containsMatchIn("||"))
        assertTrue(regex.containsMatchIn("|||"))

        assertFalse(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertFalse(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertFalse(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertFalse(regex.containsMatchIn(IPV6_ADDRESS))
        assertFalse(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testVarargAnyOf() {
        val regex = regex {
            anyOf("cat", "dog", "|")
        }

        assertEquals("(?:cat|dog|\\|)", regex.toString())
        assertFalse(regex.containsMatchIn("ca do"))
        assertTrue(regex.containsMatchIn("cat"))
        assertTrue(regex.containsMatchIn("dog"))
        assertTrue(regex.containsMatchIn("|"))

        assertFalse(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertFalse(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertFalse(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertFalse(regex.containsMatchIn(IPV6_ADDRESS))
        assertFalse(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testVarargAnyOfWithQuantifier() {
        val regex = regex {
            anyOf("cat", "dog", "|", quantifier = Exactly(2))
        }

        assertEquals("(?:cat|dog|\\|){2}", regex.toString())
        assertTrue(regex.containsMatchIn("catdog"))
        assertTrue(regex.containsMatchIn("cat|dog"))
        assertFalse(regex.containsMatchIn("cat"))
        assertTrue(regex.containsMatchIn("catcat"))
        assertTrue(regex.containsMatchIn("catcatcat"))
        assertFalse(regex.containsMatchIn("dog"))
        assertTrue(regex.containsMatchIn("dogdog"))
        assertTrue(regex.containsMatchIn("dogdogdog"))
        assertFalse(regex.containsMatchIn("|"))
        assertTrue(regex.containsMatchIn("||"))
        assertTrue(regex.containsMatchIn("|||"))

        assertFalse(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertFalse(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertFalse(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertFalse(regex.containsMatchIn(IPV6_ADDRESS))
        assertFalse(regex.containsMatchIn(MAC_ADDRESS))
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
            text("a")
        }

        assertEquals("^a", regex.toString())
        assertTrue(regex.containsMatchIn("a"))
        assertTrue(regex.containsMatchIn("aA"))
        assertTrue(regex.containsMatchIn("a_"))
        assertTrue(regex.containsMatchIn("a        big gap"))
        assertFalse(regex.containsMatchIn(" a space before"))
        assertFalse(regex.containsMatchIn("A capital letter"))
        assertFalse(regex.containsMatchIn("Aa"))
        assertFalse(regex.containsMatchIn(" "))
        assertFalse(regex.containsMatchIn(""))

        assertFalse(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertFalse(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertFalse(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertFalse(regex.containsMatchIn(IPV6_ADDRESS))
        assertFalse(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testEndOfString() {
        val regex = regex {
            text("z")
            endOfString()
        }

        assertEquals("z$", regex.toString())
        assertTrue(regex.containsMatchIn("z"))
        assertTrue(regex.containsMatchIn("zzz"))
        assertTrue(regex.containsMatchIn("fizz buzz"))
        assertFalse(regex.containsMatchIn("buzz!"))
        assertFalse(regex.containsMatchIn("zzz "))
        assertFalse(regex.containsMatchIn("zZ"))
        assertFalse(regex.containsMatchIn("z "))
        assertFalse(regex.containsMatchIn(" "))
        assertFalse(regex.containsMatchIn(""))

        assertTrue(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertFalse(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertFalse(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertFalse(regex.containsMatchIn(IPV6_ADDRESS))
        assertFalse(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testWordBoundary() {
        val regex = regex {
            text("a")
            wordBoundary()
        }

        assertEquals("a\\b", regex.toString())
        assertTrue(regex.containsMatchIn("a"))
        assertTrue(regex.containsMatchIn("spa"))
        assertTrue(regex.containsMatchIn("papa don't preach"))
        assertTrue(regex.containsMatchIn("a dog"))
        assertTrue(regex.containsMatchIn("a-dog"))
        assertFalse(regex.containsMatchIn("an apple"))
        assertFalse(regex.containsMatchIn("asp"))
        assertFalse(regex.containsMatchIn("a1b"))
        assertFalse(regex.containsMatchIn("a_b"))
        assertFalse(regex.containsMatchIn(" "))
        assertFalse(regex.containsMatchIn(""))

        assertFalse(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertFalse(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertFalse(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertFalse(regex.containsMatchIn(IPV6_ADDRESS))
        assertFalse(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testSingleGroup() {
        val regex = regex {
            anyCharacter(ZeroOrMore)
            group {
                letter()
                digit()
            }
        }

        assertEquals(".*(\\p{L}\\d)", regex.toString())

        var match = regex.find("Class A1")
        assertNotNull(match)
        assertEquals("Class A1", match!!.value)
        assertEquals("A1", match.groupValues[1])

        match = regex.find("he likes F1 racing")
        assertNotNull(match)
        assertEquals("he likes F1", match!!.value)
        assertEquals("F1", match.groupValues[1])

        match = regex.find("A4 paper")
        assertNotNull(match)
        assertEquals("A4", match!!.value)
        assertEquals("A4", match.groupValues[1])

        match = regex.find("A 4-legged dog")
        assertNull(match)

        assertFalse(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertFalse(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertFalse(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertTrue(regex.containsMatchIn(IPV6_ADDRESS))
        assertTrue(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testRepeatGroup() {
        val regex = regex {
            group {
                letter()
                digit()
            }
        }

        assertEquals("(\\p{L}\\d)", regex.toString())

        var matcher = regex.find("Class A1 f2 ZZ88")
        val matches = mutableListOf<String>()
        while (matcher != null) {
            matches.add(matcher.value)
            matcher = matcher.next()
        }
        assertEquals(3, matches.size)
        assertEquals("A1", matches[0])
        assertEquals("f2", matches[1])
        assertEquals("Z8", matches[2])

        assertFalse(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertFalse(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertFalse(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertTrue(regex.containsMatchIn(IPV6_ADDRESS))
        assertTrue(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testNamedGroup() {
        val regex = regex {
            lowercaseLetter(OneOrMore)
            namedGroup("test123") {
                digit(OneOrMore)
            }
            lowercaseLetter(OneOrMore)
        }

        assertEquals("\\p{Ll}+(?<test123>\\d+)\\p{Ll}+", regex.toString())

        val match = regex.find("a99z")
        assertNotNull(match)
        assertEquals("a99z", match!!.value)
        assertEquals("99", match.groupValues[1])
        assertEquals("99", match.groups["test123"]?.value)

        assertFalse(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertFalse(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertFalse(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertTrue(regex.containsMatchIn(IPV6_ADDRESS))
        assertFalse(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testNonCapturingGroup() {
        val regex = regex {
            lowercaseLetter(OneOrMore)
            nonCapturingGroup {
                digit(OneOrMore)
            }
            lowercaseLetter(OneOrMore)
        }

        assertEquals("\\p{Ll}+(?:\\d+)\\p{Ll}+", regex.toString())

        val match = regex.find("a99z")
        assertNotNull(match)
        assertEquals("a99z", match!!.value)
        assertEquals(1, match.groups.size)

        assertFalse(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertFalse(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertFalse(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertTrue(regex.containsMatchIn(IPV6_ADDRESS))
        assertFalse(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testMultipleGroups() {
        val regex = regex {
            group {
                anyCharacter(ZeroOrMore)
            }
            group {
                letter()
                digit()
            }
        }

        assertEquals("(.*)(\\p{L}\\d)", regex.toString())

        var match = regex.find("Class A1")
        assertNotNull(match)
        assertEquals("Class A1", match!!.value)
        assertEquals("Class ", match.groupValues[1])
        assertEquals("A1", match.groupValues[2])

        match = regex.find("he likes F1 racing")
        assertNotNull(match)
        assertEquals("he likes F1", match!!.value)
        assertEquals("he likes ", match.groupValues[1])
        assertEquals("F1", match.groupValues[2])

        match = regex.find("A4 paper")
        assertNotNull(match)
        assertEquals("A4", match!!.value)
        assertEquals("", match.groupValues[1])
        assertEquals("A4", match.groupValues[2])

        match = regex.find("A 4-legged dog")
        assertNull(match)

        assertFalse(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertFalse(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertFalse(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertTrue(regex.containsMatchIn(IPV6_ADDRESS))
        assertTrue(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testNestedGroups() {
        val regex = regex {
            anyCharacter() // Omit first character from groups
            group {
                anyCharacter(ZeroOrMore)
                group {
                    letter()
                    digit()
                }
            }
        }

        assertEquals(".(.*(\\p{L}\\d))", regex.toString())

        var match = regex.find("Class A1")
        assertNotNull(match)
        assertEquals("Class A1", match!!.value)
        assertEquals("lass A1", match.groupValues[1])
        assertEquals("A1", match.groupValues[2])

        match = regex.find("he likes F1 racing")
        assertNotNull(match)
        assertEquals("he likes F1", match!!.value)
        assertEquals("e likes F1", match.groupValues[1])
        assertEquals("F1", match.groupValues[2])

        match = regex.find(" A4 paper")
        assertNotNull(match)
        assertEquals(" A4", match!!.value)
        assertEquals("A4", match.groupValues[1])
        assertEquals("A4", match.groupValues[2])

        match = regex.find("A 4-legged dog")
        assertNull(match)

        assertFalse(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertFalse(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertFalse(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertTrue(regex.containsMatchIn(IPV6_ADDRESS))
        assertTrue(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testZeroOrMore() {
        val regex = regex {
            letter()
            digit(ZeroOrMore)
            letter()
        }

        assertEquals("\\p{L}\\d*\\p{L}", regex.toString())
        assertTrue(regex.containsMatchIn("ab"))
        assertTrue(regex.containsMatchIn("a1b"))
        assertTrue(regex.containsMatchIn("a123b"))
        assertFalse(regex.containsMatchIn("a 1 b"))
        assertFalse(regex.containsMatchIn("a b"))
        assertFalse(regex.containsMatchIn(" "))
        assertFalse(regex.containsMatchIn(""))

        assertTrue(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertTrue(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertTrue(regex.containsMatchIn(SIMPLE_NAME))
        assertTrue(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertTrue(regex.containsMatchIn(IPV6_ADDRESS))
        assertTrue(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testOneOrMore() {
        val regex = regex {
            letter()
            digit(OneOrMore)
            letter()
        }

        assertEquals("\\p{L}\\d+\\p{L}", regex.toString())
        assertFalse(regex.containsMatchIn("ab"))
        assertTrue(regex.containsMatchIn("a1b"))
        assertTrue(regex.containsMatchIn("a123b"))
        assertFalse(regex.containsMatchIn("a 1 b"))
        assertFalse(regex.containsMatchIn("a b"))
        assertFalse(regex.containsMatchIn(" "))
        assertFalse(regex.containsMatchIn(""))

        assertFalse(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertFalse(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertFalse(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertTrue(regex.containsMatchIn(IPV6_ADDRESS))
        assertFalse(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testZeroOrOne() {
        val regex = regex {
            letter()
            digit(ZeroOrOne)
            letter()
        }

        assertEquals("\\p{L}\\d?\\p{L}", regex.toString())
        assertTrue(regex.containsMatchIn("ab"))
        assertTrue(regex.containsMatchIn("a1b"))
        assertFalse(regex.containsMatchIn("a123b"))
        assertFalse(regex.containsMatchIn("a 1 b"))
        assertFalse(regex.containsMatchIn("a b"))
        assertFalse(regex.containsMatchIn(" "))
        assertFalse(regex.containsMatchIn(""))

        assertTrue(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertTrue(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertTrue(regex.containsMatchIn(SIMPLE_NAME))
        assertTrue(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertTrue(regex.containsMatchIn(IPV6_ADDRESS))
        assertTrue(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testExactlyNTimes() {
        val regex = regex {
            letter()
            digit(Exactly(3))
            letter()
        }

        assertEquals("\\p{L}\\d{3}\\p{L}", regex.toString())
        assertFalse(regex.containsMatchIn("ab"))
        assertFalse(regex.containsMatchIn("a1b"))
        assertFalse(regex.containsMatchIn("a12b"))
        assertTrue(regex.containsMatchIn("a123b"))
        assertFalse(regex.containsMatchIn("a1234b"))
        assertFalse(regex.containsMatchIn("a12345b"))
        assertFalse(regex.containsMatchIn("a 1 b"))
        assertFalse(regex.containsMatchIn("a b"))
        assertFalse(regex.containsMatchIn(" "))
        assertFalse(regex.containsMatchIn(""))

        assertFalse(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertFalse(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertFalse(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertFalse(regex.containsMatchIn(IPV6_ADDRESS))
        assertFalse(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testAtLeastQuantifier() {
        val regex = regex {
            letter()
            digit(AtLeast(3))
            letter()
        }

        assertEquals("\\p{L}\\d{3,}\\p{L}", regex.toString())
        assertFalse(regex.containsMatchIn("ab"))
        assertFalse(regex.containsMatchIn("a1b"))
        assertFalse(regex.containsMatchIn("a12b"))
        assertTrue(regex.containsMatchIn("a123b"))
        assertTrue(regex.containsMatchIn("a1234b"))
        assertTrue(regex.containsMatchIn("a12345b"))
        assertFalse(regex.containsMatchIn("a 1 b"))
        assertFalse(regex.containsMatchIn("a b"))
        assertFalse(regex.containsMatchIn(" "))
        assertFalse(regex.containsMatchIn(""))

        assertFalse(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertFalse(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertFalse(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertFalse(regex.containsMatchIn(IPV6_ADDRESS))
        assertFalse(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testNoMoreThanQuantifier() {
        val regex = regex {
            letter()
            digit(NoMoreThan(3))
            letter()
        }

        assertEquals("\\p{L}\\d{0,3}\\p{L}", regex.toString())
        assertTrue(regex.containsMatchIn("ab"))
        assertTrue(regex.containsMatchIn("a1b"))
        assertTrue(regex.containsMatchIn("a12b"))
        assertTrue(regex.containsMatchIn("a123b"))
        assertFalse(regex.containsMatchIn("a1234b"))
        assertFalse(regex.containsMatchIn("a12345b"))
        assertFalse(regex.containsMatchIn("a 1 b"))
        assertFalse(regex.containsMatchIn("a b"))
        assertFalse(regex.containsMatchIn(" "))
        assertFalse(regex.containsMatchIn(""))

        assertTrue(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertTrue(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertTrue(regex.containsMatchIn(SIMPLE_NAME))
        assertTrue(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertTrue(regex.containsMatchIn(IPV6_ADDRESS))
        assertTrue(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testBetweenMinMaxTimes() {
        val regex = regex {
            letter()
            digit(Between(minimum = 2, maximum = 4))
            letter()
        }

        assertEquals("\\p{L}\\d{2,4}\\p{L}", regex.toString())
        assertFalse(regex.containsMatchIn("ab"))
        assertFalse(regex.containsMatchIn("a1b"))
        assertTrue(regex.containsMatchIn("a12b"))
        assertTrue(regex.containsMatchIn("a123b"))
        assertTrue(regex.containsMatchIn("a1234b"))
        assertFalse(regex.containsMatchIn("a12345b"))
        assertFalse(regex.containsMatchIn("a 1 b"))
        assertFalse(regex.containsMatchIn("a b"))
        assertFalse(regex.containsMatchIn(" "))
        assertFalse(regex.containsMatchIn(""))

        assertFalse(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertFalse(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertFalse(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertFalse(regex.containsMatchIn(IPV6_ADDRESS))
        assertFalse(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testOptionMultiLine() {
        val regex = regex(MULTILINE) {
            startOfString()
            text("find me!")
            endOfString()
        }

        assertEquals("^find me!$", regex.toString())
        assertTrue(regex.containsMatchIn("find me!"))
        assertTrue(regex.containsMatchIn("find me!\nline 2"))
        assertTrue(regex.containsMatchIn("line 1\nfind me!"))
        assertTrue(regex.containsMatchIn("line 1\nfind me!\nline 3"))
        assertFalse(regex.containsMatchIn(" find me!"))
        assertFalse(regex.containsMatchIn("find me! "))
        assertFalse(regex.containsMatchIn(" find me! "))
        assertFalse(regex.containsMatchIn(""))

        assertFalse(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertFalse(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertFalse(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertFalse(regex.containsMatchIn(IPV6_ADDRESS))
        assertFalse(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testOptionIgnoreCase() {
        val regex = regex(IGNORE_CASE) {
            anyCharacterFrom("cat")
        }

        assertEquals("[cat]", regex.toString())
        assertTrue(regex.containsMatchIn("cat"))
        assertTrue(regex.containsMatchIn("tiger"))
        assertTrue(regex.containsMatchIn("Ant"))
        assertTrue(regex.containsMatchIn("CAT"))
        assertTrue(regex.containsMatchIn("                A"))
        assertFalse(regex.containsMatchIn("dog"))
        assertFalse(regex.containsMatchIn(" "))
        assertFalse(regex.containsMatchIn(""))

        assertTrue(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertTrue(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertTrue(regex.containsMatchIn(SIMPLE_NAME))
        assertTrue(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertTrue(regex.containsMatchIn(IPV6_ADDRESS))
        assertTrue(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testEmailAddress() {
        // Very basic e-mail address checker!
        val regex = regex {
            startOfString()
            nonWhitespace(AtLeast(2))
            text("@")
            nonWhitespace(AtLeast(2))
            text(".")
            nonWhitespace(AtLeast(2))
            endOfString()
        }

        assertEquals("^\\S{2,}@\\S{2,}\\.\\S{2,}$", regex.toString())
        assertTrue(regex.containsMatchIn("test.user@mainwave.co.uk"))
        assertTrue(regex.containsMatchIn("aa@bb.cc"))
        assertTrue(regex.containsMatchIn("__@__.__"))
        assertTrue(regex.containsMatchIn("..@....."))
        assertFalse(regex.containsMatchIn("aa@bb.c"))
        assertFalse(regex.containsMatchIn("aa@b.cc"))
        assertFalse(regex.containsMatchIn("a@bb.cc"))
        assertFalse(regex.containsMatchIn("a@b.c"))
        assertFalse(regex.containsMatchIn("  @  .  "))

        assertFalse(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertFalse(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertTrue(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertFalse(regex.containsMatchIn(IPV6_ADDRESS))
        assertFalse(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testUrl() {
        // Very basic URL checker!
        val regex = regex {
            text("http")
            text("s", ZeroOrOne)
            text("://")
            nonWhitespace(OneOrMore)
            anyCharacterFrom("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_/") // Valid last characters
        }

        assertEquals("http(?:s)?://\\S+[abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_/]", regex.toString())
        assertTrue(regex.containsMatchIn("http://www.mainwave.co.uk"))
        assertTrue(regex.containsMatchIn("https://www.mainwave.co.uk"))
        assertFalse(regex.containsMatchIn("www.mainwave.co.uk"))
        assertFalse(regex.containsMatchIn("ftp://www.mainwave.co.uk"))

        var match = regex.find("Go to http://www.mainwave.co.uk. Then click the link.")
        assertNotNull(match)
        assertEquals("http://www.mainwave.co.uk", match!!.value)

        match = regex.find("Go to https://www.mainwave.co.uk/test/, then click the link.")
        assertNotNull(match)
        assertEquals("https://www.mainwave.co.uk/test/", match!!.value)

        match = regex.find("Go to 'http://www.mainwave.co.uk' then click the link.")
        assertNotNull(match)
        assertEquals("http://www.mainwave.co.uk", match!!.value)

        match = regex.find("Go to \"http://www.mainwave.co.uk\" then click the link.")
        assertNotNull(match)
        assertEquals("http://www.mainwave.co.uk", match!!.value)

        assertFalse(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertFalse(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertFalse(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertFalse(regex.containsMatchIn(IPV4_ADDRESS))
        assertFalse(regex.containsMatchIn(IPV6_ADDRESS))
        assertFalse(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testIp4Address() {
        // Very basic IPv4 address checker!
        // (doesn't check values are in range, for example)
        val regex = regex {
            startOfString()
            group(Exactly(3)) {
                digit(Between(1, 3))
                text(".")
            }
            digit(Between(1, 3))
            endOfString()
        }

        assertEquals("^(\\d{1,3}\\.){3}\\d{1,3}$", regex.toString())
        assertTrue(regex.containsMatchIn("10.1.1.100"))
        assertTrue(regex.containsMatchIn("1.1.1.1"))
        assertTrue(regex.containsMatchIn("0.0.0.0"))
        assertTrue(regex.containsMatchIn("255.255.255.255"))
        assertTrue(regex.containsMatchIn("999.999.999.999"))
        assertFalse(regex.containsMatchIn("1.1.1."))
        assertFalse(regex.containsMatchIn("1.1.1."))
        assertFalse(regex.containsMatchIn("1.1.1.1."))
        assertFalse(regex.containsMatchIn("1.1.1.1.1"))
        assertFalse(regex.containsMatchIn("1.1.1.1000"))

        assertFalse(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(DECIMAL_DIGITS))
        assertFalse(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertFalse(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertTrue(regex.containsMatchIn(IPV4_ADDRESS))
        assertFalse(regex.containsMatchIn(IPV6_ADDRESS))
        assertFalse(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testZeroOrMoreButAsFewAsPossible() {
        val regex = regex {
            digit(ZeroOrMore.butAsFewAsPossible)
        }

        assertEquals("\\d*?", regex.toString())
        val nonGreedyMatch = regex.find("999")
        assertNotNull(nonGreedyMatch)
        assertEquals("", nonGreedyMatch!!.value)

        assertTrue(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(DECIMAL_DIGITS))
        assertTrue(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(SYMBOLS))
        assertTrue(regex.containsMatchIn(WHITE_SPACE))
        assertTrue(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertTrue(regex.containsMatchIn(EMPTY))
        assertTrue(regex.containsMatchIn(SIMPLE_NAME))
        assertTrue(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertTrue(regex.containsMatchIn(IPV4_ADDRESS))
        assertTrue(regex.containsMatchIn(IPV6_ADDRESS))
        assertTrue(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testOneOrMoreButAsFewAsPossible() {
        val regex = regex {
            digit(OneOrMore.butAsFewAsPossible)
        }

        assertEquals("\\d+?", regex.toString())
        val nonGreedyMatch = regex.find("999")
        assertNotNull(nonGreedyMatch)
        assertEquals("9", nonGreedyMatch!!.value)

        assertFalse(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(DECIMAL_DIGITS))
        assertTrue(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertFalse(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertTrue(regex.containsMatchIn(IPV4_ADDRESS))
        assertTrue(regex.containsMatchIn(IPV6_ADDRESS))
        assertTrue(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testAtLeastButAsFewAsPossible() {
        val regex = regex {
            digit(AtLeast(1).butAsFewAsPossible)
        }

        assertEquals("\\d{1,}?", regex.toString())
        val nonGreedyMatch = regex.find("999")
        assertNotNull(nonGreedyMatch)
        assertEquals("9", nonGreedyMatch!!.value)

        assertFalse(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(DECIMAL_DIGITS))
        assertTrue(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertFalse(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertTrue(regex.containsMatchIn(IPV4_ADDRESS))
        assertTrue(regex.containsMatchIn(IPV6_ADDRESS))
        assertTrue(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testBetweenButAsFewAsPossible() {
        val regex = regex {
            digit(Between(2, 100).butAsFewAsPossible)
        }

        assertEquals("\\d{2,100}?", regex.toString())
        val nonGreedyMatch = regex.find("999")
        assertNotNull(nonGreedyMatch)
        assertEquals("99", nonGreedyMatch!!.value)

        assertFalse(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertFalse(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertFalse(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(DECIMAL_DIGITS))
        assertTrue(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertFalse(regex.containsMatchIn(SYMBOLS))
        assertFalse(regex.containsMatchIn(WHITE_SPACE))
        assertFalse(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertFalse(regex.containsMatchIn(EMPTY))
        assertFalse(regex.containsMatchIn(SIMPLE_NAME))
        assertFalse(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertFalse(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertTrue(regex.containsMatchIn(IPV4_ADDRESS))
        assertTrue(regex.containsMatchIn(IPV6_ADDRESS))
        assertTrue(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testNoMoreThanButAsFewAsPossible() {
        val regex = regex {
            digit(NoMoreThan(2).butAsFewAsPossible)
        }

        assertEquals("\\d{0,2}?", regex.toString())
        val nonGreedyMatch = regex.find("999")
        assertNotNull(nonGreedyMatch)
        assertEquals("", nonGreedyMatch!!.value)

        assertTrue(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(DECIMAL_DIGITS))
        assertTrue(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(SYMBOLS))
        assertTrue(regex.containsMatchIn(WHITE_SPACE))
        assertTrue(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertTrue(regex.containsMatchIn(EMPTY))
        assertTrue(regex.containsMatchIn(SIMPLE_NAME))
        assertTrue(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertTrue(regex.containsMatchIn(IPV4_ADDRESS))
        assertTrue(regex.containsMatchIn(IPV6_ADDRESS))
        assertTrue(regex.containsMatchIn(MAC_ADDRESS))
    }

    @Test
    fun testZeroOrOneButAsFewAsPossible() {
        val regex = regex {
            digit(ZeroOrOne.butAsFewAsPossible)
        }

        assertEquals("\\d??", regex.toString())
        val nonGreedyMatch = regex.find("999")
        assertNotNull(nonGreedyMatch)
        assertEquals("", nonGreedyMatch!!.value)

        assertTrue(regex.containsMatchIn(BOTH_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(UPPER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_LATIN_ALPHABET))
        assertTrue(regex.containsMatchIn(BOTH_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(UPPER_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(LOWER_CASE_EXTENDED_ALPHABET))
        assertTrue(regex.containsMatchIn(DECIMAL_DIGITS))
        assertTrue(regex.containsMatchIn(BOTH_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(UPPER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(LOWER_CASE_HEX_DIGITS))
        assertTrue(regex.containsMatchIn(SYMBOLS))
        assertTrue(regex.containsMatchIn(WHITE_SPACE))
        assertTrue(regex.containsMatchIn(CONTROL_CHARACTERS))
        assertTrue(regex.containsMatchIn(EMPTY))
        assertTrue(regex.containsMatchIn(SIMPLE_NAME))
        assertTrue(regex.containsMatchIn(SIMPLE_EMAIL_ADDRESS))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTP_URL))
        assertTrue(regex.containsMatchIn(SIMPLE_HTTPS_URL))
        assertTrue(regex.containsMatchIn(IPV4_ADDRESS))
        assertTrue(regex.containsMatchIn(IPV6_ADDRESS))
        assertTrue(regex.containsMatchIn(MAC_ADDRESS))
    }
}