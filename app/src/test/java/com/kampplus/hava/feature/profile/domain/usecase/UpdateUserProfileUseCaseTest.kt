package com.kampplus.hava.feature.profile.domain.usecase

import com.kampplus.hava.feature.profile.domain.model.UserProfile
import com.kampplus.hava.feature.profile.domain.repository.UserProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class UpdateUserProfileUseCaseTest {
    private val repository = FakeUserProfileRepository()
    private val useCase = UpdateUserProfileUseCase(repository)

    @Test
    fun `blank name is rejected without saving`() = runTest {
        val result = useCase(profile().copy(fullName = "  "))

        assertEquals(ProfileUpdateResult.NameRequired, result)
        assertNull(repository.lastSaved)
    }

    @Test
    fun `invalid email is rejected without saving`() = runTest {
        val result = useCase(profile().copy(email = "yanlis-adres"))

        assertEquals(ProfileUpdateResult.InvalidEmail, result)
        assertNull(repository.lastSaved)
    }

    @Test
    fun `valid profile is trimmed and saved`() = runTest {
        val result = useCase(profile().copy(fullName = "  Yunus Emre  ", city = "  İstanbul "))

        assertTrue(result is ProfileUpdateResult.Success)
        assertEquals("Yunus Emre", repository.lastSaved?.fullName)
        assertEquals("İstanbul", repository.lastSaved?.city)
    }

    private fun profile() = UserProfile(
        id = "user-1",
        fullName = "Yunus Emre",
        email = "yunus@example.com",
        city = "İstanbul",
        biography = "",
        memberSinceYear = 2026
    )
}

private class FakeUserProfileRepository : UserProfileRepository {
    private val profile = MutableStateFlow(
        UserProfile("user-1", "Yunus Emre", "yunus@example.com", "İstanbul", "", 2026)
    )
    var lastSaved: UserProfile? = null

    override fun observeProfile(): Flow<UserProfile> = profile

    override suspend fun updateProfile(profile: UserProfile) {
        lastSaved = profile
        this.profile.value = profile
    }
}
