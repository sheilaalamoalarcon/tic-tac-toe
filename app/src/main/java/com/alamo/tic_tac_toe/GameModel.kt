package com.alamo.tic_tac_toe

import kotlin.random.Random

data class GameModel(
    var gameID: String = "",
    var fillPos: MutableList<String> = mutableListOf("", "", "", "", "", "", "", "", "", ""),
    var winner: String = "",
    var gameStatus: GameStatus = GameStatus.CREATED,
    var currentPlayer: String = (arrayOf("X", "O"))[Random.nextInt(2)]
) {
}

enum class GameStatus {
    CREATED,
    JOINED,
    IN_PROGRESS,
    FINISHED
}