@file:OptIn(ExperimentalMaterial3Api::class)

package tarsila.codingchallenge.ui.compose.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import tarsila.codingchallenge.R
import tarsila.codingchallenge.domain.DietaryAttribute
import tarsila.codingchallenge.domain.RecipeFilter
import tarsila.codingchallenge.ui.theme.TarsilaCodingChallengeTheme

@Composable
fun RecipeFilterBottomSheet(
    dietaryAttributes: Set<DietaryAttribute>,
    filterDraft: RecipeFilter,
    searchInstructionsQuery: TextFieldState,
    onDismissRequest: () -> Unit,
    onChangeFilter: (RecipeFilter) -> Unit,
    onFilterRecipes: () -> Unit,
    onClearAllFilter: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
    ) {
        RecipeFilterContent(
            dietaryAttributes = dietaryAttributes,
            filterDraft = filterDraft,
            searchInstructionsQuery = searchInstructionsQuery,
            onChangeFilter = onChangeFilter,
            onFilterRecipes = onFilterRecipes,
            onClearAllFilter = onClearAllFilter,
        )
    }
}

@Composable
private fun RecipeFilterContent(
    dietaryAttributes: Set<DietaryAttribute>,
    filterDraft: RecipeFilter,
    searchInstructionsQuery: TextFieldState,
    onChangeFilter: (RecipeFilter) -> Unit,
    onFilterRecipes: () -> Unit,
    onClearAllFilter: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .imePadding()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(bottom = 18.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.filter_label),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                )
                TextButton(onClick = onClearAllFilter) {
                    Text(
                        text = stringResource(R.string.filter_clear_all),
                    )
                }
            }

            SectionTitle(stringResource(R.string.filter_dietary_preferences))
            FilterDietaryAttributes(
                attributes = dietaryAttributes,
                selected = filterDraft.dietaryAttributesIds,
                onToggle = { id ->
                    val currentFilter = filterDraft.dietaryAttributesIds
                    val newDietaryAttributesIds = if (id in currentFilter) {
                        currentFilter - id
                    } else {
                        currentFilter + id
                    }

                    onChangeFilter(filterDraft.copy(dietaryAttributesIds = newDietaryAttributesIds))
                }
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

            SectionTitle(stringResource(R.string.filter_servings_label))
            FilterServings(
                servings = filterDraft.servings,
                onChange = { servings: Int? ->
                    onChangeFilter(filterDraft.copy(servings = servings))
                }
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

            FilterAddRemoveIngredients(
                IngredientMode.ADD,
                filterDraft.includeIngredients,
                onUpsert = { ingredient ->
                    val currentFilter = filterDraft.includeIngredients
                    val newIngredientsSet = if (ingredient in currentFilter) {
                        currentFilter - ingredient
                    } else {
                        currentFilter + ingredient
                    }

                    onChangeFilter(filterDraft.copy(includeIngredients = newIngredientsSet))
                },
            )

            Spacer(Modifier.height(12.dp))

            FilterAddRemoveIngredients(
                IngredientMode.REMOVE,
                filterDraft.excludeIngredients,
                onUpsert = { ingredient ->
                    val currentFilter = filterDraft.excludeIngredients
                    val newIngredientsSet = if (ingredient in currentFilter) {
                        currentFilter - ingredient
                    } else {
                        currentFilter + ingredient
                    }

                    onChangeFilter(filterDraft.copy(excludeIngredients = newIngredientsSet))
                },
            )

            HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 12.dp))

            SectionTitle(stringResource(R.string.search_recipes_label))

            FilterSearchText(searchInstructionsQuery = searchInstructionsQuery)
        }

        Button(
            onClick = onFilterRecipes,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colorScheme.background)
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

@PreviewLightDark
@Composable
private fun RecipesFilterPreview() {
    TarsilaCodingChallengeTheme {
        Surface {
            RecipeFilterContent(
                dietaryAttributes = setOf(
                    DietaryAttribute(21, "Vegan", 4293787637, 4278210850),
                    DietaryAttribute(93, "Lactose-free", 4294243831, 4282336848)
                ),
                filterDraft = RecipeFilter(),
                searchInstructionsQuery = rememberTextFieldState(),
                onChangeFilter = {},
                onFilterRecipes = {},
                onClearAllFilter = {},
            )
        }
    }
}