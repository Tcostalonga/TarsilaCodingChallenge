package tarsila.codingchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import tarsila.codingchallenge.data.repository.RecipeRepositoryImpl
import tarsila.codingchallenge.data.service.MockApiService
import tarsila.codingchallenge.ui.RecipeViewModel
import tarsila.codingchallenge.ui.ViewModelFactory
import tarsila.codingchallenge.ui.compose.RecipeScreen
import tarsila.codingchallenge.ui.theme.TarsilaCodingChallengeTheme

class MainActivity : ComponentActivity() {

    private val viewModel: RecipeViewModel by viewModels<RecipeViewModel> {
        val recipeApi = MockApiService(application.applicationContext)
        val repository = RecipeRepositoryImpl(recipeApi)

        ViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TarsilaCodingChallengeTheme {
                RecipeScreen(viewModel)
            }
        }
    }
}