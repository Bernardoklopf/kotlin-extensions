import java.text.Normalizer
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import javax.swing.text.MaskFormatter

const val emailRegex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"

val patternEuaDate: Pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}")

const val notWordOrWhitespaceRegex = "[^\\w\\s]"

const val diacriticsCharGroupRegex = "\\p{Mn}+"

const val nonAlphaNumericRegex = "[^a-zA-Z0-9]"


fun String.toStringDateEua(): Date {
    val formato = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    return formato.parse(this)
}

fun String.toDateEua(): Date {
    val formato = SimpleDateFormat("yyyy-MM-dd")
    return formato.parse(this)
}

fun String.toDateBr(): Date {
    val formato = SimpleDateFormat("dd/MM/yyyy")
    return formato.parse(this)
}

fun String.isFormatDateEua(): Boolean {
    return patternEuaDate.matcher(this).matches()
}

fun String.isEmail(): Boolean {
    return emailRegex.toRegex().matches(this)
}

fun String.removeNaoAlfanumerico() = this.replace(nonAlphaNumericRegex.toRegex(), "")

fun String.formatCnpj(): String {
    val mask = MaskFormatter("##.###.###/####-##")
    mask.valueContainsLiteralCharacters = false
    return mask.valueToString(this)
}

fun String.findDigitoContaCorrente(): String {
    return this.last().toString().replace("X", "0", true)
}

fun String.capitalizeEveryWord() = this.toLowerCase()
    .split(" ")
    .joinToString(" ") { it.capitalize() }

fun String.firstName() = this.split(" ").first()

fun String.removeSpecialChars(): String = Normalizer
    .normalize(this, Normalizer.Form.NFD)
    .replace(diacriticsCharGroupRegex.toRegex(), "")

fun String.removeNotAlfaNumericKeepingSpaces(): String = this.replace(notWordOrWhitespaceRegex.toRegex(), "")

fun String.normalize(): String = this.removeSpecialChars().toLowerCase().removeNotAlfaNumericKeepingSpaces()

fun String.isEqualToNormilized(outra: String): Boolean = this.normalize() == outra.normalize()
