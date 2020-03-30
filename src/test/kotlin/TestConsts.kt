package uk.co.mainwave.regextoolboxkotlin

// Character classes
const val BOTH_CASE_LATIN_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
const val UPPER_CASE_LATIN_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
const val LOWER_CASE_LATIN_ALPHABET = "abcdefghijklmnopqrstuvwxyz"
const val BOTH_CASE_EXTENDED_ALPHABET = "ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝÞẞĀĂĄĆĈĊČĎĐĒĔĖĘĚĜĞĠĢĤĦĨĪĬĮİĴĶĹĻĽĿŁŃŅŇŊŌŎŐŒŔŖŘŚŜŞŠŢŤŦŨŪŬŮŰŲŴŶŸŹŻŽàáâãäåæçèéêëìíîïðñòóôõöøùúûüýþßāăąćĉċčďđēĕėęěĝğġģĥħĩīĭįiĵķĺļľŀłńņňŋōŏőœŕŗřśŝşšţťŧũūŭůűųŵŷÿźżž"
const val UPPER_CASE_EXTENDED_ALPHABET = "ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝÞẞĀĂĄĆĈĊČĎĐĒĔĖĘĚĜĞĠĢĤĦĨĪĬĮİĴĶĹĻĽĿŁŃŅŇŊŌŎŐŒŔŖŘŚŜŞŠŢŤŦŨŪŬŮŰŲŴŶŸŹŻŽ"
const val LOWER_CASE_EXTENDED_ALPHABET = "àáâãäåæçèéêëìíîïðñòóôõöøùúûüýþßāăąćĉċčďđēĕėęěĝğġģĥħĩīĭįiĵķĺļľŀłńņňŋōŏőœŕŗřśŝşšţťŧũūŭůűųŵŷÿźżž"
const val DECIMAL_DIGITS = "0123456789"
const val BOTH_CASE_HEX_DIGITS = "0123456789ABCDEFabcdef"
const val UPPER_CASE_HEX_DIGITS = "0123456789ABCDEF"
const val LOWER_CASE_HEX_DIGITS = "0123456789abcdef"
const val SYMBOLS = "!\"\\|£$%^&*()-=_+[]{};'#:@~,./<>?"
const val WHITE_SPACE = " \t\n\r"
const val CONTROL_CHARACTERS = "\b"
const val EMPTY = ""

// Example strings
const val SIMPLE_NAME = "Jo Smith"
const val SIMPLE_EMAIL_ADDRESS = "regex.toolbox@mainwave.co.uk"
const val SIMPLE_HTTP_URL = "http://www.website.com/"
const val SIMPLE_HTTPS_URL = "https://www.website.com/"
const val IPV4_ADDRESS = "172.15.254.1"
const val IPV6_ADDRESS = "2001:0db8:85a3:0000:0000:8a2e:0370:7334"
const val MAC_ADDRESS = "00:3e:e1:c4:5d:df"
