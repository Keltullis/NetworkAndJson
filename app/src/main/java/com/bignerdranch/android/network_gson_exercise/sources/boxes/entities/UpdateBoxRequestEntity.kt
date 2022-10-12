package com.bignerdranch.android.network_gson_exercise.sources.boxes.entities


/**
 * Request body for `PUT /boxes/{id}` HTTP-request for activating/deactivating
 * the specified box.
 *
 * JSON example:
 * ```
 * {
 *   "isActive": true
 * }
 * ```
 */
data class UpdateBoxRequestEntity(
    val isActive: Boolean
)