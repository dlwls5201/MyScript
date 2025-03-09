package com.myscript.iink.demo.presentation.compose.component

import android.widget.Toast
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myscript.iink.demo.domain.model.Notebook
import com.myscript.iink.demo.presentation.viewmodel.NotebookViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddingNotebookBottomSheet(
    modifier: Modifier = Modifier,
    notebookViewModel: NotebookViewModel,
    onNotebookClick: (notebook: Notebook) -> Unit = {},
    onDismissRequest: () -> Unit = {},
) {
    val scope = rememberCoroutineScope()
    val notebookItems by notebookViewModel.notebookItems.collectAsState(emptyList())

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = { onDismissRequest() },
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    ) {
        AddingNotebookContent(
            notebookItems = notebookItems,
            onNotebookClick = onNotebookClick,
            onAddNoteBook = { title ->
                scope.launch {
                    notebookViewModel.createNotebook(title)
                }
            },
        )
    }
}

@Composable
private fun AddingNotebookContent(
    modifier: Modifier = Modifier,
    notebookItems: List<Notebook>,
    onNotebookClick: (notebook: Notebook) -> Unit = {},
    onAddNoteBook: (String) -> Unit = {},
) {
    val context = LocalContext.current

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
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(notebookItems.size) { index ->
                val notebook = notebookItems[index]
                NotebookItem(
                    content = notebook.title,
                    onClick = {
                        onNotebookClick(notebook)
                    }
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
                onClick = {
                    if (text.isEmpty()) {
                        Toast.makeText(context, "Please enter notebook name", Toast.LENGTH_SHORT).show()
                        return@CircleOutlinedIconButton
                    }

                    onAddNoteBook(text)
                    text = ""
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddingNotebookBottomSheetPreview() {
    AddingNotebookContent(
        notebookItems = listOf(
            Notebook.create("Notebook1"),
            Notebook.create("Notebook2"),
            Notebook.create("Notebook3"),
        )
    )
}