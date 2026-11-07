package tarsila.codingchallenge.ui

import tarsila.codingchallenge.domain.Recipe

sealed interface RecipeIntent {
    data class OnRecipeClick(val recipe: Recipe) : RecipeIntent
}