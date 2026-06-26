package com.azdev.ddtoolsax.views

// layout

// material icons

// m3 packages

// compose and state management

// ui packages
import android.widget.GridLayout
import androidx.collection.MutableObjectList
import androidx.collection.mutableObjectListOf
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

class DDView(
    val route: String,
    val icon: ImageVector,
    val content: @Composable () -> Unit,
    val onCreated: () -> Unit = {},
    val onDestroyed: () -> Unit = {}
) {
    // You can add your "mutable data" here for the view to use
    var stateData: Any? = null
}

// A global registry to manage all your instances
object ViewRegistry {
    var views: MutableObjectList<DDView> = mutableObjectListOf<DDView>()

    fun register(view: DDView) {
        views.add(view)
    }
}

@Composable
fun ContactPopUp(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Developer Info") },
        text = {
            Text(
                text = "version: pa\n" +
                        "contact: @azrael-x86\n" +
                        "built for directional drilling."
            )
        },
        confirmButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Close")
            }
        }
    )
}

/////////////////
//// VIEWS /////
/////////////////

@Composable
fun HomeView() {
    var showInfo by remember { mutableStateOf(false) }

    if (showInfo) {
        ContactPopUp(onDismiss = { showInfo = false })
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "DDToolsAX Dashboard",
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )

        IconButton(
            onClick = { showInfo = true },
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .wrapContentSize(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Default.HelpOutline,
                contentDescription = "Help",
                tint = Color.White.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun CalcView() {
    // ...

   Box(){
       Text("calc")
   }
}