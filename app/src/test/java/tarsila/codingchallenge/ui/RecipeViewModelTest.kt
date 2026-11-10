@file:OptIn(ExperimentalCoroutinesApi::class)

package tarsila.codingchallenge.ui

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import tarsila.codingchallenge.Dummy
import tarsila.codingchallenge.data.remote.mapper.toRecipeList
import tarsila.codingchallenge.domain.RecipeFilter
import tarsila.codingchallenge.domain.RecipeRepository
import tarsila.codingchallenge.ui.compose.RecipeIntent

class RecipeViewModelTest {

    private val recipeRepository: RecipeRepository = mockk()
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
    private val viewModel: RecipeViewModel by lazy {
        RecipeViewModel(recipeRepository)
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        coEvery { recipeRepository.getAllRecipes() } returns Result.success(emptyList())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `OnTryToFetchRecipes emits success with recipes`() = runTest {
        val recipes = Dummy.RECIPES_LIST.toRecipeList()
        coEvery { recipeRepository.getAllRecipes() } returns Result.success(recipes)

        viewModel.onIntent(RecipeIntent.OnTryToFetchRecipes)

        viewModel.uiState.test {

            val successState = awaitItem()
            assertFalse(successState.isLoading)
            assertTrue(successState.isSuccess)
            assertFalse(successState.isError)
            assertEquals(recipes, successState.recipes)
        }
    }

    @Test
    fun `OnTryToFetchRecipes emits error on repository failure`() = runTest {
        coEvery { recipeRepository.getAllRecipes() } returns Result.failure(RuntimeException("Network error"))

        viewModel.onIntent(RecipeIntent.OnTryToFetchRecipes)

        viewModel.uiState.test {
            val errorState = awaitItem()
            assertFalse(errorState.isLoading)
            assertFalse(errorState.isSuccess)
            assertTrue(errorState.isError)
        }
    }

    @Test
    fun `OnRecipeClick toggles showRecipeDetail for the clicked recipe`() = runTest {
        val recipes = Dummy.RECIPES_LIST.toRecipeList()
        coEvery { recipeRepository.getAllRecipes() } returns Result.success(recipes)

        viewModel.onIntent(RecipeIntent.OnTryToFetchRecipes)

        viewModel.uiState.test {
            assertEquals(recipes, awaitItem().recipes)

            viewModel.onIntent(RecipeIntent.OnRecipeClick(recipes.first()))

            val detailOpenedState = awaitItem()
            assertTrue(detailOpenedState.recipes.first { it.id == recipes.first().id }.showRecipeDetail)
        }
    }

    @Test
    fun `OnFilterRecipes emits success with filtered recipes`() = runTest {
        val filter = RecipeFilter(
            dietaryAttributesIds = setOf(3L),
            servings = 1,
            includeIngredients = setOf("Avocado"),
            excludeIngredients = emptySet(),
            instructionsQuery = "Toast",
        )
        val filteredRecipes = listOf(Dummy.DUMMY_RECIPE_RESPONSE).toRecipeList()

        coEvery {
            recipeRepository.getFilteredRecipes(
                dietaryAttributeIds = any(),
                servings = any(),
                includeIngredients = any(),
                excludeIngredients = any(),
                instructionsQuery = any(),
            )
        } returns Result.success(filteredRecipes)

        viewModel.onIntent(RecipeIntent.OnFilterRecipes(filter))

        viewModel.uiState.test {
            val successState = awaitItem()
            assertFalse(successState.isLoading)
            assertTrue(successState.isSuccess)
            assertEquals(filteredRecipes, successState.recipes)
        }
    }

    @Test
    fun `OnFilterRecipes emits error on repository failure`() = runTest {
        val filter = RecipeFilter(instructionsQuery = "Unknown")
        coEvery {
            recipeRepository.getFilteredRecipes(any(), any(), any(), any(), any())
        } returns Result.failure(RuntimeException("Filter failed"))

        viewModel.onIntent(RecipeIntent.OnFilterRecipes(filter))

        viewModel.uiState.test {
            val errorState = awaitItem()
            assertFalse(errorState.isLoading)
            assertFalse(errorState.isSuccess)
            assertTrue(errorState.isError)
        }
    }
}
