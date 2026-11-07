package tarsila.codingchallenge.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tarsila.codingchallenge.domain.DietaryAttribute
import tarsila.codingchallenge.domain.Recipe

@Composable
fun RecipeItem(recipe: Recipe, onClick: () -> Unit) {

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
            Text(
                text = recipe.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = recipe.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.alpha(0.6f),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            ServingsAndDietaryAttributes(recipe, scrollState)
        }
    }
}

@Preview
@Composable
private fun RecipeItemPreview() {
    RecipeItem(
        Recipe(
            id = 1,
            "Mushroom Risotto",
            "Creamy arborio rice slowly cooked with mushrooms, white wine and parmesan.",
            3,
            listOf(
                "    \"1 cup arborio rice\",\n" +
                        "    \"250 g mushrooms, sliced\",\n" +
                        "    \"1 shallot, finely chopped\",\n" +
                        "    \"2 garlic cloves, minced\",\n" +
                        "    \"1/2 cup dry white wine\",\n" +
                        "    \"1 l hot vegetable broth\",\n" +
                        "    \"2 tbsp butter\",\n" +
                        "    \"1/2 cup grated parmesan\",\n" +
                        "    \"Salt and pepper to taste\""
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
