# EcoRide Car Rental System - Class Diagram Description

## Overview
This document describes the class structure and relationships in the EcoRide Car Rental System. Use this as a reference to create the UML class diagram using any standard UML tool (e.g., draw.io, Lucidchart, PlantUML, Visual Paradigm).

## Classes and Relationships

### 1. Enum Classes

#### VehicleCategory (Enum)
**Attributes:**
- `- displayName: String`
- `- dailyRentalFee: double`
- `- freeKmPerDay: int`
- `- extraKmCharge: double`
- `- taxRate: double`

**Enum Constants:**
- `COMPACT_PETROL`
- `HYBRID`
- `ELECTRIC`
- `LUXURY_SUV`

**Methods:**
- `+ getDisplayName(): String`
- `+ getDailyRentalFee(): double`
- `+ getFreeKmPerDay(): int`
- `+ getExtraKmCharge(): double`
- `+ getTaxRate(): double`
- `+ toString(): String`

---

#### AvailabilityStatus (Enum)
**Attributes:**
- `- displayName: String`

**Enum Constants:**
- `AVAILABLE`
- `RESERVED`
- `UNDER_MAINTENANCE`

**Methods:**
- `+ getDisplayName(): String`
- `+ toString(): String`

---

### 2. Model Classes

#### Vehicle
**Attributes:**
- `- carId: String`
- `- model: String`
- `- category: VehicleCategory`
- `- availabilityStatus: AvailabilityStatus`

**Methods:**
- `+ Vehicle(carId: String, model: String, category: VehicleCategory)`
- `+ getCarId(): String`
- `+ setCarId(carId: String): void`
- `+ getModel(): String`
- `+ setModel(model: String): void`
- `+ getCategory(): VehicleCategory`
- `+ setCategory(category: VehicleCategory): void`
- `+ getAvailabilityStatus(): AvailabilityStatus`
- `+ setAvailabilityStatus(status: AvailabilityStatus): void`
- `+ getDailyRentalPrice(): double`
- `+ toString(): String`

**Relationships:**
- **Association**: Uses `VehicleCategory` (enum)
- **Association**: Uses `AvailabilityStatus` (enum)

---

#### Customer
**Attributes:**
- `- customerId: String`
- `- nicOrPassport: String`
- `- name: String`
- `- contactNumber: String`
- `- email: String`

**Methods:**
- `+ Customer(customerId: String, nicOrPassport: String, name: String, contactNumber: String, email: String)`
- `+ getCustomerId(): String`
- `+ setCustomerId(customerId: String): void`
- `+ getNicOrPassport(): String`
- `+ setNicOrPassport(nicOrPassport: String): void`
- `+ getName(): String`
- `+ setName(name: String): void`
- `+ getContactNumber(): String`
- `+ setContactNumber(contactNumber: String): void`
- `+ getEmail(): String`
- `+ setEmail(email: String): void`
- `+ toString(): String`

---

#### Booking
**Attributes:**
- `- bookingId: String`
- `- customer: Customer`
- `- vehicle: Vehicle`
- `- bookingDate: LocalDate`
- `- startDate: LocalDate`
- `- endDate: LocalDate`
- `- totalKilometers: int`
- `- depositAmount: double`
- `- isActive: boolean`
- `+ REFUNDABLE_DEPOSIT: double {static final}`
- `+ MIN_ADVANCE_BOOKING_DAYS: int {static final}`
- `+ CANCELLATION_DEADLINE_DAYS: int {static final}`

**Methods:**
- `+ Booking(bookingId: String, customer: Customer, vehicle: Vehicle, startDate: LocalDate, endDate: LocalDate, totalKilometers: int)`
- `+ getBookingId(): String`
- `+ setBookingId(bookingId: String): void`
- `+ getCustomer(): Customer`
- `+ setCustomer(customer: Customer): void`
- `+ getVehicle(): Vehicle`
- `+ setVehicle(vehicle: Vehicle): void`
- `+ getBookingDate(): LocalDate`
- `+ setBookingDate(bookingDate: LocalDate): void`
- `+ getStartDate(): LocalDate`
- `+ setStartDate(startDate: LocalDate): void`
- `+ getEndDate(): LocalDate`
- `+ setEndDate(endDate: LocalDate): void`
- `+ getTotalKilometers(): int`
- `+ setTotalKilometers(totalKilometers: int): void`
- `+ getDepositAmount(): double`
- `+ setDepositAmount(depositAmount: double): void`
- `+ isActive(): boolean`
- `+ setActive(active: boolean): void`
- `+ getRentalDays(): int`
- `+ canBeCancelled(): boolean`
- `+ toString(): String`

