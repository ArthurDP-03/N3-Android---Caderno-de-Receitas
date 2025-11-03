package br.com.cadernoreceitas.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "recipes",
    foreignKeys = [
        ForeignKey(
            entity = Notebook::class,
            parentColumns = ["notebookId"],
            childColumns = ["notebookId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val recipeId: Long = 0,
    val notebookId: Long,
    val name: String,
    val description: String,
    // ATUALIZAÇÃO: Mudou de List<String> para List<Ingredient>
    val ingredients: List<Ingredient>,
    val steps: List<String>
)