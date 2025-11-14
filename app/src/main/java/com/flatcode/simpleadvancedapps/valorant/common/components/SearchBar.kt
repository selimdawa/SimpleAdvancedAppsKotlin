package com.flatcode.simpleadvancedapps.valorant.common.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.flatcode.simpleadvancedapps.Unit.DATA
import com.flatcode.simpleadvancedapps.valorant.presentation.theme.ValoBasic


@Composable
fun SearchBar(
    searchText: String,
    placeholderText: String = DATA.EMPTY,
    onSearchTextChanged: (String) -> Unit = {},
    border: BorderStroke = BorderStroke((0.5f).dp, ValoBasic),
    backgroundColor: Color = MaterialTheme.colors.surface.copy(alpha = 0.7f),
    shape: Shape = RoundedCornerShape(12.dp),
    onClearClick: () -> Unit = {},
) {
    var showClearButton by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Card(
        elevation = 8.dp,
        modifier = Modifier.padding(10.dp),
        border = border,
        backgroundColor = backgroundColor,
        shape = shape
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier.padding(start = 10.dp),
                imageVector = Icons.Filled.Search,
                contentDescription = "Search",
                tint = MaterialTheme.colors.onSurface
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp)
                    .onFocusChanged { focusState ->
                        showClearButton = (focusState.isFocused || searchText.isNotBlank())
                    },
                value = searchText,
                onValueChange = onSearchTextChanged,
                placeholder = {
                    Text(
                        text = placeholderText,
                        modifier = Modifier.wrapContentHeight(),
                        color = Color.Black
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    backgroundColor = Color.Transparent,
                    cursorColor = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                ),
                trailingIcon = {
                    AnimatedVisibility(
                        visible = showClearButton,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        IconButton(onClick = {
                            onClearClick()
                            focusManager.clearFocus()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Close"
                            )
                        }
                    }
                },
                maxLines = 1,
                singleLine = true
            )
        }
    }
}