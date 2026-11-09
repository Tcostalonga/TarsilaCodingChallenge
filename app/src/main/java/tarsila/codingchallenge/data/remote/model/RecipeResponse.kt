package tarsila.codingchallenge.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class RecipeResponse(
    val id: Long,
    val title: String,
    val description: String,
    val numberOfServings: Int,
    val ingredients: List<String>,
    val cookingInstructions: List<String>,
    val dietaryAttributes: List<DietaryAttributeResponse>
)

@Serializable
data class DietaryAttributeResponse(
    val id: Long,
    val name: String,
    val contentColor: Long,
    val backgroundColor: Long
)