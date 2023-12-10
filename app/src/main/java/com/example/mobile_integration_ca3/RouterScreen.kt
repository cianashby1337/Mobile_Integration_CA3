package com.example.mobile_integration_ca3
import androidx.navigation.compose.NavHost
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
fun RootAppBar(
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootApp(
    viewModel: AppViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AppScreen.valueOf(
        backStackEntry?.destination?.route ?: AppScreen.Home.name
    )
    val uiState by viewModel.uiState.collectAsState()
    Mobile_Integration_CA3Theme (darkTheme = uiState.isDark) {
        Scaffold(
            topBar = {
                RootAppBar(
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
                        }
                    )
                }
                composable(route = AppScreen.Settings.name) {
                    SettingsScreen(
                        darkTheme = uiState.isDark
                    )
                    Button(onClick = { viewModel.updateIsDark() }) {
                        Text(text = if (uiState.isDark) "Change to Light Mode" else "Change to Dark Mode")
                    }
                }
            }
        }
    }
}