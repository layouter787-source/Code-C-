# Code C++

Android IDE for developing C++ applications for Android phones and tablets. The IDE itself is written in Kotlin; projects created by users are C++.

## Included in this first buildable foundation
- Kotlin Android app and dark editor UI.
- Open and save source files using Android's document picker.
- C++ sample project template.
- GitHub Actions workflow to build the IDE debug APK.

## Important status
This is the initial foundation, not yet a complete C++ toolchain. The Clang/LLVM compiler, Android linker/runtime integration, real terminal, C++ language server, debugger, and on-device creation of arbitrary APKs are not yet implemented. The Build button must not claim success until the real toolchain has run.

## Build the IDE APK
Open Actions and run Build Code C++ Android APK. Download the CodeCpp-debug-apk artifact after a successful workflow. This builds the IDE itself, not arbitrary C++ projects created inside it.

## Open in CodeAssist
Import as an Android/Gradle project in CodeAssist 3.29.0. If the native importer cannot parse Gradle, create a blank Kotlin Android project and copy app/src/main and the app configuration.

## Technical direction
The intended backend is Clang/LLVM plus Android NDK-compatible linker/runtime components, orchestrated by Kotlin. Application ID: com.lay.codecpp. Minimum Android API: 26. Target API: 35. The NDK plugin in CodeAssist is experimental and must not be treated as an embeddable library without verifying its APIs and license.
