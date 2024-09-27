package com.ratecard.invoice.controllers;

import org.springframework.ui.Model;
import com.ratecard.invoice.DTO.InvoiceRequest;
import com.ratecard.invoice.model.Invoice;
import com.ratecard.invoice.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.spring6.SpringTemplateEngine;

import org.thymeleaf.context.Context;

import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    //For Invoice Service
    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private SpringTemplateEngine templateEngine;

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


    @GetMapping("/invoice/pdf/{consumerNumber}")
    public ResponseEntity<InputStreamResource> generateInvoicePdf(@PathVariable long consumerNumber, Model model) throws IOException {
        Optional<Invoice> optionalInvoice = invoiceService.getInvoiceByConsumerNumber(consumerNumber);
        if (optionalInvoice.isPresent()) {
            Invoice invoice = optionalInvoice.get();
            model.addAttribute("invoice", invoice);

            Context context = new Context();
            context.setVariable("invoice", invoice);

            String htmlContent = templateEngine.process("invoice", context);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();

            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream);

            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=invoice_" + consumerNumber + ".pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(inputStream));
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}
