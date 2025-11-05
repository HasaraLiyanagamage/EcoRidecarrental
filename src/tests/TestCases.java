package tests;

import models.*;
import services.*;
import enums.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Test cases for EcoRide Car Rental System
 */
public class TestCases {
    private VehicleService vehicleService;
    private CustomerService customerService;
    private BookingService bookingService;
    private InvoiceService invoiceService;

    public TestCases() {
        this.vehicleService = new VehicleService();
        this.customerService = new CustomerService();
        this.bookingService = new BookingService(vehicleService);
        this.invoiceService = new InvoiceService();
    }

    public void runAllTests() {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║              ECORIDE CAR RENTAL SYSTEM - TEST SUITE            ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        int passed = 0;
        int failed = 0;

        // Vehicle Tests
        if (testAddVehicle()) passed++; else failed++;
        if (testUpdateVehicle()) passed++; else failed++;
        if (testRemoveVehicle()) passed++; else failed++;
        if (testVehicleAvailabilityStatus()) passed++; else failed++;
        if (testFindVehicleById()) passed++; else failed++;

        // Customer Tests
        if (testRegisterCustomer()) passed++; else failed++;
        if (testFindCustomerByNicOrPassport()) passed++; else failed++;
        if (testDuplicateCustomerRegistration()) passed++; else failed++;

        // Booking Tests
        if (testCreateBooking()) passed++; else failed++;
        if (testBookingAdvanceRequirement()) passed++; else failed++;
        if (testBookingUnavailableVehicle()) passed++; else failed++;
        if (testCancelBooking()) passed++; else failed++;
        if (testUpdateBooking()) passed++; else failed++;

        // Invoice Tests
        if (testGenerateInvoice()) passed++; else failed++;
        if (testInvoiceCalculationWithoutDiscount()) passed++; else failed++;
        if (testInvoiceCalculationWithDiscount()) passed++; else failed++;
        if (testInvoiceWithExtraKm()) passed++; else failed++;

        // Pricing Tests
        if (testPricingForCompactPetrol()) passed++; else failed++;
        if (testPricingForHybrid()) passed++; else failed++;
        if (testPricingForElectric()) passed++; else failed++;
        if (testPricingForLuxurySUV()) passed++; else failed++;

        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║                         TEST SUMMARY                           ║");
        System.out.println("╠════════════════════════════════════════════════════════════════╣");
        System.out.printf("║  Total Tests: %2d                                                ║\n", passed + failed);
        System.out.printf("║  Passed: %2d                                                     ║\n", passed);
        System.out.printf("║  Failed: %2d                                                     ║\n", failed);
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
    }

    // ==================== VEHICLE TESTS ====================

    private boolean testAddVehicle() {
        try {
            System.out.println("\n[TEST 1] Add Vehicle");
            Vehicle vehicle = new Vehicle("TEST001", "Test Model", VehicleCategory.COMPACT_PETROL);
            vehicleService.addVehicle(vehicle);
            boolean exists = vehicleService.findVehicleById("TEST001").isPresent();
            System.out.println(exists ? " PASSED" : " FAILED");
            return exists;
        } catch (Exception e) {
            System.out.println(" FAILED: " + e.getMessage());
            return false;
        }
    }

    private boolean testUpdateVehicle() {
        try {
            System.out.println("\n[TEST 2] Update Vehicle");
            boolean updated = vehicleService.updateVehicle("V001", "Updated Model", VehicleCategory.HYBRID);
            System.out.println(updated ? " PASSED" : " FAILED");
            return updated;
        } catch (Exception e) {
            System.out.println(" FAILED: " + e.getMessage());
            return false;
        }
    }

    private boolean testRemoveVehicle() {
        try {
            System.out.println("\n[TEST 3] Remove Vehicle");
            Vehicle vehicle = new Vehicle("REMOVE001", "To Remove", VehicleCategory.COMPACT_PETROL);
            vehicleService.addVehicle(vehicle);
            boolean removed = vehicleService.removeVehicle("REMOVE001");
            System.out.println(removed ? " PASSED" : "FAILED");
            return removed;
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            return false;
        }
    }

