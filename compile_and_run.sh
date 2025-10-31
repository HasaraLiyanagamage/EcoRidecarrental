#!/bin/bash

# EcoRide Car Rental System - Compilation and Execution Script

echo "╔════════════════════════════════════════════════════════════════╗"
echo "║           EcoRide Car Rental System - Build Script            ║"
echo "╚════════════════════════════════════════════════════════════════╝"
echo ""

echo "📦 Compiling Java files..."
echo ""

# Compile all Java files with proper classpath
javac -cp src src/EcoRideCarRentalSystem.java src/models/*.java src/services/*.java src/enums/*.java src/tests/*.java

# Check if compilation was successful
if [ $? -eq 0 ]; then
    echo "✅ Compilation successful!"
    echo ""
    echo "Choose an option:"
    echo "1. Run Main Application"
    echo "2. Run Test Suite"
    echo "3. Exit"
    echo ""
    read -p "Enter your choice (1-3): " choice
    
    case $choice in
        1)
            echo ""
            echo "🚀 Starting EcoRide Car Rental System..."
            echo ""
            java -cp src EcoRideCarRentalSystem
            ;;
        2)
            echo ""
            echo "🧪 Running Test Suite..."
            echo ""
            java -cp src tests.TestCases
            ;;
        3)
            echo "Exiting..."
            ;;
        *)
            echo "❌ Invalid choice"
            ;;
    esac
else
    echo "❌ Compilation failed! Please check for errors."
    exit 1
fi
