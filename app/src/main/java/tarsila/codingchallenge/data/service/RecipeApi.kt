package tarsila.codingchallenge.data.service

import retrofit2.http.GET
import retrofit2.http.Query
import tarsila.codingchallenge.data.model.RecipeResponse

interface RecipeApi {

    @GET("rest/v1/recipes")
    suspend fun getRecipes(): List<RecipeResponse>

    @GET("rest/v1/recipes/filtered")
    suspend fun getFilteredRecipes(
        @Query("dietaryIds") dietaryIds: Set<Long>? = null,
        @Query("servings") servings: Int? = null,
        @Query("includeIngredients") includeIngredients: Set<String>? = null,
        @Query("excludeIngredients") excludeIngredients: Set<String>? = null,
        @Query("instructionsQuery") instructionsQuery: String? = null,
    ): List<RecipeResponse>
}
