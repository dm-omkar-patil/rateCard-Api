package com.ratecard.invoice.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceRequest {
    private String consumerName;
    private Long consumerNumber;
    private String address;
    private String consumerEmail;
    private String contactNo;
    private String town;
    private String city;
    private String subDistrict;
    private String district;
    private String state;
    private String zipCode;
    private String connectionType;
    private String installationSpace;
    private String installationType;
    private String installationSize;

    // Getters and setters
}

