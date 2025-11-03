package br.com.cadernoreceitas.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(uiState.name, viewModel::onNameChange, label = { Text("Nome da Receita") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            OutlinedTextField(uiState.description, viewModel::onDescriptionChange, label = { Text("Descrição") }, modifier = Modifier.fillMaxWidth())
            Text("Ingredientes (um por linha)", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(uiState.ingredients, viewModel::onIngredientsChange, label = { Text("Ex: 1 xícara de farinha") }, modifier = Modifier.fillMaxWidth().height(200.dp))
            Text("Passo a passo (um por linha)", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(uiState.steps, viewModel::onStepsChange, label = { Text("Ex: 1. Misture os ingredientes") }, modifier = Modifier.fillMaxWidth().height(200.dp))
        }
    }
}