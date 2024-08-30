package com.ratecard.invoice.services;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.ratecard.invoice.model.Invoice;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PdfService {

    public byte[] generatePdf(Invoice invoice) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        document.add(new Paragraph("Invoice Details:")
                .setFontSize(18)
                .setBold());

        document.add(new Paragraph("Consumer Name: " + invoice.getConsumerName()));
        document.add(new Paragraph("Consumer Number: " + invoice.getConsumerNumber()));
        document.add(new Paragraph("Address: " + invoice.getAddress()));
        document.add(new Paragraph("Invoice Date: " + invoice.getInvoiceDate()));
        document.add(new Paragraph("Consumer Email: " + invoice.getConsumerEmail()));
        document.add(new Paragraph("Contact No: " + invoice.getContactNo()));
        document.add(new Paragraph("Town: " + invoice.getTown()));
        document.add(new Paragraph("City: " + invoice.getCity()));
        document.add(new Paragraph("Sub District: " + invoice.getSubDistrict()));
        document.add(new Paragraph("District: " + invoice.getDistrict()));
        document.add(new Paragraph("State: " + invoice.getState()));
        document.add(new Paragraph("Zip Code: " + invoice.getZipCode()));
        document.add(new Paragraph("Connection Type: " + invoice.getConnectionType()));
        document.add(new Paragraph("Installation Space: " + invoice.getInstallationSpace()));
        document.add(new Paragraph("Installation Type: " + invoice.getInstallationType()));
        document.add(new Paragraph("Installation Size: " + invoice.getInstallationSize()));

        document.close();
        return baos.toByteArray();
    }
}
