fun main() {
    print("Введите значение a: ")
    val a = readLine()?.toDoubleOrNull()
    if (a == null) {
        println("Некорректное значение a")
        return;
    }

    if (a > 7) {
        println("Решение невозможно для a > 7")
    }

    var sum = 0.0
    var n = 0
    while (sum <= a) {
        n++
        sum += 1.0 / n
    }

    println("Первое число, большее $a, равно $sum (при n = $n).")
}