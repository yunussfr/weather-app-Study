package com.kampplus.hava.feature.profile.domain.repository

import com.kampplus.hava.feature.profile.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

/**
 * Profil özelliğinin veri sözleşmesi.
 *
 * Domain katmanı profilin nerede tutulduğunu bilmez. Bugün SharedPreferences,
 * yarın bir REST servisi kullanılsa bile ekran ve iş kuralları değişmez.
 */
interface UserProfileRepository {
    fun observeProfile(): Flow<UserProfile>

    suspend fun updateProfile(profile: UserProfile)
}
