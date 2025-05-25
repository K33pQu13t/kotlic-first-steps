fun main() {
    println("Введите строку: ")
    val input = readLine()
    if (input == null || input.isEmpty()) {
        println("Строка пустая. Введите непустую строку")
        return
    }

    val invertedInput = invertString(input)
    println("Инвертированная строка: $invertedInput")
}

fun invertString(str: String): String {
    val chars = str.toCharArray()

    var left = 0;
    var right = chars.size - 1

    while (left < right) {
        // параллельное присваивание через деструктуризацию
        chars[left] = chars[right].also {
            chars[right] = chars[left]
        }

        left++
        right--
    }

    return String(chars)
}