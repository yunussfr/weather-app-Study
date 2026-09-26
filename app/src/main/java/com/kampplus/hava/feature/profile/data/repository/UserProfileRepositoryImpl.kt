package com.kampplus.hava.feature.profile.data.repository

import com.kampplus.hava.feature.profile.data.local.UserProfileLocalDataSource
import com.kampplus.hava.feature.profile.domain.model.UserProfile
import com.kampplus.hava.feature.profile.domain.repository.UserProfileRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

/** Domain sözleşmesini seçilen yerel veri kaynağıyla gerçekleştirir. */
class UserProfileRepositoryImpl @Inject constructor(
    private val localDataSource: UserProfileLocalDataSource
) : UserProfileRepository {
    override fun observeProfile(): Flow<UserProfile> = localDataSource.observe()

    override suspend fun updateProfile(profile: UserProfile) = localDataSource.save(profile)
}
