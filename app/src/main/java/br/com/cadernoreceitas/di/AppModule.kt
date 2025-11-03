package br.com.cadernoreceitas.di

import android.app.Application
import androidx.room.Room
import br.com.cadernoreceitas.data.local.AppDatabase
import br.com.cadernoreceitas.data.local.ReceitasDao
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
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "receitas_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDao(db: AppDatabase): ReceitasDao {
        return db.receitasDao()
    }
}