**Relationships:**
- **Composition**: Contains `Customer` (1 to 1)
- **Composition**: Contains `Vehicle` (1 to 1)

---

#### Invoice
**Attributes:**
- `- invoiceId: String`
- `- booking: Booking`
- `- generatedDate: LocalDateTime`
- `- basePrice: double`
- `- extraKmCharges: double`
- `- discountAmount: double`
- `- taxAmount: double`
- `- totalBeforeDeposit: double`
- `- depositDeduction: double`
- `- finalAmount: double`

**Methods:**
- `+ Invoice(invoiceId: String, booking: Booking)`
- `- calculatePricing(): void`
- `+ getInvoiceId(): String`
- `+ getBooking(): Booking`
- `+ getGeneratedDate(): LocalDateTime`
- `+ getBasePrice(): double`
- `+ getExtraKmCharges(): double`
- `+ getDiscountAmount(): double`
- `+ getTaxAmount(): double`
- `+ getTotalBeforeDeposit(): double`
- `+ getDepositDeduction(): double`
- `+ getFinalAmount(): double`
- `+ getFormattedInvoice(): String`
- `+ toString(): String`

**Relationships:**
- **Composition**: Contains `Booking` (1 to 1)

---

### 3. Service Classes

#### VehicleService
**Attributes:**
- `- vehicles: List<Vehicle>`

**Methods:**
- `+ VehicleService()`
- `- initializeSampleVehicles(): void`
- `+ addVehicle(vehicle: Vehicle): void`
- `+ updateVehicle(carId: String, model: String, category: VehicleCategory): boolean`
- `+ removeVehicle(carId: String): boolean`
- `+ findVehicleById(carId: String): Optional<Vehicle>`
- `+ getAllVehicles(): List<Vehicle>`
- `+ getAvailableVehicles(): List<Vehicle>`
- `+ getVehiclesByCategory(category: VehicleCategory): List<Vehicle>`
- `+ updateAvailabilityStatus(carId: String, status: AvailabilityStatus): boolean`

**Relationships:**
- **Aggregation**: Manages collection of `Vehicle` (1 to many)

---

#### CustomerService
**Attributes:**
- `- customers: List<Customer>`
- `- customerCounter: int`

**Methods:**
- `+ CustomerService()`
- `+ registerCustomer(nicOrPassport: String, name: String, contactNumber: String, email: String): Customer`
- `- generateCustomerId(): String`
- `+ findCustomerById(customerId: String): Optional<Customer>`
- `+ findCustomerByNicOrPassport(nicOrPassport: String): Optional<Customer>`
- `+ getAllCustomers(): List<Customer>`
- `+ updateCustomer(customerId: String, name: String, contactNumber: String, email: String): boolean`

**Relationships:**
- **Aggregation**: Manages collection of `Customer` (1 to many)

---

#### BookingService
**Attributes:**
- `- bookings: List<Booking>`
- `- bookingCounter: int`
- `- vehicleService: VehicleService`

**Methods:**
- `+ BookingService(vehicleService: VehicleService)`
- `+ createBooking(customer: Customer, carId: String, startDate: LocalDate, endDate: LocalDate, totalKilometers: int): Booking`
- `- generateBookingId(): String`
- `+ findBookingById(bookingId: String): Optional<Booking>`
- `+ findBookingsByCustomerName(customerName: String): List<Booking>`
- `+ getBookingsByCustomer(customerId: String): List<Booking>`
- `+ getAllBookings(): List<Booking>`
- `+ getActiveBookings(): List<Booking>`
- `+ cancelBooking(bookingId: String): boolean`
- `+ updateBooking(bookingId: String, newStartDate: LocalDate, newEndDate: LocalDate, newTotalKilometers: int): boolean`

**Relationships:**
- **Aggregation**: Manages collection of `Booking` (1 to many)
- **Dependency**: Uses `VehicleService`

---

#### InvoiceService
**Attributes:**
- `- invoices: List<Invoice>`
- `- invoiceCounter: int`

**Methods:**
- `+ InvoiceService()`
- `+ generateInvoice(booking: Booking): Invoice`
- `- generateInvoiceId(): String`
- `+ findInvoiceById(invoiceId: String): Optional<Invoice>`
- `+ findInvoiceByBookingId(bookingId: String): Optional<Invoice>`
- `+ getAllInvoices(): List<Invoice>`

