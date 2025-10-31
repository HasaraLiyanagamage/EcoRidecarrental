package services;

import models.Booking;
import models.Customer;
import models.Vehicle;
import enums.AvailabilityStatus;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing booking operations
 */
public class BookingService {
    private List<Booking> bookings;
    private int bookingCounter;
    private VehicleService vehicleService;

    public BookingService(VehicleService vehicleService) {
        this.bookings = new ArrayList<>();
        this.bookingCounter = 1;
        this.vehicleService = vehicleService;
    }

    public Booking createBooking(Customer customer, String carId, LocalDate startDate, 
                                LocalDate endDate, int totalKilometers) throws Exception {
        // Validate vehicle exists and is available
        Optional<Vehicle> vehicleOpt = vehicleService.findVehicleById(carId);
        if (!vehicleOpt.isPresent()) {
            throw new Exception("Vehicle not found with ID: " + carId);
        }

        Vehicle vehicle = vehicleOpt.get();
        if (vehicle.getAvailabilityStatus() != AvailabilityStatus.AVAILABLE) {
            throw new Exception("Vehicle is not available for booking.");
        }

        // Validate advance booking requirement (at least 3 days)
        long daysUntilStart = ChronoUnit.DAYS.between(LocalDate.now(), startDate);
        if (daysUntilStart < Booking.MIN_ADVANCE_BOOKING_DAYS) {
            throw new Exception("Booking must be made at least " + 
                    Booking.MIN_ADVANCE_BOOKING_DAYS + " days in advance.");
        }

        // Validate date range
        if (endDate.isBefore(startDate) || endDate.equals(startDate)) {
            throw new Exception("End date must be after start date.");
        }

        // Create booking
        String bookingId = generateBookingId();
        Booking booking = new Booking(bookingId, customer, vehicle, startDate, endDate, totalKilometers);
        bookings.add(booking);

        // Update vehicle status
        vehicleService.updateAvailabilityStatus(carId, AvailabilityStatus.RESERVED);

        return booking;
    }

    private String generateBookingId() {
        return String.format("B%04d", bookingCounter++);
    }

    public Optional<Booking> findBookingById(String bookingId) {
        return bookings.stream()
                .filter(b -> b.getBookingId().equals(bookingId))
                .findFirst();
    }

    public List<Booking> findBookingsByCustomerName(String customerName) {
        return bookings.stream()
                .filter(b -> b.getCustomer().getName().toLowerCase()
                        .contains(customerName.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Booking> getBookingsByCustomer(String customerId) {
        return bookings.stream()
                .filter(b -> b.getCustomer().getCustomerId().equals(customerId))
                .collect(Collectors.toList());
    }

    public List<Booking> getAllBookings() {
        return new ArrayList<>(bookings);
    }

    public List<Booking> getActiveBookings() {
        return bookings.stream()
                .filter(Booking::isActive)
                .collect(Collectors.toList());
    }

    public boolean cancelBooking(String bookingId) throws Exception {
        Optional<Booking> bookingOpt = findBookingById(bookingId);
        if (!bookingOpt.isPresent()) {
            throw new Exception("Booking not found with ID: " + bookingId);
        }

        Booking booking = bookingOpt.get();
        if (!booking.isActive()) {
            throw new Exception("Booking is already cancelled.");
        }

        if (!booking.canBeCancelled()) {
            throw new Exception("Booking cannot be cancelled. Cancellation deadline has passed " +
                    "(must cancel within " + Booking.CANCELLATION_DEADLINE_DAYS + " days of booking).");
        }

        booking.setActive(false);
        vehicleService.updateAvailabilityStatus(
                booking.getVehicle().getCarId(), 
                AvailabilityStatus.AVAILABLE
        );

        return true;
    }

    public boolean updateBooking(String bookingId, LocalDate newStartDate, 
                                LocalDate newEndDate, int newTotalKilometers) throws Exception {
        Optional<Booking> bookingOpt = findBookingById(bookingId);
        if (!bookingOpt.isPresent()) {
            throw new Exception("Booking not found with ID: " + bookingId);
        }

        Booking booking = bookingOpt.get();
        if (!booking.canBeCancelled()) {
            throw new Exception("Booking cannot be updated. Update deadline has passed " +
                    "(must update within " + Booking.CANCELLATION_DEADLINE_DAYS + " days of booking).");
        }

        // Validate new dates
        long daysUntilStart = ChronoUnit.DAYS.between(LocalDate.now(), newStartDate);
        if (daysUntilStart < Booking.MIN_ADVANCE_BOOKING_DAYS) {
            throw new Exception("Booking must be made at least " + 
                    Booking.MIN_ADVANCE_BOOKING_DAYS + " days in advance.");
        }

        if (newEndDate.isBefore(newStartDate) || newEndDate.equals(newStartDate)) {
            throw new Exception("End date must be after start date.");
        }

        booking.setStartDate(newStartDate);
        booking.setEndDate(newEndDate);
        booking.setTotalKilometers(newTotalKilometers);

        return true;
    }
}
