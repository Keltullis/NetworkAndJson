package com.bignerdranch.android.network_gson_exercise.app.screens.main.tabs.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.network_gson_exercise.app.R
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import com.bignerdranch.android.network_gson_exercise.app.screens.base.BaseViewModel
import com.bignerdranch.android.network_gson_exercise.app.utils.MutableLiveEvent
import com.bignerdranch.android.network_gson_exercise.app.utils.MutableUnitLiveEvent
import com.bignerdranch.android.network_gson_exercise.app.utils.publishEvent
import com.bignerdranch.android.network_gson_exercise.app.utils.share
import com.bignerdranch.android.network_gson_exercise.app.utils.logger.LogCatLogger
import com.bignerdranch.android.network_gson_exercise.app.Singletons
import com.bignerdranch.android.network_gson_exercise.app.model.EmptyFieldException
import com.bignerdranch.android.network_gson_exercise.app.model.Success
import com.bignerdranch.android.network_gson_exercise.app.model.accounts.AccountsRepository
import com.bignerdranch.android.network_gson_exercise.app.utils.logger.Logger

class EditProfileViewModel(
    accountsRepository: AccountsRepository = Singletons.accountsRepository,
    logger: Logger = LogCatLogger
) : BaseViewModel(accountsRepository, logger) {

    private val _initialUsernameEvent = MutableLiveEvent<String>()
    val initialUsernameEvent = _initialUsernameEvent.share()

    private val _saveInProgress = MutableLiveData(false)
    val saveInProgress = _saveInProgress.share()

    private val _goBackEvent = MutableUnitLiveEvent()
    val goBackEvent = _goBackEvent.share()

    private val _showErrorEvent = MutableLiveEvent<Int>()
    val showErrorEvent = _showErrorEvent.share()

    init {
        viewModelScope.launch {
            val res = accountsRepository.getAccount()
                .filter { it.isFinished() }
                .first()
            if (res is Success) _initialUsernameEvent.publishEvent(res.value.username)
        }
    }

    fun saveUsername(newUsername: String) = viewModelScope.safeLaunch {
        showProgress()
        try {
            accountsRepository.updateAccountUsername(newUsername)
            goBack()
        } catch (e: EmptyFieldException) {
            showEmptyFieldErrorMessage()
        } finally {
            hideProgress()
        }
    }

    private fun goBack() = _goBackEvent.publishEvent()

    private fun showProgress() {
        _saveInProgress.value = true
    }

    private fun hideProgress() {
        _saveInProgress.value = false
    }

    private fun showEmptyFieldErrorMessage() = _showErrorEvent.publishEvent(R.string.field_is_empty)

}