package com.marco.jpappnote.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.marco.jpappnote.ui.theme.*

@Entity
data class Note (
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
){
    companion object {
        //val noteColors = listOf(Salmon, Yellow, Violet, BabyBlue, RedPink)
        val noteColors = listOf(BabyBlue, Violet, Yellow, RedPink, Salmon)
    }
}

class InvalidNoteException(message: String): Exception(message)