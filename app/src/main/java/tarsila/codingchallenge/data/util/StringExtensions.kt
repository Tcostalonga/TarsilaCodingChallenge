package tarsila.codingchallenge.data.util

import java.text.Normalizer

private val DIACRITICS = Regex("\\p{Mn}+")
private val MULTIPLE_SPACES = Regex("\\s+")

fun String.normalizeForSearch(): String =
    Normalizer.normalize(this, Normalizer.Form.NFD)
        .replace(DIACRITICS, "")
        .lowercase()
        .replace(MULTIPLE_SPACES, " ")
        .trim()