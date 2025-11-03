package br.com.cadernoreceitas.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "notebooks")
data class Notebook(
    @PrimaryKey(autoGenerate = true)
    val notebookId: Long = 0,
    val name: String
)

/**
 * Classe de Relação para buscar um Caderno (Notebook)
 * e todas as suas Receitas (Recipes) de uma vez.
 */
data class NotebookWithRecipes(
    @Embedded val notebook: Notebook,
    @Relation(
        parentColumn = "notebookId",
        entityColumn = "notebookId"
    )
    val recipes: List<Recipe>
)