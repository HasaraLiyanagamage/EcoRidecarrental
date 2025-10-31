# EcoRide Car Rental System - Quick Start Guide

## Getting Started in 5 Minutes

### Step 1: Verify Java Installation
```bash
java -version
javac -version
```
You need Java 8 or higher.

### Step 2: Navigate to Project Directory
```bash
cd /Users/hasaraliyanagamage/Desktop/ProgramingIII/CW1/Carrental
```

### Step 3: Compile the Project

#### Option A: Using the Build Script (Recommended)
**On macOS/Linux:**
```bash
chmod +x compile_and_run.sh
./compile_and_run.sh
```

**On Windows:**
```bash
compile_and_run.bat
```

#### Option B: Manual Compilation
```bash
cd src
javac EcoRideCarRentalSystem.java models/*.java services/*.java enums/*.java
java EcoRideCarRentalSystem
```

### Step 4: Run the Application
If you used manual compilation:
```bash
java EcoRideCarRentalSystem
```

---

## First Time Usage Tutorial

### Scenario: Complete a Full Booking

#### 1. Start the Application
You'll see the welcome banner and main menu.

#### 2. View Available Vehicles
- Select option **6** (View Available Vehicles)
- Note down a vehicle ID (e.g., V001)

#### 3. Create a Booking
- Go back to main menu
- Select option **3** (Booking Management)
- Select option **1** (Create New Booking)

**Enter the following:**
- Customer NIC/Passport: `123456789V`
- Name: `John Doe`
- Contact: `0771234567`
- Email: `john@example.com`
- Car ID: `V001` (or any available vehicle)
- Start Date: `2025-11-05` (must be at least 3 days from today)
- End Date: `2025-11-12` (7 days for discount)
- Total KM: `800`

✅ Booking created! Note the Booking ID (e.g., B0001)

#### 4. Generate Invoice
- Go back to main menu
- Select option **4** (Invoice Management)
- Select option **1** (Generate Invoice for Booking)
- Enter Booking ID: `B0001`

📄 You'll see a detailed invoice with:
- Base rental charges
- Extra km charges (if applicable)
- 10% discount (for 7+ days)
- Tax calculation
- Final amount after deposit deduction

---

## Running Tests

### Compile Tests
```bash
cd src
javac -cp . tests/TestCases.java
```

### Run Test Suite
```bash
java -cp . tests.TestCases
```

Expected result: **21 tests passed, 0 failed**

---

## Common Operations

### Add a New Vehicle
1. Main Menu → **1** (Vehicle Management)
2. Select **1** (Add New Vehicle)
3. Enter Car ID, Model, and Category
4. Vehicle added successfully!

### Register a Customer
1. Main Menu → **2** (Customer Management)
2. Select **1** (Register New Customer)
3. Enter NIC/Passport, Name, Contact, Email
4. Customer registered with auto-generated ID!

### Cancel a Booking
1. Main Menu → **3** (Booking Management)
2. Select **5** (Cancel Booking)
3. Enter Booking ID
4. Must be within 2 days of booking date!

### Update Vehicle Status
1. Main Menu → **1** (Vehicle Management)
2. Select **4** (Update Vehicle Availability Status)
3. Enter Car ID and new status
4. Status updated (Available/Reserved/Under Maintenance)

---

## Sample Test Data

### Pre-loaded Vehicles
| Car ID | Model | Category | Daily Rate |
|--------|-------|----------|------------|
| V001 | Toyota Aqua | Compact Petrol | LKR 5,000 |
| V002 | Honda Fit | Compact Petrol | LKR 5,000 |
| V003 | Toyota Prius | Hybrid | LKR 7,500 |
| V004 | Honda Insight | Hybrid | LKR 7,500 |
| V005 | Nissan Leaf | Electric | LKR 10,000 |
| V006 | Tesla Model 3 | Electric | LKR 10,000 |
| V007 | BMW X5 | Luxury SUV | LKR 15,000 |
| V008 | Mercedes GLE | Luxury SUV | LKR 15,000 |

