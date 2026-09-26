package com.kampplus.hava.feature.profile.domain.model

/**
 * Kullanıcı profiline ait iş verileri.
 *
 * Bu model yalnızca Kotlin tiplerinden oluşur; Compose, Android veya veri saklama
 * teknolojisini bilmez. Böylece profil verisi farklı bir arayüzde de kullanılabilir.
 */
data class UserProfile(
    val id: String,
    val fullName: String,
    val email: String,
    val city: String,
    val biography: String,
    val memberSinceYear: Int
)
