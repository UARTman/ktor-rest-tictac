package com.example.plugins

import com.example.game.ResetCommand
import com.example.game.TicTacToe
import com.example.game.TurnCommand
import com.example.game.game
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.server.sessions.*

fun Application.configureRouting() {

    install(StatusPages) {
        exception<AuthenticationException> { call, cause ->
            call.respond(HttpStatusCode.Unauthorized)
        }
        exception<AuthorizationException> { call, cause ->
            call.respond(HttpStatusCode.Forbidden)
        }

    }

    routing {
        get("/") {
            call.respond(game.data())
        }
        post("/turn") {
            val turn = call.receive<TurnCommand>()
            game.turn(turn.x, turn.y)
            call.respond("Ok")
        }
        post("/reset") {
            val reset = call.receive<ResetCommand>()
            game = if (reset.criteria != null) {
                TicTacToe(reset.size, reset.criteria)
            } else {
                TicTacToe(reset.size)
            }
            call.respond("Ok")
        }
        // Static plugin. Try to access `/static/index.html`
        static("/static") {
            resources("static")
        }
    }
}

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()