    private boolean testVehicleAvailabilityStatus() {
        try {
            System.out.println("\n[TEST 4] Update Vehicle Availability Status");
            boolean updated = vehicleService.updateAvailabilityStatus("V002", AvailabilityStatus.UNDER_MAINTENANCE);
            System.out.println(updated ? "PASSED" : " FAILED");
            return updated;
        } catch (Exception e) {
            System.out.println(" FAILED: " + e.getMessage());
            return false;
        }
    }

    private boolean testFindVehicleById() {
        try {
            System.out.println("\n[TEST 5] Find Vehicle by ID");
            boolean found = vehicleService.findVehicleById("V001").isPresent();
            System.out.println(found ? "PASSED" : "FAILED");
            return found;
        } catch (Exception e) {
            System.out.println(" FAILED: " + e.getMessage());
            return false;
        }
    }

    // ==================== CUSTOMER TESTS ====================

    private boolean testRegisterCustomer() {
        try {
            System.out.println("\n[TEST 6] Register Customer");
            Customer customer = customerService.registerCustomer(
                "123456789V", "Test Customer", "0771234567", "test@email.com"
            );
            boolean registered = customer != null && customer.getCustomerId() != null;
            System.out.println(registered ? "PASSED" : "FAILED");
            return registered;
        } catch (Exception e) {
            System.out.println(" FAILED: " + e.getMessage());
            return false;
        }
    }

    private boolean testFindCustomerByNicOrPassport() {
        try {
            System.out.println("\n[TEST 7] Find Customer by NIC/Passport");
            boolean found = customerService.findCustomerByNicOrPassport("123456789V").isPresent();
            System.out.println(found ? " PASSED" : "FAILED");
            return found;
        } catch (Exception e) {
            System.out.println(" FAILED: " + e.getMessage());
            return false;
        }
    }

    private boolean testDuplicateCustomerRegistration() {
        try {
            System.out.println("\n[TEST 8] Duplicate Customer Registration (should return existing)");
            Customer customer1 = customerService.registerCustomer(
                "DUPLICATE123", "Duplicate Test", "0771234567", "dup@email.com"
            );
            Customer customer2 = customerService.registerCustomer(
                "DUPLICATE123", "Duplicate Test 2", "0779999999", "dup2@email.com"
            );
            boolean sameCustomer = customer1.getCustomerId().equals(customer2.getCustomerId());
            System.out.println(sameCustomer ? " PASSED" : " FAILED");
            return sameCustomer;
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            return false;
        }
    }

    // ==================== BOOKING TESTS ====================

    private boolean testCreateBooking() {
        try {
            System.out.println("\n[TEST 9] Create Booking");
            Customer customer = customerService.registerCustomer(
                "BOOK123", "Booking Test", "0771234567", "book@email.com"
            );
            LocalDate startDate = LocalDate.now().plusDays(5);
            LocalDate endDate = LocalDate.now().plusDays(10);
            Booking booking = bookingService.createBooking(customer, "V003", startDate, endDate, 500);
            boolean created = booking != null && booking.getBookingId() != null;
            System.out.println(created ? " PASSED" : " FAILED");
            return created;
        } catch (Exception e) {
            System.out.println(" FAILED: " + e.getMessage());
            return false;
        }
    }

    private boolean testBookingAdvanceRequirement() {
        try {
            System.out.println("\n[TEST 10] Booking Advance Requirement (should fail for < 3 days)");
            Customer customer = customerService.registerCustomer(
                "ADV123", "Advance Test", "0771234567", "adv@email.com"
            );
            LocalDate startDate = LocalDate.now().plusDays(1); // Less than 3 days
            LocalDate endDate = LocalDate.now().plusDays(5);
            
            try {
                bookingService.createBooking(customer, "V004", startDate, endDate, 300);
                System.out.println(" FAILED: Should have thrown exception");
                return false;
            } catch (Exception e) {
                System.out.println(" PASSED: Correctly rejected booking");
                return true;
            }
        } catch (Exception e) {
            System.out.println(" FAILED: " + e.getMessage());
            return false;
        }
    }

