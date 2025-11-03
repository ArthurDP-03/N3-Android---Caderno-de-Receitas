package br.com.cadernoreceitas.data.local

import androidx.room.*
import br.com.cadernoreceitas.data.model.Notebook
import br.com.cadernoreceitas.data.model.NotebookWithRecipes
import br.com.cadernoreceitas.data.model.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface ReceitasDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotebook(notebook: Notebook)

    // NOVO: Adiciona a função para atualizar um caderno
    @Update
    suspend fun updateNotebook(notebook: Notebook)

    // NOVO: Adiciona a função para deletar um caderno
    // A exclusão em cascata (onDelete = ForeignKey.CASCADE)
    // na entidade Recipe garantirá que as receitas filhas sejam excluídas.
    @Delete
    suspend fun deleteNotebook(notebook: Notebook)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    @Transaction
    @Query("SELECT * FROM notebooks ORDER BY name ASC")
    fun getNotebooksWithRecipes(): Flow<List<NotebookWithRecipes>>

    @Query("SELECT * FROM recipes WHERE recipeId = :recipeId")
    fun getRecipeById(recipeId: Long): Flow<Recipe>

    @Query("SELECT * FROM notebooks WHERE notebookId = :notebookId")
    fun getNotebookById(notebookId: Long): Flow<Notebook>
}