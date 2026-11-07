package tarsila.codingchallenge.data.service

import android.content.Context
import android.util.Log
import kotlinx.serialization.json.Json
import tarsila.codingchallenge.R
import tarsila.codingchallenge.data.model.RecipeResponse

class RecipeServiceApi(private val context: Context) : RecipeApi {
    private val json = Json {
        ignoreUnknownKeys = true
    }

    override suspend fun fetchRecipes(): List<RecipeResponse> {
        return try {
            val jsonString = context.resources.openRawResource(R.raw.recipes)
                .bufferedReader()
                .use {
                    it.readText()
                }

            json.decodeFromString<List<RecipeResponse>>(jsonString)
        } catch (e: Exception) {
            /**
            All kinds of errors will be caught here: SerializationException, FileNotFoundException, IOException...
            Since each of these issues prevent the app from displaying recipes and being usable,
            there is no need to return different error status.
             */
            Log.e("RecipeServiceImpl", "Error to return remote data - ${e.message}")
            throw e
        }
    }
}