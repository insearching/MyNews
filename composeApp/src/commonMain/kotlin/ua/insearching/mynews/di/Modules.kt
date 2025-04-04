package ua.insearching.mynews.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import ua.insearching.mynews.core.data.HttpClientFactory
import ua.insearching.mynews.news.data.database.ChannelDatabase
import ua.insearching.mynews.news.data.database.DatabaseFactory
import ua.insearching.mynews.news.data.repository.NewsRssRepository
import ua.insearching.mynews.news.domain.repository.RssRepository
import ua.insearching.mynews.news.domain.usecase.AddNewChannel
import ua.insearching.mynews.news.domain.usecase.GetAllChannels
import ua.insearching.mynews.news.domain.usecase.GetAllNews
import ua.insearching.mynews.news.domain.usecase.UpdateNews
import ua.insearching.mynews.news.presentation.favorites.FavoritesViewModel
import ua.insearching.mynews.news.presentation.profile.ProfileScreenViewModel
import ua.insearching.mynews.news.presentation.home.HomeViewModel

expect val platformModule: Module

val shareModule = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::NewsRssRepository).bind<RssRepository>()

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single { get<ChannelDatabase>().channelDao }

    single { GetAllNews(get()) }
    single { GetAllChannels(get()) }
    single { AddNewChannel(get()) }
    single { UpdateNews(get()) }

    viewModel { HomeViewModel(get(), get()) }
    viewModel { FavoritesViewModel() }
    viewModel { ProfileScreenViewModel(get(), get(), get()) }

}