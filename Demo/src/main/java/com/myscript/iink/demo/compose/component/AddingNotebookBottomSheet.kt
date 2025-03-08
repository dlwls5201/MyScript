package com.myscript.iink.demo.compose.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val noteItems = listOf(
    "Note1", "Note2", "Note3",
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddingNotebookBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {}
) {

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = { onDismissRequest() },
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    ) {
        AddingNotebookContent()
    }
}

@Composable
private fun AddingNotebookContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Your Notebook",
            style = MaterialTheme.typography.titleMedium,
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(noteItems.size) { index ->
                val notebook = noteItems[index]
                NotebookItem(
                    content = notebook,
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            var text by remember { mutableStateOf("") }

            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Notebook Name") },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(16.dp))

            CircleOutlinedIconButton(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddingNotebookBottomSheetPreview() {
    AddingNotebookContent()
}