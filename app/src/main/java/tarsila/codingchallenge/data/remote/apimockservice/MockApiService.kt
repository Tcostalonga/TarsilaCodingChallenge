package tarsila.codingchallenge.data.remote.apimockservice

import android.content.Context
import android.util.Log
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import tarsila.codingchallenge.R
import tarsila.codingchallenge.data.remote.api.RecipeApi
import tarsila.codingchallenge.data.remote.model.RecipeResponse
import tarsila.codingchallenge.data.remote.util.normalizeForSearch

class MockApiService(private val context: Context) : RecipeApi {
    private val json = Json {
        ignoreUnknownKeys = true
    }

    override suspend fun getRecipes(): List<RecipeResponse> {
        return try {
            val jsonString = context.resources.openRawResource(R.raw.recipes)
                .bufferedReader()
                .use {
                    it.readText()
                }

            val result = json.decodeFromString<List<RecipeResponse>>(jsonString)
            simulateNetworkDelay(3.seconds)
            result
        } catch (e: Exception) {
            /**
            All kinds of errors will be caught here: SerializationException, FileNotFoundException, IOException...
            Since each of these issues prevent the app from displaying recipes and being usable,
            there is no need to return different error status.
             */
            Log.e("RecipeServiceImpl", "Error to return remote list of recipes - ${e.message}")
            throw e
        }
    }

    override suspend fun getFilteredRecipes(
        dietaryIds: Set<Long>?,
        servings: Int?,
        includeIngredients: Set<String>?,
        excludeIngredients: Set<String>?,
        instructionsQuery: String?,
    ): List<RecipeResponse> {
        return try {
            val include = includeIngredients.orEmpty().map { it.normalizeForSearch() }
            val exclude = excludeIngredients.orEmpty().map { it.normalizeForSearch() }
            val query = instructionsQuery?.normalizeForSearch().orEmpty()

            val allRecipes = getRecipes()
            allRecipes.filter { recipe ->
                val recipeDietaryIds = recipe.dietaryAttributes.map { it.id }.toSet()
                val ingredients = recipe.ingredients.map { it.normalizeForSearch() }
                val instructions = recipe.cookingInstructions.map { it.normalizeForSearch() }

                val matchesDietary = dietaryIds.isNullOrEmpty() ||
                        recipeDietaryIds.containsAll(dietaryIds)

                val matchesServings = servings == null || recipe.numberOfServings <= servings

                val hasAllIncluded = include.all { term -> ingredients.any { it.contains(term) } }
                val hasAnyExcluded = exclude.any { term -> ingredients.any { it.contains(term) } }

                val matchesInstructions = query.isBlank() || instructions.any { it.contains(query) }

                matchesDietary && matchesServings && hasAllIncluded && !hasAnyExcluded && matchesInstructions
            }
        } catch (e: Exception) {
            Log.e("MockApiService", "Error to return filtered list of recipes - ${e.message}")
            throw e
        }
    }

    private suspend fun simulateNetworkDelay(timeInSeconds: Duration) = delay(timeInSeconds)
}