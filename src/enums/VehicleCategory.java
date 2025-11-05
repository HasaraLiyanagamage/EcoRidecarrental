package enums;
/**
 * vehicle categories with their pricing details
 */
public enum VehicleCategory {
    COMPACT_PETROL("Compact Petrol Car", 5000.0, 100, 50.0, 0.10),
    HYBRID("Hybrid Car", 7500.0, 150, 60.0, 0.12),
    ELECTRIC("Electric Car", 10000.0, 200, 40.0, 0.08),
    LUXURY_SUV("Luxury SUV", 15000.0, 250, 75.0, 0.15);

    private final String displayName;
    private final double dailyRentalFee;
    private final int freeKmPerDay;
    private final double extraKmCharge;
    private final double taxRate;

    VehicleCategory(String displayName, double dailyRentalFee, int freeKmPerDay, 
                    double extraKmCharge, double taxRate) {
        this.displayName = displayName;
        this.dailyRentalFee = dailyRentalFee;
        this.freeKmPerDay = freeKmPerDay;
        this.extraKmCharge = extraKmCharge;
        this.taxRate = taxRate;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getDailyRentalFee() {
        return dailyRentalFee;
    }

    public int getFreeKmPerDay() {
        return freeKmPerDay;
    }

    public double getExtraKmCharge() {
        return extraKmCharge;
    }

    public double getTaxRate() {
        return taxRate;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
