package com.example.mobile_integration_ca3
import androidx.navigation.compose.NavHost
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mobile_integration_ca3.ui.AppViewModel
import com.example.mobile_integration_ca3.ui.HomeScreen
import com.example.mobile_integration_ca3.ui.SettingsScreen
import com.example.mobile_integration_ca3.ui.theme.Mobile_Integration_CA3Theme

enum class AppScreen(@StringRes val title: Int) {
    Home(title = R.string.title),
    Settings(title = R.string.settings)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrueAppBar(
    currentScreen: AppScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrueApp(
    viewModel: AppViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = AppScreen.valueOf(
        backStackEntry?.destination?.route ?: AppScreen.Home.name
    )
        val uiState by viewModel.uiState.collectAsState()
        Mobile_Integration_CA3Theme (darkTheme = uiState.isDark) {

        Scaffold(
            topBar = {
                TrueAppBar(
                    currentScreen = currentScreen,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    navigateUp = { navController.navigateUp() }
                )
            }
        ) { innerPadding ->

            NavHost(
                navController = navController,
                startDestination = AppScreen.Home.name,
                modifier = Modifier.padding(innerPadding)
            ) {

                composable(route = AppScreen.Home.name) {
                    HomeScreen(
                        onNextButtonClicked = {
                            navController.navigate(AppScreen.Settings.name)
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(5.dp),
                        darkTheme = uiState.isDark
                    )
                }
                composable(route = AppScreen.Settings.name) {
                    val context = LocalContext.current
                    SettingsScreen(
                        onNextButtonClicked = { navController.navigate(AppScreen.Home.name) },
                        modifier = Modifier.fillMaxHeight(),
                        darkTheme = uiState.isDark
                    )

                    // By pure chance, I found out that putting this here allowed the dark
                    // mode button to set the theme for both pages. I have no idea how this
                    // works, and would like to know how that happened
                    Button(onClick = { viewModel.updateIsDark() }) {
                        Text(text = if (uiState.isDark) "Change to Light Mode" else "Change to Dark Mode")
                    }
                }
            }
        }
    }}