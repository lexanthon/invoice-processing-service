package io.github.lexanthon.invoiceparser.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.lexanthon.invoiceparser.model.InvoiceLineAllowanceCharge;




@Repository
public interface InvoiceLineAllowanceChargeRepo extends JpaRepository<InvoiceLineAllowanceCharge,String> {
	

}








