package com.bignerdranch.android.network_gson_exercise.app

object Const {
    /**
     * Use this URL if you launch android app on the emulator while boxes-server
     * is launched locally on 127.0.0.1:12345
     */
    // Если приложение запускается на эмуляторе,а сервер на локальном адресе,то для эмулятора нужно указать такой адресс
    // если приложение запускается на реальном смартфоне,то в настройках сервера нужно указать адресс вашей вай фай сети
    // и смартфон должен быть с компом в одной вай фай сети
    // и такой же адресс в BASE_URL в приложении
    const val BASE_URL = "http://10.0.2.2:12345"
}