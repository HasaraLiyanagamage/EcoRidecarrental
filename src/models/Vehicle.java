package models;

import enums.AvailabilityStatus;
import enums.VehicleCategory;

/**
 * Vehicle class representing a rental vehicle
 */
public class Vehicle {
    private String carId;
    private String model;
    private VehicleCategory category;
    private AvailabilityStatus availabilityStatus;

    public Vehicle(String carId, String model, VehicleCategory category) {
        this.carId = carId;
        this.model = model;
        this.category = category;
        this.availabilityStatus = AvailabilityStatus.AVAILABLE;
    }

    // Getters and Setters
    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public VehicleCategory getCategory() {
        return category;
    }

    public void setCategory(VehicleCategory category) {
        this.category = category;
    }

    public AvailabilityStatus getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(AvailabilityStatus availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public double getDailyRentalPrice() {
        return category.getDailyRentalFee();
    }

    @Override
    public String toString() {
        return String.format("Car ID: %s | Model: %s | Category: %s | Price: LKR %.2f/day | Status: %s",
                carId, model, category.getDisplayName(), 
            getDailyRentalPrice(), availabilityStatus.getDisplayName());
    }
}
