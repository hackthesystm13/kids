
# Kids Shield

**Practical Android Parental Control MVP**

Kids Shield is a functional Android MVP designed to give parents a clear, realistic control center for managing device safety. It focuses on what is technically enforceable on modern Android devices while preparing the system for deeper policy integration.

---

## Core Capabilities

### Live Device Readiness Checks

* Android OS version verification
* Device Owner status detection
* Vendor-specific policy limitations
* DNS configuration readiness

These checks provide immediate insight into what level of enforcement is technically possible on the current device.

### Configurable Safety Controls

Parents can define restriction goals through a simple toggle-based interface, including:

* App installation restrictions
* Unsafe website blocking
* Profanity and nudity content filtering targets
* Chat and call limitations
* Monitoring configuration preferences

### Automated Implementation Roadmap

Based on selected controls, the app generates a structured, step-by-step implementation guide.
This roadmap translates user goals into actionable setup instructions suitable for MDM or parental-control deployment environments.

---

## Platform Realities & Enforcement Limits

Android does not allow complete ad removal or total in-app communication blocking across all devices without proper Device Owner enrollment and system-level controls.

Kids Shield is intentionally designed around platform realities. It prioritizes:

* Enforceable policy paths
* Transparent capability reporting
* Maximum protection within Android’s security model

The goal is practical control—not unrealistic promises.

---

## Running the Application

1. Open the project in Android Studio.
2. Sync Gradle dependencies.
3. Launch the `app` module on a physical device or emulator.

---

## Next Development Phase

* Implement persistent managed configuration via DataStore
* Integrate DevicePolicyManager with an Admin Receiver for enforceable restrictions
* Add backend endpoints for reporting and parent dashboard exports


