package tarsila.codingchallenge.data.service

import tarsila.codingchallenge.data.model.RecipeResponse

interface RecipeApi {

    suspend fun fetchRecipes(): List<RecipeResponse>
}

