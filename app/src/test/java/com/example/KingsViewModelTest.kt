package com.example

import android.net.Uri
import com.example.model.KingsRepository
import com.example.viewmodel.KingsViewModel
import com.example.viewmodel.SelectedPhotoSource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class KingsViewModelTest {

    @Test
    fun `repository provides all four Ethiopian kings with achievements`() {
        val kings = KingsRepository.allKings
        assertEquals(4, kings.size)
        kings.forEach { king ->
            assertTrue(king.fullName.isNotBlank())
            assertTrue(king.reignEra.isNotBlank())
            assertTrue(king.biography.isNotBlank())
            assertTrue(king.achievements.isNotEmpty())
        }
    }

    @Test
    fun `picking photo resets confirmed king until user verifies`() {
        val viewModel = KingsViewModel()
        val tewodros = KingsRepository.allKings.first()

        viewModel.onSamplePortraitSelected(tewodros)
        assertTrue(viewModel.uiState.value.hasPhoto)
        assertEquals("tewodros", viewModel.uiState.value.selectedKingId)

        val uri = Uri.parse("content://media/picker/123")
        viewModel.onPhotoPicked(uri)
        assertTrue(viewModel.uiState.value.hasPhoto)
        assertTrue(viewModel.uiState.value.photoSource is SelectedPhotoSource.UserGalleryUri)
        assertNull(viewModel.uiState.value.selectedKingId)

        viewModel.onSelectKingForStory("menelik")
        assertNotNull(viewModel.uiState.value.selectedKing)
        assertEquals("ዳግማዊ አፄ ምኒልክ", viewModel.uiState.value.selectedKing?.fullName)

        viewModel.onClearPhoto()
        assertFalse(viewModel.uiState.value.hasPhoto)
        assertNull(viewModel.uiState.value.selectedKingId)
    }
}
