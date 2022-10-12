package com.bignerdranch.android.network_gson_exercise.app.screens.main.tabs.dashboard

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
import com.bignerdranch.android.network_gson_exercise.app.model.boxes.entities.BoxesFilter
import com.bignerdranch.android.network_gson_exercise.app.utils.logger.Logger

class DashboardViewModel(
    private val boxesRepository: BoxesRepository = Singletons.boxesRepository,
    accountsRepository: AccountsRepository = Singletons.accountsRepository,
    logger: Logger = LogCatLogger
) : BaseViewModel(accountsRepository, logger) {

    private val _boxes = MutableLiveData<Result<List<Box>>>()
    val boxes = _boxes.share()

    init {
        viewModelScope.launch {
            boxesRepository.getBoxesAndSettings(BoxesFilter.ONLY_ACTIVE).collect { result ->
                _boxes.value = result.map { list -> list.map { it.box } }
            }
        }
    }

    fun reload() = viewModelScope.launch {
        boxesRepository.reload(BoxesFilter.ONLY_ACTIVE)
    }

}