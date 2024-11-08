package com.example.harry_potter_app.tabs.favorites


import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.harry_potter_app.R
import com.example.harry_potter_app.components.CardData
import com.example.harry_potter_app.components.CardTabLayout
import com.example.harry_potter_app.components.SelectBoxProps
import com.example.harry_potter_app.data.favorite.manager.FavoriteViewModel
import com.example.harry_potter_app.ui.theme.MediumFontSize
import com.example.harry_potter_app.ui.theme.PaddingSmall
import com.example.harry_potter_app.ui.theme.SmallFontSize
import com.example.harry_potter_app.ui.theme.TabTitleTextSize


@Composable
fun Favorites() {
    val context = LocalContext.current
    val viewModel =
        hiltViewModel<FavoriteViewModel, FavoriteViewModel.FavoriteViewModelFactory> { factory ->
            factory.create(context = context)
        }

    var selectedFavorites by remember { mutableStateOf<List<CardData>?>(null) }

    var favorite by remember { mutableStateOf("") }

    val favoriteCharacters by viewModel.favoriteCharacters.collectAsState()
    val favoriteHouses by viewModel.favoriteHouses.collectAsState()
    val favoriteBooks by viewModel.favoriteBooks.collectAsState()
    val loading by viewModel.loadingFavorites.collectAsState()
    val showRetry by viewModel.showRetry.collectAsState()


    val isAuthenticated by viewModel.isAuthenticated.collectAsState()
    val biometricManager = remember { BiometricManager.from(context) }
    val isBiometricAvailable = remember {
        biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
    }


    fun handleChange(value: String) {
        favorite = value
        if (value == "Characters") {
            selectedFavorites = favoriteCharacters.mapIndexed { index, character ->
                CardData(
                    title = character.nickname,
                    imgUrl = character.image,
                    favorite = true,
                    onClick = {},
                    addToFavoriteFunction = {},
                    removeFromFavoriteFunction = {
                        viewModel.removeCharacterFromFavorites(character.index)
                        selectedFavorites =
                            selectedFavorites?.filter { it.title != character.nickname }
                    }
                )
            }
        } else if (value === "Houses") {
            selectedFavorites = favoriteHouses.mapIndexed { index, house ->
                CardData(
                    title = house.house,
                    imgUrl = "",
                    emoji = house.emoji,
                    favorite = true,
                    onClick = {},
                    addToFavoriteFunction = {},
                    removeFromFavoriteFunction = {
                        viewModel.removeHouseFromFavorites(house.index)
                        selectedFavorites = selectedFavorites?.filter { it.title != house.house }
                    }
                )
            }
        } else if (value === "Books") {
            selectedFavorites = favoriteBooks.mapIndexed { index, book ->
                CardData(
                    title = book.title,
                    imgUrl = book.cover,
                    favorite = true,
                    onClick = {},
                    addToFavoriteFunction = {},
                    removeFromFavoriteFunction = {
                        viewModel.removeBookFromFavorites(book.index)
                        selectedFavorites = selectedFavorites?.filter { it.title != book.title }
                    }
                )
            }
        }
    }



    when (isBiometricAvailable) {
        BiometricManager.BIOMETRIC_SUCCESS -> {
            if (isAuthenticated) {
                CardTabLayout(
                    loading = loading,
                    showRetry = showRetry,
                    layoutTitleId = R.string.favorites,
                    items = if (selectedFavorites?.isNotEmpty() == true) selectedFavorites!! else listOf(),
                    retryFunction = {
                        viewModel.retryGetFavorites()
                    },
                    selectBoxProps = SelectBoxProps(
                        listOf(stringResource(id = R.string.characters) , stringResource(id = R.string.houses) , stringResource(id = R.string.books)),
                        active = favorite,
                        ::handleChange
                    )
                )
            } else {
                viewModel.biometricAuthentication()
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center

                ) {
                    Text(
                        text = stringResource(id = R.string.you_need_to_authenticate),
                        fontSize = MediumFontSize,
                        fontFamily = FontFamily(
                            Font(R.font.harry)
                        ),
                    )
                }

            }
        }

        BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
            // No biometric features available on this device
            Text(text = stringResource(id = R.string.this_phone_is_not_prepared_for_biometric_features))
        }

        BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
            // Biometric features are currently unavailable.
            Text(text = stringResource(id = R.string.biometric_auth_is_unavailable))
        }

        BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
            // Biometric features available but a security vulnerability has been discovered
            Text(text = stringResource(id = R.string.you_cant_use_biometric_auth_until_you_have_updated_your_security_details))
        }

        BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
            // Biometric features are currently unavailable because the specified options are incompatible with the current Android version..
            Text(text = stringResource(id = R.string.you_cant_use_biometric_auth_with_this_android_version))
        }

        BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
            // Unable to determine whether the user can authenticate using biometrics
            Text(text = stringResource(id = R.string.you_cant_use_biometric_auth))
        }

        BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
            // The user can't authenticate because no biometric or device credential is enrolled.
            Text(text = stringResource(id = R.string.you_cant_use_biometric_auth))
        }
    }
}