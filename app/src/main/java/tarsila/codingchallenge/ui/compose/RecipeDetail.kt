package tarsila.codingchallenge.ui.compose


import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.outlined.RestaurantMenu
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tarsila.codingchallenge.R
import tarsila.codingchallenge.domain.DietaryAttribute
import tarsila.codingchallenge.domain.Recipe

@Composable
fun RecipeDetail(recipe: Recipe, onClick: () -> Unit) {

    val scrollState = rememberScrollState()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 18.dp)
        ) {
            RecipeNameAndDescription(recipe)

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

            Ingredients(recipe)

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

            CookingInstructions(recipe)

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

            ServingsAndDietaryAttributes(recipe, scrollState)
        }
    }
}

@Composable
private fun RecipeNameAndDescription(recipe: Recipe) {
    Text(
        text = recipe.title,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.SemiBold,
    )

    Spacer(Modifier.height(4.dp))

    Text(
        text = recipe.description,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.alpha(0.6f),
    )
}

@Composable
private fun Ingredients(recipe: Recipe) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Icon(
            imageVector = Icons.Outlined.ShoppingCart,
            contentDescription = null
        )
        Text(
            text = stringResource(R.string.ingredients),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
        )
    }

    Spacer(Modifier.height(8.dp))

    recipe.ingredients.forEach { ingredient ->
        Text(
            text = "- $ingredient",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(vertical = 2.dp)
        )
    }
}

@Composable
private fun CookingInstructions(recipe: Recipe) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Icon(
            imageVector = Icons.Outlined.RestaurantMenu,
            contentDescription = null
        )
        Text(
            text = stringResource(R.string.cooking_instructions),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
        )
    }

    Spacer(Modifier.height(8.dp))

    recipe.cookingInstructions.forEachIndexed { index, instruction ->
        Row(
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier.padding(vertical = 2.dp)
        ) {
            Text(
                text = "${index + 1}. ",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
            )

            Text(
                text = instruction,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Justify,
            )
        }
    }
}

@Composable
fun ServingsAndDietaryAttributes(recipe: Recipe, scrollState: ScrollState) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = Icons.Default.People,
            modifier = Modifier.alpha(0.6f),
            contentDescription = null
        )

        Text(
            text = recipe.numberOfServings.toString(),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.alpha(0.6f),
        )

        Row(modifier = Modifier.horizontalScroll(scrollState)) {
            recipe.dietaryAttributes.forEach { dietary ->
                Spacer(Modifier.padding(vertical = 2.dp, horizontal = 4.dp))
                SuggestionChip(
                    shape = RoundedCornerShape(50.dp),
                    onClick = { },
                    label = { Text(dietary.name) },
                    colors = SuggestionChipDefaults.elevatedSuggestionChipColors(
                        labelColor = Color(dietary.contentColor),
                        containerColor = Color(dietary.backgroundColor)
                    ),
                    border = null
                )
            }
        }
    }
}

@Preview
@Composable
private fun RecipeDetailPreview() {
    RecipeDetail(
        Recipe(
            id = 1,
            "Mushroom Risotto",
            "Creamy arborio rice slowly cooked with mushrooms, white wine and parmesan.",
            3,
            listOf(
                "1 cup arborio rice",
                "250 g mushrooms, sliced",
                "1 shallot, finely chopped",
                "2 garlic cloves, minced",
                "1/2 cup dry white wine",
                "1 l hot vegetable broth",
                "2 tbsp butter",
                "1/2 cup grated parmesan",
                "Salt and pepper to taste"
            ),
            listOf(
                "Melt 1 tbsp of butter in a pan and cook the mushrooms until golden, then set aside.",
                "In the same pan, sauté the shallot and garlic.",
                "Add the rice and toast for 2 minutes.",
                "Pour in the wine and stir until absorbed.",
                "Add the broth one ladle at a time, stirring until each addition is absorbed, for about 18 minutes.",
                "Stir in the mushrooms, the remaining butter and the parmesan.",
                "Season and serve immediately."
            ),
            listOf(
                DietaryAttribute(
                    id = 9383, name = "Gluten-free", contentColor = 4294831848, backgroundColor = 4287249178
                )
            )
        ),
        onClick = {}
    )
}
