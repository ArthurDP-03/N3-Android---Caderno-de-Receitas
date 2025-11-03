package br.com.cadernoreceitas.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.cadernoreceitas.data.model.Notebook
import br.com.cadernoreceitas.data.model.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface ReceitasDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotebook(notebook: Notebook)

    @Query("SELECT * FROM notebooks ORDER BY name ASC")
    fun getAllNotebooks(): Flow<List<Notebook>>

    @Query("SELECT * FROM notebooks WHERE notebookId = :notebookId")
    fun getNotebookById(notebookId: Long): Flow<Notebook>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe)

    @Query("SELECT * FROM recipes WHERE notebookId = :notebookId ORDER BY name ASC")
    fun getRecipesForNotebook(notebookId: Long): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE recipeId = :recipeId")
    fun getRecipeById(recipeId: Long): Flow<Recipe>
}