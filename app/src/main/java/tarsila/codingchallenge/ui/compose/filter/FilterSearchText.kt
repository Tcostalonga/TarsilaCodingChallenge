package tarsila.codingchallenge.ui.compose.filter

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tarsila.codingchallenge.R
import tarsila.codingchallenge.ui.theme.TarsilaCodingChallengeTheme

@Composable
fun FilterSearchText(
    showSearchField: Boolean,
    searchInstructionsQuery: TextFieldState
) {
    AnimatedVisibility(showSearchField) {
        TextField(
            state = searchInstructionsQuery,
            label = { Text(text = stringResource(R.string.search_recipes_hint)) },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp),
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

@Preview
@Composable
private fun FilterSearchTextPreview() {
    TarsilaCodingChallengeTheme {
        Surface {
            FilterSearchText(true, searchInstructionsQuery = TextFieldState())
        }
    }
}