    private boolean testBookingUnavailableVehicle() {
        try {
            System.out.println("\n[TEST 11] Booking Unavailable Vehicle (should fail)");
            Customer customer = customerService.registerCustomer(
                "UNAV123", "Unavailable Test", "0771234567", "unav@email.com"
            );
            
            // V003 should be reserved from previous test
            LocalDate startDate = LocalDate.now().plusDays(5);
            LocalDate endDate = LocalDate.now().plusDays(10);
            
            try {
                bookingService.createBooking(customer, "V003", startDate, endDate, 300);
                System.out.println(" FAILED: Should have thrown exception");
                return false;
            } catch (Exception e) {
                System.out.println(" PASSED: Correctly rejected unavailable vehicle");
                return true;
            }
        } catch (Exception e) {
            System.out.println(" FAILED: " + e.getMessage());
            return false;
        }
    }

    private boolean testCancelBooking() {
        try {
            System.out.println("\n[TEST 12] Cancel Booking");
            Customer customer = customerService.registerCustomer(
                "CANCEL123", "Cancel Test", "0771234567", "cancel@email.com"
            );
            LocalDate startDate = LocalDate.now().plusDays(5);
            LocalDate endDate = LocalDate.now().plusDays(10);
            Booking booking = bookingService.createBooking(customer, "V005", startDate, endDate, 400);
            
            boolean cancelled = bookingService.cancelBooking(booking.getBookingId());
            System.out.println(cancelled ? " PASSED" : " FAILED");
            return cancelled;
        } catch (Exception e) {
            System.out.println(" FAILED: " + e.getMessage());
            return false;
        }
    }

    private boolean testUpdateBooking() {
        try {
            System.out.println("\n[TEST 13] Update Booking");
            Customer customer = customerService.registerCustomer(
                "UPDATE123", "Update Test", "0771234567", "update@email.com"
            );
            LocalDate startDate = LocalDate.now().plusDays(5);
            LocalDate endDate = LocalDate.now().plusDays(10);
            Booking booking = bookingService.createBooking(customer, "V006", startDate, endDate, 400);
            
            LocalDate newStartDate = LocalDate.now().plusDays(7);
            LocalDate newEndDate = LocalDate.now().plusDays(12);
            boolean updated = bookingService.updateBooking(booking.getBookingId(), newStartDate, newEndDate, 500);
            System.out.println(updated ? "PASSED" : " FAILED");
            return updated;
        } catch (Exception e) {
            System.out.println(" FAILED: " + e.getMessage());
            return false;
        }
    }

    // ==================== INVOICE TESTS ====================

    private boolean testGenerateInvoice() {
        try {
            System.out.println("\n[TEST 14] Generate Invoice");
            Customer customer = customerService.registerCustomer(
                "INV123", "Invoice Test", "0771234567", "inv@email.com"
            );
            LocalDate startDate = LocalDate.now().plusDays(5);
            LocalDate endDate = LocalDate.now().plusDays(10);
            Booking booking = bookingService.createBooking(customer, "V007", startDate, endDate, 1000);
            
            Invoice invoice = invoiceService.generateInvoice(booking);
            boolean generated = invoice != null && invoice.getInvoiceId() != null;
            System.out.println(generated ? " PASSED" : " FAILED");
            return generated;
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            return false;
        }
    }

    private boolean testInvoiceCalculationWithoutDiscount() {
        try {
            System.out.println("\n[TEST 15] Invoice Calculation Without Discount (< 7 days)");
            Customer customer = customerService.registerCustomer(
                "CALC1", "Calc Test 1", "0771234567", "calc1@email.com"
            );
            LocalDate startDate = LocalDate.now().plusDays(5);
            LocalDate endDate = LocalDate.now().plusDays(10); // 5 days
            Booking booking = bookingService.createBooking(customer, "V008", startDate, endDate, 1000);
            
            Invoice invoice = invoiceService.generateInvoice(booking);
            // Expected: 15000 * 5 = 75000 (base), no discount
            boolean correct = invoice.getDiscountAmount() == 0.0;
            System.out.println(correct ? " PASSED" : " FAILED");
            return correct;
        } catch (Exception e) {
            System.out.println(" FAILED: " + e.getMessage());
            return false;
        }
    }

