package br.com.cadernoreceitas.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.cadernoreceitas.data.model.Ingredient
import br.com.cadernoreceitas.ui.viewmodel.AddEditRecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditRecipeScreen(
    viewModel: AddEditRecipeViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (uiState.name.isBlank()) "Nova Receita" else "Editar Receita") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.saveRecipe(onSaveFinished = onNavigateBack) }) {
                Icon(Icons.Default.Done, "Salvar")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = uiState.name,
                onValueChange = viewModel::onNameChange,
                label = { Text("Nome da Receita") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = uiState.description,
                onValueChange = viewModel::onDescriptionChange,
                label = { Text("Descrição") },
                modifier = Modifier.fillMaxWidth()
            )

            // --- NOVO Formulário de Ingredientes ---
            Text("Ingredientes", style = MaterialTheme.typography.titleLarge)

            uiState.ingredients.forEachIndexed { index, ingredient ->
                IngredientInputRow(
                    ingredient = ingredient,
                    onValueChange = { qty, unit, name ->
                        viewModel.onIngredientChange(index, qty, unit, name)
                    },
                    onRemove = { viewModel.onRemoveIngredient(index) }
                )
            }

            Button(
                onClick = viewModel::onAddIngredient,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Adicionar Ingrediente")
            }
            // --- Fim do Formulário de Ingredientes ---


            Text("Passo a passo (um por linha)", style = MaterialTheme.typography.titleLarge)
            OutlinedTextField(
                value = uiState.steps,
                onValueChange = viewModel::onStepsChange,
                label = { Text("Ex: 1. Misture os ingredientes") },
                modifier = Modifier.fillMaxWidth().height(200.dp)
            )
        }
    }
}

@Composable
fun IngredientInputRow(
    ingredient: Ingredient,
    onValueChange: (String, String, String) -> Unit,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = ingredient.quantity,
            onValueChange = { onValueChange(it, ingredient.unit, ingredient.name) },
            label = { Text("Qtd.") },
            modifier = Modifier.weight(0.2f)
        )
        OutlinedTextField(
            value = ingredient.unit,
            onValueChange = { onValueChange(ingredient.quantity, it, ingredient.name) },
            label = { Text("Un.") },
            modifier = Modifier.weight(0.2f)
        )
        OutlinedTextField(
            value = ingredient.name,
            onValueChange = { onValueChange(ingredient.quantity, ingredient.unit, it) },
            label = { Text("Ingrediente") },
            modifier = Modifier.weight(0.5f)
        )
        IconButton(onClick = onRemove, modifier = Modifier.weight(0.1f)) {
            Icon(Icons.Default.Delete, contentDescription = "Remover Ingrediente")
        }
    }
}