package com.example.mobile_integration_ca3.ui

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile_integration_ca3.R
import com.example.mobile_integration_ca3.data.allDoses
import com.example.mobile_integration_ca3.model.ConvertedDose
import com.example.mobile_integration_ca3.ui.theme.Mobile_Integration_CA3Theme

@Composable
fun HomeScreen(
    appViewModel: AppViewModel = viewModel(),
    onNextButtonClicked: () -> Unit,
    modifier: Modifier,
    darkTheme: Boolean
) {
    val appUiState by appViewModel.uiState.collectAsState()
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            Row {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = onNextButtonClicked
                ) {
                    Text(text = "Settings")
                }
            }
            DoseList(
                doseList = appUiState.doseList,
            )

        }
    }

    Log.d("DosesApp","DosesApp successfully loaded")
}


@Composable
fun DoseCard(dose: ConvertedDose, modifier: Modifier = Modifier) {
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
                    text = dose.date,
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
                    dose.scheduledMedications,
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
    Log.d("DoseCard","DoseCard successfully loaded with the string:" + "bla")
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
    contents: List<String>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Contents:",
            style = MaterialTheme.typography.labelSmall
        )
        for (med in contents) {
            Text(
                text = med,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun DoseList(doseList: List<ConvertedDose>, modifier: Modifier = Modifier) {

    LazyColumn(modifier = modifier) {
        items(doseList) { dose ->
            DoseCard(
                dose = dose,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

