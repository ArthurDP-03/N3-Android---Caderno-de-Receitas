package br.com.cadernoreceitas.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.cadernoreceitas.data.model.Recipe
import br.com.cadernoreceitas.data.repository.ReceitasRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val repository: ReceitasRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val recipeId: Long = savedStateHandle.get<Long>("recipeId") ?: 0

    val recipe: StateFlow<Recipe?> = repository.getRecipeById(recipeId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    /**
     * Função para deletar a receita atual.
     */
    fun deleteRecipe(onDeleteFinished: () -> Unit) {
        viewModelScope.launch {
            val currentRecipe = recipe.firstOrNull()
            if (currentRecipe != null) {
                repository.deleteRecipe(currentRecipe)
                onDeleteFinished()
            }
        }
    }
}