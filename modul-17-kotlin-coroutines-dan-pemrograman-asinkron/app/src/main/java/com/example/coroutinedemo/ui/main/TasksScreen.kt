package com.example.coroutinedemo.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coroutinedemo.data.Task
import com.example.coroutinedemo.ui.theme.CoroutineDemoTheme

/**
 * Route layar: satu-satunya tempat yang menghubungkan ViewModel dengan UI (Subbagian 7.8).
 * Di lapisan ini tidak ada satu baris pun kode coroutine — layar hanya menampilkan state.
 */
@Composable
fun TasksScreen(
    modifier: Modifier = Modifier,
    viewModel: TasksViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    TasksContent(
        state = state,
        onLoad = { viewModel.loadData() },
        onLoadWithError = { viewModel.loadData(shouldFail = true) },
        onCancel = viewModel::cancelLoading,
        onTap = viewModel::onTap,
        onBlock = viewModel::blockMainThread,
        modifier = modifier
    )
}

/** Layar tanpa ViewModel: menerima state dan lambda event, sehingga bisa di-preview. */
@Composable
fun TasksContent(
    state: TasksUiState,
    onLoad: () -> Unit,
    onLoadWithError: () -> Unit,
    onCancel: () -> Unit,
    onTap: () -> Unit,
    onBlock: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Coroutine Demo", style = MaterialTheme.typography.headlineSmall)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onLoad, enabled = !state.isLoading) {
                Text("Load")
            }
            OutlinedButton(
                onClick = onLoadWithError,
                enabled = !state.isLoading
            ) {
                Text("Load (fail)")
            }
            OutlinedButton(onClick = onCancel, enabled = state.isLoading) {
                Text("Cancel")
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onTap) {
                Text("Tap me: ${state.tapCount}")
            }
            OutlinedButton(onClick = onBlock) {
                Text("Block UI")
            }
        }

        if (state.isLoading) {
            CircularProgressIndicator()
        }

        state.errorMessage?.let { message ->
            Text(message, color = MaterialTheme.colorScheme.error)
        }

        if (state.announcement.isNotEmpty()) {
            Text("Announcement: ${state.announcement}")
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(state.tasks, key = { it.id }) { task ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            task.title,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            task.course,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TasksContentAwalPreview() {
    CoroutineDemoTheme {
        TasksContent(
            state = TasksUiState(),
            onLoad = {},
            onLoadWithError = {},
            onCancel = {},
            onTap = {},
            onBlock = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TasksContentErrorPreview() {
    CoroutineDemoTheme {
        TasksContent(
            state = TasksUiState(errorMessage = "Failed to load: Server is not reachable"),
            onLoad = {},
            onLoadWithError = {},
            onCancel = {},
            onTap = {},
            onBlock = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TasksContentBerhasilPreview() {
    CoroutineDemoTheme {
        TasksContent(
            state = TasksUiState(
                tasks = listOf(
                    Task(1, "Write lab report", "Mobile App Development"),
                    Task(2, "Read chapter 5", "Data Science")
                ),
                announcement = "Quiz 2 is next Monday"
            ),
            onLoad = {},
            onLoadWithError = {},
            onCancel = {},
            onTap = {},
            onBlock = {}
        )
    }
}
