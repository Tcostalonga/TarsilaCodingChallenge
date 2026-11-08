package tarsila.codingchallenge.ui.compose.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tarsila.codingchallenge.domain.DietaryAttribute
import tarsila.codingchallenge.ui.theme.TarsilaCodingChallengeTheme

@Composable
fun FilterDietaryAttributes(
    attributes: Set<DietaryAttribute>,
    selected: Set<Long>,
    onToggle: (id: Long) -> Unit
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        attributes.forEach { attribute ->
            val isSelected = attribute.id in selected
            FilterChip(
                selected = isSelected,
                onClick = { onToggle(attribute.id) },
                label = { Text(attribute.name) },
                leadingIcon = if (isSelected) {
                    { Icon(Icons.Filled.Check, contentDescription = null) }
                } else null,
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(attribute.backgroundColor),
                    selectedLabelColor = Color(attribute.contentColor),
                    selectedLeadingIconColor = Color(attribute.contentColor)
                ),
                border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = isSelected,
                    selectedBorderColor = Color(attribute.contentColor)
                )
            )
        }
    }
}

@Preview
@Composable
private fun FilterDietaryAttributesPreview() {
    TarsilaCodingChallengeTheme {
        Surface {
            FilterDietaryAttributes(
                attributes = setOf(
                    DietaryAttribute(21, "Vegan", 4293787637, 4278210850),
                    DietaryAttribute(93, "Lactose-free", 4294243831, 4282336848)
                ),
                setOf(),
                onToggle = {})
        }
    }
}