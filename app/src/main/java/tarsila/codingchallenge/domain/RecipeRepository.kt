package tarsila.codingchallenge.domain

interface RecipeRepository {

    suspend fun fetchRecipes(): Result<List<Recipe>>
}