    private boolean testInvoiceCalculationWithDiscount() {
        try {
            System.out.println("\n[TEST 16] Invoice Calculation With Discount (>= 7 days)");
            
            // Create a new vehicle for this test
            Vehicle testVehicle = new Vehicle("DISC001", "Discount Test", VehicleCategory.COMPACT_PETROL);
            vehicleService.addVehicle(testVehicle);
            
            Customer customer = customerService.registerCustomer(
                "DISC123", "Discount Test", "0771234567", "disc@email.com"
            );
            LocalDate startDate = LocalDate.now().plusDays(5);
            LocalDate endDate = LocalDate.now().plusDays(12); // 7 days
            Booking booking = bookingService.createBooking(customer, "DISC001", startDate, endDate, 500);
            
            Invoice invoice = invoiceService.generateInvoice(booking);
            // Expected: 5000 * 7 = 35000 (base), discount = 3500
            boolean correct = invoice.getDiscountAmount() > 0.0;
            System.out.println(correct ? " PASSED" : " FAILED");
            return correct;
        } catch (Exception e) {
            System.out.println(" FAILED: " + e.getMessage());
            return false;
        }
    }

    private boolean testInvoiceWithExtraKm() {
        try {
            System.out.println("\n[TEST 17] Invoice With Extra Km Charges");
            
            // Create a new vehicle for this test
            Vehicle testVehicle = new Vehicle("EXTRAKM001", "Extra KM Test", VehicleCategory.COMPACT_PETROL);
            vehicleService.addVehicle(testVehicle);
            
            Customer customer = customerService.registerCustomer(
                "EXTRAKM123", "Extra KM Test", "0771234567", "extrakm@email.com"
            );
            LocalDate startDate = LocalDate.now().plusDays(5);
            LocalDate endDate = LocalDate.now().plusDays(10); // 5 days
            // Free KM: 100 * 5 = 500, Total: 800, Extra: 300
            Booking booking = bookingService.createBooking(customer, "EXTRAKM001", startDate, endDate, 800);
            
            Invoice invoice = invoiceService.generateInvoice(booking);
            // Expected extra km charges: 300 * 50 = 15000
            boolean correct = invoice.getExtraKmCharges() > 0.0;
            System.out.println(correct ? " PASSED" : " FAILED");
            return correct;
        } catch (Exception e) {
            System.out.println(" FAILED: " + e.getMessage());
            return false;
        }
    }

    // ==================== PRICING TESTS ====================

    private boolean testPricingForCompactPetrol() {
        try {
            System.out.println("\n[TEST 18] Pricing for Compact Petrol");
            Vehicle vehicle = new Vehicle("PRICE1", "Price Test 1", VehicleCategory.COMPACT_PETROL);
            boolean correct = vehicle.getDailyRentalPrice() == 5000.0;
            System.out.println(correct ? " PASSED" : " FAILED");
            return correct;
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            return false;
        }
    }

    private boolean testPricingForHybrid() {
        try {
            System.out.println("\n[TEST 19] Pricing for Hybrid");
            Vehicle vehicle = new Vehicle("PRICE2", "Price Test 2", VehicleCategory.HYBRID);
            boolean correct = vehicle.getDailyRentalPrice() == 7500.0;
            System.out.println(correct ? " PASSED" : " FAILED");
            return correct;
        } catch (Exception e) {
            System.out.println(" FAILED: " + e.getMessage());
            return false;
        }
    }

    private boolean testPricingForElectric() {
        try {
            System.out.println("\n[TEST 20] Pricing for Electric");
            Vehicle vehicle = new Vehicle("PRICE3", "Price Test 3", VehicleCategory.ELECTRIC);
            boolean correct = vehicle.getDailyRentalPrice() == 10000.0;
            System.out.println(correct ? "PASSED" : "FAILED");
            return correct;
        } catch (Exception e) {
            System.out.println(" FAILED: " + e.getMessage());
            return false;
        }
    }

    private boolean testPricingForLuxurySUV() {
        try {
            System.out.println("\n[TEST 21] Pricing for Luxury SUV");
            Vehicle vehicle = new Vehicle("PRICE4", "Price Test 4", VehicleCategory.LUXURY_SUV);
            boolean correct = vehicle.getDailyRentalPrice() == 15000.0;
            System.out.println(correct ? " PASSED" : " FAILED");
            return correct;
        } catch (Exception e) {
            System.out.println(" FAILED: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        TestCases testCases = new TestCases();
        testCases.runAllTests();
    }
}
