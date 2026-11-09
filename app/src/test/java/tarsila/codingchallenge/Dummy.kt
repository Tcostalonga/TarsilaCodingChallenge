package tarsila.codingchallenge

import tarsila.codingchallenge.data.remote.model.DietaryAttributeResponse
import tarsila.codingchallenge.data.remote.model.RecipeResponse

object Dummy {

    val DUMMY_RECIPE_RESPONSE = RecipeResponse(
        id = 1L,
        title = "Pasta Carbonara",
        description = "Classic Roman pasta dish",
        numberOfServings = 2,
        ingredients = listOf("Spaghetti", "Eggs", "Pecorino", "Guanciale"),
        cookingInstructions = listOf("Boil pasta", "Fry guanciale", "Mix eggs with cheese", "Combine all"),
        dietaryAttributes = listOf(
            DietaryAttributeResponse(
                id = 10L,
                name = "Nut-Free",
                contentColor = 0xFFFFFFFF,
                backgroundColor = 0xFF00FF00,
            ),
        ),
    )

    val DUMMY_FILTERED_RESPONSE = RecipeResponse(
        id = 2L,
        title = "Avocado Toast",
        description = "Simple healthy toast",
        numberOfServings = 1,
        ingredients = listOf("Bread", "Avocado", "Salt", "Lemon juice"),
        cookingInstructions = listOf("Toast bread", "Mash avocado with lemon and salt", "Spread on toast"),
        dietaryAttributes = listOf(
            DietaryAttributeResponse(
                id = 20L,
                name = "Vegan",
                contentColor = 0xFFFFFFFF,
                backgroundColor = 0xFF00AA00,
            ),
        ),
    )

    val RECIPES_LIST = listOf(DUMMY_RECIPE_RESPONSE, DUMMY_FILTERED_RESPONSE)
}