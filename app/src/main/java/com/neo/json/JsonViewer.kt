package com.neo.json

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.json.JSONArray
import org.json.JSONObject

@Preview(showBackground = true)
@Composable
fun JsonViewerPreview() {

    val json = JSONObject()

    json.put("nome", "Irineu")
    json.put("idade", 23)
    json.put("programador", true)
    json.put("altura", 1.73)

    val languages = JSONArray()

    languages.put("Java")
    languages.put("Kotlin")

    json.put("languages", languages)

    val friends = JSONArray()

    friends.put(
        JSONObject().apply {
            put("name", "Carlos")
            put("languages", JSONArray().apply {
                put("Java")
            })
        }
    )

    friends.put(
        JSONObject().apply {
            put("name", "Kleber")
            put("languages", JSONArray().apply {
                put("Java")
                put("JavaScript")
            })
        }
    )

    json.put("mansões", JSONArray())

    json.put("friends", friends)

    JsonObject("origin", json = json)
}

@Composable
fun JsonObject(origin: Any, json: Any) {

    var expanded by remember { mutableStateOf(true) }

    val children = when (json) {
        is JSONArray -> {
            json.children()
        }

        is JSONObject -> {
            json.children()
        }

        else -> throw RuntimeException()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .height(30.dp)
                .fillMaxWidth()
                .clickable {
                    //
                },
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (children.isNotEmpty()) {
                IconButton(
                    modifier = Modifier.size(30.dp),
                    onClick = {
                        expanded = !expanded
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        modifier = Modifier.padding(6.dp)
                    )
                }
            } else {
                Spacer(modifier = Modifier.width(8.dp))
            }

            when (json) {
                is JSONObject -> {
                    Text(text = "$origin : ${json.type} {${children.size}}", fontSize = 18.sp)
                }
                is JSONArray -> {
                    Text(text = "$origin : ${json.type} [${children.size}]", fontSize = 18.sp)
                }
            }

            Spacer(modifier = Modifier.weight(1f))
        }

        if (children.isNotEmpty()) {
            if (expanded) {

                for ((index, child) in children.withIndex()) {

                    val isLastIndex = index == children.size - 1

                    Row(
                        modifier = Modifier
                            .wrapContentHeight()
                            .drawBehind {
                                fun getVerticalLine() =
                                    if (isLastIndex) 15.dp.toPx() else size.height

                                drawLine(
                                    color = Color.Black,
                                    start = Offset(
                                        x = 15.dp.toPx(),
                                        y = 0f
                                    ),
                                    end = Offset(
                                        x = 15.dp.toPx(),
                                        y = getVerticalLine()
                                    ),
                                    strokeWidth = 1.dp.toPx(),
                                    cap = StrokeCap.Round
                                )

                                drawLine(
                                    color = Color.Black,
                                    start = Offset(
                                        x = 15.dp.toPx(),
                                        y = 15.dp.toPx()
                                    ),
                                    end = Offset(
                                        x = 30.dp.toPx(),
                                        y = 15.dp.toPx()
                                    ),
                                    strokeWidth = 1.dp.toPx()
                                )
                            },
                    ) {
                        Spacer(
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                        )

                        when (child.second) {
                            is JSONArray, is JSONObject -> {
                                JsonObject(child.first, child.second)
                            }

                            else -> {
                                Spacer(modifier = Modifier.width(8.dp))
                                JsonValue(child.first, child.second)
                            }
                        }
                    }
                }
            } else {
                Row {
                    Canvas(
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp)
                    ) {

                        drawLine(
                            color = Color.Black,
                            start = center.copy(y = 0f),
                            end = center.copy(y = size.height / 2),
                            strokeWidth = 1.dp.toPx(),
                            cap = StrokeCap.Round
                        )

                        drawLine(
                            color = Color.Black,
                            start = center.copy(y = 15.dp.toPx()),
                            end = Offset(x = size.width, y = size.height / 2),
                            strokeWidth = 1.dp.toPx()
                        )
                    }

                    Text(
                        text = "{...}",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }
        }
    }
}

fun count(second: Any): Int {
    return when (second) {
        is JSONObject -> second.children().size
        is JSONArray -> second.children().size
        else -> 1
    }
}

@Composable
fun JsonValue(key: Any, value: Any) {
    Row(
        modifier = Modifier
            .height(30.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        when (value) {
            is String -> {
                Text(text = "$key : ${value.type} = \"$value\"", fontSize = 18.sp)
            }

            else -> {
                Text(text = "$key : ${value.type} = $value", fontSize = 18.sp)
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}


private val Any.type: String
    get() = when (this) {
        is String -> "String"
        is Boolean -> "Boolean"
        is Double -> "Double"
        is Long -> "Long"
        is Int -> "Int"
        is JSONObject -> "Object"
        is JSONArray -> "Array"
        else -> "null"
    }

private fun JSONObject.children(): List<Pair<String, Any>> {
    val children = mutableListOf<Pair<String, Any>>()

    for (key in this.keys()) {
        children.add(Pair(key, this[key]))
    }

    return children
}

private fun JSONArray.children(): List<Pair<Int, Any>> {
    val children = mutableListOf<Pair<Int, Any>>()

    for (index in 0 until length()) {
        children.add(Pair(index, this[index]))
    }

    return children
}
