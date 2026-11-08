@file:OptIn(ExperimentalMaterial3Api::class)

package tarsila.codingchallenge.ui.compose

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
import tarsila.codingchallenge.ui.compose.filter.RecipeFilterBottomSheet

@Composable
fun RecipeScreen(viewModel: RecipeViewModel) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onIntent(RecipeIntent.OnTryToFetchRecipes)
    }

    RecipeScreen(
        uiState = uiState,
        onIntent = { viewModel.onIntent(it) })
}

@Composable
fun RecipeScreen(
    uiState: RecipeUiState,
    onIntent: (RecipeIntent) -> Unit,
) {
    val searchText = rememberTextFieldState()

    var showFilterBottomSheet by rememberSaveable { mutableStateOf(false) }

    if (showFilterBottomSheet) {
        RecipeFilterBottomSheet(
            onDismissRequest = {
                showFilterBottomSheet = false
            },
            onFilterRecipes = {}
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                {
                    Text(
                        text = stringResource(R.string.explore_recipes),
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                actions = {
                    IconButton(onClick = {
                        showFilterBottomSheet = true
                    }, content = {
                        Icon(
                            Icons.Default.FilterAlt,
                            contentDescription = stringResource(R.string.filter_recipes_label)
                        )
                    })

                    IconButton(onClick = {
                        onIntent(RecipeIntent.ShowSearchField)
                    }, content = {
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
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = paddingValues,
        ) {
            item {
                AnimatedVisibility(uiState.showSearchField) {
                    TextField(
                        state = searchText,
                        label = { Text(text = stringResource(R.string.search_recipes_hint)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
            }
            when {
                uiState.isLoading -> {
                    item {
                        Box(modifier = Modifier.fillParentMaxSize()) {
                            RecipesLoadingState()
                        }
                    }
                }

                uiState.isError -> {
                    item {
                        Box(modifier = Modifier.fillParentMaxSize()) {
                            RecipesErrorState(
                                onRetry = { onIntent(RecipeIntent.OnTryToFetchRecipes) }
                            )
                        }
                    }
                }

                uiState.isSuccess -> {
                    items(uiState.recipes, key = { it.id }) { item ->
                        AnimatedContent(item.showRecipeDetail) { showRecipeDetailState ->
                            if (showRecipeDetailState) {
                                RecipeDetail(item, onClick = { onIntent(RecipeIntent.OnRecipeClick(item)) })
                            } else {
                                RecipeItem(item, onClick = { onIntent(RecipeIntent.OnRecipeClick(item)) })
                            }
                        }
                    }
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
            isSuccess = true,
            isLoading = false,
            isError = false,
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
                ),
                Recipe(
                    id = 5,
                    title = "Chickpea Salad",
                    description = "A fresh and protein-rich salad with chickpeas, cucumber, tomatoes and lemon dressing.",
                    numberOfServings = 2,
                    ingredients = listOf(
                        "1 can chickpeas, drained and rinsed",
                        "1 cucumber, diced",
                        "1 cup cherry tomatoes, halved",
                        "1/4 red onion, thinly sliced",
                        "2 tbsp chopped parsley",
                        "2 tbsp olive oil",
                        "Juice of 1 lemon",
                        "Salt and pepper to taste"
                    ),
                    cookingInstructions = listOf(
                        "Combine the chickpeas, cucumber, tomatoes, red onion and parsley in a large bowl.",
                        "Whisk the olive oil, lemon juice, salt and pepper in a small bowl.",
                        "Pour the dressing over the salad and toss well.",
                        "Let it rest for 10 minutes before serving."
                    ),
                    dietaryAttributes =
                        listOf(
                            DietaryAttribute(21, "Vegan", 4293787637, 4278210850),
                            DietaryAttribute(93, "Lactose-free", 4294243831, 4282336848)
                        ),
                )
            )
        ),
        onIntent = {}
    )
}