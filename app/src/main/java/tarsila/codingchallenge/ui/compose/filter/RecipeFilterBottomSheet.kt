@file:OptIn(ExperimentalMaterial3Api::class)

package tarsila.codingchallenge.ui.compose.filter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tarsila.codingchallenge.R
import tarsila.codingchallenge.domain.DietaryAttribute
import tarsila.codingchallenge.ui.theme.TarsilaCodingChallengeTheme

val Terracotta = Color(0xFFC96F4A)

@Composable
fun RecipeFilterBottomSheet(
    onDismissRequest: () -> Unit,
    onFilterRecipes: () -> Unit,
) {

    ModalBottomSheet(onDismissRequest = onDismissRequest) {
        RecipeFilterContent(
            listOf(
                DietaryAttribute(21, "Vegan", 4293787637, 4278210850),
                DietaryAttribute(93, "Lactose-free", 4294243831, 4282336848)
            ),
            onFilterRecipes = onFilterRecipes
        )

    }
}

@Composable
private fun RecipeFilterContent(
    dietaryAttributes: List<DietaryAttribute>,
    onFilterRecipes: () -> Unit,
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = stringResource(R.string.filters_label),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        SectionTitle(stringResource(R.string.filter_dietary_preferences))
        FilterDietaryAttributes(
            attributes = dietaryAttributes,
            selected = setOf(),
            onToggle = { name ->
/*                val current = draft.dietaryAttributes
                onDraftChange(
                    draft.copy(
                        dietaryAttributes = if (name in current) current - name else current + name
                    )
                )*/
            }
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

        SectionTitle(stringResource(R.string.filter_servings_label))
        FilterServings(
            min = 0,
            max = 0,
            onChange = { i: Int?, i1: Int? -> }
        )

        Button(
            onClick = onFilterRecipes,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp)
        ) {
            Text(stringResource(R.string.filter_recipes_text_button))
        }

    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Preview
@Composable
private fun RecipesFilterPreview() {
    TarsilaCodingChallengeTheme {
        Surface {
            RecipeFilterContent(
                dietaryAttributes = listOf(
                    DietaryAttribute(21, "Vegan", 4293787637, 4278210850),
                    DietaryAttribute(93, "Lactose-free", 4294243831, 4282336848)
                ),
                onFilterRecipes = {})
        }
    }
}