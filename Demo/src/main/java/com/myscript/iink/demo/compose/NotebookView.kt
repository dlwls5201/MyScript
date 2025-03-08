package com.myscript.iink.demo.compose

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myscript.iink.demo.compose.component.AddingNotebookBottomSheet
import com.myscript.iink.demo.compose.component.CircleOutlinedIconButton
import com.myscript.iink.demo.compose.component.PageItem
import com.myscript.iink.demo.presentation.WritingActivity
import com.myscript.iink.demo.presentation.model.PageContentsModel
import com.myscript.iink.demo.presentation.model.PageModel

private val note1PageItems = listOf(
    "Hello", "Nice", "Happy"
)

@Composable
fun NotebookView() {
    val context = LocalContext.current

    var showBottomSheet by remember { mutableStateOf(false) }

    Box {
        Column(
            modifier = Modifier
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
                    text = "Notebook1",
                    style = MaterialTheme.typography.titleLarge,
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f),
            ) {
                IconButton(onClick = {
                    showBottomSheet = true
                }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu"
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(note1PageItems.size) { index ->
                        val content = note1PageItems[index]
                        PageItem(
                            content = content,
                            onClick = {
                                WritingActivity.startActivity(
                                    context = context,
                                    pageModel = PageModel(
                                        notebookId = 1,
                                        pageContent = PageContentsModel(
                                            contents = content
                                        )
                                    )
                                )
                            }
                        )
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

        if (showBottomSheet) {
            AddingNotebookBottomSheet(
                onDismissRequest = { showBottomSheet = false },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NotebookViewPreview() {
    MaterialTheme {
        NotebookView()
    }
}
