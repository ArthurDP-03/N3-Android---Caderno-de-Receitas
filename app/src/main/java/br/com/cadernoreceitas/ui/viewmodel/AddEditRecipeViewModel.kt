package br.com.cadernoreceitas.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.cadernoreceitas.data.model.Ingredient
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
                        // ATUALIZADO: Carrega a lista de ingredientes
                        ingredients = recipe.ingredients.ifEmpty { listOf(Ingredient()) },
                        // ATUALIZADO: Junta os passos com quebra de linha
                        steps = recipe.steps.joinToString("\n")
                    )
                }
            }
        }
    }

    fun onNameChange(name: String) = _uiState.update { it.copy(name = name) }
    fun onDescriptionChange(description: String) = _uiState.update { it.copy(description = description) }
    fun onStepsChange(steps: String) = _uiState.update { it.copy(steps = steps) }

    // --- NOVAS Funções para Ingredientes ---

    fun onIngredientChange(index: Int, qty: String, unit: String, name: String) {
        _uiState.update { currentState ->
            val newIngredients = currentState.ingredients.toMutableList()
            newIngredients[index] = Ingredient(qty, unit, name)
            currentState.copy(ingredients = newIngredients)
        }
    }

    fun onAddIngredient() {
        _uiState.update { currentState ->
            val newIngredients = currentState.ingredients.toMutableList().apply {
                add(Ingredient())
            }
            currentState.copy(ingredients = newIngredients)
        }
    }

    fun onRemoveIngredient(index: Int) {
        _uiState.update { currentState ->
            val newIngredients = currentState.ingredients.toMutableList().apply {
                removeAt(index)
            }
            // Garante que sempre haja pelo menos um campo, mesmo que vazio
            if (newIngredients.isEmpty()) {
                newIngredients.add(Ingredient())
            }
            currentState.copy(ingredients = newIngredients)
        }
    }

    // --- Fim das Novas Funções ---

    fun saveRecipe(onSaveFinished: () -> Unit) {
        viewModelScope.launch {
            val currentState = _uiState.value
            val recipe = Recipe(
                recipeId = if (isEditing) recipeId else 0,
                // Corrigido: Busca o notebookId correto ao editar
                notebookId = if (isEditing) {
                    repository.getRecipeById(recipeId).firstOrNull()?.notebookId ?: 0
                } else {
                    notebookId
                },
                name = currentState.name,
                description = currentState.description,
                // ATUALIZADO: Filtra ingredientes vazios
                ingredients = currentState.ingredients.filter { it.name.isNotBlank() },
                // ATUALIZADO: Salva os passos
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
    // ATUALIZADO: O estado agora é uma lista de ingredientes
    val ingredients: List<Ingredient> = listOf(Ingredient()),
    val steps: String = ""
)