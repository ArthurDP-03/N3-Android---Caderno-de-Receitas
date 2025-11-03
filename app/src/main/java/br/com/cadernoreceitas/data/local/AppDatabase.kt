package br.com.cadernoreceitas.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.cadernoreceitas.data.model.Notebook
import br.com.cadernoreceitas.data.model.Recipe

@Database(entities = [Notebook::class, Recipe::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun receitasDao(): ReceitasDao
}