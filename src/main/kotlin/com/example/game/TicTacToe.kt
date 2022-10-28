package com.example.game

import kotlinx.serialization.Serializable

@Serializable
enum class Cell {
    X,
    O,
}

fun Cell.inverted(): Cell {
    return when (this) {
        Cell.X -> Cell.O
        Cell.O -> Cell.X
    }
}


class TicTacToe(var size: Int, var criteria: Int = size) {
    var field: MutableList<MutableList<Cell?>> = mutableListOf()
    var currentPlayer = Cell.X
    var winner: Cell? = null

    init {
        for (_i in 0 until size) {
            field.add(mutableListOf())
            for (_j in 0 until size) {
                field[field.size - 1].add(null)
            }
        }
    }

    fun turn(x: Int, y: Int) {
        if (winner != null) return
        if (x < 0 || x > field.size || y < 0 || y > field.size) return
        if (field[x][y] != null) return
        field[x][y] = currentPlayer
        currentPlayer = currentPlayer.inverted()
        winner = check_state()
    }

    private fun checkStencil(x: Int, y: Int): Cell? {
        for (i in x until (x + criteria)) {
            var c: Cell? = field[i][y]
            for (j in y until (y + criteria)) {
                if (field[i][j] == null) {
                    c = null
                    break
                }
                if (c != field[i][j]) {
                    c = null
                    break
                }
            }
            if (c != null) {
                return c
            }
        }
        for (j in y until (y + criteria)) {
            var c: Cell? = field[x][j]
            for (i in x until (x + criteria)) {
                if (field[i][j] == null) {
                    c = null
                    break
                }
                if (c != field[i][j]) {
                    c = null
                    break
                }
            }
            if (c != null) {
                return c
            }
        }
        var c: Cell? = field[x][y]
        for (i in 0 until criteria) {
            if (field[x + i][y + i] == null) {
                c = null
                break
            }
            if (c != field[x+i][y+i]) {
                c = null
                break
            }
        }
        if (c != null) {
            return c
        }
        c = field[x + 0][y + criteria - 0 - 1]
        for (i in 0 until criteria) {
            if (field[x + i][y + criteria - i - 1] == null) {
                c = null
                break
            }
            if (c != field[x + i][y + criteria - i - 1]) {
                c = null
                break
            }
        }
        return c
    }

    fun check_state(): Cell? {
        for (i in 0..(size - criteria)) {
            for (j in 0..(size - criteria)) {
                val c: Cell? = checkStencil(i, j)
                if (c != null) return c
            }
        }
        return null
    }

    fun data(): GameData {
        return GameData(
            size,
            criteria,
            field,
            currentPlayer,
            winner
        )
    }
}

@Serializable
data class GameData(
    val size: Int,
    val criteria: Int,
    val field: MutableList<MutableList<Cell?>>,
    val currentPlayer: Cell,
    val winner: Cell?
)

var game = TicTacToe(3)

@Serializable
data class ResetCommand(val size: Int, val criteria: Int?)

@Serializable
data class TurnCommand(val x: Int, val y: Int)