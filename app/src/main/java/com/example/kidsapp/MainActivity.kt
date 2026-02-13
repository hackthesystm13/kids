package com.example.kidsapp

import android.app.admin.DevicePolicyManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { KidsShieldApp() }
    }
}

private enum class AppScreen { Compatibility, Controls, Roadmap }

data class CompatibilityCheck(val name: String, val passed: Boolean, val detail: String)

data class RestrictionState(
    val blockAppInstalls: Boolean = true,
    val blockUnsafeWebsites: Boolean = true,
    val blockProfanity: Boolean = true,
    val blockNudity: Boolean = true,
    val blockCommunicationApps: Boolean = true,
    val blockInGameChat: Boolean = true,
    val blockCallsAndVoip: Boolean = true,
    val activityMonitoring: Boolean = true
)

@Composable
fun KidsShieldApp() {
    val context = LocalContext.current
    var screen by remember { mutableStateOf(AppScreen.Compatibility) }
    var restrictions by remember { mutableStateOf(RestrictionState()) }
    val checks = remember { buildCompatibilityChecks(context) }

    Surface(color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text("Kids Shield", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text("Parent setup and enforcement planner", style = MaterialTheme.typography.bodyMedium)
            Divider(modifier = Modifier.padding(vertical = 10.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = { screen = AppScreen.Compatibility }) { Text("1. Compatibility") }
                OutlinedButton(onClick = { screen = AppScreen.Controls }) { Text("2. Controls") }
                OutlinedButton(onClick = { screen = AppScreen.Roadmap }) { Text("3. Roadmap") }
            }

            when (screen) {
                AppScreen.Compatibility -> CompatibilityScreen(checks)
                AppScreen.Controls -> ControlsScreen(restrictions) { restrictions = it }
                AppScreen.Roadmap -> RoadmapScreen(checks, restrictions)
            }
        }
    }
}

private fun buildCompatibilityChecks(context: Context): List<CompatibilityCheck> {
    val dpm = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
    val packageName = context.packageName
    val isAmazon = Build.MANUFACTURER.contains("amazon", ignoreCase = true)
    val android9OrHigher = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
    val privateDnsSet = Settings.Global.getString(context.contentResolver, "private_dns_mode") != null
    val deviceOwnerReady = dpm.isDeviceOwnerApp(packageName)

    return listOf(
        CompatibilityCheck(
            name = "OS and model detected",
            passed = true,
            detail = "${Build.MANUFACTURER} ${Build.MODEL} • Android ${Build.VERSION.RELEASE}"
        ),
        CompatibilityCheck(
            name = "Recommended Android version (9+)",
            passed = android9OrHigher,
            detail = if (android9OrHigher) "Version supports stronger controls." else "Upgrade recommended for reliable policy control."
        ),
        CompatibilityCheck(
            name = "MDM Device Owner active",
            passed = deviceOwnerReady,
            detail = if (deviceOwnerReady) "Device Owner is active for this app." else "Not active. Full lockdown requires provisioning as Device Owner after reset."
        ),
        CompatibilityCheck(
            name = "Manufacturer allows full policy control",
            passed = !isAmazon,
            detail = if (isAmazon) "Amazon/Fire devices may limit MDM APIs." else "No known vendor-level limitation detected."
        ),
        CompatibilityCheck(
            name = "Private DNS configured",
            passed = privateDnsSet,
            detail = if (privateDnsSet) "DNS filtering can be applied." else "Set private DNS or managed VPN for network filtering."
        )
    )
}

