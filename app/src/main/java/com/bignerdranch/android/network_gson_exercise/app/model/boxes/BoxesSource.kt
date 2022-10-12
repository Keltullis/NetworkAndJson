package com.bignerdranch.android.network_gson_exercise.app.model.boxes

import com.bignerdranch.android.network_gson_exercise.app.model.BackendException
import com.bignerdranch.android.network_gson_exercise.app.model.ConnectionException
import com.bignerdranch.android.network_gson_exercise.app.model.ParseBackendResponseException
import com.bignerdranch.android.network_gson_exercise.app.model.boxes.entities.BoxAndSettings
import com.bignerdranch.android.network_gson_exercise.app.model.boxes.entities.BoxesFilter

interface BoxesSource {

    /**
     * Get the list of all boxes for the current logged-in user.
     * @throws BackendException
     * @throws ConnectionException
     * @throws ParseBackendResponseException
     */
    suspend fun getBoxes(boxesFilter: BoxesFilter): List<BoxAndSettings>

    /**
     * Set isActive flag for the specified box.
     * @throws BackendException
     * @throws ConnectionException
     * @throws ParseBackendResponseException
     */
    suspend fun setIsActive(boxId: Long, isActive: Boolean)

}