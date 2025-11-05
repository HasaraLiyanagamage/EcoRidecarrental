# EcoRide Car Rental System

## Overview
EcoRide is a comprehensive car rental management system developed in Java using Object-Oriented Programming principles. The system manages vehicle inventory, customer registrations, bookings, and invoice generation for a medium-sized car rental company in Colombo that provides affordable and eco-friendly vehicles.

## Features

### Core Functionalities
- **Vehicle Management**: Add, update, remove, and manage vehicle availability status
- **Customer Management**: Register customers with NIC/Passport, contact details, and email
- **Booking System**: Create, update, cancel, and search bookings with business rule validation
- **Invoice Generation**: Automated invoice generation with detailed pricing breakdown
- **Search Capabilities**: Search vehicles, customers, and bookings by various criteria

### Business Rules Implementation
-  Refundable deposit of LKR 5,000 charged upon booking
-  Minimum 3-day advance booking requirement
-  2-day cancellation/update deadline from booking date
-  10% discount for rentals of 7 or more days
-  Category-specific pricing, free kilometers, extra km charges, and tax rates
-  Automatic vehicle status management (Available/Reserved/Under Maintenance)

## Vehicle Categories

| Category | Daily Rate | Free KM/Day | Extra KM Charge | Tax Rate |
|----------|-----------|-------------|-----------------|----------|
| Compact Petrol Car | LKR 5,000 | 100 km | LKR 50/km | 10% |
| Hybrid Car | LKR 7,500 | 150 km | LKR 60/km | 12% |
| Electric Car | LKR 10,000 | 200 km | LKR 40/km | 8% |
| Luxury SUV | LKR 15,000 | 250 km | LKR 75/km | 15% |

## System Architecture

### Package Structure
```
src/
├── enums/
│   ├── VehicleCategory.java       # Vehicle category enum with pricing details
│   └── AvailabilityStatus.java    # Vehicle availability status enum
├── models/
│   ├── Vehicle.java                # Vehicle entity class
│   ├── Customer.java               # Customer entity class
│   ├── Booking.java                # Booking entity class
│   └── Invoice.java                # Invoice entity class with pricing logic
├── services/
│   ├── VehicleService.java         # Vehicle management service
│   ├── CustomerService.java        # Customer management service
│   ├── BookingService.java         # Booking management service
│   └── InvoiceService.java         # Invoice generation service
├── tests/
│   └── TestCases.java              # Comprehensive test suite
└── EcoRideCarRentalSystem.java     # Main CLI application
```

### Design Patterns Used
1. **Service Layer Pattern**: Separation of business logic from presentation
2. **Enum Pattern**: Type-safe constants for categories and statuses
3. **Builder Pattern**: Complex object construction (Invoice)
4. **Repository Pattern**: Data management through service classes

## Object-Oriented Programming Concepts

### Encapsulation
- All model classes use private fields with public getters/setters
- Business logic encapsulated within service classes
- Data validation within appropriate classes

### Inheritance
- Enum classes extend Java's Enum type
- Service classes follow common patterns

### Polymorphism
- Method overloading in service classes
- toString() method overriding for custom display

### Abstraction
- Service layer abstracts business logic from UI
- Enum abstracts category-specific pricing details

## Installation & Setup

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Any Java IDE (Eclipse, IntelliJ IDEA, NetBeans) or command line

### Compilation

#### Using Command Line
```bash
# Navigate to the src directory
cd src

# Compile all Java files
javac EcoRideCarRentalSystem.java models/*.java services/*.java enums/*.java

# Run the application
java EcoRideCarRentalSystem

# Run test cases
javac tests/TestCases.java
java tests.TestCases
```

#### Using IDE
1. Import the project into your IDE
2. Set the `src` folder as the source root
3. Run `EcoRideCarRentalSystem.java` as the main class
4. Run `tests/TestCases.java` to execute test suite

## Usage Guide

### Main Menu Options
1. **Vehicle Management**: Manage vehicle inventory
2. **Customer Management**: Register and manage customers
3. **Booking Management**: Create and manage bookings
4. **Invoice Management**: Generate and view invoices
5. **View All Vehicles**: Display complete vehicle list
6. **View Available Vehicles**: Display only available vehicles
7. **View All Bookings**: Display all bookings

### Sample Workflow

