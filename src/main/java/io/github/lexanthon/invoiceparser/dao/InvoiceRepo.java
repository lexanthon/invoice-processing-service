package io.github.lexanthon.invoiceparser.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.lexanthon.invoiceparser.model.Invoice;



@Repository
public interface InvoiceRepo extends JpaRepository<Invoice,String> {

	boolean existsByInvoiceFileId(Long invoiceFileId);
}
