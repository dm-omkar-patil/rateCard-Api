package com.ratecard.invoice.controllers;

import com.ratecard.invoice.DTO.InvoiceRequest;
import com.ratecard.invoice.model.Invoice;
import com.ratecard.invoice.services.InvoiceService;
import com.ratecard.invoice.services.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    //For Invoice Service
    @Autowired
    private InvoiceService invoiceService;

    //For pdf service
    @Autowired
    private PdfService pdfService;

    //For Invoice Consumer by consumer number

    //To generate or add the data
    @PostMapping("/generate")
    public ResponseEntity<Invoice> generateInvoice(@RequestBody InvoiceRequest request) {
        Invoice invoice = invoiceService.generateInvoice(
                request.getConsumerName(),
                request.getConsumerNumber(),
                request.getAddress(),
                request.getConsumerEmail(),
                request.getContactNo(),
                request.getTown(),
                request.getCity(),
                request.getSubDistrict(),
                request.getDistrict(),
                request.getState(),
                request.getZipCode(),
                request.getConnectionType(),
                request.getInstallationSpace(),
                request.getInstallationType(),
                request.getInstallationSize()
        );
        return new ResponseEntity<>(invoice, HttpStatus.CREATED);
    }

    //for all data
    @GetMapping
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        List<Invoice> invoices = invoiceService.getAllInvoices();
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    //for generating pdf
    @GetMapping("/generate-pdf")
    public ResponseEntity<ByteArrayResource> generateInvoicePdf(
            @RequestParam String consumerName,
            @RequestParam Long consumerNumber,
            @RequestParam String address,
            @RequestParam String consumerEmail,
            @RequestParam String contactNo,
            @RequestParam String town,
            @RequestParam String city,
            @RequestParam String subDistrict,
            @RequestParam String district,
            @RequestParam String state,
            @RequestParam String zipCode,
            @RequestParam String connectionType,
            @RequestParam String installationSpace,
            @RequestParam String installationType,
            @RequestParam String installationSize
    ) throws IOException {
        // Generate the invoice
        Invoice invoice = invoiceService.generateInvoice(
                consumerName, consumerNumber, address, consumerEmail, contactNo, town, city,
                subDistrict, district, state, zipCode, connectionType, installationSpace, installationType, installationSize
        );

        // Generate PDF from the invoice
        byte[] pdfBytes = pdfService.generatePdf(invoice);

        // Prepare the response
        ByteArrayResource resource = new ByteArrayResource(pdfBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice.pdf");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    // New method to retrieve an invoice by consumer number
    @GetMapping("/{consumerNumber}")
    public ResponseEntity<Invoice> getInvoiceByConsumerNumber(@PathVariable Long consumerNumber) {
        Invoice invoice = invoiceService.getInvoiceByConsumerNumber(consumerNumber);
        if (invoice != null) {
            return new ResponseEntity<>(invoice, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
