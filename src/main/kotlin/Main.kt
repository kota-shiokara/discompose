import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import korlibs.audio.sound.AudioStream
import korlibs.audio.sound.playAndWait
import korlibs.audio.sound.readAudioStream
import korlibs.io.file.std.toVfs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.File

@Composable
@Preview
fun App() {
    MaterialTheme {
        val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main + Dispatchers.IO)
        var filePath by remember { mutableStateOf("") }
        var dialogState by remember { mutableStateOf(false) }
        var audioStream: AudioStream? by remember { mutableStateOf(null) }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(filePath, onValueChange = { filePath = it })
            Button(
                onClick = {
                    coroutineScope.launch(Dispatchers.IO) {
                        val file = File(filePath)
                        if (file.exists()) {
                            audioStream = file.toVfs().readAudioStream()
                            audioStream!!.playAndWait()
                        } else {
                            dialogState = true
                        }
                    }
                }
            ) {
                Text("再生")
            }
        }
        if (dialogState) {
            Dialog(
                onCloseRequest = { dialogState = false }
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("File Not Found")
                }
            }
        }
    }
}

@Composable
@Preview
fun LazyColumn(
    content: () -> Unit
) {
    val scrollState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = scrollState
    ) {
        content()
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
