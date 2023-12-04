package com.example.mobile_integration_ca3

import Datasource
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobile_integration_ca3.ui.theme.Mobile_Integration_CA3Theme
import com.example.mobile_integration_ca3.model.Dose
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.Image
import androidx.compose.material3.Button
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { DosesApp() }
    }
}

@Composable
fun DosesApp() {
    var isDarkTheme by remember { mutableStateOf(true)}
    Mobile_Integration_CA3Theme (darkTheme = isDarkTheme) {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                Button(onClick = { isDarkTheme = !isDarkTheme }) {
                    Text(text = if (isDarkTheme) "Change to Light Mode" else "Change to Dark Mode")
                }
                DoseList(
                    doseList = Datasource().loadDoses(),
                )

            }

        }
    }
        Log.d("DosesApp","DosesApp successfully loaded")
}

@Composable
fun DoseCard(dose: Dose, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Text(
                text = LocalContext.current.getString(dose.stringResourceId),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineSmall
            )
    }
    Log.d("DoseCard","DoseCard successfully loaded with the string:" + LocalContext.current.getString(dose.stringResourceId))
    // Looking at Logcat, we observe that two list items above/below the currently
    // visible list items are loaded. This is the result of using a LazyColumn, which
    // renders a more narrow range of items that need to be visible. Performance benefit
}

@Preview
@Composable
private fun DoseCardPreview() {
    Mobile_Integration_CA3Theme(darkTheme = true) {
        DoseCard(Dose(R.string.Dose1))
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