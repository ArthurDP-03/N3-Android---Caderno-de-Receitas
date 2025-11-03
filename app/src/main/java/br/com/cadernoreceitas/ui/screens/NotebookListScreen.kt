package br.com.cadernoreceitas.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.cadernoreceitas.data.model.Notebook
import br.com.cadernoreceitas.ui.viewmodel.NotebookListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotebookListScreen(
    viewModel: NotebookListViewModel = hiltViewModel(),
    onNavigateToRecipeList: (Long) -> Unit
) {
    val notebooks by viewModel.notebooks.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Cadernos de receitas") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.addNotebook("Caderno de Massas") // Exemplo
            }) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Caderno")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(notebooks) { notebook ->
                NotebookItem(notebook = notebook, onClick = {
                    onNavigateToRecipeList(notebook.notebookId)
                })
            }
        }
    }
}

@Composable
fun NotebookItem(notebook: Notebook, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(Icons.Default.Book, contentDescription = null)
            Text(text = notebook.name, style = MaterialTheme.typography.titleMedium)
        }
    }
}