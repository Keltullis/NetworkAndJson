package com.bignerdranch.android.network_gson_exercise.sources.boxes

import kotlinx.coroutines.delay
import com.bignerdranch.android.network_gson_exercise.app.model.boxes.BoxesSource
import com.bignerdranch.android.network_gson_exercise.app.model.boxes.entities.BoxAndSettings
import com.bignerdranch.android.network_gson_exercise.app.model.boxes.entities.BoxesFilter
import com.bignerdranch.android.network_gson_exercise.sources.base.BaseRetrofitSource
import com.bignerdranch.android.network_gson_exercise.sources.base.RetrofitConfig
import com.bignerdranch.android.network_gson_exercise.sources.boxes.entities.UpdateBoxRequestEntity

class RetrofitBoxesSource(
    config: RetrofitConfig
) : BaseRetrofitSource(config), BoxesSource {

    private val boxesApi = retrofit.create(BoxesApi::class.java)

    override suspend fun setIsActive(
        boxId: Long,
        isActive: Boolean
    ) = wrapRetrofitExceptions {
        val updateBoxRequestEntity = UpdateBoxRequestEntity(
            isActive = isActive
        )
        boxesApi.setIsActive(boxId,updateBoxRequestEntity)
    }

    override suspend fun getBoxes(
        boxesFilter: BoxesFilter
    ): List<BoxAndSettings> = wrapRetrofitExceptions {
        delay(500)
        val isActive:Boolean? = if(boxesFilter == BoxesFilter.ONLY_ACTIVE){
            true
        } else {
            null
        }
        boxesApi.getBoxes(isActive)
            .map { it.toBoxAndSettings() }

    }

}