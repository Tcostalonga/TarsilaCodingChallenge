package tarsila.codingchallenge.domain

data class Recipe(
    val id: Long,
    val title: String,
    val description: String,
    val numberOfServings: Int,
    val ingredients: List<String>,
    val cookingInstructions: List<String>,
    val dietaryAttributes: List<DietaryAttribute>,
    val showRecipeDetail: Boolean = false
)

data class DietaryAttribute(
    val id: Long,
    val name: String,
    val contentColor: Long,
    val backgroundColor: Long
)
