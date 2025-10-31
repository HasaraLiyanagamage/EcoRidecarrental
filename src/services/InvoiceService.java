package services;

import models.Booking;
import models.Invoice;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing invoice operations
 */
public class InvoiceService {
    private List<Invoice> invoices;
    private int invoiceCounter;

    public InvoiceService() {
        this.invoices = new ArrayList<>();
        this.invoiceCounter = 1;
    }

    public Invoice generateInvoice(Booking booking) {
        String invoiceId = generateInvoiceId();
        Invoice invoice = new Invoice(invoiceId, booking);
        invoices.add(invoice);
        return invoice;
    }

    private String generateInvoiceId() {
        return String.format("INV%04d", invoiceCounter++);
    }

    public Optional<Invoice> findInvoiceById(String invoiceId) {
        return invoices.stream()
                .filter(i -> i.getInvoiceId().equals(invoiceId))
                .findFirst();
    }

    public Optional<Invoice> findInvoiceByBookingId(String bookingId) {
        return invoices.stream()
                .filter(i -> i.getBooking().getBookingId().equals(bookingId))
                .findFirst();
    }

    public List<Invoice> getAllInvoices() {
        return new ArrayList<>(invoices);
    }
}
