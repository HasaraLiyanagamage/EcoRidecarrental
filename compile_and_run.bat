@echo off
REM EcoRide Car Rental System - Compilation and Execution Script for Windows

echo ╔════════════════════════════════════════════════════════════════╗
echo ║           EcoRide Car Rental System - Build Script            ║
echo ╚════════════════════════════════════════════════════════════════╝
echo.

echo  Compiling Java files...
echo.

REM Compile all Java files with proper classpath
javac -cp src src\EcoRideCarRentalSystem.java src\models\*.java src\services\*.java src\enums\*.java src\tests\*.java

REM Check if compilation was successful
if %errorlevel% equ 0 (
    echo  Compilation successful!
    echo.
    echo Choose an option:
    echo 1. Run Main Application
    echo 2. Run Test Suite
    echo 3. Exit
    echo.
    set /p choice="Enter your choice (1-3): "
    
    if "%choice%"=="1" (
        echo.
        echo  Starting EcoRide Car Rental System...
        echo.
        java -cp src EcoRideCarRentalSystem
    ) else if "%choice%"=="2" (
        echo.
        echo  Running Test Suite...
        echo.
        java -cp src tests.TestCases
    ) else if "%choice%"=="3" (
        echo Exiting...
    ) else (
        echo  Invalid choice
    )
) else (
    echo  Compilation failed! Please check for errors.
    pause
    exit /b 1
)

pause
