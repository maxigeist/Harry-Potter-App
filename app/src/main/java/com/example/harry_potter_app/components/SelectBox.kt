package com.example.harry_potter_app.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.harry_potter_app.R
import com.example.harry_potter_app.ui.theme.SmallFontSize
import com.example.harry_potter_app.ui.theme.XSmallFontSize
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.tooling.preview.Preview
import com.example.harry_potter_app.ui.theme.HalfDivision

data class SelectBoxProps(
    val options: List<String>,
    val active: String,
    val onChange: (String) -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectBox(props: SelectBoxProps) {
    val items = props.options
    var selectedItem by remember { mutableStateOf(props.active) }
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedItem,
            onValueChange = {},
            readOnly = true,
            label = {
                Text(
                    text = stringResource(id = R.string.choose_which_favorite),
                    fontFamily = FontFamily(Font(R.font.harry)),
                    fontSize = XSmallFontSize,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            textStyle = TextStyle(
                fontFamily = FontFamily(Font(R.font.harry)),
                fontSize = SmallFontSize,
                color = MaterialTheme.colorScheme.onSurface
            ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = HalfDivision),
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = item,
                            fontFamily = FontFamily(Font(R.font.harry)),
                            fontSize = SmallFontSize,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    onClick = {
                        selectedItem = item
                        expanded = false
                        props.onChange(item)
                    }
                )
            }
        }
    }
}
@Preview
@Composable
fun SelectBoxPreview() {
    SelectBox(
        SelectBoxProps(
            listOf("Characters", "Houses", "Books"),
            "Characters",
            {}
        )
    )
}