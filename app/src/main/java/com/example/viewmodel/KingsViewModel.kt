package com.example.viewmodel

import com.example.model.King
import com.example.model.KingsRepository
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class KingsUiState(
    val kings: List<King> = KingsRepository.allKings,
    val selectedKingId: String = KingsRepository.allKings.first().id,
    val archiveDetailKingId: String = KingsRepository.allKings.first().id,
) {
    val selectedKing: King
        get() = KingsRepository.findById(selectedKingId) ?: kings.first()

    val archiveDetailKing: King
        get() = KingsRepository.findById(archiveDetailKingId) ?: kings.first()
}

class KingsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(KingsUiState())
    val uiState: StateFlow<KingsUiState> = _uiState.asStateFlow()

    fun onSelectKing(kingId: String) {
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
