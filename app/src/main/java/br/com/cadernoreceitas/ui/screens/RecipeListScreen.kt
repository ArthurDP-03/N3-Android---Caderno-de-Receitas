package br.com.cadernoreceitas.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.cadernoreceitas.data.model.Recipe
import br.com.cadernoreceitas.ui.viewmodel.RecipeListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListScreen(
    viewModel: RecipeListViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToRecipeDetail: (Long) -> Unit,
    onNavigateToAddRecipe: () -> Unit
) {
    val recipes by viewModel.recipes.collectAsState()
    val notebook by viewModel.notebook.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(notebook?.name ?: "Receitas") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAddRecipe) {
                Icon(Icons.Default.Add, "Adicionar Receita")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    "Qual será o prato de hoje?",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            items(recipes) { recipe ->
                RecipeItem(recipe = recipe, onClick = {
                    onNavigateToRecipeDetail(recipe.recipeId)
                })
            }
        }
    }
}

@Composable
fun RecipeItem(recipe: Recipe, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(recipe.name, style = MaterialTheme.typography.titleLarge)
            Text(recipe.description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}