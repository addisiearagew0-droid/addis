package com.example

import com.example.model.KingsRepository
import com.example.viewmodel.KingsViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class KingsViewModelTest {

    @Test
    fun `repository provides all ten Ethiopian leaders with rich history`() {
        val kings = KingsRepository.allKings
        assertEquals(10, kings.size)
        kings.forEach { king ->
            assertTrue(king.fullName.isNotBlank())
            assertTrue(king.reignEra.isNotBlank())
            assertTrue(king.dynasty.isNotBlank())
            assertTrue(king.capitalCity.isNotBlank())
            assertTrue(king.biography.isNotBlank())
            assertTrue(king.reignDetails.isNotBlank())
            assertTrue(king.legacyAndEnd.isNotBlank())
            assertTrue(king.famousQuote.isNotBlank())
            assertTrue(king.timeline.isNotEmpty())
            assertTrue(king.achievements.isNotEmpty())
        }
    }

    @Test
    fun `selecting king updates ui state for both main and archive screens`() {
        val viewModel = KingsViewModel()
        assertEquals("lalibela", viewModel.uiState.value.selectedKingId)
        assertEquals("ቅዱስ አፄ ላሊበላ (ገብረ መስቀል)", viewModel.uiState.value.selectedKing.fullName)

        viewModel.onSelectKing("menelik")
        assertEquals("menelik", viewModel.uiState.value.selectedKingId)
        assertEquals("ዳግማዊ አፄ ምኒልክ (እምዬ ምኒልክ)", viewModel.uiState.value.selectedKing.fullName)

        viewModel.onSelectArchiveKing("tewodros")
        assertEquals("tewodros", viewModel.uiState.value.archiveDetailKing.id)
    }
}
