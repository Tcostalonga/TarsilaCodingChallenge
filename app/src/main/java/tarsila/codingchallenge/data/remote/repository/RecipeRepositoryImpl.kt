package tarsila.codingchallenge.data.remote.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tarsila.codingchallenge.data.remote.mapper.toRecipeList
import tarsila.codingchallenge.data.remote.api.RecipeApi
import tarsila.codingchallenge.domain.Recipe
import tarsila.codingchallenge.domain.RecipeRepository

class RecipeRepositoryImpl(private val recipeApi: RecipeApi) : RecipeRepository {

    override suspend fun getAllRecipes(): Result<List<Recipe>> {
        return withContext(Dispatchers.IO) {
            runCatching {
                recipeApi.getRecipes().toRecipeList()
            }
        }
    }

    override suspend fun getFilteredRecipes(
        dietaryAttributeIds: Set<Long>?,
        servings: Int?,
        includeIngredients: Set<String>?,
        excludeIngredients: Set<String>?,
        instructionsQuery: String?
    ): Result<List<Recipe>> {
        return withContext(Dispatchers.IO) {
            runCatching {
                recipeApi.getFilteredRecipes(
                    dietaryIds = dietaryAttributeIds,
                    servings = servings,
                    includeIngredients = includeIngredients,
                    excludeIngredients = excludeIngredients,
                    instructionsQuery = instructionsQuery,
                ).toRecipeList()
            }
        }
    }
}