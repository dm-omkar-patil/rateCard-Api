package com.ratecard.invoice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String consumerName;
    @Column(unique = true, nullable = false)
    private long consumerNumber;
    private String address;
    private LocalDate invoiceDate;

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

    @PrePersist
    protected void onUpdate() {
        System.out.println(LocalDate.now());
        this.invoiceDate = LocalDate.now();
         // Automatically set to the current date
    }

}
