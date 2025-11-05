package services;

import models.Customer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * Service class for managing customer operations
 */
public class CustomerService {
    private List<Customer> customers;
    private int customerCounter;

    public CustomerService() {
        this.customers = new ArrayList<>();
        this.customerCounter = 1;
    }
    public Customer registerCustomer(String nicOrPassport, String name, 
                                    String contactNumber, String email) {
        // Check if customer already exists
        Optional<Customer> existing = findCustomerByNicOrPassport(nicOrPassport);
        if (existing.isPresent()) {
            return existing.get();
        }

        String customerId = generateCustomerId();
        Customer customer = new Customer(customerId, nicOrPassport, name, contactNumber, email);
        customers.add(customer);
        return customer;
    }

    private String generateCustomerId() {
        return String.format("C%04d", customerCounter++);
    }

    public Optional<Customer> findCustomerById(String customerId) {
        return customers.stream()
                .filter(c -> c.getCustomerId().equals(customerId))
                .findFirst();
    }

    public Optional<Customer> findCustomerByNicOrPassport(String nicOrPassport) {
        return customers.stream()
                .filter(c -> c.getNicOrPassport().equalsIgnoreCase(nicOrPassport))
                .findFirst();
    }
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers);
    }
    public boolean updateCustomer(String customerId, String name, String contactNumber, String email) {
        Optional<Customer> customerOpt = findCustomerById(customerId);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            customer.setName(name);
            customer.setContactNumber(contactNumber);
            customer.setEmail(email);
            return true;
        }
        return false;
    }
}
