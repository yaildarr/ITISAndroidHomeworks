package com.example.hw5_coroutines.screen

import android.content.Context

import android.widget.Toast

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.hw5_coroutines.R
import com.example.hw5_coroutines.util.BehaviorMode
import com.example.hw5_coroutines.util.LaunchMode
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

@Composable
fun Screen(modifier: Modifier) {
    val context = LocalContext.current
    var job by remember { mutableStateOf<Job?>(null) }
    val lifecycleOwner = LocalLifecycleOwner.current
    var text by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var canceledCoroutinesCount by remember { mutableStateOf(0) }
    var activeCoroutinesCount by remember { mutableStateOf(0) }

    val firstRadioGroup = listOf("Параллельно", "Последовательно")
    val secondRadioGroup = listOf("Отменять при сворачивании приложения", "Не отменять при сворачивании приложения")
    var launchMode by remember { mutableStateOf(LaunchMode.PARALLEL) }
    var behaviorMode by remember { mutableStateOf(BehaviorMode.CANCEL) }
    var dispatcher by remember { mutableStateOf(Dispatchers.Default) }


    val coroutineScope = rememberCoroutineScope()

    Column(modifier = modifier) {

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text(stringResource(R.string.enterColCoroutines)) },
            modifier = Modifier.fillMaxWidth(),
            isError = showError,
            supportingText = {
                if (showError) {
                    Text(stringResource(R.string.cantBeEmpty), color = MaterialTheme.colorScheme.error)
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))


        Text(
            text = stringResource(R.string.howToStartCoroutines),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        RadioGroup(
            options = firstRadioGroup,
            modifier = Modifier.fillMaxWidth(),
            selectedOption = if (launchMode == LaunchMode.PARALLEL) firstRadioGroup[0] else firstRadioGroup[1],
            onOptionSelected = { selected ->
                launchMode = if (selected == firstRadioGroup[0]) LaunchMode.PARALLEL else LaunchMode.SEQUENTIAL
            }
        )

        Text(
            text = stringResource(R.string.logicWorkCoroutines),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        RadioGroup(
            options = secondRadioGroup,
            modifier = Modifier.fillMaxWidth(),
            selectedOption = if (behaviorMode == BehaviorMode.CANCEL) secondRadioGroup[0] else secondRadioGroup[1],
            onOptionSelected = { selected ->
                behaviorMode = if (selected == secondRadioGroup[0]) BehaviorMode.CANCEL else BehaviorMode.CONTINUE
            }
        )

        DropdownSelector(
            title = stringResource(R.string.poolThreads),
            options = listOf(Dispatchers.Default, Dispatchers.IO, Dispatchers.Main),
            selectedOption = dispatcher,
            onOptionSelected = { dispatcher = it },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    if (text.isBlank()) {
                        showError = true
                    } else {
                        showError = false
                        activeCoroutinesCount = text.toInt()
                        job = when (launchMode) {
                            LaunchMode.PARALLEL -> launchParallel(
                                count = text.toInt(),
                                dispatcher = dispatcher,
                                scope = coroutineScope,
                                context = context,
                                onCoroutineCompleted = { activeCoroutinesCount-- }
                            )
                            LaunchMode.SEQUENTIAL -> launchSequentially(
                                count = text.toInt(),
                                dispatcher = dispatcher,
                                scope = coroutineScope,
                                context = context,
                                onCoroutineCompleted = { activeCoroutinesCount-- }
                            )
                        }
                    }
                }
            ) {
                Text(stringResource(R.string.lauch))
            }
            Button(
                onClick = {
                    job?.let {
                        it.cancel()
                        canceledCoroutinesCount = activeCoroutinesCount
                        job = null
                    } ?: run {
                        Toast.makeText(context,
                            context.getString(R.string.coroutinesNotStarted), Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text(stringResource(R.string.CancelButton))
            }
        }


        if (canceledCoroutinesCount > 0) {
            Text(stringResource(R.string.canceledCoroutines, canceledCoroutinesCount))
        }
    }


    DisposableEffect(lifecycleOwner, behaviorMode, job) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_PAUSE && behaviorMode == BehaviorMode.CANCEL) {
                job?.cancel()
                job = null
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

fun launchParallel(
    count: Int,
    dispatcher: CoroutineDispatcher,
    scope: CoroutineScope,
    context: Context,
    onCoroutineCompleted: () -> Unit
): Job {
    return scope.launch {
        try {
            val jobs = List(count) {
                launch(dispatcher) {
                    try {
                        delay(2500L)
                        println(context.getString(R.string.Completed, it.toString()))
                    } finally {
                        onCoroutineCompleted()
                    }
                }
            }
            jobs.forEach { it.join() }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            scope.launch(Dispatchers.Main) {
                Toast.makeText(context,
                    context.getString(R.string.Exception, e.message), Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun RadioGroup(
    options: List<String>,
    modifier: Modifier,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Column(modifier = modifier.selectableGroup()) {
        options.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) },
                        role = Role.RadioButton
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = null
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

@Composable
fun DropdownSelector(
    title: String,
    options: List<CoroutineDispatcher>,
    selectedOption: CoroutineDispatcher,
    onOptionSelected: (CoroutineDispatcher) -> Unit,
    modifier: Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    Column {
        Text(text = title)
        Box(modifier = modifier) {
            Button(onClick = { expanded = true }) {
                Text(selectedOption.toString())
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option.toString()) },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}




fun launchSequentially(
    count: Int,
    dispatcher: CoroutineDispatcher,
    scope: CoroutineScope,
    context: Context,
    onCoroutineCompleted: () -> Unit
): Job {
    return scope.launch {
        try {
            repeat(count) {
                launch(dispatcher) {
                    try {
                        delay(2500L)
                        println(context.getString(R.string.Completed, it.toString()))
                    } finally {
                        onCoroutineCompleted()
                    }
                }.join()
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            scope.launch(Dispatchers.Main) {
                Toast.makeText(context,
                    context.getString(R.string.Exception, e.message), Toast.LENGTH_SHORT).show()
            }
        }
    }
}



@Preview
@Composable
fun Preview() {
    Surface {
        Screen(Modifier)
    }
}
