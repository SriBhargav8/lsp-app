# PowerShell script to build APK
Write-Host "Building Release APK..."

# Set environment variables
$env:KEYSTORE_PASSWORD = "legalsuvidha123"
$env:KEY_ALIAS = "legal_suvidha"
$env:KEY_PASSWORD = "legalsuvidha123"

# Set Java home
$env:JAVA_HOME = "C:\Program Files\Android\Android Studio\jbr"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"

# Create keystore if it doesn't exist
if (-not (Test-Path "app\legal_suvidha.keystore")) {
    Write-Host "Creating keystore..."
    & "$env:JAVA_HOME\bin\keytool" -genkey -v -keystore "app\legal_suvidha.keystore" -alias legal_suvidha -keyalg RSA -keysize 2048 -validity 10000 -storepass legalsuvidha123 -keypass legalsuvidha123 -dname "CN=Legal Suvidha, OU=Development, O=Legal Suvidha, L=City, ST=State, C=IN"
}

# Build the APK
Write-Host "Building APK..."
& .\gradlew.bat clean
& .\gradlew.bat assembleRelease

Write-Host "Build completed. Check app\build\outputs\apk\release\ for the APK file."
Read-Host "Press Enter to continue" 