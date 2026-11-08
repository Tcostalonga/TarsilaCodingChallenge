package tarsila.codingchallenge.ui.compose.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    min: Int?,
    max: Int?,
    onChange: (min: Int?, max: Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    val isInvalid = min != null && max != null && min > max

    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ServingsField(
                label = stringResource(R.string.min),
                value = min,
                onValueChange = { onChange(it, max) },
                isError = isInvalid,
                imeAction = ImeAction.Next,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = stringResource(R.string.to),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            ServingsField(
                label = stringResource(R.string.max),
                value = max,
                onValueChange = { onChange(min, it) },
                isError = isInvalid,
                imeAction = ImeAction.Done,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(6.dp))

        Text(
            text = if (isInvalid) {
                stringResource(R.string.filter_servings_invalid_rule)
            } else {
                stringResource(R.string.filter_servings_valid_rule)
            },
            style = MaterialTheme.typography.bodySmall,
            color = if (isInvalid) {
                MaterialTheme.colorScheme.error
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            },
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

@Composable
private fun ServingsField(
    label: String,
    value: Int?,
    onValueChange: (Int?) -> Unit,
    isError: Boolean,
    imeAction: ImeAction,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value?.toString().orEmpty(),
        onValueChange = { text ->
            val digits = text.filter(Char::isDigit).take(SERVINGS_MAX_DIGITS)
            onValueChange(digits.toIntOrNull())
        },
        label = { Text(label) },
        placeholder = { Text(stringResource(R.string.any)) },
        suffix = { Text(stringResource(R.string.filter_servings_label)) },
        singleLine = true,
        isError = isError,
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
    var min by remember { mutableStateOf<Int?>(null) }
    var max by remember { mutableStateOf<Int?>(null) }
    TarsilaCodingChallengeTheme {
        Surface {
            FilterServings(
                min = min,
                max = max,
                onChange = { newMin, newMax -> min = newMin; max = newMax },
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}