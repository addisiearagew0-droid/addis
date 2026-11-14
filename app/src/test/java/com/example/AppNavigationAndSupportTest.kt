package com.example

import android.content.ClipboardManager
import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class AppNavigationAndSupportTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun samplePortraitSelection_revealsStory_andArchiveNavigationWorks() {
        composeRule.onNodeWithTag("sample_king_menelik").performScrollTo().performClick()
        composeRule.onNodeWithTag("king_story_section").performScrollTo().assertIsDisplayed()

        composeRule.onNodeWithTag("nav_royal_archive").performClick()
        composeRule.onNodeWithTag("archive_card_tewodros").assertIsDisplayed().performClick()
        composeRule.onNodeWithTag("detail_back_button").assertIsDisplayed().performClick()
        composeRule.onNodeWithTag("archive_card_tewodros").assertIsDisplayed()
    }

    @Test
    fun supportAccounts_copyExactNumbersToClipboard() {
        val clipboard = composeRule.activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        composeRule.onNodeWithTag("copy_telebirr_button").performScrollTo().performClick()
        assertEquals("0942320888", clipboard.primaryClip?.getItemAt(0)?.text?.toString())

        composeRule.onNodeWithTag("copy_cbe_button").performScrollTo().performClick()
        assertEquals("1000282540634", clipboard.primaryClip?.getItemAt(0)?.text?.toString())

        composeRule.onNodeWithTag("copy_abyssinia_button").performScrollTo().performClick()
        assertEquals("66833787", clipboard.primaryClip?.getItemAt(0)?.text?.toString())

        composeRule.onNodeWithTag("developer_support_cta_button").performScrollTo().performClick()
        composeRule.onNodeWithTag("support_dialog").assertIsDisplayed()
        composeRule.onNodeWithTag("support_dialog_close_button").performClick()
    }
}
