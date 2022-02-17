package com.neo.json

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.neo.json.ui.theme.NeoJSONTheme
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NeoJSONTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Main()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    NeoJSONTheme {
        Main()
    }
}

@Composable
private fun Main() {
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

    json.put("friends", friends)

    JsonObject("origin", json = json)
}