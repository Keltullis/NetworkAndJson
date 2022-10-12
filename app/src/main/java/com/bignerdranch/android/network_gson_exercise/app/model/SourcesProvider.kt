package com.bignerdranch.android.network_gson_exercise.app.model

import com.bignerdranch.android.network_gson_exercise.app.model.accounts.AccountsSource
import com.bignerdranch.android.network_gson_exercise.app.model.boxes.BoxesSource

/**
 * Factory class for all network sources.
 */
interface SourcesProvider {

    /**
     * Create [AccountsSource] which is responsible for reading/writing
     * user accounts data.
     */
    fun getAccountsSource(): AccountsSource

    /**
     * Create [BoxesSource] which is responsible for reading/updating
     * boxes data.
     */
    fun getBoxesSource(): BoxesSource

}