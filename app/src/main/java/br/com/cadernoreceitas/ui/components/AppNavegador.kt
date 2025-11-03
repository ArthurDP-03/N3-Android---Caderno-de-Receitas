package br.com.cadernoreceitas.ui.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.cadernoreceitas.data.model.Notebook
import br.com.cadernoreceitas.ui.screens.AddEditRecipeScreen
import br.com.cadernoreceitas.ui.screens.NotebookListScreen
import br.com.cadernoreceitas.ui.screens.RecipeDetailScreen

object Routes {
    const val NOTEBOOK_LIST = "notebookList"
    const val RECIPE_DETAIL = "recipeDetail/{recipeId}"
    fun recipeDetailRoute(recipeId: Long) = "recipeDetail/$recipeId"

    const val ADD_RECIPE = "addRecipe/{notebookId}"
    fun addRecipeRoute(notebookId: Long) = "addRecipe/$notebookId"

    const val EDIT_RECIPE = "editRecipe/{recipeId}"
    fun editRecipeRoute(recipeId: Long) = "editRecipe/$recipeId"
}

@Composable
fun AppNavegador() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.NOTEBOOK_LIST) {
        composable(Routes.NOTEBOOK_LIST) {
            NotebookListScreen(
                onNavigateToRecipeDetail = { recipeId ->
                    navController.navigate(Routes.recipeDetailRoute(recipeId))
                },
                onNavigateToAddRecipe = { notebookId ->
                    navController.navigate(Routes.addRecipeRoute(notebookId))
                },
                // NOVO: Passa a rota de edição para a lista
                onNavigateToEditRecipe = { recipeId ->
                    navController.navigate(Routes.editRecipeRoute(recipeId))
                }
            )
        }

        composable(
            route = Routes.RECIPE_DETAIL,
            arguments = listOf(navArgument("recipeId") { type = NavType.LongType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getLong("recipeId") ?: 0
            RecipeDetailScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToEditRecipe = {
                    navController.navigate(Routes.editRecipeRoute(recipeId))
                }
            )
        }
        composable(
            route = Routes.ADD_RECIPE,
            arguments = listOf(navArgument("notebookId") { type = NavType.LongType })
        ) {
            AddEditRecipeScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(
            route = Routes.EDIT_RECIPE,
            arguments = listOf(navArgument("recipeId") { type = NavType.LongType })
        ) {
            AddEditRecipeScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}