package br.com.cadernoreceitas.data.repository

import br.com.cadernoreceitas.data.local.ReceitasDao
import br.com.cadernoreceitas.data.model.Notebook
import br.com.cadernoreceitas.data.model.NotebookWithRecipes
import br.com.cadernoreceitas.data.model.Recipe
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReceitasRepository @Inject constructor(private val dao: ReceitasDao) {

    fun getNotebooksWithRecipes(): Flow<List<NotebookWithRecipes>> = dao.getNotebooksWithRecipes()

    fun getNotebookById(notebookId: Long): Flow<Notebook> = dao.getNotebookById(notebookId)
    suspend fun insertNotebook(notebook: Notebook) = dao.insertNotebook(notebook)

    // NOVO: Função para atualizar caderno
    suspend fun updateNotebook(notebook: Notebook) = dao.updateNotebook(notebook)

    // NOVO: Função para deletar caderno
    suspend fun deleteNotebook(notebook: Notebook) = dao.deleteNotebook(notebook)


    fun getRecipeById(recipeId: Long): Flow<Recipe> = dao.getRecipeById(recipeId)
    suspend fun insertRecipe(recipe: Recipe) = dao.insertRecipe(recipe)

    suspend fun deleteRecipe(recipe: Recipe) = dao.deleteRecipe(recipe)
}