package tarsila.codingchallenge.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tarsila.codingchallenge.data.mapper.toRecipeList
import tarsila.codingchallenge.data.service.RecipeApi
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
    ): Result<List<Recipe>> {
        return withContext(Dispatchers.IO) {
            runCatching {
                recipeApi.getFilteredRecipes(
                    dietaryAttributeIds,
                    servings
                ).toRecipeList()
            }
        }
    }
}