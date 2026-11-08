package tarsila.codingchallenge.data.service

import android.content.Context
import android.util.Log
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import tarsila.codingchallenge.R
import tarsila.codingchallenge.data.model.RecipeResponse

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
            simulateNetworkDelay()
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
        dietaryAttributesIds: Set<Long>?,
        servings: Int?,
    ): List<RecipeResponse> {
        return try {
            val allRecipes = getRecipes()
            allRecipes.filter { recipe ->
                val dietaryAttributesSet = recipe.dietaryAttributes.map { it.id }.toSet()

                val matchesDietary = dietaryAttributesIds.isNullOrEmpty() || dietaryAttributesSet.containsAll(dietaryAttributesIds)

                val matchesMinServings = servings == null || recipe.numberOfServings <= servings

                matchesDietary && matchesMinServings
            }
        } catch (e: Exception) {
            Log.e("RecipeServiceImpl", "Error to return remote filtered list of recipes - ${e.message}")
            throw e
        }
    }

    private suspend fun simulateNetworkDelay() = delay(3.seconds)
}