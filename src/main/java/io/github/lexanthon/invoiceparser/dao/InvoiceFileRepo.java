package io.github.lexanthon.invoiceparser.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.github.lexanthon.invoiceparser.model.InvoiceFile;


@Repository
public interface InvoiceFileRepo extends JpaRepository<InvoiceFile,String> {

	List<InvoiceFile> findInvoicesByStatus(int status);

	@Modifying
	@Transactional
	@Query(value = "UPDATE invoice_file SET status = ?2 WHERE id = ?1 AND status = ?3", nativeQuery = true)
	int updateStatusIfCurrent(long id, int newStatus, int expectedStatus);

	@Modifying
	@Transactional
	@Query(value = "UPDATE invoice_file SET status = ?2, failure_reason = NULL WHERE id = ?1 AND status = ?3", nativeQuery = true)
	int markParsedIfCurrent(long id, int newStatus, int expectedStatus);

	@Modifying
	@Transactional
	@Query(value = "UPDATE invoice_file SET status = ?2, retries_count = COALESCE(retries_count, 0) + 1, failure_reason = ?4 WHERE id = ?1 AND status = ?3", nativeQuery = true)
	int markFailedIfCurrent(long id, int newStatus, int expectedStatus, String failureReason);
	
	 @Query(value = "SELECT * FROM invoice_file WHERE ID=?1", nativeQuery = true)
	InvoiceFile findInvoiceByInvoiceId(long id);

}
