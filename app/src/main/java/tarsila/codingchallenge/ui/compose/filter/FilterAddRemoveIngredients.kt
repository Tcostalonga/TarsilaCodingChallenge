package tarsila.codingchallenge.ui.compose.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import tarsila.codingchallenge.R
 import tarsila.codingchallenge.ui.theme.TarsilaCodingChallengeTheme

enum class IngredientMode { ADD, REMOVE }

@Composable
fun FilterAddRemoveIngredients(
    mode: IngredientMode,
    ingredients: Set<String>,
    onUpsert: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val textFieldState = rememberTextFieldState()

    val title = if (mode == IngredientMode.ADD) {
        stringResource(R.string.filter_add_ingredients)
    } else {
        stringResource(R.string.filter_remove_ingredients)
    }

    val placeholder = if (mode == IngredientMode.ADD) {
        stringResource(R.string.filter_example_add)
    } else {
        stringResource(R.string.filter_example_remove)
    }

    val chipColor = if (mode == IngredientMode.ADD) {
        MaterialTheme.colorScheme.tertiary
    } else {
        MaterialTheme.colorScheme.error
    }

    fun submitIngredient() {
        val term = textFieldState.text.toString().trim().lowercase()
        if (term.isNotEmpty()) onUpsert(term)
        textFieldState.clearText()
    }

    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        OutlinedTextField(
            state = textFieldState,
            placeholder = { Text(placeholder) },
            lineLimits = TextFieldLineLimits.SingleLine,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            onKeyboardAction = {
                submitIngredient()
            },
            trailingIcon = {
                IconButton(onClick = { submitIngredient() }, enabled = textFieldState.text.isNotBlank()) {
                    Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.filter_ingredients_add_content_description))
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        if (ingredients.isNotEmpty()) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                ingredients.forEach { ingredient ->
                    InputChip(
                        selected = true,
                        onClick = { onUpsert(ingredient) },
                        label = {
                            Text(
                                if (mode == IngredientMode.ADD) {
                                    ingredient
                                } else {
                                    stringResource(R.string.filter_ingredient_chip_no, ingredient)
                                }
                            )
                        },
                        trailingIcon = {
                            Icon(
                                Icons.Filled.Close,
                                contentDescription = stringResource(R.string.filter_ingredients_remove_content_description),
                            )
                        },
                        colors = InputChipDefaults.inputChipColors(
                            selectedContainerColor = chipColor.copy(alpha = 0.12f),
                            selectedLabelColor = chipColor,
                            selectedTrailingIconColor = chipColor
                        )
                    )
                }
            }
        }
    }
}


@PreviewLightDark
@Composable
private fun IngredientSectionsPreview() {
    var add by remember { mutableStateOf(setOf("garlic", "lemon")) }
    var remove by remember { mutableStateOf(setOf("egg")) }

    TarsilaCodingChallengeTheme {
        Surface {
            Column(Modifier.padding(16.dp)) {
                FilterAddRemoveIngredients(
                    mode = IngredientMode.ADD,
                    ingredients = add,
                    onUpsert = { add = add + it },
                )
                Spacer(Modifier.height(24.dp))
                FilterAddRemoveIngredients(
                    mode = IngredientMode.REMOVE,
                    ingredients = remove,
                    onUpsert = { remove = remove - it },
                )
            }
        }
    }
}
