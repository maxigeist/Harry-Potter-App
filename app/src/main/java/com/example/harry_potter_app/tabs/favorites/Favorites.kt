package com.example.harry_potter_app.tabs.favorites

import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.harry_potter_app.components.CardData
import com.example.harry_potter_app.R
import com.example.harry_potter_app.components.CardTabLayout
import com.example.harry_potter_app.data.favorite.manager.FavoriteViewModel

@Composable
fun Favorites() {
    val context = LocalContext.current
    val viewModel = hiltViewModel<FavoriteViewModel, FavoriteViewModel.FavoriteViewModelFactory>{
        factory -> factory.create(context = context)
    }
    val favoriteCharacters by viewModel.favoriteCharacters.collectAsState()
    val favoriteHouses by viewModel.favoriteHouses.collectAsState()
    val loading by viewModel.loadingFavorites.collectAsState()
    val showRetry by viewModel.showRetry.collectAsState()

    val isAuthenticated by viewModel.isAuthenticated.collectAsState()
    val biometricManager = remember { BiometricManager.from(context) }
    val isBiometricAvailable = remember {
        biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
    }

    val items = favoriteCharacters.mapIndexed { index, character ->
        CardData(
            title = character.nickname,
            imgUrl = character.image,
            onClick = {
            },
            favorite = true,
            addToFavoriteFunction = {
            },
            removeFromFavoriteFunction = {
                viewModel.removeCharacterFromFavorites(character.index)
            }
        )
    } + favoriteHouses.mapIndexed { index, house ->
        CardData(
            title = house.house,
            imgUrl = "",
            emoji = house.emoji,
            onClick = {

            },
            favorite = true,
            addToFavoriteFunction = {

            },
            removeFromFavoriteFunction = {
            }
        )
    }
    when (isBiometricAvailable) {
        BiometricManager.BIOMETRIC_SUCCESS -> {
            if(isAuthenticated) {
                CardTabLayout(
                    loading = loading,
                    showRetry = showRetry,
                    layoutTitleId = R.string.favorites,
                    items = items,
                    retryFunction = {
                        viewModel.retryGetFavorites()
                    }
                )
            } else {
                viewModel.biometricAuthentication()
                Text(text = "You need to authenticate")
            }
        }
        BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
            // No biometric features available on this device
            Text(text = "This phone is not prepared for biometric features")
        }
        BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
            // Biometric features are currently unavailable.
            Text(text = "Biometric auth is unavailable")
        }
        BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
            // Biometric features available but a security vulnerability has been discovered
            Text(text = "You can't use biometric auth until you have updated your security details")
        }
        BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
            // Biometric features are currently unavailable because the specified options are incompatible with the current Android version..
            Text(text = "You can't use biometric auth with this Android version")
        }
        BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
            // Unable to determine whether the user can authenticate using biometrics
            Text(text = "You can't use biometric auth")
        }
        BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
            // The user can't authenticate because no biometric or device credential is enrolled.
            Text(text = "You can't use biometric auth")
        }
    }
}