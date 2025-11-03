package br.com.cadernoreceitas.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notebooks")
data class Notebook(
    @PrimaryKey(autoGenerate = true)
    val notebookId: Long = 0,
    val name: String
)