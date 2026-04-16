package io.github.lexanthon.invoiceparser.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.lexanthon.invoiceparser.model.TaxSubtotal;




@Repository
public interface TaxSubtotalRepo extends JpaRepository<TaxSubtotal,String> {
	

}
