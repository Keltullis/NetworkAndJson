package com.bignerdranch.android.network_gson_exercise.sources.boxes

import kotlinx.coroutines.delay
import com.bignerdranch.android.network_gson_exercise.app.model.boxes.BoxesSource
import com.bignerdranch.android.network_gson_exercise.app.model.boxes.entities.BoxAndSettings
import com.bignerdranch.android.network_gson_exercise.app.model.boxes.entities.BoxesFilter
import com.bignerdranch.android.network_gson_exercise.sources.base.BaseOkHttpSource
import com.bignerdranch.android.network_gson_exercise.sources.base.OkHttpConfig
import com.bignerdranch.android.network_gson_exercise.sources.boxes.entities.GetBoxResponseEntity
import com.bignerdranch.android.network_gson_exercise.sources.boxes.entities.UpdateBoxRequestEntity
import com.google.gson.reflect.TypeToken
import okhttp3.Request

// todo #7: implement methods:
//          - setIsActive() -> for making box active or inactive
//          - getBoxes() -> for fetching boxes data
class OkHttpBoxesSource(
    config: OkHttpConfig
) : BaseOkHttpSource(config), BoxesSource {

    override suspend fun setIsActive(boxId: Long, isActive: Boolean) {
        // Call "PUT /boxes/{boxId}" endpoint.
        // Use UpdateBoxRequestEntity.
        val updateBoxRequestEntity = UpdateBoxRequestEntity(isActive)
        val request = Request.Builder()
            .put(updateBoxRequestEntity.toJsonRequestBody())
            .endpoint("/boxes/$boxId")
            .build()

        val call = client.newCall(request)
        call.suspendEnqueue()
    }

    override suspend fun getBoxes(boxesFilter: BoxesFilter): List<BoxAndSettings> {
        delay(500)
        // Call "GET /boxes?active=true" if boxesFilter = ONLY_ACTIVE.
        // Call "GET /boxes" if boxesFilter = ALL.
        // Hint: use TypeToken for converting server response into List<GetBoxResponseEntity>
        // Hint: use GetBoxResponseEntity.toBoxAndSettings for mapping GetBoxResponseEntity into BoxAndSettings
        val args = if(boxesFilter == BoxesFilter.ONLY_ACTIVE)
            "?active=true"
        else
            ""
        val request = Request.Builder()
            .get()
            .endpoint("/boxes$args")
            .build()

        val call = client.newCall(request)
        // Приходит массив json,поэтому нужен TypeToken(ожидаем список,где каждый элемент должен отобразить класс GetBoxResponseEntity
        val typeToken = object :TypeToken<List<GetBoxResponseEntity>>(){}
        val response = call.suspendEnqueue().parseJsonResponse(typeToken)
        // Преобразуем
        return response.map { it.toBoxAndSettings() }
    }

}