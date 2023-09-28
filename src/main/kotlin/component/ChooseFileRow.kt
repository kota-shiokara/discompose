package component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.io.File

@Composable
fun ChooseFileRow(
    filePath: String = "",
    onValueChange: (String) -> Unit = {},
) {
    var fileDialogState by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            filePath,
            label = { Text("File Path") },
            onValueChange = onValueChange,
            modifier = Modifier.width(300.dp),
            singleLine = true
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = {
                fileDialogState = true
            },
            modifier = Modifier
        ) {
            Text("Choose File")
        }
    }
    if (fileDialogState) {
        FileDialog(
            onCloseRequest = { directory, file ->
                fileDialogState = false
                if (directory != null && file != null) {
                    onValueChange(File(directory, file).absolutePath)
                }
            }
        )
    }
}