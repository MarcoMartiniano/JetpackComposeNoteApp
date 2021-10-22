package com.marco.jpappnote.feature_note.domain.use_case

import android.graphics.Color
import com.marco.jpappnote.feature_note.data.repository.FakeNoteRepository
import com.marco.jpappnote.feature_note.domain.model.Note
import com.marco.jpappnote.feature_note.domain.util.NoteOrder
import com.marco.jpappnote.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

class AddNoteUseCaseTest{


    private lateinit var getNotes: GetNotesUseCase
    private lateinit var fakeRepository: FakeNoteRepository

    @Before
    fun setUp(){
        fakeRepository = FakeNoteRepository()
        getNotes = GetNotesUseCase(fakeRepository)

    }

    @Test
    fun `Add Note`() = runBlocking{
        var note : Note = Note("Title","Content",1111111, Color.BLUE, 1)
        //var note : Note = Note("","Content",1111111, Color.BLUE, 1)
        assertFalse(note.title.isBlank())
        assertFalse(note.content.isBlank())
        runBlocking {
            fakeRepository.insertNote(note)
        }
        val notes = getNotes(NoteOrder.Title(OrderType.Ascending)).first()
        assertFalse(notes.isEmpty())
    }
}


