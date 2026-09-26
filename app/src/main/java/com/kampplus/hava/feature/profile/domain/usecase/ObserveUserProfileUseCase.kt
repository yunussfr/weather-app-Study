package com.kampplus.hava.feature.profile.domain.usecase

import com.kampplus.hava.feature.profile.domain.model.UserProfile
import com.kampplus.hava.feature.profile.domain.repository.UserProfileRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

/** Profil verisini izleme işini ViewModel'e tek bir giriş noktası olarak sunar. */
class ObserveUserProfileUseCase @Inject constructor(
    private val repository: UserProfileRepository
) {
    operator fun invoke(): Flow<UserProfile> = repository.observeProfile()
}
