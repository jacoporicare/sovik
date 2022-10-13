package cz.jakubricar.sovik

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.jakubricar.sovik.ui.theme.SovikTheme
import kotlin.math.ceil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SovikTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Content()
                }
            }
        }
    }
}

const val maxOwls = 10

@Composable
fun Content() {
    var owls by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(0f) }
    var origin by remember { mutableStateOf(0f) }

    val context = LocalContext.current

    Column(
        modifier = Modifier.padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🦉 Sovík 🦉",
            style = MaterialTheme.typography.h3
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Kolik 🦉 dáváš?",
                style = MaterialTheme.typography.h4
            )

            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                val density = LocalDensity.current.density
                val maxValue = maxWidth.value * density
                val scaleFactor = maxValue / maxOwls

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = { position ->
                                    origin = position.x
                                    offset = 0f

                                    owls =
                                        ceil(origin / scaleFactor).coerceIn(1f, maxOwls.toFloat())
                                }
                            )
                        }
                        .draggable(
                            state = rememberDraggableState { delta ->
                                offset += delta

                                val coordinates = (origin + offset).coerceIn(0f, maxValue)
                                owls =
                                    ceil(coordinates / scaleFactor).coerceIn(1f, maxOwls.toFloat())

                            },
                            orientation = Orientation.Horizontal,
                        ),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (i in 1..10) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "🦉",
                                modifier = Modifier
                                    .alpha(if (i > owls) 0.3f else 1f),
                                fontSize = 28.sp
                            )

                            Text(
                                text = if (i == owls.toInt()) i.toString() else " ",
                                fontSize = 20.sp
                            )
                        }
                    }
                }
            }
        }

        Button(
            onClick = {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "Hodnotím ${"🦉".repeat(owls.toInt())} (${owls.toInt()}/$maxOwls) přes Sovíka."
                    )
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)

                context.startActivity(shareIntent)
            },
        ) {
            Text(
                text = "Hodnotit",
                fontSize = 24.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Content()
    }
}