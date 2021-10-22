package com.marco.jpappnote.feature_note.domain.use_case

import android.graphics.Color
import com.marco.jpappnote.feature_note.data.repository.FakeNoteRepository
import com.marco.jpappnote.feature_note.domain.model.Note
import com.marco.jpappnote.feature_note.domain.util.NoteOrder
import com.marco.jpappnote.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class DeleteNoteUseCaseTest {

    private lateinit var getNotes: GetNotesUseCase
    private lateinit var fakeRepository: FakeNoteRepository
    private var note : Note = Note("Title","Content",1111111, Color.BLUE, 1)

    @Before
    fun setUp(){
        fakeRepository = FakeNoteRepository()
        getNotes = GetNotesUseCase(fakeRepository)

    }

    @Test
    fun `Delete Note`() = runBlocking{
        fakeRepository.insertNote(note)
        val notes = getNotes(NoteOrder.Title(OrderType.Ascending)).first()
        assertFalse(notes.isEmpty())
        fakeRepository.deleteNote(note)
        val notes2 = getNotes(NoteOrder.Title(OrderType.Ascending)).first()
        assertTrue(notes2.isEmpty())
    }
}