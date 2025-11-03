package br.com.cadernoreceitas.data.repository

import br.com.cadernoreceitas.data.local.ReceitasDao
import br.com.cadernoreceitas.data.model.Notebook
import br.com.cadernoreceitas.data.model.Recipe
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReceitasRepository @Inject constructor(private val dao: ReceitasDao) {

    fun getAllNotebooks(): Flow<List<Notebook>> = dao.getAllNotebooks()
    fun getNotebookById(notebookId: Long): Flow<Notebook> = dao.getNotebookById(notebookId)
    suspend fun insertNotebook(notebook: Notebook) = dao.insertNotebook(notebook)

    fun getRecipesForNotebook(notebookId: Long): Flow<List<Recipe>> = dao.getRecipesForNotebook(notebookId)
    fun getRecipeById(recipeId: Long): Flow<Recipe> = dao.getRecipeById(recipeId)
    suspend fun insertRecipe(recipe: Recipe) = dao.insertRecipe(recipe)
}