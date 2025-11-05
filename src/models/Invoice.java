package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
Invoice class representing a rental invoice with detailed pricing breakdown
 */
public class Invoice {
    private String invoiceId;
    private Booking booking;
    private LocalDateTime generatedDate;
    private double basePrice;
    private double extraKmCharges;
    private double discountAmount;
    private double taxAmount;
    private double totalBeforeDeposit;
    private double depositDeduction;
    private double finalAmount;

    public Invoice(String invoiceId, Booking booking) {
        this.invoiceId = invoiceId;
        this.booking = booking;
        this.generatedDate = LocalDateTime.now();
        calculatePricing();
    }

    private void calculatePricing() {
        Vehicle vehicle = booking.getVehicle();
        int rentalDays = booking.getRentalDays();
        int totalKm = booking.getTotalKilometers();

        // Calculate base price
        basePrice = vehicle.getDailyRentalPrice() * rentalDays;

        // Calculate extra km charges
        int freeKm = vehicle.getCategory().getFreeKmPerDay() * rentalDays;
        int extraKm = Math.max(0, totalKm - freeKm);
        extraKmCharges = extraKm * vehicle.getCategory().getExtraKmCharge();

        // Apply discount for 7+ days
        if (rentalDays >= 7) {
            discountAmount = basePrice * 0.10;
        } else {
            discountAmount = 0.0;
        }

        // Calculate subtotal after discount
        double subtotal = basePrice - discountAmount + extraKmCharges;

        // Calculate tax
        taxAmount = subtotal * vehicle.getCategory().getTaxRate();

        // Calculate total before deposit
        totalBeforeDeposit = subtotal + taxAmount;

        // Deduct deposit
        depositDeduction = booking.getDepositAmount();
        finalAmount = totalBeforeDeposit - depositDeduction;
    }

    // Getters
    public String getInvoiceId() {
        return invoiceId;
    }

    public Booking getBooking() {
        return booking;
    }

    public LocalDateTime getGeneratedDate() {
        return generatedDate;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public double getExtraKmCharges() {
        return extraKmCharges;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public double getTotalBeforeDeposit() {
        return totalBeforeDeposit;
    }

    public double getDepositDeduction() {
        return depositDeduction;
    }

    public double getFinalAmount() {
        return finalAmount;
    }

    public String getFormattedInvoice() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        
        sb.append("\n╔════════════════════════════════════════════════════════════════╗\n");
        sb.append("║                    ECORIDE CAR RENTAL SYSTEM                   ║\n");
        sb.append("║                         RENTAL INVOICE                         ║\n");
        sb.append("╚════════════════════════════════════════════════════════════════╝\n\n");
        
        sb.append(String.format("Invoice ID: %s\n", invoiceId));
        sb.append(String.format("Generated: %s\n", generatedDate.format(formatter)));
        sb.append("\n─────────────────────────────────────────────────────────────────\n");
        sb.append("CUSTOMER DETAILS\n");
        sb.append("─────────────────────────────────────────────────────────────────\n");
        sb.append(String.format("Name: %s\n", booking.getCustomer().getName()));
        sb.append(String.format("NIC/Passport: %s\n", booking.getCustomer().getNicOrPassport()));
        sb.append(String.format("Contact: %s\n", booking.getCustomer().getContactNumber()));
        sb.append(String.format("Email: %s\n", booking.getCustomer().getEmail()));
        
        sb.append("\n─────────────────────────────────────────────────────────────────\n");
        sb.append("VEHICLE DETAILS\n");
        sb.append("─────────────────────────────────────────────────────────────────\n");
        sb.append(String.format("Car ID: %s\n", booking.getVehicle().getCarId()));
        sb.append(String.format("Model: %s\n", booking.getVehicle().getModel()));
        sb.append(String.format("Category: %s\n", booking.getVehicle().getCategory().getDisplayName()));
        
        sb.append("\n─────────────────────────────────────────────────────────────────\n");
        sb.append("RENTAL DETAILS\n");
        sb.append("─────────────────────────────────────────────────────────────────\n");
        sb.append(String.format("Booking ID: %s\n", booking.getBookingId()));
        sb.append(String.format("Start Date: %s\n", booking.getStartDate()));
        sb.append(String.format("End Date: %s\n", booking.getEndDate()));
        sb.append(String.format("Rental Duration: %d days\n", booking.getRentalDays()));
        sb.append(String.format("Total Kilometers: %d km\n", booking.getTotalKilometers()));
        
        sb.append("\n─────────────────────────────────────────────────────────────────\n");
        sb.append("PRICING BREAKDOWN\n");
        sb.append("─────────────────────────────────────────────────────────────────\n");
        sb.append(String.format("Base Rental (LKR %.2f × %d days)    LKR %10.2f\n", 
                booking.getVehicle().getDailyRentalPrice(), booking.getRentalDays(), basePrice));
        
        if (extraKmCharges > 0) {
            int freeKm = booking.getVehicle().getCategory().getFreeKmPerDay() * booking.getRentalDays();
            int extraKm = booking.getTotalKilometers() - freeKm;
            sb.append(String.format("Extra KM Charges (%d km × LKR %.2f)    LKR %10.2f\n", 
                    extraKm, booking.getVehicle().getCategory().getExtraKmCharge(), extraKmCharges));
        }
        
        if (discountAmount > 0) {
            sb.append(String.format("Discount (10%% for 7+ days)           LKR -%9.2f\n", discountAmount));
        }
        
        sb.append(String.format("Tax (%.0f%%)                              LKR %10.2f\n", 
                booking.getVehicle().getCategory().getTaxRate() * 100, taxAmount));
        sb.append("                                          ─────────────\n");
        sb.append(String.format("Total Before Deposit                  LKR %10.2f\n", totalBeforeDeposit));
        sb.append(String.format("Deposit Deduction                     LKR -%9.2f\n", depositDeduction));
        sb.append("                                          ═════════════\n");
        sb.append(String.format("FINAL AMOUNT DUE                      LKR %10.2f\n", finalAmount));
        sb.append("                                          ═════════════\n");
        
        sb.append("\n─────────────────────────────────────────────────────────────────\n");
        sb.append("Thank you for choosing EcoRide Car Rental System!\n");
        sb.append("─────────────────────────────────────────────────────────────────\n");
        
        return sb.toString();
    }

    @Override
    public String toString() {
        return getFormattedInvoice();
    }
}
