package com.bignerdranch.android.network_gson_exercise.app.model.accounts

import com.bignerdranch.android.network_gson_exercise.app.model.BackendException
import com.bignerdranch.android.network_gson_exercise.app.model.ConnectionException
import com.bignerdranch.android.network_gson_exercise.app.model.ParseBackendResponseException
import com.bignerdranch.android.network_gson_exercise.app.model.accounts.entities.Account
import com.bignerdranch.android.network_gson_exercise.app.model.accounts.entities.SignUpData

interface AccountsSource {

    /**
     * Execute sign-in request.
     * @throws ConnectionException
     * @throws BackendException
     * @throws ParseBackendResponseException
     * @return JWT-token
     */
    suspend fun signIn(email: String, password: String): String

    /**
     * Create a new account.
     * @throws ConnectionException
     * @throws BackendException
     * @throws ParseBackendResponseException
     */
    suspend fun signUp(signUpData: SignUpData)

    /**
     * Get the account info of the current signed-in user.
     * @throws ConnectionException
     * @throws BackendException
     * @throws ParseBackendResponseException
     */
    suspend fun getAccount(): Account

    /**
     * Change the username of the current signed-in user.
     * @throws ConnectionException
     * @throws BackendException
     * @throws ParseBackendResponseException
     */
    suspend fun setUsername(username: String)

}