#### Creating a Booking
1. Select "Booking Management" from main menu
2. Choose "Create New Booking"
3. Enter customer NIC/Passport (register if new)
4. View available vehicles
5. Enter vehicle ID, dates, and estimated kilometers
6. System validates and creates booking
7. Deposit of LKR 5,000 is charged

#### Generating an Invoice
1. Select "Invoice Management" from main menu
2. Choose "Generate Invoice for Booking"
3. Enter booking ID
4. System calculates and displays detailed invoice with:
   - Base rental charges
   - Extra kilometer charges (if applicable)
   - Discount (if 7+ days)
   - Tax based on vehicle category
   - Final amount after deposit deduction

## Test Cases

The system includes 21 comprehensive test cases covering:

### Vehicle Tests (5 tests)
- Add, update, remove vehicles
- Availability status management
- Vehicle search functionality

### Customer Tests (3 tests)
- Customer registration
- Customer search
- Duplicate registration handling

### Booking Tests (5 tests)
- Booking creation
- Advance booking requirement validation
- Unavailable vehicle rejection
- Booking cancellation
- Booking updates

### Invoice Tests (4 tests)
- Invoice generation
- Pricing without discount
- Pricing with 7+ day discount
- Extra kilometer charges

### Pricing Tests (4 tests)
- Verify pricing for each vehicle category

### Running Tests
```bash
cd src
javac tests/TestCases.java
java tests.TestCases
```

## Sample Data

The system initializes with 8 sample vehicles:
- V001: Toyota Aqua (Compact Petrol)
- V002: Honda Fit (Compact Petrol)
- V003: Toyota Prius (Hybrid)
- V004: Honda Insight (Hybrid)
- V005: Nissan Leaf (Electric)
- V006: Tesla Model 3 (Electric)
- V007: BMW X5 (Luxury SUV)
- V008: Mercedes GLE (Luxury SUV)

## Pricing Calculation Example

### Scenario: 7-day rental, Hybrid Car, 1200 km
```
Base Price: LKR 7,500 × 7 days = LKR 52,500
Free KM: 150 km × 7 days = 1,050 km
Extra KM: 1,200 - 1,050 = 150 km
Extra KM Charge: 150 × LKR 60 = LKR 9,000
Discount (10%): LKR 52,500 × 0.10 = LKR 5,250
Subtotal: LKR 52,500 + LKR 9,000 - LKR 5,250 = LKR 56,250
Tax (12%): LKR 56,250 × 0.12 = LKR 6,750
Total: LKR 56,250 + LKR 6,750 = LKR 63,000
Deposit Deduction: LKR 5,000
Final Amount: LKR 58,000
```

## Key Design Decisions

### 1. Enum for Categories
Using enums for `VehicleCategory` ensures type safety and encapsulates all category-specific pricing details in one place, following the DRY principle.

### 2. Service Layer Architecture
Separating business logic into service classes makes the system:
- More maintainable
- Easier to test
- Follows Single Responsibility Principle
- Allows for future expansion (e.g., database integration)

### 3. Date Validation
Using Java's `LocalDate` and `ChronoUnit` provides robust date handling and validation for booking requirements.

### 4. Invoice Calculation
Invoice pricing is calculated automatically upon creation, ensuring consistency and reducing errors. All calculations follow the specified business rules.

### 5. Status Management
Automatic vehicle status updates when bookings are created or cancelled ensures data consistency.

## Error Handling

The system includes comprehensive error handling for:
- Invalid date formats
- Booking validation failures
- Duplicate vehicle IDs
- Non-existent entities
- Business rule violations

## Future Enhancements

Potential improvements for future versions:
1. Database integration (MySQL/PostgreSQL)
2. GUI using JavaFX or Swing
3. Payment gateway integration
4. Email notifications
5. Reporting and analytics
6. Multi-branch support
7. User authentication and authorization
8. Booking history and customer loyalty programs

## Author Information

**Module**: CI6115 Programming III - Patterns and Algorithms  
**Institution**: Kingston University, BSc (Hons)  
**Assignment**: Coursework 1 - Car Rental System  
**Name**: Hasara Sesadi

**Note**: This system demonstrates the application of OOP principles, design patterns, and software engineering best practices in developing a real-world business application.
# EcoRidecarrental
