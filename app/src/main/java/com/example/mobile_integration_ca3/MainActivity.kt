package com.example.mobile_integration_ca3

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.mobile_integration_ca3.data.allDoses
import android.os.Bundle
import androidx.compose.foundation.layout.Spacer
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobile_integration_ca3.ui.theme.Mobile_Integration_CA3Theme
import com.example.mobile_integration_ca3.model.Dose
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material3.Icon
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile_integration_ca3.ui.AppViewModel


enum class AppScreen() {
    Home,
    Settings
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { DosesApp() }
    }
}

@Composable
fun DosesApp(appViewModel: AppViewModel = viewModel()) {
    val appUiState by appViewModel.uiState.collectAsState()
    Mobile_Integration_CA3Theme (darkTheme = appUiState.isDark) {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                Button(onClick = { appViewModel.updateIsDark() }) {
                    Text(text = if (appUiState.isDark) "Change to Light Mode" else "Change to Dark Mode")
                }
                DoseList(
                    doseList = allDoses,
                )

            }

        }
    }
        Log.d("DosesApp","DosesApp successfully loaded")
}

@Composable
fun DoseCard(dose: Dose, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium)
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp)
            ) {
                Text(
                    text = LocalContext.current.getString(dose.datetime),
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.weight(1f))
                DoseCardButton(
                    expanded = expanded,
                    onClick = { expanded = !expanded  },
                    modifier = Modifier.padding(8.dp)
                )
            }
            if (expanded) {
                DoseContents(
                    dose.contents,
                    modifier = Modifier.padding(
                        start = 5.dp,
                        top = 3.dp,
                        end = 5.dp,
                        bottom = 5.dp
                    )
                )
            }
        }
    }
    Log.d("DoseCard","DoseCard successfully loaded with the string:" + LocalContext.current.getString(dose.datetime))
    // Looking at Logcat, we observe that two list items above/below the currently
    // visible list items are loaded. This is the result of using a LazyColumn, which
    // renders a more narrow range of items that need to be visible. Performance benefit
}

@Composable
private fun DoseCardButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = stringResource(R.string.expand_button_content_description),
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun DoseContents(
    @StringRes contents: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Contents:",
            style = MaterialTheme.typography.labelSmall
        )
        Text(
            text = stringResource(contents),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview
@Composable
private fun DoseCardPreview() {
    Mobile_Integration_CA3Theme(darkTheme = true) {
        DoseCard(Dose(R.string.Dose1,R.string.Contents1))
    }
}

@Composable
fun DoseList(doseList: List<Dose>, modifier: Modifier = Modifier) {

    LazyColumn(modifier = modifier) {
        items(doseList) { dose ->
            DoseCard(
                dose = dose,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}