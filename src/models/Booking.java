package models;

import enums.VehicleCategory;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Booking class representing a car rental reservation
 */
public class Booking {
    private String bookingId;
    private Customer customer;
    private Vehicle vehicle;
    private LocalDate bookingDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private int totalKilometers;
    private double depositAmount;
    private boolean isActive;

    public static final double REFUNDABLE_DEPOSIT = 5000.0;
    public static final int MIN_ADVANCE_BOOKING_DAYS = 3;
    public static final int CANCELLATION_DEADLINE_DAYS = 2;

    public Booking(String bookingId, Customer customer, Vehicle vehicle, 
                   LocalDate startDate, LocalDate endDate, int totalKilometers) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.vehicle = vehicle;
        this.bookingDate = LocalDate.now();
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalKilometers = totalKilometers;
        this.depositAmount = REFUNDABLE_DEPOSIT;
        this.isActive = true;
    }

    // Getters and Setters
    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getTotalKilometers() {
        return totalKilometers;
    }

    public void setTotalKilometers(int totalKilometers) {
        this.totalKilometers = totalKilometers;
    }

    public double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(double depositAmount) {
        this.depositAmount = depositAmount;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getRentalDays() {
        return (int) ChronoUnit.DAYS.between(startDate, endDate);
    }

    public boolean canBeCancelled() {
        long daysSinceBooking = ChronoUnit.DAYS.between(bookingDate, LocalDate.now());
        return daysSinceBooking <= CANCELLATION_DEADLINE_DAYS && isActive;
    }

    @Override
    public String toString() {
        return String.format("Booking ID: %s | Customer: %s | Vehicle: %s (%s) | Period: %s to %s | Days: %d | KM: %d | Status: %s",
                bookingId, customer.getName(), vehicle.getModel(), vehicle.getCarId(),
                startDate, endDate, getRentalDays(), totalKilometers, isActive ? "Active" : "Cancelled");
    }
}
