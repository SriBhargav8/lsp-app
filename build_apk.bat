@echo off
echo Building Release APK...

set KEYSTORE_PASSWORD=legalsuvidha123
set KEY_ALIAS=legal_suvidha
set KEY_PASSWORD=legalsuvidha123

call gradlew.bat clean
call gradlew.bat assembleRelease

echo Build completed. Check app/build/outputs/apk/release/ for the APK file.
pause 