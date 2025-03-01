package ua.insearching.mynews.di

import com.prof18.rssparser.RssParser
import com.prof18.rssparser.RssParserBuilder
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module
import ua.insearching.mynews.news.data.database.DatabaseFactory

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
        single<RssParser> {
            val builder = RssParserBuilder(
                callFactory = OkHttpClient(),
                charset = Charsets.UTF_8,
            )
            builder.build()
        }
        single { DatabaseFactory(androidApplication()) }
    }