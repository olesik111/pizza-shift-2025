package com.example.pizzaproject.api.mappers

const val BASE_IMAGE_URL = "https://shift-intensive.ru/api/"

fun formatImageUrl(url: String): String {
    return BASE_IMAGE_URL + url

}

fun formatToRusString(type: String): String {
    return when (type) {
        "PINEAPPLE" -> "Ананас"
        "MOZZARELLA" -> "Моцарелла"
        "PEPERONI" -> "Пепперони"
        "GREEN_PEPPER" -> "Зелёный перец"
        "MUSHROOMS" -> "Грибы"
        "BASIL" -> "Базилик"
        "CHEDDAR" -> "Чеддер"
        "PARMESAN" -> "Пармезан"
        "FETA" -> "Фета"
        "HAM" -> "Ветчина"
        "PICKLE" -> "Маринованный огурец"
        "TOMATO" -> "Помидор"
        "BACON" -> "Бекон"
        "ONION" -> "Лук"
        "CHILE" -> "Перец чили"
        "SHRIMPS" -> "Креветки"
        "CHICKEN_FILLET" -> "Куриное филе"
        "MEATBALLS" -> "Фрикадельки"

        else -> "Неизвестный топпинг"
    }
}