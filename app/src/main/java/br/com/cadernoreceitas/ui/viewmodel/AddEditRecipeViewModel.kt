package br.com.cadernoreceitas.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.cadernoreceitas.data.model.Recipe
import br.com.cadernoreceitas.data.repository.ReceitasRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditRecipeViewModel @Inject constructor(
    private val repository: ReceitasRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val notebookId: Long = savedStateHandle.get<Long>("notebookId") ?: 0
    private val recipeId: Long = savedStateHandle.get<Long>("recipeId") ?: 0
    private val isEditing = recipeId != 0L

    private val _uiState = MutableStateFlow(AddEditRecipeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        if (isEditing) loadRecipe()
    }

    private fun loadRecipe() {
        viewModelScope.launch {
            repository.getRecipeById(recipeId).firstOrNull()?.let { recipe ->
                _uiState.update {
                    it.copy(
                        name = recipe.name,
                        description = recipe.description,
                        ingredients = recipe.ingredients.joinToString("\n"),
                        steps = recipe.steps.joinToString("\n")
                    )
                }
            }
        }
    }

    fun onNameChange(name: String) = _uiState.update { it.copy(name = name) }
    fun onDescriptionChange(description: String) = _uiState.update { it.copy(description = description) }
    fun onIngredientsChange(ingredients: String) = _uiState.update { it.copy(ingredients = ingredients) }
    fun onStepsChange(steps: String) = _uiState.update { it.copy(steps = steps) }

    fun saveRecipe(onSaveFinished: () -> Unit) {
        viewModelScope.launch {
            val currentState = _uiState.value
            val recipe = Recipe(
                recipeId = if (isEditing) recipeId else 0,
                notebookId = if (isEditing) repository.getRecipeById(recipeId).firstOrNull()?.notebookId ?: 0 else notebookId,
                name = currentState.name,
                description = currentState.description,
                ingredients = currentState.ingredients.split("\n").filter { it.isNotBlank() },
                steps = currentState.steps.split("\n").filter { it.isNotBlank() }
            )
            repository.insertRecipe(recipe)
            onSaveFinished()
        }
    }
}

data class AddEditRecipeUiState(
    val name: String = "",
    val description: String = "",
    val ingredients: String = "",
    val steps: String = ""
)