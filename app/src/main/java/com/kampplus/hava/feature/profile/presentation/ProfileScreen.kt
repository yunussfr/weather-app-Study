package com.kampplus.hava.feature.profile.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kampplus.hava.R
import com.kampplus.hava.core.ui.component.LoadingView
import com.kampplus.hava.core.ui.theme.HavaTheme

/**
 * Kullanıcı Profili ekranının tek sorumluluğu profil state'ini göstermek ve kullanıcı
 * hareketlerini callback olarak dışarı iletmektir. Veri kaydetmez, iş kuralı çalıştırmaz.
 *
 * Gösterilen veriler: ad, e-posta, şehir, biyografi ve üyelik yılı.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onEditClick: () -> Unit,
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit,
    onFullNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onCityChange: (String) -> Unit,
    onBiographyChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.profile_title)) },
                actions = {
                    if (!uiState.isLoading && !uiState.isEditing) {
                        TextButton(onClick = onEditClick) { Text(stringResource(R.string.action_edit)) }
                    }
                }
            )
        }
    ) { innerPadding ->
        if (uiState.isLoading) {
            LoadingView(modifier = Modifier.padding(innerPadding))
        } else {
            ProfileContent(
                uiState = uiState,
                onCancelClick = onCancelClick,
                onSaveClick = onSaveClick,
                onFullNameChange = onFullNameChange,
                onEmailChange = onEmailChange,
                onCityChange = onCityChange,
                onBiographyChange = onBiographyChange,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
private fun ProfileContent(
    uiState: ProfileUiState,
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit,
    onFullNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onCityChange: (String) -> Unit,
    onBiographyChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Surface(shape = CircleShape, color = MaterialTheme.colorScheme.primaryContainer) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier
                    .padding(20.dp)
                    .size(56.dp)
            )
        }

        Text(uiState.fullName, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text(
            stringResource(R.string.profile_member_since, uiState.memberSinceYear),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        if (uiState.isEditing) {
            ProfileEditForm(
                uiState = uiState,
                onFullNameChange = onFullNameChange,
                onEmailChange = onEmailChange,
                onCityChange = onCityChange,
                onBiographyChange = onBiographyChange
            )
        } else {
            ProfileReadOnlyCard(uiState)
        }

        uiState.validationMessage?.let {
            Text(it.asString(), color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium)
        }

        if (uiState.isEditing) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(onClick = onCancelClick, modifier = Modifier.weight(1f), enabled = !uiState.isSaving) {
                    Text(stringResource(R.string.action_cancel))
                }
                Button(onClick = onSaveClick, modifier = Modifier.weight(1f), enabled = !uiState.isSaving) {
                    Text(stringResource(if (uiState.isSaving) R.string.action_saving else R.string.action_save))
                }
            }
        }
    }
}

@Composable
private fun ProfileEditForm(
    uiState: ProfileUiState,
    onFullNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onCityChange: (String) -> Unit,
    onBiographyChange: (String) -> Unit
) {
    OutlinedTextField(
        value = uiState.fullName,
        onValueChange = onFullNameChange,
        label = { Text(stringResource(R.string.profile_name)) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
    OutlinedTextField(
        value = uiState.email,
        onValueChange = onEmailChange,
        label = { Text(stringResource(R.string.profile_email)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
    OutlinedTextField(
        value = uiState.city,
        onValueChange = onCityChange,
        label = { Text(stringResource(R.string.profile_city)) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
    OutlinedTextField(
        value = uiState.biography,
        onValueChange = onBiographyChange,
        label = { Text(stringResource(R.string.profile_biography)) },
        minLines = 3,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun ProfileReadOnlyCard(uiState: ProfileUiState) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            ProfileValue(stringResource(R.string.profile_email), uiState.email)
            ProfileValue(stringResource(R.string.profile_city), uiState.city.ifBlank { stringResource(R.string.not_specified) })
            ProfileValue(
                stringResource(R.string.profile_biography),
                uiState.biography.ifBlank { stringResource(R.string.not_specified) }
            )
        }
    }
}

@Composable
private fun ProfileValue(label: String, value: String) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    HavaTheme {
        ProfileScreen(
            uiState = ProfileUiState(
                fullName = "Yunus Emre",
                email = "yunus@example.com",
                city = "İstanbul",
                biography = "Hava durumunu takip etmeyi seviyorum.",
                memberSinceYear = 2026,
                isLoading = false,
                validationMessage = null
            ),
            onEditClick = {},
            onCancelClick = {},
            onSaveClick = {},
            onFullNameChange = {},
            onEmailChange = {},
            onCityChange = {},
            onBiographyChange = {}
        )
    }
}
