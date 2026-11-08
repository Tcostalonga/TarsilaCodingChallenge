package tarsila.codingchallenge.ui.compose.filter

data class RecipeFilter(
    val dietaryAttributesIds: Set<Long> = emptySet(),
    val servings: Int? = null,
    val includeIngredients: Set<String> = emptySet(),
    val excludeIngredients: Set<String> = emptySet()
)