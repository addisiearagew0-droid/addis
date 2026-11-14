package com.example.viewmodel

import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import com.example.model.King
import com.example.model.KingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

sealed interface SelectedPhotoSource {
    data object None : SelectedPhotoSource
    data class UserGalleryUri(val uri: Uri) : SelectedPhotoSource
    data class ArchiveSample(@DrawableRes val drawableRes: Int, val sampleKingId: String) : SelectedPhotoSource
}

data class KingsUiState(
    val kings: List<King> = KingsRepository.allKings,
    val photoSource: SelectedPhotoSource = SelectedPhotoSource.None,
    val selectedKingId: String? = null,
    val archiveDetailKingId: String = KingsRepository.allKings.first().id,
) {
    val selectedKing: King?
        get() = KingsRepository.findById(selectedKingId)

    val archiveDetailKing: King
        get() = KingsRepository.findById(archiveDetailKingId) ?: kings.first()

    val hasPhoto: Boolean
        get() = photoSource !is SelectedPhotoSource.None
}

class KingsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(KingsUiState())
    val uiState: StateFlow<KingsUiState> = _uiState.asStateFlow()

    fun onPhotoPicked(uri: Uri) {
        _uiState.update { current ->
            current.copy(
                photoSource = SelectedPhotoSource.UserGalleryUri(uri),
                // Reset confirmed king selection so the user verifies who is in the new photo
                selectedKingId = null,
            )
        }
    }

    fun onSamplePortraitSelected(king: King) {
        _uiState.update { current ->
            current.copy(
                photoSource = SelectedPhotoSource.ArchiveSample(
                    drawableRes = king.portraitResId,
                    sampleKingId = king.id,
                ),
                selectedKingId = king.id,
            )
        }
    }

    fun onClearPhoto() {
        _uiState.update { current ->
            current.copy(
                photoSource = SelectedPhotoSource.None,
                selectedKingId = null,
            )
        }
    }

    fun onSelectKingForStory(kingId: String?) {
        _uiState.update { current ->
            current.copy(selectedKingId = kingId)
        }
    }

    fun onSelectArchiveKing(kingId: String) {
        _uiState.update { current ->
            current.copy(archiveDetailKingId = kingId)
        }
    }
}
