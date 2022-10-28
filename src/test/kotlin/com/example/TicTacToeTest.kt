package com.example

import com.example.game.Cell
import com.example.game.TicTacToe
import kotlin.test.Test
import kotlin.test.assertEquals

class TicTacToeTest {
    @Test
    fun testSolving() {

        fun generateLineX(game: TicTacToe, xStart: Int,  y : Int, fill: Cell) {
            for (i in xStart until xStart + game.criteria) {
                game.field[i][y] = fill
            }
        }

        fun generateLineY(game: TicTacToe, x: Int,  yStart : Int, fill: Cell) {
            for (i in yStart until yStart + game.criteria) {
                game.field[x][i] = fill
            }
        }

        fun generateDiagonalOne(game: TicTacToe, x: Int,  y : Int, fill: Cell) {
            for (i in 0 until game.criteria) {
                game.field[i+x][i+y] = fill
            }
        }

        fun generateDiagonalTwo(game: TicTacToe, x: Int,  y : Int, fill: Cell) {
            for (i in 0 until game.criteria) {
                game.field[i+x][y + game.criteria - 1 - i] = fill
            }
        }

        var game = TicTacToe(5, 3)
        for (i in 0 .. 2) {
            for (j in 0 .. 2) {
                for (l in listOf(Cell.X, Cell.O)) {
                    game = TicTacToe(5, 3)
                    generateLineX(game, i, j, l)
                    assertEquals(l, game.check_state())
                }
                for (l in listOf(Cell.X, Cell.O)) {
                    game = TicTacToe(5, 3)
                    generateLineY(game, i, j, l)
                    assertEquals(l, game.check_state())
                }
                for (l in listOf(Cell.X, Cell.O)) {
                    game = TicTacToe(5, 3)
                    generateDiagonalOne(game, i, j, l)
                    assertEquals(l, game.check_state())
                }
                for (l in listOf(Cell.X, Cell.O)) {
                    game = TicTacToe(5, 3)
                    generateDiagonalTwo(game, i, j, l)
                    assertEquals(l, game.check_state())
                }
            }
        }
        game = TicTacToe(5, 3)
        game.field[0][0] = Cell.X
        game.field[1][0] = Cell.O
        game.field[2][0] = Cell.X
        println(game.check_state())
    }
}