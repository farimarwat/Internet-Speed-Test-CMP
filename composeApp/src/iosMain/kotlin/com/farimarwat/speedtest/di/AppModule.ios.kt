package com.farimarwat.speedtest.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platFormModule = module {
    singleOf(::httpClient)
}
actual val httpClient: HttpClient = HttpClient(Darwin){
    install(ContentNegotiation){
        json(
            Json {
                ignoreUnknownKeys = true
            }
        )
    }
}