package com.ratecard.invoice.repositories;

import com.ratecard.invoice.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long>{
    Invoice findByConsumerNumber(Long consumerNumber);
}
