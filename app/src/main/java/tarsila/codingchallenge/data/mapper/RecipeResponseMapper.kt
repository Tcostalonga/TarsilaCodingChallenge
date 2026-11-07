package tarsila.codingchallenge.data.mapper

import tarsila.codingchallenge.data.model.DietaryAttributeResponse
import tarsila.codingchallenge.data.model.RecipeResponse
import tarsila.codingchallenge.domain.DietaryAttribute
import tarsila.codingchallenge.domain.Recipe

fun List<RecipeResponse>.toRecipeList() = map { it.toRecipe() }

fun RecipeResponse.toRecipe() = Recipe(
    id = id,
    title = title,
    description = description,
    numberOfServings = numberOfServings,
    ingredients = ingredients,
    cookingInstructions = cookingInstructions.toCookingInstructionsList(),
    dietaryAttributes = dietaryAttributes.toDietaryAttributeList()
)

fun DietaryAttributeResponse.toDietaryAttribute() = DietaryAttribute(
    id = id,
    name = name,
    contentColor = contentColor,
    backgroundColor = backgroundColor
)

fun List<DietaryAttributeResponse>.toDietaryAttributeList() = map { it.toDietaryAttribute() }

fun List<String>.toCookingInstructionsList() = map { it }
