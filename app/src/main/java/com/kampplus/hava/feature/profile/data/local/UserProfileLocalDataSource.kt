package com.kampplus.hava.feature.profile.data.local

import com.kampplus.hava.feature.profile.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

/** Data katmanında profilin yerel saklama operasyonlarını tanımlar. */
interface UserProfileLocalDataSource {
    fun observe(): Flow<UserProfile>

    suspend fun save(profile: UserProfile)
}
