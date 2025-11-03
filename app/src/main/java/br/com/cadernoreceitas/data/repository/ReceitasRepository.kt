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

    // ATUALIZADO: Nova função para a tela principal
    fun getNotebooksWithRecipes(): Flow<List<NotebookWithRecipes>> = dao.getNotebooksWithRecipes()

    fun getNotebookById(notebookId: Long): Flow<Notebook> = dao.getNotebookById(notebookId)
    suspend fun insertNotebook(notebook: Notebook) = dao.insertNotebook(notebook)

    fun getRecipeById(recipeId: Long): Flow<Recipe> = dao.getRecipeById(recipeId)
    suspend fun insertRecipe(recipe: Recipe) = dao.insertRecipe(recipe)

    // NOVO: Função para deletar receita
    suspend fun deleteRecipe(recipe: Recipe) = dao.deleteRecipe(recipe)

    // Funções antigas que não são mais necessárias para a nova tela principal
    // fun getAllNotebooks(): Flow<List<Notebook>> = dao.getAllNotebooks()
    // fun getRecipesForNotebook(notebookId: Long): Flow<List<Recipe>> = dao.getRecipesForNotebook(notebookId)
}