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
