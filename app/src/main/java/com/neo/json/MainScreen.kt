package com.neo.json

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener

@Composable
fun MainScreen(jsonState: String, onValueChange: (String) -> Unit) {

    BoxWithConstraints {
        if (maxWidth < maxHeight) {
            MainPhoneScreen(jsonState = jsonState, onValueChange)
        } else {
            MainTabletScreen(jsonState = jsonState, onValueChange)
        }
    }

}

@Composable
private fun MainPhoneScreen(jsonState: String, onValueChange: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        JsonViewer(
            jsonString = jsonState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        Divider()

        BasicTextField(
            value = jsonState,
            onValueChange = onValueChange,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .weight(1f)
                .horizontalScroll(rememberScrollState())
        )
    }
}

@Composable
fun MainTabletScreen(jsonState: String, onValueChange: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        JsonViewer(
            jsonString = jsonState,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)

        )

        Divider(
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
        )

        BasicTextField(
            value = jsonState,
            onValueChange = onValueChange,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxHeight()
                .weight(1f)
        )
    }
}

@Composable
fun JsonViewer(jsonString: String, modifier: Modifier = Modifier) {
    if (jsonString.isEmpty()) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Text(text = "is empty")
        }

    } else {
        runCatching {
            when (val value = JSONTokener(jsonString).nextValue()) {
                is JSONObject, is JSONArray -> {
                    Box(
                        modifier = modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .horizontalScroll(rememberScrollState()),
                    ) {
                        JsonObject(
                            origin = "origin",
                            json = value,
                            defaultExpanded = true
                        )
                    }
                }

                else -> {
                    Box(
                        modifier = modifier,
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "invalid origin")
                    }
                }
            }

        }.onFailure {
            Box(
                modifier = modifier,
                contentAlignment = Alignment.Center
            ) {
                Text(text = "invalid")
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MainScreenPreview() {
    MainScreen("") {}
}

@Composable
@Preview(showBackground = true, widthDp = 600, heightDp = 300)
fun MainTabletScreenPreview() {
    MainTabletScreen("") {}
}