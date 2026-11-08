package tarsila.codingchallenge.data.service

import retrofit2.http.GET
import retrofit2.http.Query
import tarsila.codingchallenge.data.model.RecipeResponse

interface RecipeApi {

    @GET("rest/v1/recipes")
    suspend fun getRecipes(): List<RecipeResponse>

    @GET("rest/v1/recipes/filtered")
    suspend fun getFilteredRecipes(
        @Query("dietaryAttributesIds") dietaryAttributesIds: Set<Long>? = null,
        @Query("servings") servings: Int? = null,
    ): List<RecipeResponse>
}


