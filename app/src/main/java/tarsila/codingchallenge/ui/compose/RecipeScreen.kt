@file:OptIn(ExperimentalMaterial3Api::class)

package tarsila.codingchallenge.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import tarsila.codingchallenge.R
import tarsila.codingchallenge.domain.DietaryAttribute
import tarsila.codingchallenge.domain.Recipe
import tarsila.codingchallenge.ui.RecipeIntent
import tarsila.codingchallenge.ui.RecipeUiState
import tarsila.codingchallenge.ui.RecipeViewModel

@Composable
fun RecipeScreen(viewModel: RecipeViewModel) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    RecipeScreen(
        uiState = uiState,
        onIntent = { viewModel.onIntent(it) })
}

@Composable
fun RecipeScreen(
    uiState: RecipeUiState,
    onIntent: (RecipeIntent) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                {
                    Text(
                        text = stringResource(R.string.explore_recipes),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }, actions = {
                    IconButton(onClick = {}, content = {
                        Icon(
                            Icons.Default.FilterAlt,
                            contentDescription = stringResource(R.string.filter_recipes_label)
                        )
                    })

                    IconButton(onClick = {}, content = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = stringResource(R.string.search_recipes_label)
                        )
                    })
                }


            )
        }
    ) { paddingValues ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = paddingValues,
        ) {
            items(uiState.recipes, key = { it.id }) { item ->
                if (item.showRecipeDetail) {
                    RecipeDetail(item, onClick = { onIntent(RecipeIntent.OnRecipeClick(item)) })
                } else {
                    RecipeItem(item, onClick = { onIntent(RecipeIntent.OnRecipeClick(item)) })
                }
            }
        }
    }
}

@Preview
@Composable
private fun RecipeScreenPreview() {
    RecipeScreen(
        uiState = RecipeUiState(
            recipes = listOf(
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
                )
            ),
            isSuccess = true,
        ),
        onIntent = {}
    )
}


