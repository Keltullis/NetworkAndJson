package com.bignerdranch.android.network_gson_exercise.app.screens.main.tabs.dashboard

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import com.bignerdranch.android.network_gson_exercise.app.screens.base.BaseViewModel
import com.bignerdranch.android.network_gson_exercise.app.utils.MutableLiveEvent
import com.bignerdranch.android.network_gson_exercise.app.utils.publishEvent
import com.bignerdranch.android.network_gson_exercise.app.utils.share
import com.bignerdranch.android.network_gson_exercise.app.utils.logger.LogCatLogger
import com.bignerdranch.android.network_gson_exercise.app.Singletons
import com.bignerdranch.android.network_gson_exercise.app.model.Success
import com.bignerdranch.android.network_gson_exercise.app.model.accounts.AccountsRepository
import com.bignerdranch.android.network_gson_exercise.app.model.boxes.BoxesRepository
import com.bignerdranch.android.network_gson_exercise.app.model.boxes.entities.BoxesFilter
import com.bignerdranch.android.network_gson_exercise.app.utils.logger.Logger

class BoxViewModel(
    private val boxId: Long,
    private val boxesRepository: BoxesRepository = Singletons.boxesRepository,
    accountsRepository: AccountsRepository = Singletons.accountsRepository,
    logger: Logger = LogCatLogger
) : BaseViewModel(accountsRepository, logger) {

    private val _shouldExitEvent = MutableLiveEvent<Boolean>()
    val shouldExitEvent = _shouldExitEvent.share()

    init {
        viewModelScope.launch {
            boxesRepository.getBoxesAndSettings(BoxesFilter.ONLY_ACTIVE)
                .map { res -> res.map { boxes -> boxes.firstOrNull { it.box.id == boxId } } }
                .collect { res ->
                    _shouldExitEvent.publishEvent(res is Success && res.value == null)
                }
        }
    }
}