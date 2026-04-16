package io.github.lexanthon.invoiceparser.model;

import java.io.Serializable;
import java.math.BigDecimal;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class TaxTotal  implements Serializable{	
	private static final long serialVersionUID = 1L;
		
	@Id
	@GeneratedValue(
		    strategy= GenerationType.AUTO, 
		    generator="native"
		)//for dont create the extra hibernate table for auto increments in db
		@GenericGenerator(
		    name = "native", 
		    strategy = "native"
		)
		@Column
		private Long rowId;
		private Long invoiceId;
		@Column(precision = 15, scale = 2)
		private BigDecimal vatAmount;
		private String vatAmmountCurrencyId;
		
		public TaxTotal(Long invoiceId, BigDecimal vatAmount, String vatAmmountCurrencyId) {
			super();
			this.invoiceId = invoiceId;
			this.vatAmount = vatAmount;
			this.vatAmmountCurrencyId = vatAmmountCurrencyId;
		}
		public Long getRowId() {
			return rowId;
		}
		public void setRowId(Long rowId) {
			this.rowId = rowId;
		}
		public Long getInvoiceId() {
			return invoiceId;
		}
		public void setInvoiceId(Long invoiceId) {
			this.invoiceId = invoiceId;
		}
		public BigDecimal getVatAmount() {
			return vatAmount;
		}
		public void setVatAmount(BigDecimal vatAmount) {
			this.vatAmount = vatAmount;
		}
		public String getVatAmmountCurrencyId() {
			return vatAmmountCurrencyId;
		}
		public void setVatAmmountCurrencyId(String vatAmmountCurrencyId) {
			this.vatAmmountCurrencyId = vatAmmountCurrencyId;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		@Override
		public String toString() {
			return "TaxTotal [rowId=" + rowId + ", invoiceId=" + invoiceId + ", vatAmount=" + vatAmount
					+ ", vatAmmountCurrencyId=" + vatAmmountCurrencyId + "]";
		}
		
		

}
