package ua.insearching.mynews

import android.app.Application
import org.koin.android.ext.koin.androidContext
import ua.insearching.mynews.di.initKoin

class NewsApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@NewsApplication)
        }
    }
}