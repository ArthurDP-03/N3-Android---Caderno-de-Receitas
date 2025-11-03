package br.com.cadernoreceitas.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.cadernoreceitas.ui.screens.AddEditRecipeScreen
import br.com.cadernoreceitas.ui.screens.NotebookListScreen
import br.com.cadernoreceitas.ui.screens.RecipeDetailScreen
// import br.com.cadernoreceitas.ui.screens.RecipeListScreen // Não é mais necessária

object Routes {
    const val NOTEBOOK_LIST = "notebookList"

    // Rota RECIPE_LIST foi removida, pois a funcionalidade agora está na NOTEBOOK_LIST
    // const val RECIPE_LIST = "recipeList/{notebookId}"
    // fun recipeListRoute(notebookId: Long) = "recipeList/$notebookId"

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
                }
            )
        }

        /*
        // Rota não é mais necessária, pois a lista de receitas
        // agora é exibida na NotebookListScreen.
        composable(
            route = Routes.RECIPE_LIST,
            arguments = listOf(navArgument("notebookId") { type = NavType.LongType })
        ) { backStackEntry ->
            val notebookId = backStackEntry.arguments?.getLong("notebookId") ?: 0
            RecipeListScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToRecipeDetail = { recipeId ->
                    navController.navigate(Routes.recipeDetailRoute(recipeId))
                },
                onNavigateToAddRecipe = {
                    navController.navigate(Routes.addRecipeRoute(notebookId))
                }
            )
        }
        */

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