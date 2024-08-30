package com.ratecard.invoice.services;

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

    public Invoice generateInvoice(String consumerName, Long consumerNumber, String address,
                                   String consumerEmail, String contactNo, String town, String city,
                                   String subDistrict, String district, String state, String zipCode,
                                   String connectionType, String installationSpace, String installationType,
                                   String installationSize) {
        Invoice invoice = new Invoice();
        invoice.setConsumerName(consumerName);
        invoice.setConsumerNumber(consumerNumber);
        invoice.setAddress(address);
        invoice.setInvoiceDate(LocalDate.now());

        invoice.setConsumerEmail(consumerEmail);
        invoice.setContactNo(contactNo);
        invoice.setTown(town);
        invoice.setCity(city);
        invoice.setSubDistrict(subDistrict);
        invoice.setDistrict(district);
        invoice.setState(state);
        invoice.setZipCode(zipCode);
        invoice.setConnectionType(connectionType);
        invoice.setInstallationSpace(installationSpace);
        invoice.setInstallationType(installationType);
        invoice.setInstallationSize(installationSize);

        return invoiceRepository.save(invoice);
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    // New method to retrieve an invoice by consumer ID
//    public Invoice getInvoiceByConsumerNumber(Long consumerNumber) {
//        return invoiceRepository.findByConsumerNumber(consumerNumber);
//    }

    public Optional<Invoice> getInvoiceByConsumerNumber(long consumerNumber) {
        return invoiceRepository.findByConsumerNumber(consumerNumber);
    }
}