@Composable
private fun CompatibilityScreen(checks: List<CompatibilityCheck>) {
    val passed = checks.count { it.passed }
    val total = checks.size
    Text("Compatibility Results", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
    Text("$passed / $total checks passed", style = MaterialTheme.typography.bodyMedium)

    LazyColumn(contentPadding = PaddingValues(top = 12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(checks) { check ->
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
                Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
                    Text((if (check.passed) "✅ " else "⚠️ ") + check.name, fontWeight = FontWeight.Medium)
                    Text(check.detail, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Composable
private fun ControlsScreen(state: RestrictionState, onChange: (RestrictionState) -> Unit) {
    Text("Restriction Controls", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
    Text("Choose the enforcement goals for your child profile.")

    val items = listOf(
        "Block new app installs" to state.blockAppInstalls,
        "Block unsafe websites" to state.blockUnsafeWebsites,
        "Block profanity" to state.blockProfanity,
        "Block nudity" to state.blockNudity,
        "Block communication apps" to state.blockCommunicationApps,
        "Block in-game chats" to state.blockInGameChat,
        "Block calls / VoIP" to state.blockCallsAndVoip,
        "Enable activity monitoring" to state.activityMonitoring
    )

    LazyColumn(contentPadding = PaddingValues(top = 10.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(items) { (label, enabled) ->
            Card {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(label, modifier = Modifier.weight(1f))
                    Switch(
                        checked = enabled,
                        onCheckedChange = { checked ->
                            onChange(
                                when (label) {
                                    "Block new app installs" -> state.copy(blockAppInstalls = checked)
                                    "Block unsafe websites" -> state.copy(blockUnsafeWebsites = checked)
                                    "Block profanity" -> state.copy(blockProfanity = checked)
                                    "Block nudity" -> state.copy(blockNudity = checked)
                                    "Block communication apps" -> state.copy(blockCommunicationApps = checked)
                                    "Block in-game chats" -> state.copy(blockInGameChat = checked)
                                    "Block calls / VoIP" -> state.copy(blockCallsAndVoip = checked)
                                    else -> state.copy(activityMonitoring = checked)
                                }
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun RoadmapScreen(checks: List<CompatibilityCheck>, state: RestrictionState) {
    val context = LocalContext.current
    val roadmap = remember(checks, state) { buildRoadmap(checks, state) }

    Text("Implementation Roadmap", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
    Text("Copy this plan and follow it in your MDM/admin tool.")

    Card(modifier = Modifier.fillMaxSize().padding(top = 12.dp)) {
        LazyColumn(modifier = Modifier.fillMaxSize().padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            items(roadmap) { line -> Text(line, style = MaterialTheme.typography.bodySmall) }
            item {
                Button(onClick = {
                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    clipboard.setPrimaryClip(ClipData.newPlainText("Kids Shield Plan", roadmap.joinToString("\n")))
                }) {
                    Text("Copy roadmap")
                }
            }
        }
    }
}

private fun buildRoadmap(checks: List<CompatibilityCheck>, state: RestrictionState): List<String> {
    val steps = mutableListOf<String>()
    steps += "1) Verify compatibility failures and remediate first."
    checks.filter { !it.passed }.forEach { steps += " - Fix: ${it.name} => ${it.detail}" }
    steps += "2) If MDM Device Owner is not active, factory-reset and provision in Device Owner mode."
    steps += "3) Apply policies for selected controls:"
    if (state.blockAppInstalls) steps += " - Restrict app installs via managed Play/Amazon controls."
    if (state.blockUnsafeWebsites) steps += " - Configure DNS/web filtering categories (malware, adult, gambling, trackers)."
    if (state.blockProfanity) steps += " - Enable safe-search and content filtering rules in browser + DNS provider."
    if (state.blockNudity) steps += " - Block adult content category and lock browser to approved sites where possible."
    if (state.blockCommunicationApps) steps += " - Block chat/social apps and disable sideloading."
    if (state.blockInGameChat) steps += " - Allowlist games without chat or enforce app-level parental settings."
    if (state.blockCallsAndVoip) steps += " - Disable dialer/VoIP apps and deny microphone permissions for non-approved apps."
    if (state.activityMonitoring) steps += " - Enable activity reports and export daily summaries from parental dashboard."
    steps += "4) Validate by attempting blocked actions and documenting results."
    steps += "5) Re-check after reboot and weekly policy audits."
    return steps
}
