package br.com.cadernoreceitas.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.cadernoreceitas.data.model.Recipe
import br.com.cadernoreceitas.data.repository.ReceitasRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    repository: ReceitasRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val recipeId: Long = savedStateHandle.get<Long>("recipeId") ?: 0

    val recipe: StateFlow<Recipe?> = repository.getRecipeById(recipeId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
}