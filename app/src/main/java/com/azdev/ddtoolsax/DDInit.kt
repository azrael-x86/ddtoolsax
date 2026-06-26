/**
 * azdev.ddtoolsax
 * tools for directional drillers
 *
 * @author azrael-x86
 * @see DDActivity
 */
package com.azdev.ddtoolsax

// ax imports
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home

// ddtools arc
import com.azdev.ddtoolsax.nav.DDNavHost
import com.azdev.ddtoolsax.ui.theme.DDToolsAXTheme
import com.azdev.ddtoolsax.views.DDView
import com.azdev.ddtoolsax.views.HomeView
import com.azdev.ddtoolsax.views.ViewRegistry

class DDActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        ViewRegistry.register(
            DDView(
                route = "home",
                icon = Icons.Default.Home,
                content = { HomeView() }
            )
        )

        setContent {
            DDToolsAXTheme {
                DDNavHost()
            }
        }
    }
}