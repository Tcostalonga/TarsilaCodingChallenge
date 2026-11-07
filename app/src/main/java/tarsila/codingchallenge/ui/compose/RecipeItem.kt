package tarsila.codingchallenge.ui.compose

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
import androidx.compose.material3.Card
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tarsila.codingchallenge.data.DietaryAttribute
import tarsila.codingchallenge.data.Recipe

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
                text = recipe.title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.alpha(0.6f),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

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
                    overflow = TextOverflow.Ellipsis
                )

                Row(modifier = Modifier.horizontalScroll(scrollState)) {
                    recipe.dietaryAttributes.forEach { dietary ->
                        Spacer(Modifier.padding(2.dp))
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
    }
}

@Preview
@Composable
private fun RecipeItemPreview() {
    RecipeItem(
        Recipe(
            id = 1,
            "Salada de grão-de-bico",
            "dsfsdfsdf",
            3,
            listOf("vegan"),
            "#D9EAE0",
            listOf(
                DietaryAttribute(
                    id = 9383, name = "Vegan", contentColor = 0xFF005122, backgroundColor = 0xFFEDFFF5

                )
            )
        ),
        onClick = {}
    )
}