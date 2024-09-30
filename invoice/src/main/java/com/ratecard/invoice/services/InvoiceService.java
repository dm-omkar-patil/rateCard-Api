package com.ratecard.invoice.services;

import com.ratecard.invoice.DTO.InvoiceRequest;
import com.ratecard.invoice.model.Invoice;
import com.ratecard.invoice.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public Invoice generateInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Optional<Invoice> getInvoiceByConsumerNumber(long consumerNumber) {
        return invoiceRepository.findByConsumerNumber(consumerNumber);
    }
}
