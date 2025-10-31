import models.*;
import services.*;
import enums.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class EcoRideCarRentalSystem {
    private VehicleService vehicleService;
    private CustomerService customerService;
    private BookingService bookingService;
    private InvoiceService invoiceService;
    private Scanner scanner;
    private DateTimeFormatter dateFormatter;

    public EcoRideCarRentalSystem() {
        this.vehicleService = new VehicleService();
        this.customerService = new CustomerService();
        this.bookingService = new BookingService(vehicleService);
        this.invoiceService = new InvoiceService();
        this.scanner = new Scanner(System.in);
        this.dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    public void start() {
        displayWelcomeBanner();
        boolean running = true;

        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");

            try {
                switch (choice) {
                    case 1:
                        vehicleManagementMenu();
                        break;
                    case 2:
                        customerManagementMenu();
                        break;
                    case 3:
                        bookingManagementMenu();
                        break;
                    case 4:
                        invoiceManagementMenu();
                        break;
                    case 5:
                        displayAllVehicles();
                        break;
                    case 6:
                        displayAvailableVehicles();
                        break;
                    case 7:
                        displayAllBookings();
                        break;
                    case 0:
                        running = false;
                        System.out.println("\nThank you for using EcoRide Car Rental System!");
                        break;
                    default:
                        System.out.println("\n Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("\n Error: " + e.getMessage());
            }

            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    private void displayWelcomeBanner() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    ECORIDE CAR RENTAL SYSTEM                   ║");
        System.out.println("║              Affordable & Eco-Friendly Vehicle Rentals         ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
    }

    private void displayMainMenu() {
        System.out.println("\n┌────────────────────────────────────────────────────────────────┐");
        System.out.println("│                          MAIN MENU                             │");
        System.out.println("├────────────────────────────────────────────────────────────────┤");
        System.out.println("│  1. Vehicle Management                                         │");
        System.out.println("│  2. Customer Management                                        │");
        System.out.println("│  3. Booking Management                                         │");
        System.out.println("│  4. Invoice Management                                         │");
        System.out.println("│  5. View All Vehicles                                          │");
        System.out.println("│  6. View Available Vehicles                                    │");
        System.out.println("│  7. View All Bookings                                          │");
        System.out.println("│  0. Exit                                                       │");
        System.out.println("└────────────────────────────────────────────────────────────────┘");
    }

    // ==================== VEHICLE MANAGEMENT ====================

    private void vehicleManagementMenu() {
        System.out.println("\n┌────────────────────────────────────────────────────────────────┐");
        System.out.println("│                    VEHICLE MANAGEMENT                          │");
        System.out.println("├────────────────────────────────────────────────────────────────┤");
        System.out.println("│  1. Add New Vehicle                                            │");
        System.out.println("│  2. Update Vehicle                                             │");
        System.out.println("│  3. Remove Vehicle                                             │");
        System.out.println("│  4. Update Vehicle Availability Status                         │");
        System.out.println("│  5. Search Vehicle by ID                                       │");
        System.out.println("│  0. Back to Main Menu                                          │");
        System.out.println("└────────────────────────────────────────────────────────────────┘");

        int choice = getIntInput("Enter your choice: ");

        switch (choice) {
            case 1:
                addVehicle();
                break;
            case 2:
                updateVehicle();
                break;
            case 3:
                removeVehicle();
                break;
            case 4:
                updateVehicleStatus();
                break;
            case 5:
                searchVehicle();
                break;
            case 0:
                break;
            default:
                System.out.println("\n Invalid choice.");
        }
    }

    private void addVehicle() {
        System.out.println("\n=== Add New Vehicle ===");
        String carId = getStringInput("Enter Car ID: ");
        String model = getStringInput("Enter Model: ");
        VehicleCategory category = selectVehicleCategory();

        try {
            Vehicle vehicle = new Vehicle(carId, model, category);
            vehicleService.addVehicle(vehicle);
            System.out.println("\n Vehicle added successfully!");
            System.out.println(vehicle);
        } catch (Exception e) {
            System.out.println("\n Error: " + e.getMessage());
        }
    }

    private void updateVehicle() {
        System.out.println("\n=== Update Vehicle ===");
        String carId = getStringInput("Enter Car ID to update: ");
        
        Optional<Vehicle> vehicleOpt = vehicleService.findVehicleById(carId);
        if (!vehicleOpt.isPresent()) {
            System.out.println("\n Vehicle not found.");
            return;
        }

        System.out.println("\nCurrent details:");
        System.out.println(vehicleOpt.get());

        String model = getStringInput("\nEnter new Model: ");
        VehicleCategory category = selectVehicleCategory();

        if (vehicleService.updateVehicle(carId, model, category)) {
            System.out.println("\n Vehicle updated successfully!");
        } else {
            System.out.println("\n Failed to update vehicle.");
        }
    }

    private void removeVehicle() {
        System.out.println("\n=== Remove Vehicle ===");
        String carId = getStringInput("Enter Car ID to remove: ");

        if (vehicleService.removeVehicle(carId)) {
            System.out.println("\n Vehicle removed successfully!");
        } else {
            System.out.println("\n Vehicle not found.");
        }
    }

    private void updateVehicleStatus() {
        System.out.println("\n=== Update Vehicle Availability Status ===");
        String carId = getStringInput("Enter Car ID: ");

        Optional<Vehicle> vehicleOpt = vehicleService.findVehicleById(carId);
        if (!vehicleOpt.isPresent()) {
            System.out.println("\n Vehicle not found.");
            return;
        }

        System.out.println("\nCurrent status: " + vehicleOpt.get().getAvailabilityStatus());
        AvailabilityStatus status = selectAvailabilityStatus();

        if (vehicleService.updateAvailabilityStatus(carId, status)) {
            System.out.println("\n Vehicle status updated successfully!");
        } else {
            System.out.println("\n Failed to update status.");
        }
    }

    private void searchVehicle() {
        System.out.println("\n=== Search Vehicle ===");
        String carId = getStringInput("Enter Car ID: ");

        Optional<Vehicle> vehicleOpt = vehicleService.findVehicleById(carId);
        if (vehicleOpt.isPresent()) {
            System.out.println("\n Vehicle found:");
            System.out.println(vehicleOpt.get());
        } else {
            System.out.println("\n Vehicle not found.");
        }
    }

    private void displayAllVehicles() {
        System.out.println("\n=== All Vehicles ===");
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles available.");
        } else {
            for (Vehicle vehicle : vehicles) {
                System.out.println(vehicle);
            }
        }
    }

    private void displayAvailableVehicles() {
        System.out.println("\n=== Available Vehicles ===");
        List<Vehicle> vehicles = vehicleService.getAvailableVehicles();
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles currently available.");
        } else {
            for (Vehicle vehicle : vehicles) {
                System.out.println(vehicle);
            }
        }
    }

    // ==================== CUSTOMER MANAGEMENT ====================

    private void customerManagementMenu() {
        System.out.println("\n┌────────────────────────────────────────────────────────────────┐");
        System.out.println("│                    CUSTOMER MANAGEMENT                         │");
        System.out.println("├────────────────────────────────────────────────────────────────┤");
        System.out.println("│  1. Register New Customer                                      │");
        System.out.println("│  2. Search Customer                                            │");
        System.out.println("│  3. View All Customers                                         │");
        System.out.println("│  0. Back to Main Menu                                          │");
        System.out.println("└────────────────────────────────────────────────────────────────┘");

        int choice = getIntInput("Enter your choice: ");

        switch (choice) {
            case 1:
                registerCustomer();
                break;
            case 2:
                searchCustomer();
                break;
            case 3:
                displayAllCustomers();
                break;
            case 0:
                break;
            default:
                System.out.println("\n Invalid choice.");
        }
    }

    private void registerCustomer() {
        System.out.println("\n=== Register New Customer ===");
        String nicOrPassport = getStringInput("Enter NIC/Passport: ");
        String name = getStringInput("Enter Name: ");
        String contactNumber = getStringInput("Enter Contact Number: ");
        String email = getStringInput("Enter Email: ");

        try {
            Customer customer = customerService.registerCustomer(nicOrPassport, name, contactNumber, email);
            System.out.println("\n Customer registered successfully!");
            System.out.println(customer);
        } catch (Exception e) {
            System.out.println("\n Error: " + e.getMessage());
        }
    }

    private void searchCustomer() {
        System.out.println("\n=== Search Customer ===");
        String nicOrPassport = getStringInput("Enter NIC/Passport: ");

        Optional<Customer> customerOpt = customerService.findCustomerByNicOrPassport(nicOrPassport);
        if (customerOpt.isPresent()) {
            System.out.println("\n Customer found:");
            System.out.println(customerOpt.get());
        } else {
            System.out.println("\n Customer not found.");
        }
    }

    private void displayAllCustomers() {
        System.out.println("\n=== All Customers ===");
        List<Customer> customers = customerService.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers registered.");
        } else {
            for (Customer customer : customers) {
                System.out.println(customer);
            }
        }
    }

    // ==================== BOOKING MANAGEMENT ====================

    private void bookingManagementMenu() {
        System.out.println("\n┌────────────────────────────────────────────────────────────────┐");
        System.out.println("│                    BOOKING MANAGEMENT                          │");
        System.out.println("├────────────────────────────────────────────────────────────────┤");
        System.out.println("│  1. Create New Booking                                         │");
        System.out.println("│  2. Search Booking by ID                                       │");
        System.out.println("│  3. Search Bookings by Customer Name                           │");
        System.out.println("│  4. Update Booking                                             │");
        System.out.println("│  5. Cancel Booking                                             │");
        System.out.println("│  6. View Active Bookings                                       │");
        System.out.println("│  0. Back to Main Menu                                          │");
        System.out.println("└────────────────────────────────────────────────────────────────┘");

        int choice = getIntInput("Enter your choice: ");

        switch (choice) {
            case 1:
                createBooking();
                break;
            case 2:
                searchBookingById();
                break;
            case 3:
                searchBookingsByCustomerName();
                break;
            case 4:
                updateBooking();
                break;
            case 5:
                cancelBooking();
                break;
            case 6:
                displayActiveBookings();
                break;
            case 0:
                break;
            default:
                System.out.println("\n Invalid choice.");
        }
    }

    private void createBooking() {
        System.out.println("\n=== Create New Booking ===");
        
        // Get or register customer
        String nicOrPassport = getStringInput("Enter Customer NIC/Passport: ");
        Optional<Customer> customerOpt = customerService.findCustomerByNicOrPassport(nicOrPassport);
        
        Customer customer;
        if (customerOpt.isPresent()) {
            customer = customerOpt.get();
            System.out.println("\n Existing customer found: " + customer.getName());
        } else {
            System.out.println("\n Customer not found. Please register:");
            String name = getStringInput("Enter Name: ");
            String contactNumber = getStringInput("Enter Contact Number: ");
            String email = getStringInput("Enter Email: ");
            customer = customerService.registerCustomer(nicOrPassport, name, contactNumber, email);
            System.out.println("\n Customer registered successfully!");
        }

        // Display available vehicles
        System.out.println("\n=== Available Vehicles ===");
        List<Vehicle> availableVehicles = vehicleService.getAvailableVehicles();
        if (availableVehicles.isEmpty()) {
            System.out.println(" No vehicles currently available.");
            return;
        }
        for (Vehicle vehicle : availableVehicles) {
            System.out.println(vehicle);
        }

        // Get booking details
        String carId = getStringInput("\nEnter Car ID to book: ");
        LocalDate startDate = getDateInput("Enter Start Date (yyyy-MM-dd): ");
        LocalDate endDate = getDateInput("Enter End Date (yyyy-MM-dd): ");
        int totalKilometers = getIntInput("Enter Estimated Total Kilometers: ");

        try {
            Booking booking = bookingService.createBooking(customer, carId, startDate, endDate, totalKilometers);
            System.out.println("\n Booking created successfully!");
            System.out.println(booking);
            System.out.println("\n Deposit charged: LKR " + Booking.REFUNDABLE_DEPOSIT);
        } catch (Exception e) {
            System.out.println("\n Error: " + e.getMessage());
        }
    }

    private void searchBookingById() {
        System.out.println("\n=== Search Booking ===");
        String bookingId = getStringInput("Enter Booking ID: ");

        Optional<Booking> bookingOpt = bookingService.findBookingById(bookingId);
        if (bookingOpt.isPresent()) {
            System.out.println("\n Booking found:");
            System.out.println(bookingOpt.get());
        } else {
            System.out.println("\n Booking not found.");
        }
    }

    private void searchBookingsByCustomerName() {
        System.out.println("\n=== Search Bookings by Customer Name ===");
        String customerName = getStringInput("Enter Customer Name: ");

        List<Booking> bookings = bookingService.findBookingsByCustomerName(customerName);
        if (bookings.isEmpty()) {
            System.out.println("\n No bookings found for customer: " + customerName);
        } else {
            System.out.println("\n Bookings found:");
            for (Booking booking : bookings) {
                System.out.println(booking);
            }
        }
    }

    private void updateBooking() {
        System.out.println("\n=== Update Booking ===");
        String bookingId = getStringInput("Enter Booking ID: ");

        Optional<Booking> bookingOpt = bookingService.findBookingById(bookingId);
        if (!bookingOpt.isPresent()) {
            System.out.println("\n Booking not found.");
            return;
        }

        Booking booking = bookingOpt.get();
        System.out.println("\nCurrent booking details:");
        System.out.println(booking);

        if (!booking.canBeCancelled()) {
            System.out.println("\n Booking cannot be updated. Update deadline has passed.");
            return;
        }

        LocalDate newStartDate = getDateInput("\nEnter new Start Date (yyyy-MM-dd): ");
        LocalDate newEndDate = getDateInput("Enter new End Date (yyyy-MM-dd): ");
        int newTotalKilometers = getIntInput("Enter new Estimated Total Kilometers: ");

        try {
            bookingService.updateBooking(bookingId, newStartDate, newEndDate, newTotalKilometers);
            System.out.println("\n Booking updated successfully!");
        } catch (Exception e) {
            System.out.println("\n Error: " + e.getMessage());
        }
    }

    private void cancelBooking() {
        System.out.println("\n=== Cancel Booking ===");
        String bookingId = getStringInput("Enter Booking ID: ");

        try {
            bookingService.cancelBooking(bookingId);
            System.out.println("\n Booking cancelled successfully!");
            System.out.println(" Deposit of LKR " + Booking.REFUNDABLE_DEPOSIT + " will be refunded.");
        } catch (Exception e) {
            System.out.println("\n Error: " + e.getMessage());
        }
    }

    private void displayAllBookings() {
        System.out.println("\n=== All Bookings ===");
        List<Booking> bookings = bookingService.getAllBookings();
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            for (Booking booking : bookings) {
                System.out.println(booking);
            }
        }
    }

    private void displayActiveBookings() {
        System.out.println("\n=== Active Bookings ===");
        List<Booking> bookings = bookingService.getActiveBookings();
        if (bookings.isEmpty()) {
            System.out.println("No active bookings found.");
        } else {
            for (Booking booking : bookings) {
                System.out.println(booking);
            }
        }
    }

    // ==================== INVOICE MANAGEMENT ====================

    private void invoiceManagementMenu() {
        System.out.println("\n┌────────────────────────────────────────────────────────────────┐");
        System.out.println("│                    INVOICE MANAGEMENT                          │");
        System.out.println("├────────────────────────────────────────────────────────────────┤");
        System.out.println("│  1. Generate Invoice for Booking                              │");
        System.out.println("│  2. View Invoice by Invoice ID                                │");
        System.out.println("│  3. View Invoice by Booking ID                                │");
        System.out.println("│  4. View All Invoices                                          │");
        System.out.println("│  0. Back to Main Menu                                          │");
        System.out.println("└────────────────────────────────────────────────────────────────┘");

        int choice = getIntInput("Enter your choice: ");

        switch (choice) {
            case 1:
                generateInvoice();
                break;
            case 2:
                viewInvoiceById();
                break;
            case 3:
                viewInvoiceByBookingId();
                break;
            case 4:
                displayAllInvoices();
                break;
            case 0:
                break;
            default:
                System.out.println("\n Invalid choice.");
        }
    }

    private void generateInvoice() {
        System.out.println("\n=== Generate Invoice ===");
        String bookingId = getStringInput("Enter Booking ID: ");

        Optional<Booking> bookingOpt = bookingService.findBookingById(bookingId);
        if (!bookingOpt.isPresent()) {
            System.out.println("\n Booking not found.");
            return;
        }

        Booking booking = bookingOpt.get();
        if (!booking.isActive()) {
            System.out.println("\n Cannot generate invoice for cancelled booking.");
            return;
        }

        // Check if invoice already exists
        Optional<Invoice> existingInvoice = invoiceService.findInvoiceByBookingId(bookingId);
        if (existingInvoice.isPresent()) {
            System.out.println("\n Invoice already exists for this booking:");
            System.out.println(existingInvoice.get());
            return;
        }

        Invoice invoice = invoiceService.generateInvoice(booking);
        System.out.println("\n Invoice generated successfully!");
        System.out.println(invoice);
    }

    private void viewInvoiceById() {
        System.out.println("\n=== View Invoice ===");
        String invoiceId = getStringInput("Enter Invoice ID: ");

        Optional<Invoice> invoiceOpt = invoiceService.findInvoiceById(invoiceId);
        if (invoiceOpt.isPresent()) {
            System.out.println(invoiceOpt.get());
        } else {
            System.out.println("\n Invoice not found.");
        }
    }

    private void viewInvoiceByBookingId() {
        System.out.println("\n=== View Invoice by Booking ID ===");
        String bookingId = getStringInput("Enter Booking ID: ");

        Optional<Invoice> invoiceOpt = invoiceService.findInvoiceByBookingId(bookingId);
        if (invoiceOpt.isPresent()) {
            System.out.println(invoiceOpt.get());
        } else {
            System.out.println("\n No invoice found for this booking.");
        }
    }

    private void displayAllInvoices() {
        System.out.println("\n=== All Invoices ===");
        List<Invoice> invoices = invoiceService.getAllInvoices();
        if (invoices.isEmpty()) {
            System.out.println("No invoices generated yet.");
        } else {
            for (Invoice invoice : invoices) {
                System.out.println("\n" + invoice.getInvoiceId() + " - Booking: " + 
                        invoice.getBooking().getBookingId() + " - Amount: LKR " + 
                        String.format("%.2f", invoice.getFinalAmount()));
            }
        }
    }

    // ==================== UTILITY METHODS ====================

    private VehicleCategory selectVehicleCategory() {
        System.out.println("\nSelect Vehicle Category:");
        System.out.println("1. Compact Petrol Car (LKR 5,000/day)");
        System.out.println("2. Hybrid Car (LKR 7,500/day)");
        System.out.println("3. Electric Car (LKR 10,000/day)");
        System.out.println("4. Luxury SUV (LKR 15,000/day)");

        int choice = getIntInput("Enter choice: ");
        switch (choice) {
            case 1: return VehicleCategory.COMPACT_PETROL;
            case 2: return VehicleCategory.HYBRID;
            case 3: return VehicleCategory.ELECTRIC;
            case 4: return VehicleCategory.LUXURY_SUV;
            default:
                System.out.println("Invalid choice. Defaulting to Compact Petrol.");
                return VehicleCategory.COMPACT_PETROL;
        }
    }

    private AvailabilityStatus selectAvailabilityStatus() {
        System.out.println("\nSelect Availability Status:");
        System.out.println("1. Available");
        System.out.println("2. Reserved");
        System.out.println("3. Under Maintenance");

        int choice = getIntInput("Enter choice: ");
        switch (choice) {
            case 1: return AvailabilityStatus.AVAILABLE;
            case 2: return AvailabilityStatus.RESERVED;
            case 3: return AvailabilityStatus.UNDER_MAINTENANCE;
            default:
                System.out.println("Invalid choice. Defaulting to Available.");
                return AvailabilityStatus.AVAILABLE;
        }
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println(" Invalid input. Please enter a number.");
            }
        }
    }

    private LocalDate getDateInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String dateStr = scanner.nextLine().trim();
                return LocalDate.parse(dateStr, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println(" Invalid date format. Please use yyyy-MM-dd (e.g., 2025-11-05).");
            }
        }
    }

    public static void main(String[] args) {
        EcoRideCarRentalSystem system = new EcoRideCarRentalSystem();
        system.start();
    }
}
