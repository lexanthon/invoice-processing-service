package io.github.lexanthon.invoiceparser.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.lexanthon.invoiceparser.model.AllowanceCharge;




@Repository
public interface AllowanceChargeRepo extends JpaRepository<AllowanceCharge,String> {
	

}
