package com.bignerdranch.android.network_gson_exercise.sources.base

import com.bignerdranch.android.network_gson_exercise.app.model.SourcesProvider
import com.bignerdranch.android.network_gson_exercise.app.model.accounts.AccountsSource
import com.bignerdranch.android.network_gson_exercise.app.model.boxes.BoxesSource
import com.bignerdranch.android.network_gson_exercise.sources.accounts.OkHttpAccountsSource
import com.bignerdranch.android.network_gson_exercise.sources.boxes.OkHttpBoxesSource


class OkHttpSourcesProvider(
    private val config: OkHttpConfig
) : SourcesProvider {

    override fun getAccountsSource(): AccountsSource {
        return OkHttpAccountsSource(config)
    }

    override fun getBoxesSource(): BoxesSource {
        return OkHttpBoxesSource(config)
    }

}