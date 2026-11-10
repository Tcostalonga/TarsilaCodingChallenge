package tarsila.codingchallenge.ui.compose

import tarsila.codingchallenge.domain.Recipe
import tarsila.codingchallenge.domain.RecipeFilter

sealed interface RecipeIntent {
    data class OnRecipeClick(val recipe: Recipe) : RecipeIntent
    data class OnFilterRecipes(val filter: RecipeFilter) : RecipeIntent
    data object OnTryToFetchRecipes : RecipeIntent
}