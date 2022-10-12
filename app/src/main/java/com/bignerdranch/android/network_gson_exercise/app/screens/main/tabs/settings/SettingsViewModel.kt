package com.bignerdranch.android.network_gson_exercise.app.screens.main.tabs.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import com.bignerdranch.android.network_gson_exercise.app.screens.base.BaseViewModel
import com.bignerdranch.android.network_gson_exercise.app.utils.share
import com.bignerdranch.android.network_gson_exercise.app.utils.logger.LogCatLogger
import com.bignerdranch.android.network_gson_exercise.app.Singletons
import com.bignerdranch.android.network_gson_exercise.app.model.Result
import com.bignerdranch.android.network_gson_exercise.app.model.accounts.AccountsRepository
import com.bignerdranch.android.network_gson_exercise.app.model.boxes.BoxesRepository
import com.bignerdranch.android.network_gson_exercise.app.model.boxes.entities.Box
import com.bignerdranch.android.network_gson_exercise.app.model.boxes.entities.BoxAndSettings
import com.bignerdranch.android.network_gson_exercise.app.model.boxes.entities.BoxesFilter
import com.bignerdranch.android.network_gson_exercise.app.utils.logger.Logger

class SettingsViewModel(
    private val boxesRepository: BoxesRepository = Singletons.boxesRepository,
    accountsRepository: AccountsRepository = Singletons.accountsRepository,
    logger: Logger = LogCatLogger
) : BaseViewModel(accountsRepository, logger), SettingsAdapter.Listener {

    private val _boxSettings = MutableLiveData<Result<List<BoxAndSettings>>>()
    val boxSettings = _boxSettings.share()

    init {
        viewModelScope.launch {
            boxesRepository.getBoxesAndSettings(BoxesFilter.ALL).collect {
                _boxSettings.value = it
            }
        }
    }

    fun tryAgain() = viewModelScope.safeLaunch {
        boxesRepository.reload(BoxesFilter.ALL)
    }

    override fun enableBox(box: Box) = viewModelScope.safeLaunch {
        boxesRepository.activateBox(box)
    }

    override fun disableBox(box: Box) = viewModelScope.safeLaunch {
        boxesRepository.deactivateBox(box)
    }

}