package com.kampplus.hava.feature.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kampplus.hava.R
import com.kampplus.hava.core.ui.text.UiText
import com.kampplus.hava.feature.profile.domain.model.UserProfile
import com.kampplus.hava.feature.profile.domain.usecase.ObserveUserProfileUseCase
import com.kampplus.hava.feature.profile.domain.usecase.ProfileUpdateResult
import com.kampplus.hava.feature.profile.domain.usecase.UpdateUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Profil ekranının durumunu ve kullanıcı eylemlerini yönetir.
 *
 * ViewModel Compose bileşeni çizmez ve SharedPreferences kullanmaz. Yalnızca use case'lerle
 * konuşur; böylece UI ve veri saklama yöntemi birbirinden bağımsız kalır.
 */
@HiltViewModel
class ProfileViewModel @Inject constructor(
    observeUserProfile: ObserveUserProfileUseCase,
    private val updateUserProfile: UpdateUserProfileUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private var savedProfile: UserProfile? = null

    init {
        viewModelScope.launch {
            observeUserProfile().collect { profile ->
                savedProfile = profile
                if (!_uiState.value.isEditing) _uiState.value = profile.toUiState()
            }
        }
    }

    fun onEditClick() {
        _uiState.update { it.copy(isEditing = true, validationMessage = null) }
    }

    fun onCancelClick() {
        savedProfile?.let { _uiState.value = it.toUiState() }
    }

    fun onFullNameChange(value: String) = updateForm { copy(fullName = value, validationMessage = null) }

    fun onEmailChange(value: String) = updateForm { copy(email = value, validationMessage = null) }

    fun onCityChange(value: String) = updateForm { copy(city = value, validationMessage = null) }

    fun onBiographyChange(value: String) = updateForm { copy(biography = value, validationMessage = null) }

    fun onSaveClick() {
        val original = savedProfile ?: return
        val form = _uiState.value
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, validationMessage = null) }
            val result = updateUserProfile(
                original.copy(
                    fullName = form.fullName,
                    email = form.email,
                    city = form.city,
                    biography = form.biography
                )
            )
            when (result) {
                is ProfileUpdateResult.Success -> {
                    savedProfile = result.profile
                    _uiState.value = result.profile.toUiState()
                }
                ProfileUpdateResult.NameRequired -> showValidationError(R.string.profile_error_name)
                ProfileUpdateResult.InvalidEmail -> showValidationError(R.string.profile_error_email)
            }
        }
    }

    private fun showValidationError(messageRes: Int) {
        _uiState.update { it.copy(isSaving = false, validationMessage = UiText.Resource(messageRes)) }
    }

    private fun updateForm(transform: ProfileUiState.() -> ProfileUiState) {
        _uiState.update(transform)
    }

    private fun UserProfile.toUiState() = ProfileUiState(
        userId = id,
        fullName = fullName,
        email = email,
        city = city,
        biography = biography,
        memberSinceYear = memberSinceYear,
        isLoading = false
    )
}
