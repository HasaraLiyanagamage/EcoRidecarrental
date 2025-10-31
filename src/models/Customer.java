package models;

/**
 * Customer class representing a rental customer
 */
public class Customer {
    private String customerId;
    private String nicOrPassport;
    private String name;
    private String contactNumber;
    private String email;

    public Customer(String customerId, String nicOrPassport, String name, 
                   String contactNumber, String email) {
        this.customerId = customerId;
        this.nicOrPassport = nicOrPassport;
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    // Getters and Setters
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getNicOrPassport() {
        return nicOrPassport;
    }

    public void setNicOrPassport(String nicOrPassport) {
        this.nicOrPassport = nicOrPassport;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("Customer ID: %s | Name: %s | NIC/Passport: %s | Contact: %s | Email: %s",
                customerId, name, nicOrPassport, contactNumber, email);
    }
}
