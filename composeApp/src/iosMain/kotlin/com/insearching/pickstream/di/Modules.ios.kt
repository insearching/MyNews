package com.insearching.pickstream.di

import com.prof18.rssparser.RssParser
import com.prof18.rssparser.RssParserBuilder
import io.ktor.client.engine.HttpClientEngine
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSURLSession
import com.insearching.pickstream.news.data.BrowserLauncher

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { Darwin.create() }
        single<RssParser> {
            val builder = RssParserBuilder(
                nsUrlSession = NSURLSession(),
            )
            builder.build()
        }
        single<BrowserLauncher> { BrowserLauncher() }
    }