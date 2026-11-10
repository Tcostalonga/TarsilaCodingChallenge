package tarsila.codingchallenge.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import tarsila.codingchallenge.ui.compose.RecipeScreen
import tarsila.codingchallenge.ui.theme.TarsilaCodingChallengeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: RecipeViewModel by viewModels()

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