package com.insearching.pickstream.di

import com.insearching.pickstream.news.data.BrowserLauncher
import com.insearching.pickstream.news.data.database.DatabaseFactory
import com.prof18.rssparser.RssParser
import com.prof18.rssparser.RssParserBuilder
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSURLSession

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { Darwin.create() }
        single { DatabaseFactory() }
        single<RssParser> {
            val builder = RssParserBuilder(
                nsUrlSession = NSURLSession(),
            )
            builder.build()
        }
        single<BrowserLauncher> { BrowserLauncher() }
    }
