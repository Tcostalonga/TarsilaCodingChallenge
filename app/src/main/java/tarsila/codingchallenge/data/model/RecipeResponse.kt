package tarsila.codingchallenge.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RecipeResponse(
    val id: Long,
    val title: String,
    val description: String,
    val numberOfServings: Int = 0,
    val ingredients: List<String>,
    val cookingInstructions: String,
    val dietaryAttributes: List<DietaryAttributeResponse>
)

@Serializable
data class DietaryAttributeResponse(
    val id: Long,
    val name: String,
    val contentColor: Long,
    val backgroundColor: Long
)