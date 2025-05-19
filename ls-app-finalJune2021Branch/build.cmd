@echo off
echo Building Release APK...

:: Set environment variables
set KEYSTORE_PASSWORD=legalsuvidha123
set KEY_ALIAS=legal_suvidha
set KEY_PASSWORD=legalsuvidha123

:: Set paths
set "JAVA_HOME=C:\Program Files\Android\Android Studio\jbr"
set "PATH=%JAVA_HOME%\bin;%PATH%"

:: Create keystore if it doesn't exist
if not exist "app\legal_suvidha.keystore" (
    echo Creating keystore...
    "%JAVA_HOME%\bin\keytool" -genkey -v -keystore "app\legal_suvidha.keystore" -alias legal_suvidha -keyalg RSA -keysize 2048 -validity 10000 -storepass legalsuvidha123 -keypass legalsuvidha123 -dname "CN=Legal Suvidha, OU=Development, O=Legal Suvidha, L=City, ST=State, C=IN"
)

:: Build the APK
echo Building APK...
gradlew.bat clean
gradlew.bat assembleRelease

echo Build completed. Check app\build\outputs\apk\release\ for the APK file.
pause 