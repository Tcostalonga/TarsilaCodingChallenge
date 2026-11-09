package tarsila.codingchallenge.data

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.io.IOException
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import tarsila.codingchallenge.Dummy
import tarsila.codingchallenge.data.remote.api.RecipeApi
import tarsila.codingchallenge.data.remote.repository.RecipeRepositoryImpl

class RecipeRepositoryImplTest {

    private val recipeApi: RecipeApi = mockk()
    private val repository: RecipeRepositoryImpl by lazy {
        RecipeRepositoryImpl(recipeApi)
    }

    @Test
    fun `getAllRecipes returns mapped recipe list on success`() = runTest {
        val remoteResponses = listOf(Dummy.DUMMY_RECIPE_RESPONSE)

        coEvery { recipeApi.getRecipes() } returns remoteResponses

        val result = repository.getAllRecipes()

        assertTrue(result.isSuccess)
        val recipes = result.getOrNull()
        assertNotNull(recipes)
        assertEquals(1, recipes?.size)

        val recipe = recipes!!.first()
        assertEquals(1L, recipe.id)
        assertEquals("Pasta Carbonara", recipe.title)
        assertEquals("Classic Roman pasta dish", recipe.description)

        val dietary = recipe.dietaryAttributes.first()
        assertEquals(10L, dietary.id)
        assertEquals("Nut-Free", dietary.name)

        coVerify(exactly = 1) { recipeApi.getRecipes() }
    }

    @Test
    fun `getAllRecipes returns empty list when api returns empty list`() = runTest {
        coEvery { recipeApi.getRecipes() } returns emptyList()

        val result = repository.getAllRecipes()

        assertTrue(result.isSuccess)
        assertEquals(emptyList<Any>(), result.getOrNull())
        coVerify(exactly = 1) { recipeApi.getRecipes() }
    }

    @Test
    fun `getAllRecipes returns failure when api throws exception`() = runTest {
        val expectedException = IOException("Network connection timed out")
        coEvery { recipeApi.getRecipes() } throws expectedException

        val result = repository.getAllRecipes()

        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())
        coVerify(exactly = 1) { recipeApi.getRecipes() }
    }

    @Test
    fun `getFilteredRecipes forwards parameters to api and returns mapped list on success`() = runTest {
        val filteredResponses = listOf(Dummy.DUMMY_FILTERED_RESPONSE)

        val dietaryIds = setOf(20L)
        val servings = 2
        val include = setOf("avocado")
        val exclude = setOf("bacon")
        val instructionsQuery = "toast"

        coEvery {
            recipeApi.getFilteredRecipes(
                dietaryIds = any(),
                servings = any(),
                includeIngredients = any(),
                excludeIngredients = any(),
                instructionsQuery = any(),
            )
        } returns filteredResponses

        val result = repository.getFilteredRecipes(
            dietaryAttributeIds = dietaryIds,
            servings = servings,
            includeIngredients = include,
            excludeIngredients = exclude,
            instructionsQuery = instructionsQuery,
        )

        assertTrue(result.isSuccess)
        val recipes = result.getOrNull()
        assertNotNull(recipes)
        assertEquals(1, recipes?.size)
        assertEquals(2L, recipes?.first()?.id)
        assertEquals("Avocado Toast", recipes?.first()?.title)

        coVerify(exactly = 1) {
            recipeApi.getFilteredRecipes(
                dietaryIds = dietaryIds,
                servings = servings,
                includeIngredients = include,
                excludeIngredients = exclude,
                instructionsQuery = instructionsQuery,
            )
        }
    }

    @Test
    fun `getFilteredRecipes handles null and empty filter parameters correctly`() = runTest {
        coEvery {
            recipeApi.getFilteredRecipes(
                dietaryIds = any(),
                servings = any(),
                includeIngredients = any(),
                excludeIngredients = any(),
                instructionsQuery = any(),
            )
        } returns emptyList()

        val result = repository.getFilteredRecipes(
            dietaryAttributeIds = null,
            servings = null,
            includeIngredients = null,
            excludeIngredients = null,
            instructionsQuery = null,
        )

        assertTrue(result.isSuccess)
        assertEquals(0, result.getOrNull()?.size)

        coVerify(exactly = 1) {
            recipeApi.getFilteredRecipes(
                dietaryIds = null,
                servings = null,
                includeIngredients = null,
                excludeIngredients = null,
                instructionsQuery = null,
            )
        }
    }

    @Test
    fun `getFilteredRecipes returns failure when api throws exception`() = runTest {
        val expectedException = RuntimeException("Server error 500")
        coEvery {
            recipeApi.getFilteredRecipes(
                dietaryIds = any(),
                servings = any(),
                includeIngredients = any(),
                excludeIngredients = any(),
                instructionsQuery = any(),
            )
        } throws expectedException

        val result = repository.getFilteredRecipes(
            dietaryAttributeIds = setOf(1L),
            servings = 4,
            includeIngredients = emptySet(),
            excludeIngredients = emptySet(),
            instructionsQuery = "",
        )

        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())

        coVerify(exactly = 1) {
            recipeApi.getFilteredRecipes(
                dietaryIds = setOf(1L),
                servings = 4,
                includeIngredients = emptySet(),
                excludeIngredients = emptySet(),
                instructionsQuery = "",
            )
        }
    }
}
