package com.kampplus.hava.feature.profile.presentation

import com.kampplus.hava.core.ui.text.UiText

/**
 * Profil ekranının ihtiyaç duyduğu bütün veriler tek state içinde tutulur.
 * TextField değerlerinin de burada bulunması ekranı stateless ve test edilebilir yapar.
 */
data class ProfileUiState(
    val userId: String = "",
    val fullName: String = "",
    val email: String = "",
    val city: String = "",
    val biography: String = "",
    val memberSinceYear: Int = 0,
    val isLoading: Boolean = true,
    val isEditing: Boolean = false,
    val isSaving: Boolean = false,
    val validationMessage: UiText? = null
)
