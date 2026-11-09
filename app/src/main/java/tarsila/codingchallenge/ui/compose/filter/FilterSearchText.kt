package tarsila.codingchallenge.ui.compose.filter

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.setTextAndSelectAll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import tarsila.codingchallenge.R
import tarsila.codingchallenge.ui.RecipeUiState

@Composable
fun SearchQuery(
    uiState: RecipeUiState,
    searchInstructionsQuery: TextFieldState
) {
    AnimatedVisibility(uiState.showSearchField) {
        TextField(
            state = searchInstructionsQuery,
            label = { Text(text = stringResource(R.string.search_recipes_hint)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 24.dp)
                .background(MaterialTheme.colorScheme.background),
            lineLimits = TextFieldLineLimits.SingleLine,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            trailingIcon = {
                if (searchInstructionsQuery.text.isNotEmpty()) {
                    IconButton(onClick = { searchInstructionsQuery.clearText() }) {
                        Icon(Icons.Filled.Close, contentDescription = null)
                    }
                }
            },
        )
    }
}
