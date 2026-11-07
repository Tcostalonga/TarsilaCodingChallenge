package tarsila.codingchallenge.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tarsila.codingchallenge.R
import tarsila.codingchallenge.data.DietaryAttribute
import tarsila.codingchallenge.data.Recipe

//fun RecipeScreen()

val mockedList = listOf(
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
    Recipe(
        id = 3,
        "Salada de grão-de-bico",
        "dsfsdfsdf",
        3,
        listOf("vegan"),
        "#D9EAE0",
        listOf(
            DietaryAttribute(
                id = 9383, name = "Vegan", contentColor = 0xFF009122, backgroundColor = 0xFFEDFFF5

            )
        )
    )
)

@Composable
fun RecipeScreen() {
    Scaffold() { paddingValues ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = paddingValues,
            ) {

            stickyHeader {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = stringResource(R.string.explore_recipes),
                        style = MaterialTheme.typography.headlineMedium
                    )

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
            }


            items(mockedList, key = { it.id }) { item ->
                RecipeItem(item, onClick = {})
            }

        }
    }
}

@Preview
@Composable
private fun RecipeScreenPreview() {
    RecipeScreen()
}


