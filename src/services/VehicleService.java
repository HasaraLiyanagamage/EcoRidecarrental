package services;

import models.Vehicle;
import enums.AvailabilityStatus;
import enums.VehicleCategory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing vehicle operations
 */
public class VehicleService {
    private List<Vehicle> vehicles;

    public VehicleService() {
        this.vehicles = new ArrayList<>();
        initializeSampleVehicles();
    }

    private void initializeSampleVehicles() {
        // Add sample vehicles
        addVehicle(new Vehicle("V001", "Toyota Aqua", VehicleCategory.COMPACT_PETROL));
        addVehicle(new Vehicle("V002", "Honda Fit", VehicleCategory.COMPACT_PETROL));
        addVehicle(new Vehicle("V003", "Toyota Prius", VehicleCategory.HYBRID));
        addVehicle(new Vehicle("V004", "Honda Insight", VehicleCategory.HYBRID));
        addVehicle(new Vehicle("V005", "Nissan Leaf", VehicleCategory.ELECTRIC));
        addVehicle(new Vehicle("V006", "Tesla Model 3", VehicleCategory.ELECTRIC));
        addVehicle(new Vehicle("V007", "BMW X5", VehicleCategory.LUXURY_SUV));
        addVehicle(new Vehicle("V008", "Mercedes GLE", VehicleCategory.LUXURY_SUV));
    }

    public void addVehicle(Vehicle vehicle) {
        if (findVehicleById(vehicle.getCarId()).isPresent()) {
            throw new IllegalArgumentException("Vehicle with ID " + vehicle.getCarId() + " already exists.");
        }
        vehicles.add(vehicle);
    }

    public boolean updateVehicle(String carId, String model, VehicleCategory category) {
        Optional<Vehicle> vehicleOpt = findVehicleById(carId);
        if (vehicleOpt.isPresent()) {
            Vehicle vehicle = vehicleOpt.get();
            vehicle.setModel(model);
            vehicle.setCategory(category);
            return true;
        }
        return false;
    }

    public boolean removeVehicle(String carId) {
        return vehicles.removeIf(v -> v.getCarId().equals(carId));
    }

    public Optional<Vehicle> findVehicleById(String carId) {
        return vehicles.stream()
                .filter(v -> v.getCarId().equals(carId))
                .findFirst();
    }

    public List<Vehicle> getAllVehicles() {
        return new ArrayList<>(vehicles);
    }

    public List<Vehicle> getAvailableVehicles() {
        return vehicles.stream()
                .filter(v -> v.getAvailabilityStatus() == AvailabilityStatus.AVAILABLE)
                .collect(Collectors.toList());
    }

    public List<Vehicle> getVehiclesByCategory(VehicleCategory category) {
        return vehicles.stream()
                .filter(v -> v.getCategory() == category)
                .collect(Collectors.toList());
    }

    public boolean updateAvailabilityStatus(String carId, AvailabilityStatus status) {
        Optional<Vehicle> vehicleOpt = findVehicleById(carId);
        if (vehicleOpt.isPresent()) {
            vehicleOpt.get().setAvailabilityStatus(status);
            return true;
        }
        return false;
    }
}
