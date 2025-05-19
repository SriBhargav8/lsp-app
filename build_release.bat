@echo off
echo ===================================
echo    Building Legal Suvidha APK
echo ===================================
echo.

:: Set environment variables
echo Setting up environment variables...
set KEYSTORE_PASSWORD=legalsuvidha123
set KEY_ALIAS=legal_suvidha
set KEY_PASSWORD=legalsuvidha123

:: Set Java home and Android SDK paths
echo Setting up Java and Android SDK environment...
set "ANDROID_SDK=C:\Users\sribh\AppData\Local\Android\Sdk"
set "JAVA_HOME=%ANDROID_SDK%\jre"
set "PATH=%JAVA_HOME%\bin;%PATH%"

:: Create app directory if it doesn't exist
if not exist "app" mkdir app

:: Create keystore if it doesn't exist
if not exist "app\legal_suvidha.keystore" (
    echo.
    echo Creating keystore file...
    "%JAVA_HOME%\bin\keytool" -genkey -v -keystore "app\legal_suvidha.keystore" -alias legal_suvidha -keyalg RSA -keysize 2048 -validity 10000 -storepass legalsuvidha123 -keypass legalsuvidha123 -dname "CN=Legal Suvidha, OU=Development, O=Legal Suvidha, L=City, ST=State, C=IN"
    if errorlevel 1 (
        echo Error creating keystore!
        echo Please make sure Java is installed and the path is correct.
        pause
        exit /b 1
    )
)

:: Build the APK
echo.
echo Starting APK build process...
echo Step 1: Cleaning project...
call gradlew.bat clean
if errorlevel 1 (
    echo Error during clean!
    pause
    exit /b 1
)

echo.
echo Step 2: Building release APK...
call gradlew.bat assembleRelease
if errorlevel 1 (
    echo Error during build!
    pause
    exit /b 1
)

echo.
echo ===================================
echo Build completed successfully!
echo.
echo Your APK is located at:
echo app\build\outputs\apk\release\app-release.apk
echo ===================================
echo.
echo Press any key to exit...
pause > nul 