### Sample Customer Data for Testing
```
NIC: 987654321V
Name: Jane Smith
Contact: 0779876543
Email: jane@example.com
```

---

## Business Rules Quick Reference

### Booking Rules
- ✅ Minimum 3 days advance booking required
- ✅ Deposit: LKR 5,000 (refundable)
- ✅ Can cancel/update within 2 days of booking
- ✅ 10% discount for 7+ day rentals

### Pricing Structure
| Category | Daily Rate | Free KM/Day | Extra KM | Tax |
|----------|-----------|-------------|----------|-----|
| Compact Petrol | 5,000 | 100 | 50/km | 10% |
| Hybrid | 7,500 | 150 | 60/km | 12% |
| Electric | 10,000 | 200 | 40/km | 8% |
| Luxury SUV | 15,000 | 250 | 75/km | 15% |

---

## Troubleshooting

### Issue: "javac: command not found"
**Solution**: Install Java JDK and add to PATH
```bash
# Check Java installation
which java
which javac
```

### Issue: "cannot find symbol" errors
**Solution**: Ensure you're in the correct directory
```bash
# Should be in the 'src' directory when compiling
cd src
javac EcoRideCarRentalSystem.java models/*.java services/*.java enums/*.java
```

### Issue: Date format error
**Solution**: Use format `yyyy-MM-dd` (e.g., 2025-11-05)

### Issue: Booking rejected - advance requirement
**Solution**: Start date must be at least 3 days from today
```
Today: 2025-10-29
Minimum Start Date: 2025-11-01
```

### Issue: Cannot cancel booking
**Solution**: Can only cancel within 2 days of booking date
```
Booking Date: 2025-10-29
Cancellation Deadline: 2025-10-31
```

---

## Menu Navigation Map

```
Main Menu
├── 1. Vehicle Management
│   ├── 1. Add New Vehicle
│   ├── 2. Update Vehicle
│   ├── 3. Remove Vehicle
│   ├── 4. Update Vehicle Availability Status
│   └── 5. Search Vehicle by ID
├── 2. Customer Management
│   ├── 1. Register New Customer
│   ├── 2. Search Customer
│   └── 3. View All Customers
├── 3. Booking Management
│   ├── 1. Create New Booking
│   ├── 2. Search Booking by ID
│   ├── 3. Search Bookings by Customer Name
│   ├── 4. Update Booking
│   ├── 5. Cancel Booking
│   └── 6. View Active Bookings
├── 4. Invoice Management
│   ├── 1. Generate Invoice for Booking
│   ├── 2. View Invoice by Invoice ID
│   ├── 3. View Invoice by Booking ID
│   └── 4. View All Invoices
├── 5. View All Vehicles
├── 6. View Available Vehicles
├── 7. View All Bookings
└── 0. Exit
```

---

## Tips for Best Experience

1. **Always check available vehicles** before creating a booking
2. **Note down IDs** (Booking ID, Customer ID, etc.) for future reference
3. **Use realistic dates** - at least 3 days in advance
4. **Test with 7+ day rentals** to see the discount in action
5. **Try different vehicle categories** to see pricing differences
6. **Generate invoices** to see detailed pricing breakdowns

---

## Next Steps

1. ✅ Run the application and explore all features
2. ✅ Run the test suite to verify everything works
3. ✅ Create sample bookings with different scenarios
4. ✅ Generate invoices to understand pricing
5. ✅ Read the README.md for detailed documentation
6. ✅ Review CLASS_DIAGRAM_DESCRIPTION.md for system design
7. ✅ Check TEST_DOCUMENTATION.md for test details

---

## Support

For issues or questions:
1. Check the README.md for detailed documentation
2. Review the troubleshooting section above
3. Verify Java version compatibility
4. Ensure all files are in correct directories

---

**Happy Renting! 🚗💚**

*EcoRide - Affordable & Eco-Friendly Vehicle Rentals*
