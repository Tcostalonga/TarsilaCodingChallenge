package tarsila.codingchallenge.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tarsila.codingchallenge.domain.DietaryAttribute
import tarsila.codingchallenge.domain.Recipe
import tarsila.codingchallenge.domain.RecipeFilter
import tarsila.codingchallenge.domain.RecipeRepository
import tarsila.codingchallenge.ui.compose.RecipeIntent
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(private val recipeRepository: RecipeRepository) : ViewModel() {

    private var _uiState = MutableStateFlow(RecipeUiState())
    val uiState = _uiState.asStateFlow()

    private var lastAppliedFilter = RecipeFilter()

    private var searchJob: Job? = null

    init {
        getRecipes()
    }

    fun onIntent(intent: RecipeIntent) {
        when (intent) {
            is RecipeIntent.OnRecipeClick -> updateRecipeDetailVisibility(intent.recipe)
            RecipeIntent.OnTryToFetchRecipes -> getRecipes()
            RecipeIntent.ShowSearchField -> updateSearchFieldVisibility()
            is RecipeIntent.OnFilterRecipes -> getFilteredRecipes(intent.filter)
        }
    }

    private fun updateRecipeDetailVisibility(recipe: Recipe) {
        val newRecipeList = _uiState.value.recipes.map { item ->
            if (recipe.id == item.id) {
                item.copy(showRecipeDetail = !item.showRecipeDetail)
            } else {
                item
            }
        }

        _uiState.update { it.copy(recipes = newRecipeList) }
    }

    private fun getRecipes() {
        viewModelScope.launch {
            _uiState.update { it.loading() }

            recipeRepository.getAllRecipes()
                .onSuccess { recipes ->
                    val dietaryAttributeList = recipes.allDietaryAttributes()

                    _uiState.update {
                        it.success(recipes).copy(dietaryAttributeList = dietaryAttributeList)
                    }
                }
                .onFailure {
                    _uiState.update { it.error() }
                }
        }
    }

    private fun updateSearchFieldVisibility() {
        _uiState.update {
            it.copy(showSearchField = !it.showSearchField)
        }
    }

    private fun getFilteredRecipes(filter: RecipeFilter) {
        if (lastAppliedFilter == filter) return

        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            _uiState.update { it.loading() }

            lastAppliedFilter = filter
            recipeRepository.getFilteredRecipes(
                dietaryAttributeIds = filter.dietaryAttributesIds,
                servings = filter.servings,
                includeIngredients = filter.includeIngredients,
                excludeIngredients = filter.excludeIngredients,
                instructionsQuery = filter.instructionsQuery,
            )
                .onSuccess { recipes ->
                    _uiState.update {
                        it.success(recipes)
                    }
                }
                .onFailure {
                    _uiState.update { it.error() }
                }
        }
    }

    private fun List<Recipe>.allDietaryAttributes(): Set<DietaryAttribute> =
        flatMap { it.dietaryAttributes }
            .distinctBy { it.name }
            .sortedBy { it.name }
            .toSet()
}

data class RecipeUiState(
    val recipes: List<Recipe> = emptyList(),
    val dietaryAttributeList: Set<DietaryAttribute> = setOf(),
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val showSearchField: Boolean = false
) {
    fun success(recipes: List<Recipe>) = copy(recipes = recipes, isSuccess = true, isLoading = false, isError = false)
    fun loading() = copy(isSuccess = false, isLoading = true, isError = false)
    fun error() = copy(isSuccess = false, isLoading = false, isError = true)

}