package com.marco.jpappnote.feature_note.domain.use_case

data class NoteUsesCases (
    val getNotes: GetNotesUseCase,
    val deleteNote: DeleteNoteUseCase,
    val addNote: AddNoteUseCase,
    val getNote: GetNoteUseCase
)