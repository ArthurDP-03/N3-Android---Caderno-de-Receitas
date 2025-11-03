package br.com.cadernoreceitas.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.cadernoreceitas.ui.viewmodel.RecipeDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    viewModel: RecipeDetailViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToEditRecipe: () -> Unit
) {
    val recipe by viewModel.recipe.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(recipe?.name ?: "Receita") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToEditRecipe) {
                        Icon(Icons.Default.Edit, "Editar Receita")
                    }
                }
            )
        }
    ) { padding ->
        recipe?.let {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { Text("Ingredientes:", style = MaterialTheme.typography.headlineSmall) }
                items(it.ingredients) { ingredient ->
                    Text("• $ingredient", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(start = 8.dp))
                }
                item { Text("Passo a passo:", style = MaterialTheme.typography.headlineSmall) }
                items(it.steps.withIndex().toList()) { (index, step) ->
                    Text("${index + 1}. $step", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(start = 8.dp))
                }
            }
        } ?: Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Carregando receita...")
        }
    }
}