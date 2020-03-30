package uk.co.mainwave.regextoolboxkotlin.extensions

import org.junit.Assert
import org.junit.Test
import uk.co.mainwave.regextoolboxkotlin.RegexQuantifier
import uk.co.mainwave.regextoolboxkotlin.regex

class RegexExtensionsTest {
    @Test
    fun `GIVEN input string containing one regex match WHEN remove() THEN match is removed from string`() {
        // Given
        val input = "Hello there friendly world"
        val regex = regex {
            whitespace()
            text("there")
        }

        // When
        val actualResult = regex.remove(input)

        // Then
        Assert.assertEquals("Hello friendly world", actualResult)
    }

    @Test
    fun `GIVEN input string containing one regex match WHEN removeFirst() THEN match is removed from string`() {
        // Given
        val input = "Hello there friendly world"
        val regex = regex {
            whitespace()
            text("there")
        }

        // When
        val actualResult = regex.removeFirst(input)

        // Then
        Assert.assertEquals("Hello friendly world", actualResult)
    }

    @Test
    fun `GIVEN input string containing one regex match WHEN removeLast() THEN match is removed from string`() {
        // Given
        val input = "Hello there friendly world"
        val regex = regex {
            whitespace()
            text("there")
        }

        // When
        val actualResult = regex.removeLast(input)

        // Then
        Assert.assertEquals("Hello friendly world", actualResult)
    }

    @Test
    fun `GIVEN input string containing multiple regex matches WHEN remove() THEN all matches are removed from string`() {
        // Given
        val input = "Hello there friendly world"
        val regex = regex {
            whitespace()
            letter(RegexQuantifier.OneOrMore)
        }

        // When
        val actualResult = regex.remove(input)

        // Then
        Assert.assertEquals("Hello", actualResult)
    }

    @Test
    fun `GIVEN input string containing multiple regex matches WHEN removeFirst() THEN first match is removed from string`() {
        // Given
        val input = "Hello there friendly world"
        val regex = regex {
            whitespace()
            letter(RegexQuantifier.OneOrMore)
        }

        // When
        val actualResult = regex.removeFirst(input)

        // Then
        Assert.assertEquals("Hello friendly world", actualResult)
    }

    @Test
    fun `GIVEN input string containing multiple regex matches WHEN removeLast() THEN last match is removed from string`() {
        // Given
        val input = "Hello there friendly world"
        val regex = regex {
            whitespace()
            letter(RegexQuantifier.OneOrMore)
        }

        // When
        val actualResult = regex.removeLast(input)

        // Then
        Assert.assertEquals("Hello there friendly", actualResult)
    }

    @Test
    fun `GIVEN input string containing no regex matches WHEN remove() THEN string is returned unaltered`() {
        // Given
        val input = "Hello there friendly world"
        val regex = regex {
            digit(RegexQuantifier.OneOrMore)
        }

        // When
        val actualResult = regex.remove(input)

        // Then
        Assert.assertEquals("Hello there friendly world", actualResult)
    }

    @Test
    fun `GIVEN empty input string WHEN remove() THEN empty string is returned`() {
        // Given
        val input = ""
        val regex = regex {
            digit(RegexQuantifier.OneOrMore)
        }

        // When
        val actualResult = regex.remove(input)

        // Then
        Assert.assertEquals("", actualResult)
    }

    @Test
    fun `GIVEN input string containing no regex matches WHEN removeFirst() THEN string is returned unaltered`() {
        // Given
        val input = "Hello there friendly world"
        val regex = regex {
            digit(RegexQuantifier.OneOrMore)
        }

        // When
        val actualResult = regex.removeFirst(input)

        // Then
        Assert.assertEquals("Hello there friendly world", actualResult)
    }

    @Test
    fun `GIVEN empty input string WHEN removeFirst() THEN empty string is returned`() {
        // Given
        val input = ""
        val regex = regex {
            digit(RegexQuantifier.OneOrMore)
        }

        // When
        val actualResult = regex.removeFirst(input)

        // Then
        Assert.assertEquals("", actualResult)
    }

    @Test
    fun `GIVEN input string containing no regex matches WHEN removeLast() THEN string is returned unaltered`() {
        // Given
        val input = "Hello there friendly world"
        val regex = regex {
            digit(RegexQuantifier.OneOrMore)
        }

        // When
        val actualResult = regex.removeLast(input)

        // Then
        Assert.assertEquals("Hello there friendly world", actualResult)
    }

    @Test
    fun `GIVEN empty input string WHEN removeLast() THEN empty string is returned`() {
        // Given
        val input = ""
        val regex = regex {
            digit(RegexQuantifier.OneOrMore)
        }

        // When
        val actualResult = regex.removeLast(input)

        // Then
        Assert.assertEquals("", actualResult)
    }
}
