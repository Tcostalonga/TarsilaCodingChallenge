package tarsila.codingchallenge.domain

interface RecipeRepository {

    suspend fun getAllRecipes(): Result<List<Recipe>>

    suspend fun getFilteredRecipes(dietaryAttribute: List<DietaryAttribute>, numberOfServings: IntRange): Result<List<Recipe>>
}