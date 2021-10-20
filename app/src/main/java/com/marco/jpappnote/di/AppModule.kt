package com.marco.jpappnote.di

import android.app.Application
import androidx.room.Room
import com.marco.jpappnote.feature_note.data.data_source.NoteDatabase
import com.marco.jpappnote.feature_note.data.repository.NoteRepositoryImp
import com.marco.jpappnote.feature_note.domain.repository.NoteRepository
import com.marco.jpappnote.feature_note.domain.use_case.AddNoteUseCase
import com.marco.jpappnote.feature_note.domain.use_case.DeleteNoteUseCase
import com.marco.jpappnote.feature_note.domain.use_case.GetNotesUseCase
import com.marco.jpappnote.feature_note.domain.use_case.NoteUsesCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase{
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository{
        return NoteRepositoryImp(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUsesCases(repository: NoteRepository): NoteUsesCases {
        return NoteUsesCases(
            getNotes = GetNotesUseCase(repository),
            deleteNote = DeleteNoteUseCase(repository),
            addNote = AddNoteUseCase(repository)
        )
    }
}