package com.bignerdranch.android.network_gson_exercise.sources.boxes

import com.bignerdranch.android.network_gson_exercise.sources.boxes.entities.GetBoxResponseEntity
import com.bignerdranch.android.network_gson_exercise.sources.boxes.entities.UpdateBoxRequestEntity
import retrofit2.http.*


interface BoxesApi{
    // Аннотация @Path нужна для тогоч то бы подставился id-шник
    @PUT("boxes/{boxId}")
    suspend fun setIsActive(
        @Path("boxId") boxId:Long,
        @Body updateBoxRequestEntity: UpdateBoxRequestEntity
    )

    // Если передадим null то аргумент не будет передан,так можно получить все активные ящики и не активные,а так же просто все ящики
    @GET("boxes")
    suspend fun getBoxes(
        @Query("active") isActive:Boolean?
    ):List<GetBoxResponseEntity>
}