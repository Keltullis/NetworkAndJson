package com.bignerdranch.android.network_gson_exercise.sources.accounts

import kotlinx.coroutines.delay
import com.bignerdranch.android.network_gson_exercise.app.model.accounts.AccountsSource
import com.bignerdranch.android.network_gson_exercise.app.model.accounts.entities.Account
import com.bignerdranch.android.network_gson_exercise.app.model.accounts.entities.SignUpData
import com.bignerdranch.android.network_gson_exercise.sources.accounts.entities.SignInRequestEntity
import com.bignerdranch.android.network_gson_exercise.sources.accounts.entities.SignUpRequestEntity
import com.bignerdranch.android.network_gson_exercise.sources.accounts.entities.UpdateUsernameRequestEntity
import com.bignerdranch.android.network_gson_exercise.sources.base.BaseRetrofitSource
import com.bignerdranch.android.network_gson_exercise.sources.base.RetrofitConfig


class RetrofitAccountsSource(
    config: RetrofitConfig
) : BaseRetrofitSource(config), AccountsSource {

    private val accountsApi = retrofit.create(AccountsApi::class.java)

    override suspend fun signIn(
        email: String,
        password: String
    ): String = wrapRetrofitExceptions {
        delay(1000)
        // Создаём тело запроса
        val signInRequestEntity = SignInRequestEntity(
            email = email,
            password = password
        )
        accountsApi.signIn(signInRequestEntity).token
    }

    override suspend fun signUp(
        signUpData: SignUpData
    ) = wrapRetrofitExceptions {
        delay(1000)
        val signUpRequestEntity = SignUpRequestEntity(
            email = signUpData.email,
            username = signUpData.username,
            password = signUpData.password
        )
        accountsApi.signUp(signUpRequestEntity)
    }

    override suspend fun getAccount(): Account = wrapRetrofitExceptions {
        delay(1000)
        accountsApi.getAccount().toAccount()
    }

    override suspend fun setUsername(
        username: String
    ) = wrapRetrofitExceptions {
        delay(1000)
        val updateUsernameRequestEntity = UpdateUsernameRequestEntity(
            username = username
        )
        accountsApi.setUsername(updateUsernameRequestEntity)
    }

}