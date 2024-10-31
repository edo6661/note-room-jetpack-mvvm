package com.example.notes.di

import com.example.notes.data.repo.NoteRepositoryImpl
import com.example.notes.domain.repo.NoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
  @Binds
  @Singleton
  abstract fun bindRepository(
    repositoryImpl : NoteRepositoryImpl
  ) : NoteRepository
}