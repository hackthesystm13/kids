# Kids Shield (workable MVP)

Kids Shield is now a runnable Android MVP that gives parents a practical control center with:

- **Live compatibility checks** (OS version, device-owner status, vendor limitations, DNS readiness)
- **Configurable restriction goals** (app installs, unsafe sites, profanity/nudity categories, chat/calls, monitoring)
- **Generated implementation roadmap** you can copy and apply in an MDM/parential-control stack

## What this app can do now
- Detect device details and show readiness checks directly on device.
- Let you configure core safety targets in a simple toggle-based interface.
- Create a text roadmap that translates selected controls into concrete setup actions.

## Important platform limits
No Android app can guarantee 100% ad removal and total in-app communication blocking on every device without proper Device Owner enrollment and supporting platform controls. This MVP is designed to be realistic, safe, and fast while preparing the device for maximum available enforcement.

## Run the app
1. Open in Android Studio.
2. Sync Gradle.
3. Run `app` on a real device/emulator.

## Next implementation phase
- Add managed configuration persistence (DataStore)
- Add admin receiver + DevicePolicy integration for enforceable restrictions
- Add backend/reporting export endpoints for parent dashboards
# Kids Shield (starter app)

This repository now includes a minimal Android app that demonstrates the **compatibility check** flow and the **MDM (Device Owner) setup** steps you asked for. It is not a fully functional blocking/monitoring solution yet, but it provides the structure and screens to guide parents through the required device checks and enrollment prerequisites.

## What the app does today
- Shows a **Compatibility Check** screen with required checks and warnings.
- Shows an **MDM Setup** screen with enrollment steps and fallback guidance.

## Next steps for full functionality
To implement actual enforcement (blocking apps, filtering web, disabling chats, etc.), the app will need:
- Device Owner (MDM) enrollment workflows and an MDM backend.
- Policy enforcement via Device Policy APIs and app restrictions.
- A managed DNS or VPN configuration for web and ad filtering.

## How to open
1. Open the project in Android Studio.
2. Let Gradle sync.
3. Run the `app` configuration on a connected device or emulator.

## Compatibility check (before install/setup)
Use these steps at the start of the app to determine if the device can support the requested controls.

1. **Identify OS and device model**
   - Capture `manufacturer`, `model`, and `OS version` (Android vs Fire OS).
   - Flag Fire OS devices early; some device-owner APIs are unavailable or limited.
2. **Check Android version**
   - Minimum recommended: **Android 9+** (Android 8.1+ if you must, but some controls are weaker).
3. **Verify device-owner (MDM) eligibility**
   -  Device must be **factory-reset** or **freshly provisioned** to enroll as Device Owner.
   - If the device is already in use and cannot be wiped, device-owner enrollment is **not possible**.
4. **Assess manufacturer restrictions**
   - Some vendors (including Amazon Fire) **do not allow** full device-owner control.
5. **Network filtering feasibility**
   - If DNS filtering is planned, confirm the device can apply a **private DNS** or a **managed VPN/DNS** profile.
6. **Security constraints summary**
   - If device-owner (MDM) mode is **not** available, set expectations: ad blocking and in-app communication blocking will be partial.

If the device fails any requirement, stop and show a clear compatibility report with a list of limitations and the best-possible fallback options.

# MDM (Device Owner) mode setup steps
Only proceed if compatibility checks confirm device-owner support. These steps should appear immediately after the compatibility screen.
1. **Back up user data**
   - Inform the parent that device-owner enrollment requires a **factory reset**.
2. **Factory reset**
   - Reset the tablet and complete the first-boot wizard without personal Google/Amazon account setup yet.
3. **Provision as Device Owner**
   - Use **QR code provisioning** or **DPC/EMM provisioning** during initial setup.
   - Enroll in the chosen MDM platform (e.g., Intune, Mosyle, Hexnode, ManageEngine).
4. **Apply baseline policies**
   - Disable unknown sources and restrict new app installs.
   - Lock down browser/web access to a safe list.
    - Disable communication apps, in-app chat permissions, and microphone access where supported.
5. **Configure network filtering**
   - Install the managed DNS/VPN profile (NextDNS/AdGuard/MDM DNS settings).
   - Validate that blocked categories and ad filters are active.
6. **Verify enforcement**
   - Attempt to install a blocked app or open a blocked site.
   - Confirm policies persist after reboot.

+If MDM provisioning fails, fall back to standard parental controls (Family Link or Amazon Parent Dashboard) and document the reduced control scope.
