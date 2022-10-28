package com.example

import com.example.game.Cell
import com.example.game.GameData
import com.example.game.game
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.sessions.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import java.time.Duration
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlin.test.*
import io.ktor.server.testing.*
import com.example.plugins.*
import io.ktor.client.call.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting()
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }

        val client = createClient {
            this.install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }

        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            var field = mutableListOf<MutableList<Cell?>>(
                mutableListOf(null, null, null),
                mutableListOf(null, null, null),
                mutableListOf(null, null, null),
            )
            println(bodyAsText())
            assertEquals(GameData(3, 3, field, Cell.X, null), body())
        }
    }
}