**Relationships:**
- **Aggregation**: Manages collection of `Invoice` (1 to many)

---

### 4. Main Application Class

#### EcoRideCarRentalSystem
**Attributes:**
- `- vehicleService: VehicleService`
- `- customerService: CustomerService`
- `- bookingService: BookingService`
- `- invoiceService: InvoiceService`
- `- scanner: Scanner`
- `- dateFormatter: DateTimeFormatter`

**Methods:**
- `+ EcoRideCarRentalSystem()`
- `+ start(): void`
- `- displayWelcomeBanner(): void`
- `- displayMainMenu(): void`
- `- vehicleManagementMenu(): void`
- `- customerManagementMenu(): void`
- `- bookingManagementMenu(): void`
- `- invoiceManagementMenu(): void`
- `- addVehicle(): void`
- `- updateVehicle(): void`
- `- removeVehicle(): void`
- `- updateVehicleStatus(): void`
- `- searchVehicle(): void`
- `- displayAllVehicles(): void`
- `- displayAvailableVehicles(): void`
- `- registerCustomer(): void`
- `- searchCustomer(): void`
- `- displayAllCustomers(): void`
- `- createBooking(): void`
- `- searchBookingById(): void`
- `- searchBookingsByCustomerName(): void`
- `- updateBooking(): void`
- `- cancelBooking(): void`
- `- displayAllBookings(): void`
- `- displayActiveBookings(): void`
- `- generateInvoice(): void`
- `- viewInvoiceById(): void`
- `- viewInvoiceByBookingId(): void`
- `- displayAllInvoices(): void`
- `- selectVehicleCategory(): VehicleCategory`
- `- selectAvailabilityStatus(): AvailabilityStatus`
- `- getStringInput(prompt: String): String`
- `- getIntInput(prompt: String): int`
- `- getDateInput(prompt: String): LocalDate`
- `+ main(args: String[]): void {static}`

**Relationships:**
- **Dependency**: Uses all service classes

---

## Class Relationships Summary

### Associations
1. **Vehicle** → **VehicleCategory** (uses)
2. **Vehicle** → **AvailabilityStatus** (uses)
3. **Booking** → **Customer** (composition)
4. **Booking** → **Vehicle** (composition)
5. **Invoice** → **Booking** (composition)

### Aggregations
1. **VehicleService** ◇→ **Vehicle** (manages collection)
2. **CustomerService** ◇→ **Customer** (manages collection)
3. **BookingService** ◇→ **Booking** (manages collection)
4. **InvoiceService** ◇→ **Invoice** (manages collection)

### Dependencies
1. **BookingService** ⇢ **VehicleService** (uses)
2. **EcoRideCarRentalSystem** ⇢ All Services (uses)

---

## UML Diagram Layout Suggestions

### Package Structure
```
┌─────────────────────────────────────────────────────────────┐
│                         enums                               │
├─────────────────────────────────────────────────────────────┤
│  <<enumeration>>          <<enumeration>>                   │
│  VehicleCategory          AvailabilityStatus                │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│                         models                              │
├─────────────────────────────────────────────────────────────┤
│  Vehicle    Customer    Booking    Invoice                  │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│                        services                             │
├─────────────────────────────────────────────────────────────┤
│  VehicleService  CustomerService  BookingService            │
│  InvoiceService                                             │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│                      main application                       │
├─────────────────────────────────────────────────────────────┤
│  EcoRideCarRentalSystem                                     │
└─────────────────────────────────────────────────────────────┘
```

### Recommended Tools
- **draw.io** (free, web-based)
- **Lucidchart** (free tier available)
- **PlantUML** (text-based, can generate diagrams)
- **Visual Paradigm** (professional tool)
- **StarUML** (free for basic use)

### Important Notes for Class Diagram
1. Prefix each class name with your Kingston University student ID
2. Use standard UML notation:
   - `+` for public
   - `-` for private
   - `#` for protected
   - Underline for static members
3. Show multiplicities on associations (1, *, 0..1, etc.)
4. Use proper arrow types:
   - Solid line with hollow arrow for inheritance
   - Solid line with filled diamond for composition
   - Solid line with hollow diamond for aggregation
   - Dashed line with arrow for dependency
5. Include all attributes with types
6. Include all public methods with parameters and return types

---

**Document Version**: 1.0  
**Last Updated**: 2025-10-29
