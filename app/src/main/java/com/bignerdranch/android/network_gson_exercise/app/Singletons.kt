package com.bignerdranch.android.network_gson_exercise.app

import android.content.Context
import com.bignerdranch.android.network_gson_exercise.app.model.settings.SharedPreferencesAppSettings
import com.bignerdranch.android.network_gson_exercise.app.model.SourcesProvider
import com.bignerdranch.android.network_gson_exercise.app.model.accounts.AccountsRepository
import com.bignerdranch.android.network_gson_exercise.app.model.accounts.AccountsSource
import com.bignerdranch.android.network_gson_exercise.app.model.boxes.BoxesRepository
import com.bignerdranch.android.network_gson_exercise.app.model.boxes.BoxesSource
import com.bignerdranch.android.network_gson_exercise.app.model.settings.AppSettings
import com.bignerdranch.android.network_gson_exercise.sources.SourceProviderHolder

object Singletons {

    private lateinit var appContext: Context

    private val sourcesProvider: SourcesProvider by lazy {
        SourceProviderHolder.sourcesProvider
    }

    val appSettings: AppSettings by lazy {
        SharedPreferencesAppSettings(appContext)
    }

    // --- sources

    private val accountsSource: AccountsSource by lazy {
        sourcesProvider.getAccountsSource()
    }

    private val boxesSource: BoxesSource by lazy {
        sourcesProvider.getBoxesSource()
    }

    // --- repositories

    val accountsRepository: AccountsRepository by lazy {
        AccountsRepository(
            accountsSource = accountsSource,
            appSettings = appSettings
        )
    }

    val boxesRepository: BoxesRepository by lazy {
        BoxesRepository(
            accountsRepository = accountsRepository,
            boxesSource = boxesSource
        )
    }

    /**
     * Call this method in all application components that may be created at app startup/restoring
     * (e.g. in onCreate of activities and services)
     */
    fun init(appContext: Context) {
        Singletons.appContext = appContext
    }
}