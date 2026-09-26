package com.kampplus.hava.feature.profile.data.local

import android.content.Context
import androidx.core.content.edit
import com.kampplus.hava.feature.profile.domain.model.UserProfile
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Profil verisini cihazda kalıcı olarak saklar.
 *
 * Ekran bu sınıfı doğrudan kullanmaz. Değişiklikler StateFlow ile yayınlandığı için
 * profil açıkken yapılan bir güncelleme arayüze otomatik olarak ulaşır.
 */
@Singleton
class SharedPreferencesUserProfileDataSource @Inject constructor(
    @ApplicationContext context: Context
) : UserProfileLocalDataSource {
    private val preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    private val profile = MutableStateFlow(readProfile())

    override fun observe(): Flow<UserProfile> = profile.asStateFlow()

    override suspend fun save(profile: UserProfile) {
        preferences.edit {
            putString(KEY_ID, profile.id)
            putString(KEY_FULL_NAME, profile.fullName)
            putString(KEY_EMAIL, profile.email)
            putString(KEY_CITY, profile.city)
            putString(KEY_BIOGRAPHY, profile.biography)
            putInt(KEY_MEMBER_SINCE_YEAR, profile.memberSinceYear)
        }
        this.profile.value = profile
    }

    private fun readProfile() = UserProfile(
        id = preferences.getString(KEY_ID, DEFAULT_ID).orEmpty(),
        fullName = preferences.getString(KEY_FULL_NAME, "Yunus Emre").orEmpty(),
        email = preferences.getString(KEY_EMAIL, "yunus@example.com").orEmpty(),
        city = preferences.getString(KEY_CITY, "İstanbul").orEmpty(),
        biography = preferences.getString(KEY_BIOGRAPHY, "Hava durumunu takip etmeyi seviyorum.").orEmpty(),
        memberSinceYear = preferences.getInt(KEY_MEMBER_SINCE_YEAR, 2026)
    )

    private companion object {
        const val FILE_NAME = "user_profile"
        const val KEY_ID = "id"
        const val KEY_FULL_NAME = "full_name"
        const val KEY_EMAIL = "email"
        const val KEY_CITY = "city"
        const val KEY_BIOGRAPHY = "biography"
        const val KEY_MEMBER_SINCE_YEAR = "member_since_year"
        const val DEFAULT_ID = "local-user"
    }
}
