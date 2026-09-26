package com.kampplus.hava.feature.profile.domain.usecase

import com.kampplus.hava.feature.profile.domain.model.UserProfile
import com.kampplus.hava.feature.profile.domain.repository.UserProfileRepository
import javax.inject.Inject

/**
 * Profil kaydetme iş kuralı.
 *
 * Ad ve e-posta doğrulaması UI'a bırakılmaz; başka bir ekran profili güncellese de
 * aynı kurallar uygulanır.
 */
class UpdateUserProfileUseCase @Inject constructor(
    private val repository: UserProfileRepository
) {
    suspend operator fun invoke(profile: UserProfile): ProfileUpdateResult {
        if (profile.fullName.isBlank()) return ProfileUpdateResult.NameRequired
        if (!EMAIL_PATTERN.matches(profile.email.trim())) return ProfileUpdateResult.InvalidEmail

        val normalizedProfile = profile.copy(
            fullName = profile.fullName.trim(),
            email = profile.email.trim(),
            city = profile.city.trim(),
            biography = profile.biography.trim()
        )
        repository.updateProfile(normalizedProfile)
        return ProfileUpdateResult.Success(normalizedProfile)
    }

    private companion object {
        val EMAIL_PATTERN = Regex("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")
    }
}

/** UI'ın göstereceği metinden bağımsız, alan doğrulama sonuçları. */
sealed interface ProfileUpdateResult {
    data class Success(
        val profile: UserProfile
    ) : ProfileUpdateResult

    data object NameRequired : ProfileUpdateResult

    data object InvalidEmail : ProfileUpdateResult
}
