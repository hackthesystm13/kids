diff --git a/README.md b/README.md
index cafba8fe01d164bc789494f13636ab70324d93e9..d2ea0704c75608b2a746f063fe48ba0e875bb203 100644
--- a/README.md
+++ b/README.md
@@ -1 +1,44 @@
-# kids
\ No newline at end of file
+# kids
+
+## Compatibility check (before install/setup)
+Use these steps at the start of the app to determine if the device can support the requested controls.
+
+1. **Identify OS and device model**
+   - Capture `manufacturer`, `model`, and `OS version` (Android vs Fire OS).
+   - Flag Fire OS devices early; some device-owner APIs are unavailable or limited.
+2. **Check Android version**
+   - Minimum recommended: **Android 9+** (Android 8.1+ if you must, but some controls are weaker).
+3. **Verify device-owner (MDM) eligibility**
+   - Device must be **factory-reset** or **freshly provisioned** to enroll as Device Owner.
+   - If the device is already in use and cannot be wiped, device-owner enrollment is **not possible**.
+4. **Assess manufacturer restrictions**
+   - Some vendors (including Amazon Fire) **do not allow** full device-owner control.
+5. **Network filtering feasibility**
+   - If DNS filtering is planned, confirm the device can apply a **private DNS** or a **managed VPN/DNS** profile.
+6. **Security constraints summary**
+   - If device-owner (MDM) mode is **not** available, set expectations: ad blocking and in-app communication blocking will be partial.
+
+If the device fails any requirement, stop and show a clear compatibility report with a list of limitations and the best-possible fallback options.
+
+## MDM (Device Owner) mode setup steps
+Only proceed if compatibility checks confirm device-owner support. These steps should appear immediately after the compatibility screen.
+
+1. **Back up user data**
+   - Inform the parent that device-owner enrollment requires a **factory reset**.
+2. **Factory reset**
+   - Reset the tablet and complete the first-boot wizard without personal Google/Amazon account setup yet.
+3. **Provision as Device Owner**
+   - Use **QR code provisioning** or **DPC/EMM provisioning** during initial setup.
+   - Enroll in the chosen MDM platform (e.g., Intune, Mosyle, Hexnode, ManageEngine).
+4. **Apply baseline policies**
+   - Disable unknown sources and restrict new app installs.
+   - Lock down browser/web access to a safe list.
+   - Disable communication apps, in-app chat permissions, and microphone access where supported.
+5. **Configure network filtering**
+   - Install the managed DNS/VPN profile (NextDNS/AdGuard/MDM DNS settings).
+   - Validate that blocked categories and ad filters are active.
+6. **Verify enforcement**
+   - Attempt to install a blocked app or open a blocked site.
+   - Confirm policies persist after reboot.
+
+If MDM provisioning fails, fall back to standard parental controls (Family Link or Amazon Parent Dashboard) and document the reduced control scope.
