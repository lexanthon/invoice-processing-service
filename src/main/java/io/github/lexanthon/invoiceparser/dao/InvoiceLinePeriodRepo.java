package io.github.lexanthon.invoiceparser.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.lexanthon.invoiceparser.model.InvoiceLinePeriod;




@Repository
public interface InvoiceLinePeriodRepo extends JpaRepository<InvoiceLinePeriod,String> {
	

}








