package tech.connordavis.sylcore.utils

class NumberUtils {
    companion object {
        fun isNumber(str: String) = try {
            str.toDouble()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }
}