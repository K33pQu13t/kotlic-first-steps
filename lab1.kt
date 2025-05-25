fun main() {
    print("Введите возраст: ")
    val age = readLine()?.toIntOrNull()
    if (age == null) {
        println("Возраст некорректный")
        return
    }

    when {
        age <= 20 -> println("Вы слишком молоды!")
        age == 30
            || age == 40
            || age == 50
            || age == 60 -> println("Поздравляем с повышением!")
        age == 65 -> println("Преподносим вам золотые часы!")
        age > 65 -> println("Вы слишком стары!")
        else -> println("Продолжайте накапливать опыт!")
    }
}