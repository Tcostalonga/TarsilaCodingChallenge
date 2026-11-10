package tarsila.codingchallenge.ui.compose.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tarsila.codingchallenge.R
import tarsila.codingchallenge.ui.theme.TarsilaCodingChallengeTheme

private const val SERVINGS_MAX_DIGITS = 2

@Composable
fun FilterServings(
    servings: Int?,
    onChange: (servings: Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ServingsField(
                value = servings,
                onValueChange = onChange,
                imeAction = ImeAction.Done,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun ServingsField(
    value: Int?,
    onValueChange: (Int?) -> Unit,
    imeAction: ImeAction,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value?.toString().orEmpty(),
        onValueChange = { text ->
            val digits = text.filter(Char::isDigit).take(SERVINGS_MAX_DIGITS)
            onValueChange(digits.toIntOrNull())
        },
        placeholder = { Text(stringResource(R.string.any)) },
        suffix = { Text(stringResource(R.string.filter_servings_label)) },
        singleLine = true,
        supportingText = {
            Text(
                text = stringResource(R.string.filter_servings_valid_rule),
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = imeAction
        ),
        modifier = modifier
    )
}

@Preview
@Composable
private fun FilterServingsPreview() {
    TarsilaCodingChallengeTheme {
        Surface {
            FilterServings(
                servings = 4,
                onChange = { },
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}