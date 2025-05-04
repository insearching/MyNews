package com.insearching.pickstream.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.insearching.pickstream.core.data.HttpClientFactory
import com.insearching.pickstream.news.data.database.ChannelDatabase
import com.insearching.pickstream.news.data.database.DatabaseFactory
import com.insearching.pickstream.news.data.repository.RssRepositoryImpl
import com.insearching.pickstream.news.domain.repository.RssRepository
import com.insearching.pickstream.news.domain.usecase.AddFeedChannel
import com.insearching.pickstream.news.domain.usecase.GetAllNews
import com.insearching.pickstream.news.domain.usecase.UpdateFeeds
import com.insearching.pickstream.news.presentation.favorites.FavoritesViewModel
import com.insearching.pickstream.news.presentation.home.HomeViewModel
import com.insearching.pickstream.news.presentation.profile.ProfileScreenViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val shareModule = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::RssRepositoryImpl).bind<RssRepository>()

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single { get<ChannelDatabase>().channelDao }
    single { get<ChannelDatabase>().storyDao }

    factoryOf(::GetAllNews)
    factoryOf(::AddFeedChannel)
    factoryOf(::UpdateFeeds)

    viewModelOf(::HomeViewModel)
    viewModelOf(::FavoritesViewModel)
    viewModelOf(::ProfileScreenViewModel)
}