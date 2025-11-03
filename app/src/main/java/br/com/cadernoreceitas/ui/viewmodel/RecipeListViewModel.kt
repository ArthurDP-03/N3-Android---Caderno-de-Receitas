package br.com.cadernoreceitas.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.cadernoreceitas.data.model.Notebook
import br.com.cadernoreceitas.data.model.Recipe
import br.com.cadernoreceitas.data.repository.ReceitasRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    repository: ReceitasRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val notebookId: Long = savedStateHandle.get<Long>("notebookId") ?: 0

    val notebook: StateFlow<Notebook?> = repository.getNotebookById(notebookId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val recipes: StateFlow<List<Recipe>> = repository.getRecipesForNotebook(notebookId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}