/**
 * ddtoolsax.nav
 * - handles view navigation and user state management
 *
 * @author azrael-x86
 *
 * 06/2026 azdev
 */
package com.azdev.ddtoolsax.nav

// layout
import android.R.attr.alpha
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding

// material icons
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home

// m3 packages
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text

// composable + state mgmt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment

// ui packages
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp

// compose nav
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

// views
import com.azdev.ddtoolsax.views.DDView
import com.azdev.ddtoolsax.views.HomeView
import com.azdev.ddtoolsax.views.ViewRegistry

@Composable
fun DDNavHost() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "home"

    Scaffold(
        containerColor = Color(0xFF121212),
        topBar = {
            // Slim Top Bar
            Surface(
                color = Color.Transparent,
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Box(contentAlignment = Alignment.TopCenter, modifier = Modifier.fillMaxWidth().background(Color(0xFF1A1A1A)).statusBarsPadding()) {
                    Text(
                        text = currentRoute,
                        color = Color.White.copy(alpha = 0.8f),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        bottomBar = {
            NavigationBar(containerColor = Color(0xFF1A1A1A), tonalElevation = 10.dp) {
                // This is your requested procedural composition
                ViewRegistry.views.forEach { view : DDView ->
                    ViewRegistry.views.forEach { view ->
                        NavigationBarItem(
                            icon = { Icon(view.icon, contentDescription = view.route, modifier = Modifier.padding(5.dp))},
                            selected = currentRoute == view.route,
                            onClick = { if (navController.currentDestination?.route == view.route) { return@NavigationBarItem } else { navController.navigate(view.route)}},
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.White,
                                unselectedIconColor = Color.White.copy(alpha = 0.4f),
                                indicatorColor = Color.Transparent
                            ),
                            modifier = Modifier.padding(0.dp),
                            label = null
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            Icon(
                imageVector = Icons.Default.Explore,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.05f), // Very subtle
                modifier = Modifier
                    .size(300.dp)
                    .align(Alignment.Center),
                )

            NavHost(
                navController,
                startDestination = "home",
                modifier = Modifier.padding(0.dp)
            ) {
                ViewRegistry.views.forEach { view: DDView ->
                    composable(view.route) {
                        // Lifecycle Hooks
                        DisposableEffect(Unit) {
                            view.onCreated()
                            onDispose { view.onDestroyed() }
                        }
                        view.content()
                    }
                }
            }
        }
    }
}