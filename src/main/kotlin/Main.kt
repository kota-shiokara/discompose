import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import component.ChooseFileRow
import korlibs.audio.sound.PlaybackParameters
import korlibs.audio.sound.SoundChannel
import korlibs.audio.sound.await
import korlibs.audio.sound.readSound
import korlibs.io.file.std.toVfs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import util.formatTimer
import java.io.File

@Composable
@Preview
fun App() {
    MaterialTheme {
        val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main + Dispatchers.IO)
        var filePath by remember { mutableStateOf("") }
        var dialogState by remember { mutableStateOf(false) }
        // 曲の時間
        var currentTime by remember { mutableStateOf(0f) }
        var totalTime by remember { mutableStateOf(0f) }

        var soundChannel: SoundChannel? by remember { mutableStateOf(null) }

        LaunchedEffect(soundChannel) {
            soundChannel?.await { current, total ->
                currentTime = current.seconds.toFloat()
                totalTime = total.seconds.toFloat()
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ChooseFileRow(filePath) {
                filePath = it
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Row {
                Button(
                    onClick = {
                        println("Start")
                        val file = File(filePath)
                        coroutineScope.launch(Dispatchers.IO) {
                            if (file.exists()) {
                                soundChannel = file.toVfs().readSound().play(
                                    PlaybackParameters(
                                        volume = 0.2
                                    )
                                )
                            } else {
                                dialogState = true
                            }
                        }
                    }
                ) {
                    Text("Play")
                }
                Spacer(modifier = Modifier.size(8.dp))
                Button(
                    onClick = {
                        println("Stop")
                        if (soundChannel != null) {
                            soundChannel!!.stop()
                            soundChannel = null
                            println("Stop Complete")
                        }
                    }
                ) {
                    Text("Stop")
                }
            }
            if (soundChannel != null) {
                Text("${currentTime.toInt().formatTimer()} / ${totalTime.toInt().formatTimer()}")
            } else {
                Text("- / -")
            }

            Slider(
                currentTime,
                onValueChange = {  }, // TODO: SeekBarとしての処理を追加
                modifier = Modifier
                    .padding(horizontal = 32.dp),
                valueRange = 0f..totalTime
            )
        }
        if (dialogState) {
            Dialog(
                onCloseRequest = { dialogState = false }
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("File Not Found")
                }
            }
        }

    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Discompose"
    ) {
        App()
    }
}
