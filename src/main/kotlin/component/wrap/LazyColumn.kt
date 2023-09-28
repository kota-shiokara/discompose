package component.wrap

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LazyColumn(
    content: () -> Unit
) {
    val scrollState = rememberLazyListState()

    androidx.compose.foundation.lazy.LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = scrollState
    ) {
        content()
    }
}