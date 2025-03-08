package com.myscript.iink.demo.presentation.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.myscript.iink.demo.presentation.compose.component.AddingNotebookBottomSheet
import com.myscript.iink.demo.presentation.compose.component.CircleOutlinedIconButton
import com.myscript.iink.demo.presentation.compose.component.PageItem
import com.myscript.iink.demo.data.model.NotebookEntity
import com.myscript.iink.demo.data.model.PageEntity
import com.myscript.iink.demo.presentation.WritingActivity
import com.myscript.iink.demo.presentation.viewmodel.NotebookViewModel

@Composable
fun NotebookRoute(
    notebookViewModel: NotebookViewModel = hiltViewModel()
) {
    var notebook by remember { mutableStateOf(NotebookEntity.EMPTY_NOTEBOOK) }
    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        notebook = notebookViewModel.getFirstNotebook()
    }

    val pageItems by notebookViewModel.getPageItems(notebook.id).collectAsState(emptyList())

    NotebookView(
        notebook = notebook,
        pageItems = pageItems,
        onShowBottomSheet = {
            showBottomSheet = true
        }
    )

    if (showBottomSheet) {
        AddingNotebookBottomSheet(
            notebookViewModel = notebookViewModel,
            onNotebookClick = { clickedNotebook ->
                notebook = clickedNotebook
                showBottomSheet = false
            },
            onDismissRequest = { showBottomSheet = false },
        )
    }
}

@Composable
fun NotebookView(
    modifier: Modifier = Modifier,
    notebook: NotebookEntity,
    pageItems: List<PageEntity>,
    onShowBottomSheet: () -> Unit = {},
) {
    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = notebook.title,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f),
            ) {
                IconButton(onClick = {
                    onShowBottomSheet()
                }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu"
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                if (pageItems.isEmpty()) {
                    EmptyNotebook()
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(pageItems.size) { index ->
                            val page = pageItems[index]
                            PageItem(
                                content = page.contents,
                                onClick = {
                                    WritingActivity.startActivity(
                                        context = context,
                                        pageId = page.id
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .padding(
                    end = 12.dp,
                    bottom = 16.dp,
                )
                .align(Alignment.BottomEnd)
        ) {
            CircleOutlinedIconButton(
                imageVector = Icons.Default.Create,
                contentDescription = "Create",
                onClick = {
                    WritingActivity.startActivity(context)
                }
            )
        }
    }
}

@Composable
private fun EmptyNotebook(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No pages",
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NotebookViewPreview() {
    MaterialTheme {
        NotebookView(
            notebook = NotebookEntity.create("Notebook1"),
            pageItems = listOf(
                PageEntity.create(notebookId = "1", contents = "Page1"),
                PageEntity.create(notebookId = "2", contents = "Page2"),
                PageEntity.create(notebookId = "3", contents = "Page3"),
            )
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun NotebookViewEmptyPreview() {
    MaterialTheme {
        NotebookView(
            notebook = NotebookEntity.EMPTY_NOTEBOOK,
            pageItems = emptyList()
        )
    }
}
