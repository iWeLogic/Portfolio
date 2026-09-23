# Dialog Recipe

This recipe demonstrates how to display a destination as a dialog.

## How it works

To show a destination as a dialog, you need to do two things:

1. **Use `DialogSceneStrategy`** : Create an instance of `DialogSceneStrategy` and pass it to the `sceneStrategy` parameter of the `NavDisplay` composable.

2. **Add metadata to the destination** : For the destination that you want to display as a dialog, add `DialogSceneStrategy.dialog()` to its metadata. This is done in the `entry` function. You can also pass a `DialogProperties` object to customize the dialog's behavior and appearance.

In this example, `RouteB` is configured to be a dialog. When you navigate from `RouteA` to `RouteB`, `RouteB` will be displayed in a dialog window.

The content of the dialog can be styled as needed. In this recipe, the content is clipped to have rounded corners.

For more information, see the official documentation on [custom layouts](https://developer.android.com/guide/navigation/navigation-3/custom-layouts).
[![](https://developer.android.com/static/images/picto-icons/code.svg) Explore View the full recipe on GitHub.](https://github.com/android/nav3-recipes/tree/main/app/src/main/java/com/example/nav3recipes/dialog)

```
package com.example.nav3recipes.dialog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.scene.DialogSceneStrategy
import androidx.navigation3.ui.NavDisplay
import com.example.nav3recipes.content.ContentBlue
import com.example.nav3recipes.content.ContentGreen
import com.example.nav3recipes.ui.setEdgeToEdgeConfig
import kotlinx.serialization.Serializable

@Serializable
private data object RouteA : NavKey

@Serializable
private data class RouteB(val id: String) : NavKey

class DialogActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setEdgeToEdgeConfig()
        super.onCreate(savedInstanceState)
        setContent {
            val backStack = rememberNavBackStack(RouteA)
            val dialogStrategy = remember { DialogSceneStrategy<NavKey>() }

            NavDisplay(
                backStack = backStack,
                onBack = { backStack.removeLastOrNull() },
                sceneStrategies = listOf(dialogStrategy),
                entryProvider = entryProvider {
                    entry<RouteA> {
                        ContentGreen("Welcome to Nav3") {
                            Button(onClick = dropUnlessResumed {
                                backStack.add(RouteB("123"))
                            }) {
                                Text("Click to open dialog")
                            }
                        }
                    }
                    entry<RouteB>(
                        metadata = DialogSceneStrategy.dialog(
                            DialogProperties(windowTitle = "Route B dialog")
                        )
                    ) { key ->
                        ContentBlue(
                            title = "Route id: ${key.id}",
                            modifier = Modifier.clip(
                                shape = RoundedCornerShape(16.dp)
                            )
                        )
                    }
                }
            )
        }
    }
}
```