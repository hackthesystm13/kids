package com.example.kidsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KidsApp()
        }
    }
}

@Composable
fun KidsApp() {
    val screenState = remember { mutableStateOf(AppScreen.Compatibility) }

    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Kids Shield Setup",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = "Start with compatibility checks, then move to MDM enrollment if supported.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.padding(8.dp))

            when (screenState.value) {
                AppScreen.Compatibility -> CompatibilityScreen(
                    onContinue = { screenState.value = AppScreen.MdmSetup }
                )
                AppScreen.MdmSetup -> MdmSetupScreen(
                    onBack = { screenState.value = AppScreen.Compatibility }
                )
            }
        }
    }
}

private enum class AppScreen {
    Compatibility,
    MdmSetup
}

@Composable
fun CompatibilityScreen(onContinue: () -> Unit) {
    val steps = listOf(
        StepData(
            title = "Identify OS and device model",
            detail = "Capture manufacturer, model, and OS version. Flag Fire OS devices early."
        ),
        StepData(
            title = "Check Android version",
            detail = "Recommend Android 9+. Android 8.1 is possible but has fewer controls."
        ),
        StepData(
            title = "Verify device-owner eligibility",
            detail = "Device must be factory reset or freshly provisioned for Device Owner mode."
        ),
        StepData(
            title = "Assess manufacturer restrictions",
            detail = "Some vendors (including Amazon Fire) do not allow full device-owner control."
        ),
        StepData(
            title = "Network filtering feasibility",
            detail = "Confirm private DNS or managed VPN/DNS can be installed on the device."
        ),
        StepData(
            title = "Security constraints summary",
            detail = "If Device Owner mode is unavailable, ad blocking and chat blocking are partial."
        )
    )

    StepList(
        title = "Compatibility Check",
        description = "Stop setup if any critical requirement fails and show a limitation report.",
        steps = steps
    )

    Spacer(modifier = Modifier.padding(8.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Button(onClick = onContinue) {
            Text("Continue to MDM Setup")
        }
    }
}

@Composable
fun MdmSetupScreen(onBack: () -> Unit) {
    val steps = listOf(
        StepData(
            title = "Back up user data",
            detail = "Explain that Device Owner enrollment requires a factory reset."
        ),
        StepData(
            title = "Factory reset",
            detail = "Reset and complete the first-boot wizard without adding accounts yet."
        ),
        StepData(
            title = "Provision as Device Owner",
            detail = "Use QR code or DPC/EMM provisioning during initial setup."
        ),
        StepData(
            title = "Apply baseline policies",
            detail = "Disable unknown sources, restrict installs, and lock down browser access."
        ),
        StepData(
            title = "Configure network filtering",
            detail = "Install managed DNS/VPN (NextDNS or AdGuard) and validate blocking."
        ),
        StepData(
            title = "Verify enforcement",
            detail = "Attempt blocked actions and confirm policies persist after reboot."
        )
    )

    StepList(
        title = "MDM (Device Owner) Setup",
        description = "Only proceed if compatibility checks confirm device-owner support.",
        steps = steps
    )

    Spacer(modifier = Modifier.padding(8.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(onClick = onBack) {
            Text("Back")
        }
        Text(
            text = "Fallback to Family Link or Amazon Parent Dashboard if MDM fails.",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Composable
private fun StepList(
    title: String,
    description: String,
    steps: List<StepData>
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Medium
    )
    Spacer(modifier = Modifier.padding(2.dp))
    Text(
        text = description,
        style = MaterialTheme.typography.bodyMedium
    )
    Spacer(modifier = Modifier.padding(8.dp))

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(steps) { step ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = step.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    Text(
                        text = step.detail,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
