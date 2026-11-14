package com.example.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable

@Immutable
data class TimelineEvent(
    val year: String,
    val title: String,
    val detail: String,
)

@Immutable
data class King(
    val id: String,
    val shortName: String,
    val fullName: String,
    val dynasty: String,
    val reignEra: String,
    val eraYears: String,
    val birthplace: String,
    val capitalCity: String,
    val biography: String,
    val reignDetails: String,
    val legacyAndEnd: String,
    val famousQuote: String,
    val timeline: List<TimelineEvent>,
    val achievements: List<String>,
    @param:DrawableRes val portraitResId: Int,
)

object KingsRepository {
    val allKings: List<King> = medievalKings + modernKings

    fun findById(id: String?): King? = allKings.firstOrNull { it.id == id }
}
