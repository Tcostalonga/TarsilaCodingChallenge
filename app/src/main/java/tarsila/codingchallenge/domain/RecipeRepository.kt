package tarsila.codingchallenge.domain

interface RecipeRepository {

    suspend fun getAllRecipes(): Result<List<Recipe>>

    suspend fun getFilteredRecipes(
        dietaryAttributeIds: Set<Long>?,
        servings: Int?,
        includeIngredients: Set<String>?,
        excludeIngredients: Set<String>?,
        instructionsQuery: String?
    ): Result<List<Recipe>>
}