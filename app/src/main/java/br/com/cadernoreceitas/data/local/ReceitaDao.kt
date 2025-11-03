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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe)

    /**
     * NOVO: Adiciona a função para deletar uma receita.
     */
    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    /**
     * ATUALIZADO: Busca cadernos já com suas respectivas receitas.
     * Isso substitui a necessidade do getAllNotebooks() e getRecipesForNotebook()
     */
    @Transaction
    @Query("SELECT * FROM notebooks ORDER BY name ASC")
    fun getNotebooksWithRecipes(): Flow<List<NotebookWithRecipes>>

    // Esta query ainda é útil para o RecipeDetailViewModel
    @Query("SELECT * FROM recipes WHERE recipeId = :recipeId")
    fun getRecipeById(recipeId: Long): Flow<Recipe>

    // Esta query ainda é útil para o RecipeListViewModel (se você decidir mantê-lo)
    @Query("SELECT * FROM notebooks WHERE notebookId = :notebookId")
    fun getNotebookById(notebookId: Long): Flow<Notebook>

    // Esta query foi substituída por getNotebooksWithRecipes
    // @Query("SELECT * FROM notebooks ORDER BY name ASC")
    // fun getAllNotebooks(): Flow<List<Notebook>>

    // Esta query foi substituída por getNotebooksWithRecipes
    // @Query("SELECT * FROM recipes WHERE notebookId = :notebookId ORDER BY name ASC")
    // fun getRecipesForNotebook(notebookId: Long): Flow<List<Recipe>>
}