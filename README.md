diff --git a/README.md b/README.md
index cafba8fe01d164bc789494f13636ab70324d93e9..a68eb6d4889322a2711e620c7299d92d27c0baa3 100644
--- a/README.md
+++ b/README.md
@@ -1 +1,25 @@
-# kids
\ No newline at end of file
+# Kids Shield (workable MVP)
+
+Kids Shield is now a runnable Android MVP that gives parents a practical control center with:
+
+- **Live compatibility checks** (OS version, device-owner status, vendor limitations, DNS readiness)
+- **Configurable restriction goals** (app installs, unsafe sites, profanity/nudity categories, chat/calls, monitoring)
+- **Generated implementation roadmap** you can copy and apply in an MDM/parential-control stack
+
+## What this app can do now
+- Detect device details and show readiness checks directly on device.
+- Let you configure core safety targets in a simple toggle-based interface.
+- Create a text roadmap that translates selected controls into concrete setup actions.
+
+## Important platform limits
+No Android app can guarantee 100% ad removal and total in-app communication blocking on every device without proper Device Owner enrollment and supporting platform controls. This MVP is designed to be realistic, safe, and fast while preparing the device for maximum available enforcement.
+
+## Run the app
+1. Open in Android Studio.
+2. Sync Gradle.
+3. Run `app` on a real device/emulator.
+
+## Next implementation phase
+- Add managed configuration persistence (DataStore)
+- Add admin receiver + DevicePolicy integration for enforceable restrictions
+- Add backend/reporting export endpoints for parent dashboards
