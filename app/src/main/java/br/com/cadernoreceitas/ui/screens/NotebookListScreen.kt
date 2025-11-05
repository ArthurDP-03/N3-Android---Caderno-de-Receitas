package br.com.cadernoreceitas.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.cadernoreceitas.data.model.Notebook
import br.com.cadernoreceitas.data.model.NotebookWithRecipes
import br.com.cadernoreceitas.data.model.Recipe
import br.com.cadernoreceitas.ui.viewmodel.NotebookListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotebookListScreen(
    viewModel: NotebookListViewModel = hiltViewModel(),
    onNavigateToRecipeDetail: (Long) -> Unit,
    onNavigateToAddRecipe: (Long) -> Unit,
    onNavigateToEditRecipe: (Long) -> Unit // NOVO
) {
    val notebooksWithRecipes by viewModel.notebooksWithRecipes.collectAsState()

    // Estados para controlar os diálogos
    var showAddNotebookDialog by remember { mutableStateOf(false) }
    var notebookToEdit by remember { mutableStateOf<Notebook?>(null) }
    var notebookToDelete by remember { mutableStateOf<Notebook?>(null) }
    var recipeToDelete by remember { mutableStateOf<Recipe?>(null) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Cadernos de receitas") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddNotebookDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Caderno")
            }
        }
    ) { padding ->

        // --- Diálogos ---

        // 1. Diálogo para Adicionar Caderno
        if (showAddNotebookDialog) {
            AddOrEditNotebookDialog(
                onDismiss = { showAddNotebookDialog = false },
                onConfirm = {
                    name -> viewModel.addNotebook(name)
                    showAddNotebookDialog = false },
                dialogTitle = "Novo Caderno",
                textFieldLabel = "Nome do Caderno"
            )
        }

        // 2. Diálogo para Editar Caderno
        notebookToEdit?.let { notebook ->
            AddOrEditNotebookDialog(
                onDismiss = { notebookToEdit = null },
                onConfirm = { newName ->
                    viewModel.updateNotebookName(notebook, newName)
                    notebookToEdit = null
                },
                dialogTitle = "Editar Caderno",
                textFieldLabel = "Novo nome",
                initialValue = notebook.name
            )
        }

        // 3. Diálogo para Deletar Caderno
        notebookToDelete?.let { notebook ->
            DeleteConfirmationDialog(
                onDismiss = { notebookToDelete = null },
                onConfirm = { viewModel.deleteNotebook(notebook) },
                title = "Excluir Caderno",
                text = "Tem certeza que deseja excluir o caderno \"${notebook.name}\"? Todas as receitas contidas nele também serão excluídas."
            )
        }

        // 4. Diálogo para Deletar Receita
        recipeToDelete?.let { recipe ->
            DeleteConfirmationDialog(
                onDismiss = { recipeToDelete = null },
                onConfirm = { viewModel.deleteRecipe(recipe) },
                title = "Excluir Receita",
                text = "Tem certeza que deseja excluir a receita \"${recipe.name}\"?"
            )
        }

        // --- Conteúdo da Tela ---

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(notebooksWithRecipes, key = { it.notebook.notebookId }) { item ->
                ExpandableNotebookItem(
                    item = item,
                    onRecipeClick = { recipeId -> onNavigateToRecipeDetail(recipeId) },
                    onAddRecipeClick = { notebookId -> onNavigateToAddRecipe(notebookId) },
                    // Novas ações para Caderno
                    onEditNotebookClick = { notebookToEdit = item.notebook },
                    onDeleteNotebookClick = { notebookToDelete = item.notebook },
                    // Novas ações para Receita
                    onEditRecipeClick = { recipeId -> onNavigateToEditRecipe(recipeId) },
                    onDeleteRecipeClick = { recipe -> recipeToDelete = recipe }
                )
            }
        }
    }
}

@Composable
fun ExpandableNotebookItem(
    item: NotebookWithRecipes,
    onRecipeClick: (Long) -> Unit,
    onAddRecipeClick: (Long) -> Unit,
    onEditNotebookClick: () -> Unit,      // NOVO
    onDeleteNotebookClick: () -> Unit,    // NOVO
    onEditRecipeClick: (Long) -> Unit,    // NOVO
    onDeleteRecipeClick: (Recipe) -> Unit // NOVO
) {
    var expanded by remember { mutableStateOf(false) }
    var showMenu by remember { mutableStateOf(false) }

    Card(modifier = Modifier.fillMaxWidth()) {
        Column {
            // Header do Caderno (com menu de editar/excluir)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Book, contentDescription = null, modifier = Modifier.padding(end = 16.dp))
                Text(
                    text = item.notebook.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                // Ícone para expandir/recolher
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandMore else Icons.Default.MoreVert,
                    contentDescription = if (expanded) "Recolher" else "Opções",
                    modifier = Modifier.clickable(onClick = {
                        if (expanded) {
                            expanded = false
                        } else {
                            showMenu = true
                        }
                    })
                )

                // Menu Dropdown para Editar/Excluir Caderno
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Editar Caderno") },
                        onClick = {
                            onEditNotebookClick()
                            showMenu = false
                        },
                        leadingIcon = { Icon(Icons.Outlined.Edit, null) }
                    )
                    DropdownMenuItem(
                        text = { Text("Excluir Caderno") },
                        onClick = {
                            onDeleteNotebookClick()
                            showMenu = false
                        },
                        leadingIcon = { Icon(Icons.Outlined.Delete, null) }
                    )
                }
            }

            // Lista de Receitas (expansível)
            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Divider()
                    Spacer(modifier = Modifier.height(8.dp))

                    if (item.recipes.isEmpty()) {
                        Text("Nenhuma receita neste caderno.", style = MaterialTheme.typography.bodyMedium)
                    }

                    item.recipes.forEach { recipe ->
                        RecipeRow(
                            recipe = recipe,
                            onClick = { onRecipeClick(recipe.recipeId) },
                            onEditClick = { onEditRecipeClick(recipe.recipeId) }, // NOVO
                            onDeleteClick = { onDeleteRecipeClick(recipe) }     // NOVO
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    TextButton(
                        onClick = { onAddRecipeClick(item.notebook.notebookId) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Outlined.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Adicionar Receita")
                    }
                }
            }
        }
    }
}

@Composable
fun RecipeRow(
    recipe: Recipe,
    onClick: () -> Unit,
    onEditClick: () -> Unit,    // NOVO
    onDeleteClick: () -> Unit // NOVO
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = recipe.name,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        // Botões de Ação para Receita
        IconButton(onClick = onEditClick) {
            Icon(Icons.Default.Edit, contentDescription = "Editar Receita")
        }
        IconButton(onClick = onDeleteClick) {
            Icon(Icons.Default.Delete, contentDescription = "Excluir Receita")
        }
    }
}

// Diálogo genérico para Adicionar ou Editar Caderno
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOrEditNotebookDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
    dialogTitle: String,
    textFieldLabel: String,
    initialValue: String = ""
) {
    var name by remember { mutableStateOf(initialValue) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(dialogTitle) },
        text = {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(textFieldLabel) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(name) },
                enabled = name.isNotBlank()
            ) { Text("Salvar") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}

// Diálogo genérico para Confirmação de Exclusão
@Composable
fun DeleteConfirmationDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    title: String,
    text: String
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = { Text(text) },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm()
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) { Text("Excluir") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}