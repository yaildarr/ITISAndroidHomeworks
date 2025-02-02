package com.example.hw5_coroutines

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            requestNotificationPermission()
        }

        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Screen(modifier = Modifier.padding(innerPadding))
            }
        }
    }
//    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
//    private fun requestNotificationPermission() {
//        val permissionLauncher = registerForActivityResult(
//            ActivityResultContracts.RequestPermission()
//        ) { isGranted,  ->
//            if (!isGranted && !shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
//                showPermissionRationaleDialog()
//            }
//        }
//
//        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
//    }

    private fun showPermissionRationaleDialog() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
        }
        startActivity(intent)
    }
}

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
            label = { Text("Введите количество корутин") },
            modifier = Modifier.fillMaxWidth(),
            isError = showError,
            supportingText = {
                if (showError) {
                    Text("Поле не может быть пустым", color = MaterialTheme.colorScheme.error)
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))


        Text(
            text = "Как будут запущены корутины",
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
            text = "Логика работы корутин",
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
            title = "Пул потоков",
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
                Text("Запустить")
            }
            Button(
                onClick = {
                    job?.let {
                        it.cancel()
                        canceledCoroutinesCount = activeCoroutinesCount
                        job = null
                    } ?: run {
                        Toast.makeText(context, "Корутины не запущены", Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text("Отменить")
            }
        }


        if (canceledCoroutinesCount > 0) {
            Text("Отменено корутин: $canceledCoroutinesCount")
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
                        println("$it Завершена")
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
                Toast.makeText(context, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
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
                        println("$it Завершена")
                    } finally {
                        onCoroutineCompleted()
                    }
                }.join()
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            scope.launch(Dispatchers.Main) {
                Toast.makeText(context, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
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

enum class LaunchMode { SEQUENTIAL, PARALLEL }
enum class BehaviorMode { CANCEL, CONTINUE }