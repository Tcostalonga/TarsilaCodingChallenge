package tarsila.codingchallenge.domain

interface RecipeRepository {

    suspend fun getAllRecipes(): Result<List<Recipe>>

    suspend fun getFilteredRecipes(
        dietaryAttributeIds: Set<Long>?,
        servings: Int?,
    ): Result<List<Recipe>>
}