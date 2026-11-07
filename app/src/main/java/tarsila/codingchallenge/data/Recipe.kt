package tarsila.codingchallenge.data

data class Recipe(
    val id: Long,
    val title: String,
    val description: String,
    val numberOfServings: Int,
    val ingredients: List<String>,
    val cookingInstructions: String,
    val dietaryAttributes: List<DietaryAttribute>
)

data class DietaryAttribute(
    val id: Long,
    val name: String,
    val contentColor: Long,
    val backgroundColor: Long
)