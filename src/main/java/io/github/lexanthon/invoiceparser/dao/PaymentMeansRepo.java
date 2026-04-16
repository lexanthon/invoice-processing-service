package io.github.lexanthon.invoiceparser.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.lexanthon.invoiceparser.model.PaymentMeans;




@Repository
public interface PaymentMeansRepo extends JpaRepository<PaymentMeans,String> {
	

}
