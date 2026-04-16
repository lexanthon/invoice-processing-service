package io.github.lexanthon.invoiceparser.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import io.github.lexanthon.invoiceparser.model.Invoice;
import io.github.lexanthon.invoiceparser.model.InvoiceFile;


@Repository
public interface ParserRepo extends JpaRepository<InvoiceFile,String> {
	List<InvoiceFile> findInvoiceFilesByInvoiceType(String invoiceType);
	List<InvoiceFile> findInvoicesByStatus(int status);
	InvoiceFile findTopByOrderByDateReceived();
	
	 @Query(value = "SELECT * FROM invoice_file WHERE invoice_type = ?1 AND ID=?2", nativeQuery = true)
	List<InvoiceFile> findByCustom(String invoiceType, long id);
}
