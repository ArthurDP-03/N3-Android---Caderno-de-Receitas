package br.com.cadernoreceitas.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.cadernoreceitas.data.model.Notebook
import br.com.cadernoreceitas.data.model.NotebookWithRecipes
import br.com.cadernoreceitas.data.model.Recipe
import br.com.cadernoreceitas.data.repository.ReceitasRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotebookListViewModel @Inject constructor(
    private val repository: ReceitasRepository
) : ViewModel() {

    val notebooksWithRecipes: StateFlow<List<NotebookWithRecipes>> = repository.getNotebooksWithRecipes()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addNotebook(name: String) {
        if (name.isBlank()) return
        viewModelScope.launch {
            repository.insertNotebook(Notebook(name = name))
        }
    }

    // NOVO: Função para atualizar o nome de um caderno
    fun updateNotebookName(notebook: Notebook, newName: String) {
        if (newName.isBlank()) return
        viewModelScope.launch {
            repository.updateNotebook(notebook.copy(name = newName))
        }
    }

    // NOVO: Função para deletar um caderno
    fun deleteNotebook(notebook: Notebook) {
        viewModelScope.launch {
            repository.deleteNotebook(notebook)
        }
    }

    // NOVO: Função para deletar uma receita (usada pela RecipeRow)
    fun deleteRecipe(recipe: Recipe) {
        viewModelScope.launch {
            repository.deleteRecipe(recipe)
        }
    }
}