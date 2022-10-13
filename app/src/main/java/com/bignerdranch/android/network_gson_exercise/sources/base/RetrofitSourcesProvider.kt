package com.bignerdranch.android.network_gson_exercise.sources.base

import com.bignerdranch.android.network_gson_exercise.app.model.SourcesProvider
import com.bignerdranch.android.network_gson_exercise.app.model.accounts.AccountsSource
import com.bignerdranch.android.network_gson_exercise.app.model.boxes.BoxesSource
import com.bignerdranch.android.network_gson_exercise.sources.accounts.RetrofitAccountsSource
import com.bignerdranch.android.network_gson_exercise.sources.boxes.RetrofitBoxesSource


class RetrofitSourcesProvider(
    private val config: RetrofitConfig
) : SourcesProvider {

    override fun getAccountsSource(): AccountsSource {
        return RetrofitAccountsSource(config)
    }

    override fun getBoxesSource(): BoxesSource {
        return RetrofitBoxesSource(config)
    }

}